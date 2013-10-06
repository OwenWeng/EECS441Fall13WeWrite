package com.example.wewritebeta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.wewritebeta.Event.ChangeType;

import edu.umich.imlc.collabrify.client.CollabrifyClient;
import edu.umich.imlc.collabrify.client.exceptions.CollabrifyException;

public class MainActivity extends Activity {

  // variables for layout
  public Button createSessionButton;
  public Button joinSeesionButton;
  public Button leaveSessionButton;
  public CheckBox baseFile;
  
  //variables for Collabrify 
  public final String TAG = "WeWrite";
  public CollabrifyClient myClient;
  public long sessionId;
  public String sessionName;
  public ArrayList<String> tags = new ArrayList<String>();
  public ByteArrayInputStream baseFileBuffer;
  public ByteArrayOutputStream baseFileReceiveBuffer;
  public String broadcastedText;
  public boolean inSession;
  
  //0 in the default state, negative if events have been coming from other users, 
  //  and positive if coming from this user. 
  public int eventCombo;
  
  
  //listener variables
  public Listener listen;
  public boolean suppress;
  public EditTextCursorWatcher messageText;
  
  //Global and local queue/stacks
  public CharSequence storeSavedGlobalState = "";
  public Vector<Event> totalGlobalEventState = new Vector<Event>();
  public Queue<Event> localChanges = new LinkedList<Event>();
  public Queue<Event> tempGlobalEvent = new LinkedList<Event>();
  public Stack<Event> redoEventStack = new Stack<Event>();
  
  
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    
    
    
    
    eventCombo = 0;
     
    
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    createSessionButton = (Button) findViewById(R.id.createSessionButton);
    joinSeesionButton = (Button) findViewById(R.id.joinSessionButton);
    leaveSessionButton = (Button) findViewById(R.id.leaveSessionButton);
    baseFile = (CheckBox) findViewById(R.id.baseFile);
    
    //instantiate listener and EditTextCursorWatcher
    listen = new Listener(this);
    listen.unsuppress();
    messageText = (EditTextCursorWatcher) findViewById(R.id.edit_message);
    messageText.setMainActivity(this);
    suppress = false;
    
    tags.add("team23");
    
    // Instantiate client object
    inSession = false;
    boolean getLatestEvent = false;
    try
    {
      myClient = new CollabrifyClient(this, "user email", "user display name",
          "441fall2013@umich.edu", "XY3721425NoScOpE", getLatestEvent,
          new WeWriteCollabrifyAdapter(this));
    }
    catch(CollabrifyException e)
    {
      e.printStackTrace();
    }
    
    createSessionButton.setOnClickListener(new CreateListener(this));
    joinSeesionButton.setOnClickListener(new JoinListener(this)); 
    leaveSessionButton.setOnClickListener(new LeaveListener(this)); 
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) 
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  /*@Override
  public boolean onOptionsItemSelected(MenuItem item) 
  {
    switch(item.getItemId())
    {
      case R.id.action_undo:
    }
    
  }*/
  
  public void generateEvent(CharSequence changeText, CharSequence wholeText, 
      int begin, int end, int cursorPos, ChangeType type) //return type Event
  {
    if(inSession)
    {
      Event event = new Event(changeText, wholeText, 
            begin, end, cursorPos, type);
      
      localChanges.add(event);
      
      final byte[] b = event.serializeEvent();
      CharSequence test = "type: " + event.getType().toString() +  "start: " + Integer.toString(event.getStart()) 
          + "end: " + Integer.toString(event.getEnd()) + "text" + event.getMessage();
      Log.d("Broadcast Event", "Event: " + test);
     
      try
      {
        myClient.broadcast(b, "extra message");
      }
      catch(CollabrifyException e)
      {
        Log.e("Collabrify Exception", e.getMessage());
      } 
    }
  }
  
  
  
  public Editable applyEvent(Event e, Editable currentText)
  {
    int start = e.getStart();
    int end = e.getEnd();
    int deletionLength = start - end + 1;
    CharSequence newText = e.getMessage();
    
    if(e.getType() == Event.ChangeType.CURSORMOVE)
    {
      messageText.setSelection(e.getCursor());
    }
    else if(e.getType() == Event.ChangeType.INSERT)
    {
      if(start < currentText.length())
      {
        try
        {
          currentText.insert(start, newText);
        }
        catch(Exception ex)
        {
          Log.e("Insert Error", ex.getMessage());
        }
      }
      else
      {
        try
        {
          currentText.append(newText);
        }
        catch(Exception ex)
        {
          Log.e("Insert Error", ex.getMessage());
        }
      }
      
    }
    else if(e.getType() == Event.ChangeType.DELETE)
    {
      if(start > currentText.length())
      {
        if(deletionLength >= currentText.length())
        {
          currentText.clear(); 
        }
        else
        {
          end = start - deletionLength +1;
          start = currentText.length();
          currentText.delete(end, start);
        } 
      }
      else
      {
        try
        {
          currentText.delete(end, start);
        }
        catch(Exception ex)
        {
          Log.e("Delete Error", ex.getMessage());
        }
      }
    }
    
    return currentText;
    
  }
  
  public void receivedEvent(long orderId, int subId, Event e)
  {
    
    CharSequence test = "type: " + e.getType().toString() +  "start: " + Integer.toString(e.getStart()) 
        + "end: " + Integer.toString(e.getEnd()) + "text" + e.getMessage();
    Log.d("Receive Event", "Event: " + test + " subId: " + Integer.toString(subId));
    Log.d("Receive Event", "eventCombo: " + Integer.toString(eventCombo));
   
    e.setglobalID(orderId);
    tempGlobalEvent.add(e);
    if(subId != -1)
    {
      localChanges.poll();
    }
    
    //If( this user just submitted AND other users have been submitting 
    //    OR other user submitted AND this user has no pending local changes
    //    OR other user just submitted AND this user has been submitting)
    //    THEN apply changes in the queue and update UI
    if((subId != -1 && eventCombo < 0) || (subId == -1 && localChanges.isEmpty()) || (subId == -1 && eventCombo > 0)  ) 
    {
      Editable textHolder = new SpannableStringBuilder(storeSavedGlobalState);
      for (Event event : tempGlobalEvent) 
      {
        event.setSavedState(textHolder.toString());
        textHolder = applyEvent(event, textHolder);
        totalGlobalEventState.add(event);
      }
      
      tempGlobalEvent.clear();
      storeSavedGlobalState = textHolder;
      
      if(!localChanges.isEmpty())
      {
        for (Event event : localChanges) 
        {
          textHolder = applyEvent(event, textHolder);
        }
      }
      //to appease the compiler for runOnUiThread, the text must be a final variable;
      final String textFinal = textHolder.toString(); 
      int currentCursorPos = messageText.getSelectionStart();
      
      if(currentCursorPos > textFinal.length())
      {
        currentCursorPos = textFinal.length();
      }
      final int cursorFinal = currentCursorPos;
      runOnUiThread(new Runnable() {
        @Override
        public void run() 
        {
          listen.suppress();
          listen.flush();
          messageText.setText(textFinal);
          messageText.setSelection(cursorFinal);
          listen.unsuppress();
        }
      });
      
      eventCombo = 0;
    }
    else if(subId == -1)
    {
      eventCombo--;
    }
    else
    {
      eventCombo++;
    }
  }
  
  public void undo(long orderID)
  {
    int removeIndex = 0, replaceIndex = 0;
    boolean startUpdating = false;
    Editable textHolder = new SpannableStringBuilder("");;
    for (Event event : totalGlobalEventState) 
    {
      if(startUpdating)
      {
        replaceIndex = totalGlobalEventState.indexOf(event);
        event.setSavedState(textHolder.toString());
        textHolder = applyEvent(event, textHolder);
        totalGlobalEventState.set(replaceIndex, event);
      }
      if((event.getGlobalID() == orderID) && !startUpdating)
      {
        redoEventStack.push(event);
        removeIndex = totalGlobalEventState.indexOf(event);
        startUpdating = true;
        textHolder = new SpannableStringBuilder(event.getSavedState()); 
      }
      
    }
    totalGlobalEventState.removeElementAt(removeIndex);
    
    final String textFinal = textHolder.toString(); 
    int currentCursorPos = messageText.getSelectionStart();
    if(currentCursorPos > textFinal.length())
    {
      currentCursorPos = textFinal.length();
    }
    final int cursorFinal = currentCursorPos;
    runOnUiThread(new Runnable() {
      @Override
      public void run() 
      {
        listen.suppress();
        listen.flush();
        messageText.setText(textFinal);
        messageText.setSelection(cursorFinal);
        listen.unsuppress();
      }
    });
  }
  
  public void redo(Event e)
  {
    int addIndex = 0, replaceIndex = 0;
    boolean startUpdating = false;
    Editable textHolder = new SpannableStringBuilder("");;
    for (Event event : totalGlobalEventState) 
    {
      if(startUpdating)
      {
        replaceIndex = totalGlobalEventState.indexOf(event);
        event.setSavedState(textHolder.toString());
        textHolder = applyEvent(event, textHolder);
        totalGlobalEventState.set(replaceIndex, event);
      }
      if( (event.getGlobalID() > e.getGlobalID()) && !startUpdating)
      {
        startUpdating = true;
        addIndex = totalGlobalEventState.indexOf(event);
        replaceIndex = addIndex;
        e.setSavedState(event.getSavedState());
        textHolder = new SpannableStringBuilder(event.getSavedState());
        textHolder = applyEvent(e, textHolder);
        event.setSavedState(textHolder.toString());
        textHolder = applyEvent(event, textHolder);
        totalGlobalEventState.set(replaceIndex, event);
      }
      
    }
    totalGlobalEventState.add(addIndex, e);
    
    final String textFinal = textHolder.toString(); 
    int currentCursorPos = messageText.getSelectionStart();
    if(currentCursorPos > textFinal.length())
    {
      currentCursorPos = textFinal.length();
    }
    final int cursorFinal = currentCursorPos;
    runOnUiThread(new Runnable() {
      @Override
      public void run() 
      {
        listen.suppress();
        listen.flush();
        messageText.setText(textFinal);
        messageText.setSelection(cursorFinal);
        listen.unsuppress();
      }
    });
  }
}


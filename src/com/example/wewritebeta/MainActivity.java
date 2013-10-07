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
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
  
  //Interface Update variables
  public Handler timer = new Handler();
  public int timerLength = 10000;
  public Runnable runUpdateInterface = new Runnable() 
  {
    @Override
    public void run() 
    {
      if(!tempGlobalEvent.isEmpty())
      {
        updateInterface();
      }
      timer.postDelayed(runUpdateInterface, timerLength);
    }
  }; 
  
  
  //listener variables
  public Listener listen;
  public EditTextCursorWatcher messageText;
  
  //Global and local queue/stacks
  public CharSequence storeSavedGlobalState = "";
  public Vector<Event> totalGlobalEventState = new Vector<Event>();
  public Queue<Event> localChanges = new LinkedList<Event>();
  public Queue<Event> tempGlobalEvent = new LinkedList<Event>();
  public Stack<Event> redoEventStack = new Stack<Event>();
  public Stack<Event> undoEventStack = new Stack<Event>();
  
  
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    
    
    
   
    
    eventCombo = 0;
     
    
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    createSessionButton = (Button) findViewById(R.id.createSessionButton);
    joinSeesionButton = (Button) findViewById(R.id.joinSessionButton);
    leaveSessionButton = (Button) findViewById(R.id.leaveSessionButton);
    //baseFile = (CheckBox) findViewById(R.id.baseFile);
    
    //instantiate listener and EditTextCursorWatcher
    listen = new Listener(this);
    listen.unsuppress();
    messageText = (EditTextCursorWatcher) findViewById(R.id.edit_message);
    messageText.setMainActivity(this);
    
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
  
  
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch ( item.getItemId() )
    {
      case R.id.action_undo:

        if( undoEventStack.isEmpty() )
        {
          Log.e("MYMYundo", "The undo stack is empty");
        }
        else
        {
          Event event = undoEventStack.pop();
          final byte[] e = event.serializeEvent();
          try
          {
            myClient.broadcast(e, "extra message");
          }
          catch( CollabrifyException ex )
          {
            Log.e("Collabrify Exception", ex.getMessage());
          }
        }


        return true;
      case R.id.action_redo:

        if( redoEventStack.isEmpty() ) {
          Log.e("MYMYredo", "The redo stack empty");
        }
        else
        {
          Event event = redoEventStack.pop();
          final byte[] e = event.serializeEvent();
          try
          {
            myClient.broadcast(e, "extra message");
          }
          catch( CollabrifyException ex )
          {
            Log.e("Collabrify Exception", ex.getMessage());
          } 
        }

        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
  
  public void generateEvent(CharSequence changeText, CharSequence wholeText, 
      int begin, int end, int cursorPos, ChangeType type) 
  {
    if(inSession)
    {
      Event event = new Event(changeText, wholeText, 
            begin, end, cursorPos, type);
      
      localChanges.add(event);
      
      final byte[] b = event.serializeEvent();
      CharSequence test = "type: " + event.getType().toString() +  "start: " + Integer.toString(event.getStart()) 
          + "end: " + Integer.toString(event.getEnd()) + " text: " + event.getMessage();
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
          start = currentText.length();
          end = start - deletionLength +1;
          
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
        + "end: " + Integer.toString(e.getEnd()) + " text: " + e.getMessage();
    Log.d("Receive Event", "Event: " + test + " subId: " + Integer.toString(subId));
    Log.d("Receive Event", "eventCombo: " + Integer.toString(eventCombo));
    Log.d("Receive Event", "localChangesEmptyBefore: " + localChanges.isEmpty());
    Log.d("Receive Event", "saved state: " + storeSavedGlobalState);
    
    
    
    
    
    if( e.getType() == Event.ChangeType.UNDO )
    {
      undo(e.getGlobalID());
      if( subId != -1 )
      {
        Event evRedo = new Event(e.getMessage(), "", e.getStart(), e.getEnd(),
            0, Event.ChangeType.REDO);
        evRedo.setglobalID(e.getGlobalID());
        redoEventStack.push(evRedo);
      }
      return;
    }
    if( e.getType() == Event.ChangeType.REDO )
    {
      Event.ChangeType tempType;
      if( e.getStart() < e.getEnd() )
      {
        tempType = Event.ChangeType.INSERT;
      }
      else
      {
        tempType = Event.ChangeType.DELETE;
      }
      Event evNew = new Event(e.getMessage(), "", e.getStart(), e.getEnd(), 0,
          tempType);
      evNew.setglobalID(e.getGlobalID());
      redo(evNew);
      if(subId != -1)
      {
        evNew.setType(Event.ChangeType.UNDO);
        undoEventStack.push(evNew);
      }
      return;
    }
    
    e.setglobalID(orderId);
    tempGlobalEvent.add(e);
    if( subId != -1 )
    {
      localChanges.poll();
      Event evUndo = new Event(e.getMessage(), "", e.getStart(), e.getEnd(), 0,
          Event.ChangeType.UNDO);
      evUndo.setglobalID(orderId);
      undoEventStack.push(evUndo);
    }
    
    
    
    Log.d("Receive Event", "localChangesEmptyAfter: " + localChanges.isEmpty());
    //If( this user just submitted AND other users have been submitting 
    //    OR other user submitted AND this user has no pending local changes
    //    OR other user just submitted AND this user has been submitting)
    //    THEN apply changes in the queue and update UI
    if((subId != -1 && eventCombo < 0) || (subId == -1 && localChanges.isEmpty()) || (subId == -1 && eventCombo > 0)  ) 
    { 
      timer.removeCallbacks(runUpdateInterface);
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
          Log.d("Receive Event", "textFinalBefore: " + textFinal);
          listen.flush();
          Log.d("Receive Event", "textFinalAfter: " + textFinal);
          messageText.setText(textFinal);
          messageText.setSelection(cursorFinal);
          listen.unsuppress();
        }
      });
      
      eventCombo = 0;
      timer.postDelayed(runUpdateInterface, timerLength);
    }
    else if(subId == -1)
    {
      eventCombo--;
    }
    else
    {
      eventCombo++;
    }
    Log.d("Receive Event", "changedTo: " + messageText.getText().toString());
  }
  
  public void undo(long orderID)
  {
 // Move tempGlobal to totalGlobal
    Editable textHolder = new SpannableStringBuilder(storeSavedGlobalState);
    for( Event event : tempGlobalEvent )
    {
      event.setSavedState(textHolder.toString());
      textHolder = applyEvent(event, textHolder);
      totalGlobalEventState.add(event);
    }
    tempGlobalEvent.clear();
    
    int removeIndex = 0, replaceIndex = 0;
    boolean startUpdating = false;
    
 // Update totalGlobal
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
        removeIndex = totalGlobalEventState.indexOf(event);
        startUpdating = true;
        textHolder = new SpannableStringBuilder(event.getSavedState()); 
      }
    }

 // Apply local changes
    if( !localChanges.isEmpty() )
    {
      for( Event event : localChanges )
      {
        textHolder = applyEvent(event, textHolder);
      }
    }
    totalGlobalEventState.removeElementAt(removeIndex);
    storeSavedGlobalState = textHolder.toString();
    eventCombo = 0;
    
    // Set text to be displayed
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
 // Move tempGlobal to totalGlobal
    Editable textHolder = new SpannableStringBuilder(storeSavedGlobalState);
    for( Event event : tempGlobalEvent )
    {
      event.setSavedState(textHolder.toString());
      textHolder = applyEvent(event, textHolder);
      totalGlobalEventState.add(event);
    }
    tempGlobalEvent.clear();
    
    // Update totalGlobal
    int replaceIndex = 0;
    boolean startUpdating = false;
    int addIndex = totalGlobalEventState.size();
    
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
    if(addIndex == totalGlobalEventState.size())
    {
      textHolder = applyEvent(e, textHolder);
    }
    totalGlobalEventState.add(addIndex, e);
    storeSavedGlobalState = textHolder;
    eventCombo = 0;
    
 // Apply local changes
    if( !localChanges.isEmpty() )
    {
      for( Event event : localChanges )
      {
        textHolder = applyEvent(event, textHolder);
      }
    }

    // Set text to be displayed
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
  
  public void updateInterface()
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
        Log.d("Receive Event", "textFinalBefore: " + textFinal);
        listen.flush();
        Log.d("Receive Event", "textFinalAfter: " + textFinal);
        messageText.setText(textFinal);
        messageText.setSelection(cursorFinal);
        listen.unsuppress();
      }
    });
    
    eventCombo = 0;
  }
}


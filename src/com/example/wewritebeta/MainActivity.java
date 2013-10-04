package com.example.wewritebeta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

import com.example.wewritebeta.Event.ChangeType;



import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
  
  //listener variables
  public Listener listen;
  public boolean suppress;
  public EditTextCursorWatcher messageText;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    
    CharSequence testText = "AAA BBB CCC";
    Event testEvent = new Event(testText, "", 
        0, 11, 11, Event.ChangeType.INSERT);
    applyEvent(testEvent);
    testEvent = new Event(testText, "", 
        9, 5, 11, Event.ChangeType.DELETE);
    applyEvent(testEvent);
    
    
    
    
    
    
    
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
    
    tags.add("test");
    
    // Instantiate client object
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
    
    Event event = new Event(changeText, wholeText, 
        begin, end, cursorPos, type);
  }
  
  public void applyEvent(Event e)
  {
    int start = e.getStart();
    int end = e.getEnd();
    int deletionLength = start - end + 1;
    CharSequence newText = e.getMessage();
    
    
    Editable currentText; //for manipulating strings;
    listen.suppress();
    listen.flush();
    currentText = messageText.getText();
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
          Log.d("Insert Error", ex.getMessage());
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
          Log.d("Insert Error", ex.getMessage());
        }
      }
      messageText.setText(currentText);
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
          Log.d("Delete Error", ex.getMessage());
        }
      }
    
    }
    listen.unsuppress();
    
  }
}


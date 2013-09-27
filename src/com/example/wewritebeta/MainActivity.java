package com.example.wewritebeta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

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
//Change

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
  
  // variables for undo/redo  
  public static boolean ButtonChanged = false;
  Stack<CharSequence> undoStack = new Stack<CharSequence>();
  Stack<CharSequence> redoStack = new Stack<CharSequence>();
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    createSessionButton = (Button) findViewById(R.id.createSessionButton);
    joinSeesionButton = (Button) findViewById(R.id.joinSessionButton);
    leaveSessionButton = (Button) findViewById(R.id.leaveSessionButton);
    baseFile = (CheckBox) findViewById(R.id.baseFile);
    
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
    
    
    
    
    TextListener();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) 
  {
      CharSequence replaceText;
      EditText currentText = (EditText) findViewById(R.id.edit_message);
      switch (item.getItemId()) 
      {
        
      case R.id.action_undo:
        if(undoStack.empty())
        {
          Log.d("empty undo", "The undo stack is empty");
        }
        else
        {
          ButtonChanged = true;
          replaceText = undoStack.pop();
          
          Log.d("Replacement Text", replaceText.toString()); //GET RID OF THIS
          
          redoStack.push(currentText.getText());
          currentText.setText(replaceText);
          currentText.setSelection(replaceText.length());
        }
        return true;
      case R.id.action_redo:
        if(redoStack.empty())
        {
          Log.d("empty redo", "The redo stack is empty");
        }
        else
        {
          ButtonChanged = true;
          replaceText = redoStack.pop();
          undoStack.push(currentText.getText());
          currentText.setText(replaceText);
          currentText.setSelection(replaceText.length());
        }
        return true;
        
      default:
          return super.onOptionsItemSelected(item);
      }
           
     
  }
  
  public void TextListener()
  {
    
    EditText messageText = (EditText) findViewById(R.id.edit_message);
    messageText.addTextChangedListener(new TextWatcher() 
    {
      
      boolean dontPush = false;
      private Calendar startingTime = Calendar.getInstance();
      
      private CharSequence toBePushed = ""; 
      /* toBePushed holds the text that will be added to the undo stack next. This is so that we do not pop the most 
       * recently added text. Think about setting this to initial message text to avoid issues when opening 
       * a file. */
      
      public void afterTextChanged(Editable s) 
      {
      }
      
      public void beforeTextChanged(CharSequence s, int start, 
        int count, int after) 
      {
      }
      
      @Override 
      public void onTextChanged(CharSequence s, int start, 
          int before, int count)
      {
        if(ButtonChanged == true)
        {
          ButtonChanged = false; 
          dontPush = true;
        }
        else
        {
          
          Calendar cal = Calendar.getInstance();
          
          long timerInMilli = cal.getTimeInMillis();
          long startInMilli = startingTime.getTimeInMillis();
          
         
          if( timerInMilli - startInMilli > 5000 ) //Define Constant MILLISECONDs = 1000 and do 1*MILLISECONDS
          {
            if(!dontPush)
            {
            undoStack.push(toBePushed.toString());
            }
            dontPush = false;
            Log.d("Adding to undo:", toBePushed.toString());
            toBePushed = s;
            startingTime.setTimeInMillis(timerInMilli);  
            
          }
        }
        
      
         
        
        
      }

    });
  }

}



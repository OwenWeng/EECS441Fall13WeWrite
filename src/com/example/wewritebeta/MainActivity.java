package com.example.wewritebeta;

import java.util.Calendar;
import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.util.Log;

public class MainActivity extends Activity
{

  public static boolean ButtonChanged = false;
  Stack<CharSequence> undoStack = new Stack<CharSequence>();
  Stack<CharSequence> redoStack = new Stack<CharSequence>();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    TextListener();
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
        }
        else
        {
          
          Calendar cal = Calendar.getInstance();
          
          long timerInMilli = cal.getTimeInMillis();
          long startInMilli = startingTime.getTimeInMillis();
          
         
          if( timerInMilli - startInMilli > 5000 ) //Define Constant MILLISECONDs = 1000 and do 1*MILLISECONDS
          {
            undoStack.push(toBePushed.toString());
            Log.d("Adding to undo:", toBePushed.toString());
            toBePushed = s;
            startingTime.setTimeInMillis(timerInMilli);  
            
          }
        }
        
      
         
        
        
      }

    });
  }

}



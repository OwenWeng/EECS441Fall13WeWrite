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

public class MainActivity extends Activity
{

  
  Stack<CharSequence> undoStack = new Stack<CharSequence>();
  Stack<CharSequence> redoStack = new Stack<CharSequence>();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
   
  CharSequence replaceText;
  EditText currentText = (EditText) findViewById(R.id.edit_message);
      switch (item.getItemId()) 
      {
      case R.id.action_undo:
          replaceText = undoStack.pop();
          redoStack.push(currentText.getText());
          currentText.setText(replaceText);
      case R.id.action_redo:
          replaceText = redoStack.pop();
          undoStack.push(currentText.getText());
          currentText.setText(replaceText);
          }
           
      return super.onOptionsItemSelected(item);
      } 
  
  public void Listener()
  {
    
    
    EditText messageText = (EditText) findViewById(R.id.edit_message);
    messageText.addTextChangedListener(new TextWatcher() 
    {
      private int startingTime = 0;
      String changeHolder;
      
      public void afterTextChanged(Editable s) 
      {
      }
      
      public void beforeTextChanged(CharSequence s, int start, 
        int count, int after) 
      {
      }
      
      public void onTextChanged(CharSequence s, int start, 
          int before, int count)
      {
        Calendar cal = Calendar.getInstance();
        int second = cal.get(Calendar.SECOND);
        
        if(startingTime == 0)
        {
          startingTime = second;
          
        }
        if( second - startingTime > 2 )
        {
          undoStack.push(s);
          startingTime = second; 
        }
         
        
        
      }

    });
  }

}



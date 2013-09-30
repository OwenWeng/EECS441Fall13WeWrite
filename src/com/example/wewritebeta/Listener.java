package com.example.wewritebeta;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
public class Listener
{
 
  private EditTextCursorWatcher messageText; 
  private CharSequence textString; 
  private boolean begin;
  private MainActivity m;
  private CharSequence wholeText;
  private CharSequence changeText;
  private int startIndex;
  private int cursor;
  private boolean cursorChange;
  private Handler timer; 
  private Runnable runEventGen; 
  public Listener(MainActivity main)
  {
    m = main;
    
    begin = true;
    messageText = (EditTextCursorWatcher) m.findViewById(R.id.edit_message);
    instantiateListener();
    timer = new Handler();
    runEventGen = new Runnable() 
    {
      @Override
      public void run() 
      {
         
         flush();
      }
   };
   timer.postDelayed(runEventGen, 10000);
  }
  
    private void instantiateListener()
    {
      messageText.addTextChangedListener(new TextWatcher() 
      {

        public void afterTextChanged(Editable s) 
        {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, 
            int count, int after) 
        {
          
          if((cursor - startIndex) < 0 && after > 0 && !begin)
          {
            flush();
          }
        }

        @Override 
        public void onTextChanged(CharSequence s, int start, 
            int before, int count)
        {
          if(begin)
          {
            
            startIndex = start + before;
            begin = false;
            cursor = startIndex;
          }
          wholeText = s;
          cursorChange = true;
          
          //want to allow inserts followed by deletes but not deletes followed by inserts
          
            cursor+= (count - before);
          


          textString = "start" + Integer.toString(start) + "before" + Integer.toString(before) 
              + "count" + Integer.toString(count);
          Log.d("onTextChangeStuff", textString.toString());
          Log.d("onTextChangeText", s.toString());


        }
      });
    }
    public void flush()
    {
      timer.removeCallbacks(runEventGen);
      if(cursor < startIndex)
      {
        changeText = "";
        eventGen();
      }
      if(cursor > startIndex)
      {
        changeText = wholeText.subSequence(startIndex, cursor);
        eventGen();
      }
      cursor = startIndex;
      begin = true;
      timer.postDelayed(runEventGen, 5000);
    }
    private void eventGen()
    {
      if(cursor < startIndex)
      {
        m.generateEvent(changeText, wholeText, startIndex, cursor, cursor, Event.ChangeType.DELETE);
      }
      else
      {
        m.generateEvent(changeText, wholeText, startIndex, cursor, cursor, Event.ChangeType.INSERT);
      }
    }
    
    public boolean cursorChanged()
    {
      return cursorChange;
    }
    public void resetCursorChange()
    {
      cursorChange = false;
    }
    
    
}

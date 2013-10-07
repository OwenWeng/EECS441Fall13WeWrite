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
  private boolean suppress;
  private int timerLength;
  public Listener(MainActivity main)
  {
    m = main;
    
    begin = true;
    suppress = true;
    timerLength = 5000;
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
   timer.postDelayed(runEventGen, timerLength);
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
          if(!suppress)
          {
            if((cursor - startIndex) < 0 && after > 0 && !begin)
            {
              flush();
            }
          }
        }

        @Override 
        public void onTextChanged(CharSequence s, int start, 
            int before, int count)
        {
          if(!suppress)
          {
            m.timer.removeCallbacks(m.runUpdateInterface);
            m.timer.postDelayed(m.runUpdateInterface, m.timerLength);
            
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
        }
      });
    }
    public boolean flush()
    {
      boolean generatesEvent = false;
      timer.removeCallbacks(runEventGen);
      if(cursor < startIndex)
      {
        changeText = "";
        generatesEvent = true;
        eventGen();
      }
      if(cursor > startIndex)
      {
        changeText = wholeText.subSequence(startIndex, cursor);
        generatesEvent = true;
        eventGen();
      }
      cursor = startIndex;
      begin = true;
      timer.postDelayed(runEventGen, timerLength);
      return generatesEvent;
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
    public void suppress()
    {
      suppress = true;
      timer.removeCallbacks(runEventGen);
    }
    public void unsuppress()
    {
      suppress = false; 
      timer.postDelayed(runEventGen, timerLength);
    }
}

package com.example.wewritebeta;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditTextCursorWatcher extends EditText {

  MainActivity m;
  private int previousPosition;
  private boolean set = false;

  public void setMainActivity(MainActivity m) //team 0
  {  
    this.m = m;
    
    this.setCustomSelectionActionModeCallback(new ActionMode.Callback() 
    {

      public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
          return false;
      }

      public void onDestroyActionMode(ActionMode mode) {                  
      }

      public boolean onCreateActionMode(ActionMode mode, Menu menu) {
          return false;
      }

      public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
          return false;
      }
  });
    set = true;
  }
  
  public EditTextCursorWatcher(Context context, AttributeSet attrs,
          int defStyle) 
  {
      super(context, attrs, defStyle);

  }

  public EditTextCursorWatcher(Context context, AttributeSet attrs) 
  {
      super(context, attrs);

  }

  public EditTextCursorWatcher(Context context) 
  {
      super(context);

  }


   @Override   
   protected void onSelectionChanged(int selStart, int selEnd)
   {
     if(set)
     {
       CharSequence selectionString = Integer.toString(selStart) + "to" + Integer.toString(selEnd);
      // Log.d("selectionChanged",selectionString.toString());
       if(m.listen.cursorChanged())
       {
         m.listen.resetCursorChange();
         previousPosition = selStart;
       }
       else
       {
         if(selEnd != previousPosition)
         {
           m.listen.flush();
           m.generateEvent("", this.getText().toString(), previousPosition, selEnd, selEnd, Event.ChangeType.CURSORMOVE);
         }
       }
     }
   }

}

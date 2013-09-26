package com.example.wewritebeta;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import edu.umich.imlc.collabrify.client.exceptions.CollabrifyException;

public class LeaveListener implements OnClickListener
{
  private MainActivity a;
  
  public LeaveListener(MainActivity a) {
    this.a = a;
  }

  @Override
  public void onClick(View v) {
    try {
      if (a.myClient.inSession())
        a.myClient.leaveSession(false);
    } catch (CollabrifyException e) {
      Log.e(a.TAG, "error", e);
    }
  }
}

package com.example.wewritebeta;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class JoinListener implements OnClickListener
{
  private MainActivity a;

  JoinListener(MainActivity a) {
    this.a = a; 
  }

  @Override
  public void onClick(View v)
  {
    try
    {
      a.myClient.requestSessionList(a.tags);
    }
    catch( Exception e )
    {
      Log.e(a.TAG, "error", e);
    }
  }
}

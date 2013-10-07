package com.example.wewritebeta;

import java.io.ByteArrayInputStream;
import java.util.Random;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import edu.umich.imlc.collabrify.client.exceptions.CollabrifyException;

public class CreateListener implements OnClickListener
{
  private MainActivity a;

  public CreateListener(MainActivity a) {
    this.a = a;
  }

  @Override
  public void onClick(View v) {
    try {
      Random rand = new Random();
      a.sessionName = "Test " + rand.nextInt(Integer.MAX_VALUE);

      if (a.baseFile.isChecked()) {
        // initialize baseFile data for this example we will use the session name as the data
        a.baseFileBuffer = new ByteArrayInputStream(a.messageText.toString().getBytes());
        a.myClient.createSessionWithBase(a.sessionName, a.tags, null, 0);
      } else {
        a.myClient.createSession(a.sessionName, a.tags, null, 0);
      }
      Log.i(a.TAG, "Session name is " + a.sessionName);
    } catch (CollabrifyException e) {
      Log.e(a.TAG, "error", e);
    }
  }
}

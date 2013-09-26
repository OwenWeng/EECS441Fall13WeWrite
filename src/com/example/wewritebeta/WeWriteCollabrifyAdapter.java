package com.example.wewritebeta;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import edu.umich.imlc.collabrify.client.CollabrifyAdapter;
import edu.umich.imlc.collabrify.client.CollabrifyClient;
import edu.umich.imlc.collabrify.client.CollabrifySession;
import edu.umich.imlc.collabrify.client.exceptions.CollabrifyException;

public class WeWriteCollabrifyAdapter extends CollabrifyAdapter
{
  private MainActivity a;
  public WeWriteCollabrifyAdapter(MainActivity a) {
    this.a = a;
  }
  
  
  @Override
  public void onSessionCreated(long id) {
    Log.i(a.TAG, "Session created, id: " + id);
    a.sessionId = id;
    a.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        a.createSessionButton.setText(a.sessionName);
      }
    });
  }
  
  
  @Override
  public byte[] onBaseFileChunkRequested(long currentBaseFileSize) {
    // read up to max chunk size at a time
    byte[] temp = new byte[CollabrifyClient.MAX_BASE_FILE_CHUNK_SIZE];
    int read = 0;
    try { 
      read = a.baseFileBuffer.read(temp); 
    } catch (IOException e){
    // TODO Auto-generated catch block 
      e.printStackTrace(); 
    } 
    if(read == -1) {
      return null; 
    }
    if(read < CollabrifyClient.MAX_BASE_FILE_CHUNK_SIZE) { 
      // Trim garbage data
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      bos.write(temp, 0, read); temp = bos.toByteArray(); 
    }
    return temp;
  }
  
  
  @Override
  public void onBaseFileUploadComplete(long baseFileSize) {
    a.runOnUiThread(new Runnable() {

      @Override
      public void run() {
        //broadcastedText.setText(sessionName);
      }
    });
    /*
     * try { // baseFileBuffer.close(); } /* catch( IOException e ) { //
     * TODO Auto-generated catch block e.printStackTrace(); }
     */
  }
  
  
  @Override
  public void onReceiveSessionList(final List<CollabrifySession> sessionList) {
     if( sessionList.isEmpty() )
     {
       Log.i(a.TAG, "No session available");
       return;
     }
     List<String> sessionNames = new ArrayList<String>();
     for( CollabrifySession s : sessionList )
     {
       sessionNames.add(s.name());
     }
     final AlertDialog.Builder builder = new AlertDialog.Builder(a);
     builder.setTitle("Choose Session").setItems(
         sessionNames.toArray(new String[sessionList.size()]),
         new DialogInterface.OnClickListener()
         {
           @Override
           public void onClick(DialogInterface dialog, int which)
           {
             try
             {
               a.sessionId = sessionList.get(which).id();
               a.sessionName = sessionList.get(which).name();
               a.myClient.joinSession(a.sessionId, null);
             }
             catch( CollabrifyException e )
             {
               Log.e(a.TAG, "error", e);
             }
           }
         });

     a.runOnUiThread(new Runnable()
     {
       @Override
       public void run()
       {
         builder.show();
       }
     });
  }
  
  
  @Override
  public void onSessionJoined(long maxOrderId, long baseFileSize) {
    Log.i(a.TAG, "Session Joined");
    if (baseFileSize > 0) {
      // initialize buffer to receive base file
      // baseFileReceiveBuffer = new ByteArrayOutputStream((int)
      // baseFileSize);
    }
    a.runOnUiThread(new Runnable() {

      @Override
      public void run() {
        a.createSessionButton.setText(a.sessionName);
      }
    });
  }
  
  
  @Override
  public void onBaseFileChunkReceived(byte[] baseFileChunk) {
    
     try { 
       if (baseFileChunk != null) {
         a.baseFileReceiveBuffer.write(baseFileChunk); 
       } else {
         a.runOnUiThread(new Runnable() {
           @Override 
           public void run() {
             //a.broadcastedText.setText(a.baseFileReceiveBuffer.toString()); 
           } 
         }); //
         a.baseFileReceiveBuffer.close(); 
       } 
     } 
     catch (IOException e) { 
       // TODO Auto-generated catch block 
       e.printStackTrace(); 
     }
  }
  
  @Override
  public void onDisconnect() {
    Log.i(a.TAG, "disconnected");
    a.runOnUiThread(new Runnable() {

      @Override
      public void run() {
        a.createSessionButton.setText("CreateSession");
      }
    });
  }
}

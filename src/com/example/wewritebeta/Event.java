package com.example.wewritebeta;

import com.example.wewritebeta.MessageProtos.EventCarrier;
import com.google.protobuf.InvalidProtocolBufferException;

enum ChangeType{
  INSERT, DELETE, CURSORMOVE
}

public class Event{
  
  private int startIndex;
  private int endIndex;
  private CharSequence text;
  private int cursorLoc;
  private int bufferStringCount;
  private EventCarrier.Builder carrierBuilder = EventCarrier.newBuilder();
  private EventCarrier eCarry;
  
  private void setEventCarryPriv()
  {
    carrierBuilder.setStartIndex(startIndex);
    carrierBuilder.setEndIndex(endIndex);
    carrierBuilder.setText(bufferStringCount, text.toString());
    eCarry = carrierBuilder.build();
    
  }
  
  public Event(int sIndex, int eIndex, CharSequence tex)
  {
    startIndex = sIndex;
    endIndex = eIndex;
    text = tex;
    bufferStringCount = 0;
    carrierBuilder.setStartIndex(sIndex);
    carrierBuilder.setEndIndex(eIndex);
    carrierBuilder.setText(bufferStringCount, text.toString());
  }
  
  public Event(byte[] by )
  {
    try
    {
      eCarry.parseFrom(by);
    }
   catch(InvalidProtocolBufferException e){}
 }
    
  public Event(){}
  
  public byte[] serializeEvent()
  {
    return eCarry.toByteArray();
  }
  
  public void setVariables(int sIndex, int eIndex, CharSequence tex)
  {
    startIndex = sIndex;
    endIndex = eIndex;
    text = tex;
  }
  
}

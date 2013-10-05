package com.example.wewritebeta;


import android.util.Log;


import com.example.wewritebeta.MessageProtos.EventCarrier;
import com.google.protobuf.InvalidProtocolBufferException;

enum ChangeType{
  INSERT, DELETE, CURSORMOVE
}

public class Event{
  
  private int startIndex;
  private int endIndex;
  private CharSequence text;
  private CharSequence wholeText;
  private int cursorLoc;
  private int bufferStringCount;
  private ChangeType type;
  private EventCarrier.Builder carrierBuilder = EventCarrier.newBuilder();
  private EventCarrier eCarry;
  private long subID;
  
  
  public enum ChangeType
  {
    INSERT(0),
    DELETE(1),
    CURSORMOVE(2),
    UNDO(3),
    REDO(4);
    private int value;
    private ChangeType(int value) 
    {
      this.value = value;
    }
    public int getValue() 
    {
      return value;
    }
  }
 
  
  
  public Event(CharSequence change, CharSequence whole, 
      int begin, int end, int cursorPos, ChangeType cType)
  {
    startIndex = begin;
    endIndex = end; 
    cursorLoc = cursorPos;
    type = cType;
    text = change;
    wholeText = whole;
    carrierBuilder.setStartIndex(startIndex);
    carrierBuilder.setEndIndex(endIndex);
    carrierBuilder.setText(text.toString());
    eCarry = carrierBuilder.build();
    switch(type)  //TODO add UNDO and REDO
    {
      case INSERT:
        carrierBuilder.setType(EventCarrier.EventType.INSERT);
        break;
      case DELETE:
        carrierBuilder.setType(EventCarrier.EventType.DELETE);
        break;
    }
    
  }
  
  public Event(byte[] by )
  {
     
    try
    {
      eCarry.parseFrom(by);
      startIndex = eCarry.getStartIndex();
      endIndex = eCarry.getEndIndex();
      text = eCarry.getText();
      
      if(eCarry.getType() == EventCarrier.EventType.INSERT)
      {
        type = ChangeType.INSERT;
      }
      else if(eCarry.getType() == EventCarrier.EventType.DELETE)
      {
        type = ChangeType.DELETE;
      }
    }
   catch(InvalidProtocolBufferException e)
   {
     Log.d("Protocol Buffer", e.getMessage());
   }
 }
    
  public Event(){}
  
  public byte[] serializeEvent()
  {
    return eCarry.toByteArray();
  }
  public ChangeType getType()
  {
    return type;
  }
  public int getStart()
  {
    return startIndex;
  }
  public int getEnd()
  {
    return endIndex;
  }
  public CharSequence getMessage()
  {
    return text;
  }
  public int getCursor()
  {
    return cursorLoc;
  }
  public void setSubID(long ID)
  {
    subID = ID;
  }
}

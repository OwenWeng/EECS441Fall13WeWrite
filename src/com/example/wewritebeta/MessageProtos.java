// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: WeWrite.proto

package com.example.wewritebeta;

public final class MessageProtos {
  private MessageProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface EventCarrierOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // repeated string text = 1;
    /**
     * <code>repeated string text = 1;</code>
     */
    java.util.List<java.lang.String>
    getTextList();
    /**
     * <code>repeated string text = 1;</code>
     */
    int getTextCount();
    /**
     * <code>repeated string text = 1;</code>
     */
    java.lang.String getText(int index);
    /**
     * <code>repeated string text = 1;</code>
     */
    com.google.protobuf.ByteString
        getTextBytes(int index);

    // optional int32 startIndex = 2;
    /**
     * <code>optional int32 startIndex = 2;</code>
     */
    boolean hasStartIndex();
    /**
     * <code>optional int32 startIndex = 2;</code>
     */
    int getStartIndex();

    // optional int32 endIndex = 3;
    /**
     * <code>optional int32 endIndex = 3;</code>
     */
    boolean hasEndIndex();
    /**
     * <code>optional int32 endIndex = 3;</code>
     */
    int getEndIndex();

    // optional int32 cursorLoc = 4;
    /**
     * <code>optional int32 cursorLoc = 4;</code>
     */
    boolean hasCursorLoc();
    /**
     * <code>optional int32 cursorLoc = 4;</code>
     */
    int getCursorLoc();

    // optional string changedBy = 5;
    /**
     * <code>optional string changedBy = 5;</code>
     */
    boolean hasChangedBy();
    /**
     * <code>optional string changedBy = 5;</code>
     */
    java.lang.String getChangedBy();
    /**
     * <code>optional string changedBy = 5;</code>
     */
    com.google.protobuf.ByteString
        getChangedByBytes();

    // optional .WeWriteBeta.EventCarrier.EventType type = 6;
    /**
     * <code>optional .WeWriteBeta.EventCarrier.EventType type = 6;</code>
     */
    boolean hasType();
    /**
     * <code>optional .WeWriteBeta.EventCarrier.EventType type = 6;</code>
     */
    com.example.wewritebeta.MessageProtos.EventCarrier.EventType getType();
  }
  /**
   * Protobuf type {@code WeWriteBeta.EventCarrier}
   */
  public static final class EventCarrier extends
      com.google.protobuf.GeneratedMessage
      implements EventCarrierOrBuilder {
    // Use EventCarrier.newBuilder() to construct.
    private EventCarrier(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private EventCarrier(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final EventCarrier defaultInstance;
    public static EventCarrier getDefaultInstance() {
      return defaultInstance;
    }

    public EventCarrier getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private EventCarrier(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                text_ = new com.google.protobuf.LazyStringArrayList();
                mutable_bitField0_ |= 0x00000001;
              }
              text_.add(input.readBytes());
              break;
            }
            case 16: {
              bitField0_ |= 0x00000001;
              startIndex_ = input.readInt32();
              break;
            }
            case 24: {
              bitField0_ |= 0x00000002;
              endIndex_ = input.readInt32();
              break;
            }
            case 32: {
              bitField0_ |= 0x00000004;
              cursorLoc_ = input.readInt32();
              break;
            }
            case 42: {
              bitField0_ |= 0x00000008;
              changedBy_ = input.readBytes();
              break;
            }
            case 48: {
              int rawValue = input.readEnum();
              com.example.wewritebeta.MessageProtos.EventCarrier.EventType value = com.example.wewritebeta.MessageProtos.EventCarrier.EventType.valueOf(rawValue);
              if (value == null) {
                unknownFields.mergeVarintField(6, rawValue);
              } else {
                bitField0_ |= 0x00000010;
                type_ = value;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
          text_ = new com.google.protobuf.UnmodifiableLazyStringList(text_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.example.wewritebeta.MessageProtos.internal_static_WeWriteBeta_EventCarrier_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.example.wewritebeta.MessageProtos.internal_static_WeWriteBeta_EventCarrier_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.example.wewritebeta.MessageProtos.EventCarrier.class, com.example.wewritebeta.MessageProtos.EventCarrier.Builder.class);
    }

    public static com.google.protobuf.Parser<EventCarrier> PARSER =
        new com.google.protobuf.AbstractParser<EventCarrier>() {
      public EventCarrier parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new EventCarrier(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<EventCarrier> getParserForType() {
      return PARSER;
    }

    /**
     * Protobuf enum {@code WeWriteBeta.EventCarrier.EventType}
     */
    public enum EventType
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>INSERT = 0;</code>
       */
      INSERT(0, 0),
      /**
       * <code>DELETE = 1;</code>
       */
      DELETE(1, 1),
      /**
       * <code>CURSOR = 2;</code>
       */
      CURSOR(2, 2),
      ;

      /**
       * <code>INSERT = 0;</code>
       */
      public static final int INSERT_VALUE = 0;
      /**
       * <code>DELETE = 1;</code>
       */
      public static final int DELETE_VALUE = 1;
      /**
       * <code>CURSOR = 2;</code>
       */
      public static final int CURSOR_VALUE = 2;


      public final int getNumber() { return value; }

      public static EventType valueOf(int value) {
        switch (value) {
          case 0: return INSERT;
          case 1: return DELETE;
          case 2: return CURSOR;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<EventType>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static com.google.protobuf.Internal.EnumLiteMap<EventType>
          internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<EventType>() {
              public EventType findValueByNumber(int number) {
                return EventType.valueOf(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        return getDescriptor().getValues().get(index);
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return com.example.wewritebeta.MessageProtos.EventCarrier.getDescriptor().getEnumTypes().get(0);
      }

      private static final EventType[] VALUES = values();

      public static EventType valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        return VALUES[desc.getIndex()];
      }

      private final int index;
      private final int value;

      private EventType(int index, int value) {
        this.index = index;
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:WeWriteBeta.EventCarrier.EventType)
    }

    private int bitField0_;
    // repeated string text = 1;
    public static final int TEXT_FIELD_NUMBER = 1;
    private com.google.protobuf.LazyStringList text_;
    /**
     * <code>repeated string text = 1;</code>
     */
    public java.util.List<java.lang.String>
        getTextList() {
      return text_;
    }
    /**
     * <code>repeated string text = 1;</code>
     */
    public int getTextCount() {
      return text_.size();
    }
    /**
     * <code>repeated string text = 1;</code>
     */
    public java.lang.String getText(int index) {
      return text_.get(index);
    }
    /**
     * <code>repeated string text = 1;</code>
     */
    public com.google.protobuf.ByteString
        getTextBytes(int index) {
      return text_.getByteString(index);
    }

    // optional int32 startIndex = 2;
    public static final int STARTINDEX_FIELD_NUMBER = 2;
    private int startIndex_;
    /**
     * <code>optional int32 startIndex = 2;</code>
     */
    public boolean hasStartIndex() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional int32 startIndex = 2;</code>
     */
    public int getStartIndex() {
      return startIndex_;
    }

    // optional int32 endIndex = 3;
    public static final int ENDINDEX_FIELD_NUMBER = 3;
    private int endIndex_;
    /**
     * <code>optional int32 endIndex = 3;</code>
     */
    public boolean hasEndIndex() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional int32 endIndex = 3;</code>
     */
    public int getEndIndex() {
      return endIndex_;
    }

    // optional int32 cursorLoc = 4;
    public static final int CURSORLOC_FIELD_NUMBER = 4;
    private int cursorLoc_;
    /**
     * <code>optional int32 cursorLoc = 4;</code>
     */
    public boolean hasCursorLoc() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>optional int32 cursorLoc = 4;</code>
     */
    public int getCursorLoc() {
      return cursorLoc_;
    }

    // optional string changedBy = 5;
    public static final int CHANGEDBY_FIELD_NUMBER = 5;
    private java.lang.Object changedBy_;
    /**
     * <code>optional string changedBy = 5;</code>
     */
    public boolean hasChangedBy() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>optional string changedBy = 5;</code>
     */
    public java.lang.String getChangedBy() {
      java.lang.Object ref = changedBy_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          changedBy_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string changedBy = 5;</code>
     */
    public com.google.protobuf.ByteString
        getChangedByBytes() {
      java.lang.Object ref = changedBy_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        changedBy_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // optional .WeWriteBeta.EventCarrier.EventType type = 6;
    public static final int TYPE_FIELD_NUMBER = 6;
    private com.example.wewritebeta.MessageProtos.EventCarrier.EventType type_;
    /**
     * <code>optional .WeWriteBeta.EventCarrier.EventType type = 6;</code>
     */
    public boolean hasType() {
      return ((bitField0_ & 0x00000010) == 0x00000010);
    }
    /**
     * <code>optional .WeWriteBeta.EventCarrier.EventType type = 6;</code>
     */
    public com.example.wewritebeta.MessageProtos.EventCarrier.EventType getType() {
      return type_;
    }

    private void initFields() {
      text_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      startIndex_ = 0;
      endIndex_ = 0;
      cursorLoc_ = 0;
      changedBy_ = "";
      type_ = com.example.wewritebeta.MessageProtos.EventCarrier.EventType.INSERT;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      for (int i = 0; i < text_.size(); i++) {
        output.writeBytes(1, text_.getByteString(i));
      }
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(2, startIndex_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeInt32(3, endIndex_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeInt32(4, cursorLoc_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeBytes(5, getChangedByBytes());
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        output.writeEnum(6, type_.getNumber());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      {
        int dataSize = 0;
        for (int i = 0; i < text_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeBytesSizeNoTag(text_.getByteString(i));
        }
        size += dataSize;
        size += 1 * getTextList().size();
      }
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, startIndex_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, endIndex_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(4, cursorLoc_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(5, getChangedByBytes());
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(6, type_.getNumber());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.example.wewritebeta.MessageProtos.EventCarrier parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.example.wewritebeta.MessageProtos.EventCarrier parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.example.wewritebeta.MessageProtos.EventCarrier parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.example.wewritebeta.MessageProtos.EventCarrier parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.example.wewritebeta.MessageProtos.EventCarrier parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.example.wewritebeta.MessageProtos.EventCarrier parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.example.wewritebeta.MessageProtos.EventCarrier parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.example.wewritebeta.MessageProtos.EventCarrier parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.example.wewritebeta.MessageProtos.EventCarrier parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.example.wewritebeta.MessageProtos.EventCarrier parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.example.wewritebeta.MessageProtos.EventCarrier prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code WeWriteBeta.EventCarrier}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.example.wewritebeta.MessageProtos.EventCarrierOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.example.wewritebeta.MessageProtos.internal_static_WeWriteBeta_EventCarrier_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.example.wewritebeta.MessageProtos.internal_static_WeWriteBeta_EventCarrier_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.example.wewritebeta.MessageProtos.EventCarrier.class, com.example.wewritebeta.MessageProtos.EventCarrier.Builder.class);
      }

      // Construct using com.example.wewritebeta.MessageProtos.EventCarrier.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        text_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        startIndex_ = 0;
        bitField0_ = (bitField0_ & ~0x00000002);
        endIndex_ = 0;
        bitField0_ = (bitField0_ & ~0x00000004);
        cursorLoc_ = 0;
        bitField0_ = (bitField0_ & ~0x00000008);
        changedBy_ = "";
        bitField0_ = (bitField0_ & ~0x00000010);
        type_ = com.example.wewritebeta.MessageProtos.EventCarrier.EventType.INSERT;
        bitField0_ = (bitField0_ & ~0x00000020);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.example.wewritebeta.MessageProtos.internal_static_WeWriteBeta_EventCarrier_descriptor;
      }

      public com.example.wewritebeta.MessageProtos.EventCarrier getDefaultInstanceForType() {
        return com.example.wewritebeta.MessageProtos.EventCarrier.getDefaultInstance();
      }

      public com.example.wewritebeta.MessageProtos.EventCarrier build() {
        com.example.wewritebeta.MessageProtos.EventCarrier result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.example.wewritebeta.MessageProtos.EventCarrier buildPartial() {
        com.example.wewritebeta.MessageProtos.EventCarrier result = new com.example.wewritebeta.MessageProtos.EventCarrier(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          text_ = new com.google.protobuf.UnmodifiableLazyStringList(
              text_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.text_ = text_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000001;
        }
        result.startIndex_ = startIndex_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000002;
        }
        result.endIndex_ = endIndex_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000004;
        }
        result.cursorLoc_ = cursorLoc_;
        if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
          to_bitField0_ |= 0x00000008;
        }
        result.changedBy_ = changedBy_;
        if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
          to_bitField0_ |= 0x00000010;
        }
        result.type_ = type_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.example.wewritebeta.MessageProtos.EventCarrier) {
          return mergeFrom((com.example.wewritebeta.MessageProtos.EventCarrier)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.example.wewritebeta.MessageProtos.EventCarrier other) {
        if (other == com.example.wewritebeta.MessageProtos.EventCarrier.getDefaultInstance()) return this;
        if (!other.text_.isEmpty()) {
          if (text_.isEmpty()) {
            text_ = other.text_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureTextIsMutable();
            text_.addAll(other.text_);
          }
          onChanged();
        }
        if (other.hasStartIndex()) {
          setStartIndex(other.getStartIndex());
        }
        if (other.hasEndIndex()) {
          setEndIndex(other.getEndIndex());
        }
        if (other.hasCursorLoc()) {
          setCursorLoc(other.getCursorLoc());
        }
        if (other.hasChangedBy()) {
          bitField0_ |= 0x00000010;
          changedBy_ = other.changedBy_;
          onChanged();
        }
        if (other.hasType()) {
          setType(other.getType());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.example.wewritebeta.MessageProtos.EventCarrier parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.example.wewritebeta.MessageProtos.EventCarrier) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // repeated string text = 1;
      private com.google.protobuf.LazyStringList text_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      private void ensureTextIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          text_ = new com.google.protobuf.LazyStringArrayList(text_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
       * <code>repeated string text = 1;</code>
       */
      public java.util.List<java.lang.String>
          getTextList() {
        return java.util.Collections.unmodifiableList(text_);
      }
      /**
       * <code>repeated string text = 1;</code>
       */
      public int getTextCount() {
        return text_.size();
      }
      /**
       * <code>repeated string text = 1;</code>
       */
      public java.lang.String getText(int index) {
        return text_.get(index);
      }
      /**
       * <code>repeated string text = 1;</code>
       */
      public com.google.protobuf.ByteString
          getTextBytes(int index) {
        return text_.getByteString(index);
      }
      /**
       * <code>repeated string text = 1;</code>
       */
      public Builder setText(
          int index, java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureTextIsMutable();
        text_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string text = 1;</code>
       */
      public Builder addText(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureTextIsMutable();
        text_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string text = 1;</code>
       */
      public Builder addAllText(
          java.lang.Iterable<java.lang.String> values) {
        ensureTextIsMutable();
        super.addAll(values, text_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string text = 1;</code>
       */
      public Builder clearText() {
        text_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string text = 1;</code>
       */
      public Builder addTextBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureTextIsMutable();
        text_.add(value);
        onChanged();
        return this;
      }

      // optional int32 startIndex = 2;
      private int startIndex_ ;
      /**
       * <code>optional int32 startIndex = 2;</code>
       */
      public boolean hasStartIndex() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional int32 startIndex = 2;</code>
       */
      public int getStartIndex() {
        return startIndex_;
      }
      /**
       * <code>optional int32 startIndex = 2;</code>
       */
      public Builder setStartIndex(int value) {
        bitField0_ |= 0x00000002;
        startIndex_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 startIndex = 2;</code>
       */
      public Builder clearStartIndex() {
        bitField0_ = (bitField0_ & ~0x00000002);
        startIndex_ = 0;
        onChanged();
        return this;
      }

      // optional int32 endIndex = 3;
      private int endIndex_ ;
      /**
       * <code>optional int32 endIndex = 3;</code>
       */
      public boolean hasEndIndex() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>optional int32 endIndex = 3;</code>
       */
      public int getEndIndex() {
        return endIndex_;
      }
      /**
       * <code>optional int32 endIndex = 3;</code>
       */
      public Builder setEndIndex(int value) {
        bitField0_ |= 0x00000004;
        endIndex_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 endIndex = 3;</code>
       */
      public Builder clearEndIndex() {
        bitField0_ = (bitField0_ & ~0x00000004);
        endIndex_ = 0;
        onChanged();
        return this;
      }

      // optional int32 cursorLoc = 4;
      private int cursorLoc_ ;
      /**
       * <code>optional int32 cursorLoc = 4;</code>
       */
      public boolean hasCursorLoc() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>optional int32 cursorLoc = 4;</code>
       */
      public int getCursorLoc() {
        return cursorLoc_;
      }
      /**
       * <code>optional int32 cursorLoc = 4;</code>
       */
      public Builder setCursorLoc(int value) {
        bitField0_ |= 0x00000008;
        cursorLoc_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 cursorLoc = 4;</code>
       */
      public Builder clearCursorLoc() {
        bitField0_ = (bitField0_ & ~0x00000008);
        cursorLoc_ = 0;
        onChanged();
        return this;
      }

      // optional string changedBy = 5;
      private java.lang.Object changedBy_ = "";
      /**
       * <code>optional string changedBy = 5;</code>
       */
      public boolean hasChangedBy() {
        return ((bitField0_ & 0x00000010) == 0x00000010);
      }
      /**
       * <code>optional string changedBy = 5;</code>
       */
      public java.lang.String getChangedBy() {
        java.lang.Object ref = changedBy_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          changedBy_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string changedBy = 5;</code>
       */
      public com.google.protobuf.ByteString
          getChangedByBytes() {
        java.lang.Object ref = changedBy_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          changedBy_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string changedBy = 5;</code>
       */
      public Builder setChangedBy(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000010;
        changedBy_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string changedBy = 5;</code>
       */
      public Builder clearChangedBy() {
        bitField0_ = (bitField0_ & ~0x00000010);
        changedBy_ = getDefaultInstance().getChangedBy();
        onChanged();
        return this;
      }
      /**
       * <code>optional string changedBy = 5;</code>
       */
      public Builder setChangedByBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000010;
        changedBy_ = value;
        onChanged();
        return this;
      }

      // optional .WeWriteBeta.EventCarrier.EventType type = 6;
      private com.example.wewritebeta.MessageProtos.EventCarrier.EventType type_ = com.example.wewritebeta.MessageProtos.EventCarrier.EventType.INSERT;
      /**
       * <code>optional .WeWriteBeta.EventCarrier.EventType type = 6;</code>
       */
      public boolean hasType() {
        return ((bitField0_ & 0x00000020) == 0x00000020);
      }
      /**
       * <code>optional .WeWriteBeta.EventCarrier.EventType type = 6;</code>
       */
      public com.example.wewritebeta.MessageProtos.EventCarrier.EventType getType() {
        return type_;
      }
      /**
       * <code>optional .WeWriteBeta.EventCarrier.EventType type = 6;</code>
       */
      public Builder setType(com.example.wewritebeta.MessageProtos.EventCarrier.EventType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000020;
        type_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional .WeWriteBeta.EventCarrier.EventType type = 6;</code>
       */
      public Builder clearType() {
        bitField0_ = (bitField0_ & ~0x00000020);
        type_ = com.example.wewritebeta.MessageProtos.EventCarrier.EventType.INSERT;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:WeWriteBeta.EventCarrier)
    }

    static {
      defaultInstance = new EventCarrier(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:WeWriteBeta.EventCarrier)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_WeWriteBeta_EventCarrier_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_WeWriteBeta_EventCarrier_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rWeWrite.proto\022\013WeWriteBeta\"\314\001\n\014EventCa" +
      "rrier\022\014\n\004text\030\001 \003(\t\022\022\n\nstartIndex\030\002 \001(\005\022" +
      "\020\n\010endIndex\030\003 \001(\005\022\021\n\tcursorLoc\030\004 \001(\005\022\021\n\t" +
      "changedBy\030\005 \001(\t\0221\n\004type\030\006 \001(\0162#.WeWriteB" +
      "eta.EventCarrier.EventType\"/\n\tEventType\022" +
      "\n\n\006INSERT\020\000\022\n\n\006DELETE\020\001\022\n\n\006CURSOR\020\002B(\n\027c" +
      "om.example.wewritebetaB\rMessageProtos"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_WeWriteBeta_EventCarrier_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_WeWriteBeta_EventCarrier_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_WeWriteBeta_EventCarrier_descriptor,
              new java.lang.String[] { "Text", "StartIndex", "EndIndex", "CursorLoc", "ChangedBy", "Type", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}

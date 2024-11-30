package org.example.web_rest;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: input.proto
// Protobuf Java Version: 4.28.2

public final class Input {
  private Input() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 2,
      /* suffix= */ "",
      Input.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface InputProtoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:InputProto)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated string expressions = 1;</code>
     * @return A list containing the expressions.
     */
    java.util.List<String>
        getExpressionsList();
    /**
     * <code>repeated string expressions = 1;</code>
     * @return The count of expressions.
     */
    int getExpressionsCount();
    /**
     * <code>repeated string expressions = 1;</code>
     * @param index The index of the element to return.
     * @return The expressions at the given index.
     */
    String getExpressions(int index);
    /**
     * <code>repeated string expressions = 1;</code>
     * @param index The index of the value to return.
     * @return The bytes of the expressions at the given index.
     */
    com.google.protobuf.ByteString
        getExpressionsBytes(int index);
  }
  /**
   * Protobuf type {@code InputProto}
   */
  public static final class InputProto extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:InputProto)
      InputProtoOrBuilder {
  private static final long serialVersionUID = 0L;
    static {
      com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 28,
        /* patch= */ 2,
        /* suffix= */ "",
        InputProto.class.getName());
    }
    // Use InputProto.newBuilder() to construct.
    private InputProto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
    }
    private InputProto() {
      expressions_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Input.internal_static_InputProto_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Input.internal_static_InputProto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              InputProto.class, Builder.class);
    }

    public static final int EXPRESSIONS_FIELD_NUMBER = 1;
    @SuppressWarnings("serial")
    private com.google.protobuf.LazyStringArrayList expressions_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
    /**
     * <code>repeated string expressions = 1;</code>
     * @return A list containing the expressions.
     */
    public com.google.protobuf.ProtocolStringList
        getExpressionsList() {
      return expressions_;
    }
    /**
     * <code>repeated string expressions = 1;</code>
     * @return The count of expressions.
     */
    public int getExpressionsCount() {
      return expressions_.size();
    }
    /**
     * <code>repeated string expressions = 1;</code>
     * @param index The index of the element to return.
     * @return The expressions at the given index.
     */
    public String getExpressions(int index) {
      return expressions_.get(index);
    }
    /**
     * <code>repeated string expressions = 1;</code>
     * @param index The index of the value to return.
     * @return The bytes of the expressions at the given index.
     */
    public com.google.protobuf.ByteString
        getExpressionsBytes(int index) {
      return expressions_.getByteString(index);
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      for (int i = 0; i < expressions_.size(); i++) {
        com.google.protobuf.GeneratedMessage.writeString(output, 1, expressions_.getRaw(i));
      }
      getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      {
        int dataSize = 0;
        for (int i = 0; i < expressions_.size(); i++) {
          dataSize += computeStringSizeNoTag(expressions_.getRaw(i));
        }
        size += dataSize;
        size += 1 * getExpressionsList().size();
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof InputProto)) {
        return super.equals(obj);
      }
      InputProto other = (InputProto) obj;

      if (!getExpressionsList()
          .equals(other.getExpressionsList())) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (getExpressionsCount() > 0) {
        hash = (37 * hash) + EXPRESSIONS_FIELD_NUMBER;
        hash = (53 * hash) + getExpressionsList().hashCode();
      }
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static InputProto parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static InputProto parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static InputProto parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static InputProto parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static InputProto parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static InputProto parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static InputProto parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static InputProto parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static InputProto parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static InputProto parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static InputProto parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static InputProto parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(InputProto prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code InputProto}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:InputProto)
        InputProtoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return Input.internal_static_InputProto_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return Input.internal_static_InputProto_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                InputProto.class, Builder.class);
      }

      // Construct using Input.InputProto.newBuilder()
      private Builder() {

      }

      private Builder(
          BuilderParent parent) {
        super(parent);

      }
      @Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        expressions_ =
            com.google.protobuf.LazyStringArrayList.emptyList();
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return Input.internal_static_InputProto_descriptor;
      }

      @Override
      public InputProto getDefaultInstanceForType() {
        return InputProto.getDefaultInstance();
      }

      @Override
      public InputProto build() {
        InputProto result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public InputProto buildPartial() {
        InputProto result = new InputProto(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(InputProto result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          expressions_.makeImmutable();
          result.expressions_ = expressions_;
        }
      }

      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof InputProto) {
          return mergeFrom((InputProto)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(InputProto other) {
        if (other == InputProto.getDefaultInstance()) return this;
        if (!other.expressions_.isEmpty()) {
          if (expressions_.isEmpty()) {
            expressions_ = other.expressions_;
            bitField0_ |= 0x00000001;
          } else {
            ensureExpressionsIsMutable();
            expressions_.addAll(other.expressions_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 10: {
                String s = input.readStringRequireUtf8();
                ensureExpressionsIsMutable();
                expressions_.add(s);
                break;
              } // case 10
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.LazyStringArrayList expressions_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
      private void ensureExpressionsIsMutable() {
        if (!expressions_.isModifiable()) {
          expressions_ = new com.google.protobuf.LazyStringArrayList(expressions_);
        }
        bitField0_ |= 0x00000001;
      }
      /**
       * <code>repeated string expressions = 1;</code>
       * @return A list containing the expressions.
       */
      public com.google.protobuf.ProtocolStringList
          getExpressionsList() {
        expressions_.makeImmutable();
        return expressions_;
      }
      /**
       * <code>repeated string expressions = 1;</code>
       * @return The count of expressions.
       */
      public int getExpressionsCount() {
        return expressions_.size();
      }
      /**
       * <code>repeated string expressions = 1;</code>
       * @param index The index of the element to return.
       * @return The expressions at the given index.
       */
      public String getExpressions(int index) {
        return expressions_.get(index);
      }
      /**
       * <code>repeated string expressions = 1;</code>
       * @param index The index of the value to return.
       * @return The bytes of the expressions at the given index.
       */
      public com.google.protobuf.ByteString
          getExpressionsBytes(int index) {
        return expressions_.getByteString(index);
      }
      /**
       * <code>repeated string expressions = 1;</code>
       * @param index The index to set the value at.
       * @param value The expressions to set.
       * @return This builder for chaining.
       */
      public Builder setExpressions(
          int index, String value) {
        if (value == null) { throw new NullPointerException(); }
        ensureExpressionsIsMutable();
        expressions_.set(index, value);
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string expressions = 1;</code>
       * @param value The expressions to add.
       * @return This builder for chaining.
       */
      public Builder addExpressions(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        ensureExpressionsIsMutable();
        expressions_.add(value);
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string expressions = 1;</code>
       * @param values The expressions to add.
       * @return This builder for chaining.
       */
      public Builder addAllExpressions(
          Iterable<String> values) {
        ensureExpressionsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, expressions_);
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string expressions = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearExpressions() {
        expressions_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string expressions = 1;</code>
       * @param value The bytes of the expressions to add.
       * @return This builder for chaining.
       */
      public Builder addExpressionsBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        ensureExpressionsIsMutable();
        expressions_.add(value);
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:InputProto)
    }

    // @@protoc_insertion_point(class_scope:InputProto)
    private static final InputProto DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new InputProto();
    }

    public static InputProto getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<InputProto>
        PARSER = new com.google.protobuf.AbstractParser<InputProto>() {
      @Override
      public InputProto parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<InputProto> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<InputProto> getParserForType() {
      return PARSER;
    }

    @Override
    public InputProto getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_InputProto_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_InputProto_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\013input.proto\"!\n\nInputProto\022\023\n\013expressio" +
      "ns\030\001 \003(\tb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_InputProto_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_InputProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_InputProto_descriptor,
        new String[] { "Expressions", });
    descriptor.resolveAllFeaturesImmutable();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
package org.example.web_rest;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: output.proto
// Protobuf Java Version: 4.28.2

public final class Output {
  private Output() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 2,
      /* suffix= */ "",
      Output.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface OutputProtoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:OutputProto)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated string results = 1;</code>
     * @return A list containing the results.
     */
    java.util.List<String>
        getResultsList();
    /**
     * <code>repeated string results = 1;</code>
     * @return The count of results.
     */
    int getResultsCount();
    /**
     * <code>repeated string results = 1;</code>
     * @param index The index of the element to return.
     * @return The results at the given index.
     */
    String getResults(int index);
    /**
     * <code>repeated string results = 1;</code>
     * @param index The index of the value to return.
     * @return The bytes of the results at the given index.
     */
    com.google.protobuf.ByteString
        getResultsBytes(int index);
  }
  /**
   * Protobuf type {@code OutputProto}
   */
  public static final class OutputProto extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:OutputProto)
      OutputProtoOrBuilder {
  private static final long serialVersionUID = 0L;
    static {
      com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 28,
        /* patch= */ 2,
        /* suffix= */ "",
        OutputProto.class.getName());
    }
    // Use OutputProto.newBuilder() to construct.
    private OutputProto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
    }
    private OutputProto() {
      results_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Output.internal_static_OutputProto_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Output.internal_static_OutputProto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              OutputProto.class, Builder.class);
    }

    public static final int RESULTS_FIELD_NUMBER = 1;
    @SuppressWarnings("serial")
    private com.google.protobuf.LazyStringArrayList results_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
    /**
     * <code>repeated string results = 1;</code>
     * @return A list containing the results.
     */
    public com.google.protobuf.ProtocolStringList
        getResultsList() {
      return results_;
    }
    /**
     * <code>repeated string results = 1;</code>
     * @return The count of results.
     */
    public int getResultsCount() {
      return results_.size();
    }
    /**
     * <code>repeated string results = 1;</code>
     * @param index The index of the element to return.
     * @return The results at the given index.
     */
    public String getResults(int index) {
      return results_.get(index);
    }
    /**
     * <code>repeated string results = 1;</code>
     * @param index The index of the value to return.
     * @return The bytes of the results at the given index.
     */
    public com.google.protobuf.ByteString
        getResultsBytes(int index) {
      return results_.getByteString(index);
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
      for (int i = 0; i < results_.size(); i++) {
        com.google.protobuf.GeneratedMessage.writeString(output, 1, results_.getRaw(i));
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
        for (int i = 0; i < results_.size(); i++) {
          dataSize += computeStringSizeNoTag(results_.getRaw(i));
        }
        size += dataSize;
        size += 1 * getResultsList().size();
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
      if (!(obj instanceof OutputProto)) {
        return super.equals(obj);
      }
      OutputProto other = (OutputProto) obj;

      if (!getResultsList()
          .equals(other.getResultsList())) return false;
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
      if (getResultsCount() > 0) {
        hash = (37 * hash) + RESULTS_FIELD_NUMBER;
        hash = (53 * hash) + getResultsList().hashCode();
      }
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static OutputProto parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static OutputProto parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static OutputProto parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static OutputProto parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static OutputProto parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static OutputProto parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static OutputProto parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static OutputProto parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static OutputProto parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static OutputProto parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static OutputProto parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static OutputProto parseFrom(
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
    public static Builder newBuilder(OutputProto prototype) {
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
     * Protobuf type {@code OutputProto}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:OutputProto)
        OutputProtoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return Output.internal_static_OutputProto_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return Output.internal_static_OutputProto_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                OutputProto.class, Builder.class);
      }

      // Construct using Output.OutputProto.newBuilder()
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
        results_ =
            com.google.protobuf.LazyStringArrayList.emptyList();
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return Output.internal_static_OutputProto_descriptor;
      }

      @Override
      public OutputProto getDefaultInstanceForType() {
        return OutputProto.getDefaultInstance();
      }

      @Override
      public OutputProto build() {
        OutputProto result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public OutputProto buildPartial() {
        OutputProto result = new OutputProto(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(OutputProto result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          results_.makeImmutable();
          result.results_ = results_;
        }
      }

      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof OutputProto) {
          return mergeFrom((OutputProto)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(OutputProto other) {
        if (other == OutputProto.getDefaultInstance()) return this;
        if (!other.results_.isEmpty()) {
          if (results_.isEmpty()) {
            results_ = other.results_;
            bitField0_ |= 0x00000001;
          } else {
            ensureResultsIsMutable();
            results_.addAll(other.results_);
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
                ensureResultsIsMutable();
                results_.add(s);
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

      private com.google.protobuf.LazyStringArrayList results_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
      private void ensureResultsIsMutable() {
        if (!results_.isModifiable()) {
          results_ = new com.google.protobuf.LazyStringArrayList(results_);
        }
        bitField0_ |= 0x00000001;
      }
      /**
       * <code>repeated string results = 1;</code>
       * @return A list containing the results.
       */
      public com.google.protobuf.ProtocolStringList
          getResultsList() {
        results_.makeImmutable();
        return results_;
      }
      /**
       * <code>repeated string results = 1;</code>
       * @return The count of results.
       */
      public int getResultsCount() {
        return results_.size();
      }
      /**
       * <code>repeated string results = 1;</code>
       * @param index The index of the element to return.
       * @return The results at the given index.
       */
      public String getResults(int index) {
        return results_.get(index);
      }
      /**
       * <code>repeated string results = 1;</code>
       * @param index The index of the value to return.
       * @return The bytes of the results at the given index.
       */
      public com.google.protobuf.ByteString
          getResultsBytes(int index) {
        return results_.getByteString(index);
      }
      /**
       * <code>repeated string results = 1;</code>
       * @param index The index to set the value at.
       * @param value The results to set.
       * @return This builder for chaining.
       */
      public Builder setResults(
          int index, String value) {
        if (value == null) { throw new NullPointerException(); }
        ensureResultsIsMutable();
        results_.set(index, value);
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string results = 1;</code>
       * @param value The results to add.
       * @return This builder for chaining.
       */
      public Builder addResults(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        ensureResultsIsMutable();
        results_.add(value);
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string results = 1;</code>
       * @param values The results to add.
       * @return This builder for chaining.
       */
      public Builder addAllResults(
          Iterable<String> values) {
        ensureResultsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, results_);
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string results = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearResults() {
        results_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string results = 1;</code>
       * @param value The bytes of the results to add.
       * @return This builder for chaining.
       */
      public Builder addResultsBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        ensureResultsIsMutable();
        results_.add(value);
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:OutputProto)
    }

    // @@protoc_insertion_point(class_scope:OutputProto)
    private static final OutputProto DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new OutputProto();
    }

    public static OutputProto getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<OutputProto>
        PARSER = new com.google.protobuf.AbstractParser<OutputProto>() {
      @Override
      public OutputProto parsePartialFrom(
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

    public static com.google.protobuf.Parser<OutputProto> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<OutputProto> getParserForType() {
      return PARSER;
    }

    @Override
    public OutputProto getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_OutputProto_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_OutputProto_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\014output.proto\"\036\n\013OutputProto\022\017\n\007results" +
      "\030\001 \003(\tb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_OutputProto_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_OutputProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_OutputProto_descriptor,
        new String[] { "Results", });
    descriptor.resolveAllFeaturesImmutable();
  }

  // @@protoc_insertion_point(outer_class_scope)
}

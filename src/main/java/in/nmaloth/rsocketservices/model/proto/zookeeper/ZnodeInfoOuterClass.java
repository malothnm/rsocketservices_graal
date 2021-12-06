// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: znodeInfo.proto

package in.nmaloth.rsocketservices.model.proto.zookeeper;

public final class ZnodeInfoOuterClass {
  private ZnodeInfoOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ZnodeInfoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string serviceName = 1;</code>
     * @return The serviceName.
     */
    java.lang.String getServiceName();
    /**
     * <code>string serviceName = 1;</code>
     * @return The bytes for serviceName.
     */
    com.google.protobuf.ByteString
        getServiceNameBytes();

    /**
     * <code>string instanceName = 2;</code>
     * @return The instanceName.
     */
    java.lang.String getInstanceName();
    /**
     * <code>string instanceName = 2;</code>
     * @return The bytes for instanceName.
     */
    com.google.protobuf.ByteString
        getInstanceNameBytes();

    /**
     * <code>repeated string serviceToWatch = 3;</code>
     * @return A list containing the serviceToWatch.
     */
    java.util.List<java.lang.String>
        getServiceToWatchList();
    /**
     * <code>repeated string serviceToWatch = 3;</code>
     * @return The count of serviceToWatch.
     */
    int getServiceToWatchCount();
    /**
     * <code>repeated string serviceToWatch = 3;</code>
     * @param index The index of the element to return.
     * @return The serviceToWatch at the given index.
     */
    java.lang.String getServiceToWatch(int index);
    /**
     * <code>repeated string serviceToWatch = 3;</code>
     * @param index The index of the value to return.
     * @return The bytes of the serviceToWatch at the given index.
     */
    com.google.protobuf.ByteString
        getServiceToWatchBytes(int index);
  }
  /**
   * Protobuf type {@code in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfo}
   */
  public static final class ZnodeInfo extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfo)
      ZnodeInfoOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ZnodeInfo.newBuilder() to construct.
    private ZnodeInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ZnodeInfo() {
      serviceName_ = "";
      instanceName_ = "";
      serviceToWatch_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ZnodeInfo();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ZnodeInfo(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
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
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              serviceName_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              instanceName_ = s;
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                serviceToWatch_ = new com.google.protobuf.LazyStringArrayList();
                mutable_bitField0_ |= 0x00000001;
              }
              serviceToWatch_.add(s);
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) != 0)) {
          serviceToWatch_ = serviceToWatch_.getUnmodifiableView();
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.internal_static_in_nmaloth_rsocketservices_model_proto_zookeeper_ZnodeInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.internal_static_in_nmaloth_rsocketservices_model_proto_zookeeper_ZnodeInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo.class, in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo.Builder.class);
    }

    public static final int SERVICENAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object serviceName_;
    /**
     * <code>string serviceName = 1;</code>
     * @return The serviceName.
     */
    @java.lang.Override
    public java.lang.String getServiceName() {
      java.lang.Object ref = serviceName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        serviceName_ = s;
        return s;
      }
    }
    /**
     * <code>string serviceName = 1;</code>
     * @return The bytes for serviceName.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getServiceNameBytes() {
      java.lang.Object ref = serviceName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        serviceName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int INSTANCENAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object instanceName_;
    /**
     * <code>string instanceName = 2;</code>
     * @return The instanceName.
     */
    @java.lang.Override
    public java.lang.String getInstanceName() {
      java.lang.Object ref = instanceName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        instanceName_ = s;
        return s;
      }
    }
    /**
     * <code>string instanceName = 2;</code>
     * @return The bytes for instanceName.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getInstanceNameBytes() {
      java.lang.Object ref = instanceName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        instanceName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SERVICETOWATCH_FIELD_NUMBER = 3;
    private com.google.protobuf.LazyStringList serviceToWatch_;
    /**
     * <code>repeated string serviceToWatch = 3;</code>
     * @return A list containing the serviceToWatch.
     */
    public com.google.protobuf.ProtocolStringList
        getServiceToWatchList() {
      return serviceToWatch_;
    }
    /**
     * <code>repeated string serviceToWatch = 3;</code>
     * @return The count of serviceToWatch.
     */
    public int getServiceToWatchCount() {
      return serviceToWatch_.size();
    }
    /**
     * <code>repeated string serviceToWatch = 3;</code>
     * @param index The index of the element to return.
     * @return The serviceToWatch at the given index.
     */
    public java.lang.String getServiceToWatch(int index) {
      return serviceToWatch_.get(index);
    }
    /**
     * <code>repeated string serviceToWatch = 3;</code>
     * @param index The index of the value to return.
     * @return The bytes of the serviceToWatch at the given index.
     */
    public com.google.protobuf.ByteString
        getServiceToWatchBytes(int index) {
      return serviceToWatch_.getByteString(index);
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getServiceNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, serviceName_);
      }
      if (!getInstanceNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, instanceName_);
      }
      for (int i = 0; i < serviceToWatch_.size(); i++) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, serviceToWatch_.getRaw(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getServiceNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, serviceName_);
      }
      if (!getInstanceNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, instanceName_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < serviceToWatch_.size(); i++) {
          dataSize += computeStringSizeNoTag(serviceToWatch_.getRaw(i));
        }
        size += dataSize;
        size += 1 * getServiceToWatchList().size();
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo)) {
        return super.equals(obj);
      }
      in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo other = (in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo) obj;

      if (!getServiceName()
          .equals(other.getServiceName())) return false;
      if (!getInstanceName()
          .equals(other.getInstanceName())) return false;
      if (!getServiceToWatchList()
          .equals(other.getServiceToWatchList())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + SERVICENAME_FIELD_NUMBER;
      hash = (53 * hash) + getServiceName().hashCode();
      hash = (37 * hash) + INSTANCENAME_FIELD_NUMBER;
      hash = (53 * hash) + getInstanceName().hashCode();
      if (getServiceToWatchCount() > 0) {
        hash = (37 * hash) + SERVICETOWATCH_FIELD_NUMBER;
        hash = (53 * hash) + getServiceToWatchList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfo}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfo)
        in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.internal_static_in_nmaloth_rsocketservices_model_proto_zookeeper_ZnodeInfo_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.internal_static_in_nmaloth_rsocketservices_model_proto_zookeeper_ZnodeInfo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo.class, in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo.Builder.class);
      }

      // Construct using in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        serviceName_ = "";

        instanceName_ = "";

        serviceToWatch_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.internal_static_in_nmaloth_rsocketservices_model_proto_zookeeper_ZnodeInfo_descriptor;
      }

      @java.lang.Override
      public in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo getDefaultInstanceForType() {
        return in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo.getDefaultInstance();
      }

      @java.lang.Override
      public in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo build() {
        in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo buildPartial() {
        in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo result = new in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo(this);
        int from_bitField0_ = bitField0_;
        result.serviceName_ = serviceName_;
        result.instanceName_ = instanceName_;
        if (((bitField0_ & 0x00000001) != 0)) {
          serviceToWatch_ = serviceToWatch_.getUnmodifiableView();
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.serviceToWatch_ = serviceToWatch_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo) {
          return mergeFrom((in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo other) {
        if (other == in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo.getDefaultInstance()) return this;
        if (!other.getServiceName().isEmpty()) {
          serviceName_ = other.serviceName_;
          onChanged();
        }
        if (!other.getInstanceName().isEmpty()) {
          instanceName_ = other.instanceName_;
          onChanged();
        }
        if (!other.serviceToWatch_.isEmpty()) {
          if (serviceToWatch_.isEmpty()) {
            serviceToWatch_ = other.serviceToWatch_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureServiceToWatchIsMutable();
            serviceToWatch_.addAll(other.serviceToWatch_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object serviceName_ = "";
      /**
       * <code>string serviceName = 1;</code>
       * @return The serviceName.
       */
      public java.lang.String getServiceName() {
        java.lang.Object ref = serviceName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          serviceName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string serviceName = 1;</code>
       * @return The bytes for serviceName.
       */
      public com.google.protobuf.ByteString
          getServiceNameBytes() {
        java.lang.Object ref = serviceName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          serviceName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string serviceName = 1;</code>
       * @param value The serviceName to set.
       * @return This builder for chaining.
       */
      public Builder setServiceName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        serviceName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string serviceName = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearServiceName() {
        
        serviceName_ = getDefaultInstance().getServiceName();
        onChanged();
        return this;
      }
      /**
       * <code>string serviceName = 1;</code>
       * @param value The bytes for serviceName to set.
       * @return This builder for chaining.
       */
      public Builder setServiceNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        serviceName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object instanceName_ = "";
      /**
       * <code>string instanceName = 2;</code>
       * @return The instanceName.
       */
      public java.lang.String getInstanceName() {
        java.lang.Object ref = instanceName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          instanceName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string instanceName = 2;</code>
       * @return The bytes for instanceName.
       */
      public com.google.protobuf.ByteString
          getInstanceNameBytes() {
        java.lang.Object ref = instanceName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          instanceName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string instanceName = 2;</code>
       * @param value The instanceName to set.
       * @return This builder for chaining.
       */
      public Builder setInstanceName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        instanceName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string instanceName = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearInstanceName() {
        
        instanceName_ = getDefaultInstance().getInstanceName();
        onChanged();
        return this;
      }
      /**
       * <code>string instanceName = 2;</code>
       * @param value The bytes for instanceName to set.
       * @return This builder for chaining.
       */
      public Builder setInstanceNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        instanceName_ = value;
        onChanged();
        return this;
      }

      private com.google.protobuf.LazyStringList serviceToWatch_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      private void ensureServiceToWatchIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          serviceToWatch_ = new com.google.protobuf.LazyStringArrayList(serviceToWatch_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
       * <code>repeated string serviceToWatch = 3;</code>
       * @return A list containing the serviceToWatch.
       */
      public com.google.protobuf.ProtocolStringList
          getServiceToWatchList() {
        return serviceToWatch_.getUnmodifiableView();
      }
      /**
       * <code>repeated string serviceToWatch = 3;</code>
       * @return The count of serviceToWatch.
       */
      public int getServiceToWatchCount() {
        return serviceToWatch_.size();
      }
      /**
       * <code>repeated string serviceToWatch = 3;</code>
       * @param index The index of the element to return.
       * @return The serviceToWatch at the given index.
       */
      public java.lang.String getServiceToWatch(int index) {
        return serviceToWatch_.get(index);
      }
      /**
       * <code>repeated string serviceToWatch = 3;</code>
       * @param index The index of the value to return.
       * @return The bytes of the serviceToWatch at the given index.
       */
      public com.google.protobuf.ByteString
          getServiceToWatchBytes(int index) {
        return serviceToWatch_.getByteString(index);
      }
      /**
       * <code>repeated string serviceToWatch = 3;</code>
       * @param index The index to set the value at.
       * @param value The serviceToWatch to set.
       * @return This builder for chaining.
       */
      public Builder setServiceToWatch(
          int index, java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureServiceToWatchIsMutable();
        serviceToWatch_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string serviceToWatch = 3;</code>
       * @param value The serviceToWatch to add.
       * @return This builder for chaining.
       */
      public Builder addServiceToWatch(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureServiceToWatchIsMutable();
        serviceToWatch_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string serviceToWatch = 3;</code>
       * @param values The serviceToWatch to add.
       * @return This builder for chaining.
       */
      public Builder addAllServiceToWatch(
          java.lang.Iterable<java.lang.String> values) {
        ensureServiceToWatchIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, serviceToWatch_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string serviceToWatch = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearServiceToWatch() {
        serviceToWatch_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string serviceToWatch = 3;</code>
       * @param value The bytes of the serviceToWatch to add.
       * @return This builder for chaining.
       */
      public Builder addServiceToWatchBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        ensureServiceToWatchIsMutable();
        serviceToWatch_.add(value);
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfo)
    }

    // @@protoc_insertion_point(class_scope:in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfo)
    private static final in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo();
    }

    public static in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ZnodeInfo>
        PARSER = new com.google.protobuf.AbstractParser<ZnodeInfo>() {
      @java.lang.Override
      public ZnodeInfo parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ZnodeInfo(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ZnodeInfo> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ZnodeInfo> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public in.nmaloth.rsocketservices.model.proto.zookeeper.ZnodeInfoOuterClass.ZnodeInfo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_in_nmaloth_rsocketservices_model_proto_zookeeper_ZnodeInfo_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_in_nmaloth_rsocketservices_model_proto_zookeeper_ZnodeInfo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017znodeInfo.proto\0220in.nmaloth.rsocketser" +
      "vices.model.proto.zookeeper\"N\n\tZnodeInfo" +
      "\022\023\n\013serviceName\030\001 \001(\t\022\024\n\014instanceName\030\002 " +
      "\001(\t\022\026\n\016serviceToWatch\030\003 \003(\tb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_in_nmaloth_rsocketservices_model_proto_zookeeper_ZnodeInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_in_nmaloth_rsocketservices_model_proto_zookeeper_ZnodeInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_in_nmaloth_rsocketservices_model_proto_zookeeper_ZnodeInfo_descriptor,
        new java.lang.String[] { "ServiceName", "InstanceName", "ServiceToWatch", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

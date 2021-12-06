// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ServerRegistration.proto

package in.nmaloth.rsocketservices.model.proto;

public final class ServerRegistrationOuterClass {
  private ServerRegistrationOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ServerRegistrationOrBuilder extends
      // @@protoc_insertion_point(interface_extends:in.nmaloth.rsocketservices.model.proto.ServerRegistration)
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
     * <code>string serviceInstance = 2;</code>
     * @return The serviceInstance.
     */
    java.lang.String getServiceInstance();
    /**
     * <code>string serviceInstance = 2;</code>
     * @return The bytes for serviceInstance.
     */
    com.google.protobuf.ByteString
        getServiceInstanceBytes();

    /**
     * <code>bool statusReady = 3;</code>
     * @return The statusReady.
     */
    boolean getStatusReady();
  }
  /**
   * Protobuf type {@code in.nmaloth.rsocketservices.model.proto.ServerRegistration}
   */
  public static final class ServerRegistration extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:in.nmaloth.rsocketservices.model.proto.ServerRegistration)
      ServerRegistrationOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ServerRegistration.newBuilder() to construct.
    private ServerRegistration(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ServerRegistration() {
      serviceName_ = "";
      serviceInstance_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ServerRegistration();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ServerRegistration(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
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

              serviceInstance_ = s;
              break;
            }
            case 24: {

              statusReady_ = input.readBool();
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
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.internal_static_in_nmaloth_rsocketservices_model_proto_ServerRegistration_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.internal_static_in_nmaloth_rsocketservices_model_proto_ServerRegistration_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration.class, in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration.Builder.class);
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

    public static final int SERVICEINSTANCE_FIELD_NUMBER = 2;
    private volatile java.lang.Object serviceInstance_;
    /**
     * <code>string serviceInstance = 2;</code>
     * @return The serviceInstance.
     */
    @java.lang.Override
    public java.lang.String getServiceInstance() {
      java.lang.Object ref = serviceInstance_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        serviceInstance_ = s;
        return s;
      }
    }
    /**
     * <code>string serviceInstance = 2;</code>
     * @return The bytes for serviceInstance.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getServiceInstanceBytes() {
      java.lang.Object ref = serviceInstance_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        serviceInstance_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int STATUSREADY_FIELD_NUMBER = 3;
    private boolean statusReady_;
    /**
     * <code>bool statusReady = 3;</code>
     * @return The statusReady.
     */
    @java.lang.Override
    public boolean getStatusReady() {
      return statusReady_;
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
      if (!getServiceInstanceBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, serviceInstance_);
      }
      if (statusReady_ != false) {
        output.writeBool(3, statusReady_);
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
      if (!getServiceInstanceBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, serviceInstance_);
      }
      if (statusReady_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(3, statusReady_);
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
      if (!(obj instanceof in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration)) {
        return super.equals(obj);
      }
      in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration other = (in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration) obj;

      if (!getServiceName()
          .equals(other.getServiceName())) return false;
      if (!getServiceInstance()
          .equals(other.getServiceInstance())) return false;
      if (getStatusReady()
          != other.getStatusReady()) return false;
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
      hash = (37 * hash) + SERVICEINSTANCE_FIELD_NUMBER;
      hash = (53 * hash) + getServiceInstance().hashCode();
      hash = (37 * hash) + STATUSREADY_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getStatusReady());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parseFrom(
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
    public static Builder newBuilder(in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration prototype) {
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
     * Protobuf type {@code in.nmaloth.rsocketservices.model.proto.ServerRegistration}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:in.nmaloth.rsocketservices.model.proto.ServerRegistration)
        in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistrationOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.internal_static_in_nmaloth_rsocketservices_model_proto_ServerRegistration_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.internal_static_in_nmaloth_rsocketservices_model_proto_ServerRegistration_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration.class, in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration.Builder.class);
      }

      // Construct using in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration.newBuilder()
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

        serviceInstance_ = "";

        statusReady_ = false;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.internal_static_in_nmaloth_rsocketservices_model_proto_ServerRegistration_descriptor;
      }

      @java.lang.Override
      public in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration getDefaultInstanceForType() {
        return in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration.getDefaultInstance();
      }

      @java.lang.Override
      public in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration build() {
        in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration buildPartial() {
        in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration result = new in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration(this);
        result.serviceName_ = serviceName_;
        result.serviceInstance_ = serviceInstance_;
        result.statusReady_ = statusReady_;
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
        if (other instanceof in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration) {
          return mergeFrom((in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration other) {
        if (other == in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration.getDefaultInstance()) return this;
        if (!other.getServiceName().isEmpty()) {
          serviceName_ = other.serviceName_;
          onChanged();
        }
        if (!other.getServiceInstance().isEmpty()) {
          serviceInstance_ = other.serviceInstance_;
          onChanged();
        }
        if (other.getStatusReady() != false) {
          setStatusReady(other.getStatusReady());
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
        in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

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

      private java.lang.Object serviceInstance_ = "";
      /**
       * <code>string serviceInstance = 2;</code>
       * @return The serviceInstance.
       */
      public java.lang.String getServiceInstance() {
        java.lang.Object ref = serviceInstance_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          serviceInstance_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string serviceInstance = 2;</code>
       * @return The bytes for serviceInstance.
       */
      public com.google.protobuf.ByteString
          getServiceInstanceBytes() {
        java.lang.Object ref = serviceInstance_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          serviceInstance_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string serviceInstance = 2;</code>
       * @param value The serviceInstance to set.
       * @return This builder for chaining.
       */
      public Builder setServiceInstance(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        serviceInstance_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string serviceInstance = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearServiceInstance() {
        
        serviceInstance_ = getDefaultInstance().getServiceInstance();
        onChanged();
        return this;
      }
      /**
       * <code>string serviceInstance = 2;</code>
       * @param value The bytes for serviceInstance to set.
       * @return This builder for chaining.
       */
      public Builder setServiceInstanceBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        serviceInstance_ = value;
        onChanged();
        return this;
      }

      private boolean statusReady_ ;
      /**
       * <code>bool statusReady = 3;</code>
       * @return The statusReady.
       */
      @java.lang.Override
      public boolean getStatusReady() {
        return statusReady_;
      }
      /**
       * <code>bool statusReady = 3;</code>
       * @param value The statusReady to set.
       * @return This builder for chaining.
       */
      public Builder setStatusReady(boolean value) {
        
        statusReady_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool statusReady = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearStatusReady() {
        
        statusReady_ = false;
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


      // @@protoc_insertion_point(builder_scope:in.nmaloth.rsocketservices.model.proto.ServerRegistration)
    }

    // @@protoc_insertion_point(class_scope:in.nmaloth.rsocketservices.model.proto.ServerRegistration)
    private static final in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration();
    }

    public static in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ServerRegistration>
        PARSER = new com.google.protobuf.AbstractParser<ServerRegistration>() {
      @java.lang.Override
      public ServerRegistration parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ServerRegistration(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ServerRegistration> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ServerRegistration> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_in_nmaloth_rsocketservices_model_proto_ServerRegistration_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_in_nmaloth_rsocketservices_model_proto_ServerRegistration_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\030ServerRegistration.proto\022&in.nmaloth.r" +
      "socketservices.model.proto\"W\n\022ServerRegi" +
      "stration\022\023\n\013serviceName\030\001 \001(\t\022\027\n\017service" +
      "Instance\030\002 \001(\t\022\023\n\013statusReady\030\003 \001(\010b\006pro" +
      "to3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_in_nmaloth_rsocketservices_model_proto_ServerRegistration_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_in_nmaloth_rsocketservices_model_proto_ServerRegistration_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_in_nmaloth_rsocketservices_model_proto_ServerRegistration_descriptor,
        new java.lang.String[] { "ServiceName", "ServiceInstance", "StatusReady", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
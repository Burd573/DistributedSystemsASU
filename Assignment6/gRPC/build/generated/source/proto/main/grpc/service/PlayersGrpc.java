package service;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.33.1)",
    comments = "Source: services/players.proto")
public final class PlayersGrpc {

  private PlayersGrpc() {}

  public static final String SERVICE_NAME = "services.Players";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.Empty,
      service.PlayersReadResponse> getReadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "read",
      requestType = service.Empty.class,
      responseType = service.PlayersReadResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.Empty,
      service.PlayersReadResponse> getReadMethod() {
    io.grpc.MethodDescriptor<service.Empty, service.PlayersReadResponse> getReadMethod;
    if ((getReadMethod = PlayersGrpc.getReadMethod) == null) {
      synchronized (PlayersGrpc.class) {
        if ((getReadMethod = PlayersGrpc.getReadMethod) == null) {
          PlayersGrpc.getReadMethod = getReadMethod =
              io.grpc.MethodDescriptor.<service.Empty, service.PlayersReadResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "read"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.PlayersReadResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PlayersMethodDescriptorSupplier("read"))
              .build();
        }
      }
    }
    return getReadMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.PlayerWriteRequest,
      service.PlayerWriteResponse> getWriteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "write",
      requestType = service.PlayerWriteRequest.class,
      responseType = service.PlayerWriteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.PlayerWriteRequest,
      service.PlayerWriteResponse> getWriteMethod() {
    io.grpc.MethodDescriptor<service.PlayerWriteRequest, service.PlayerWriteResponse> getWriteMethod;
    if ((getWriteMethod = PlayersGrpc.getWriteMethod) == null) {
      synchronized (PlayersGrpc.class) {
        if ((getWriteMethod = PlayersGrpc.getWriteMethod) == null) {
          PlayersGrpc.getWriteMethod = getWriteMethod =
              io.grpc.MethodDescriptor.<service.PlayerWriteRequest, service.PlayerWriteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "write"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.PlayerWriteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.PlayerWriteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PlayersMethodDescriptorSupplier("write"))
              .build();
        }
      }
    }
    return getWriteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PlayersStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PlayersStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PlayersStub>() {
        @java.lang.Override
        public PlayersStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PlayersStub(channel, callOptions);
        }
      };
    return PlayersStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PlayersBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PlayersBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PlayersBlockingStub>() {
        @java.lang.Override
        public PlayersBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PlayersBlockingStub(channel, callOptions);
        }
      };
    return PlayersBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PlayersFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PlayersFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PlayersFutureStub>() {
        @java.lang.Override
        public PlayersFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PlayersFutureStub(channel, callOptions);
        }
      };
    return PlayersFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PlayersImplBase implements io.grpc.BindableService {

    /**
     */
    public void read(service.Empty request,
        io.grpc.stub.StreamObserver<service.PlayersReadResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getReadMethod(), responseObserver);
    }

    /**
     */
    public void write(service.PlayerWriteRequest request,
        io.grpc.stub.StreamObserver<service.PlayerWriteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getWriteMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getReadMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                service.Empty,
                service.PlayersReadResponse>(
                  this, METHODID_READ)))
          .addMethod(
            getWriteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                service.PlayerWriteRequest,
                service.PlayerWriteResponse>(
                  this, METHODID_WRITE)))
          .build();
    }
  }

  /**
   */
  public static final class PlayersStub extends io.grpc.stub.AbstractAsyncStub<PlayersStub> {
    private PlayersStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlayersStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PlayersStub(channel, callOptions);
    }

    /**
     */
    public void read(service.Empty request,
        io.grpc.stub.StreamObserver<service.PlayersReadResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReadMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void write(service.PlayerWriteRequest request,
        io.grpc.stub.StreamObserver<service.PlayerWriteResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getWriteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PlayersBlockingStub extends io.grpc.stub.AbstractBlockingStub<PlayersBlockingStub> {
    private PlayersBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlayersBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PlayersBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.PlayersReadResponse read(service.Empty request) {
      return blockingUnaryCall(
          getChannel(), getReadMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.PlayerWriteResponse write(service.PlayerWriteRequest request) {
      return blockingUnaryCall(
          getChannel(), getWriteMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PlayersFutureStub extends io.grpc.stub.AbstractFutureStub<PlayersFutureStub> {
    private PlayersFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlayersFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PlayersFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.PlayersReadResponse> read(
        service.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getReadMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.PlayerWriteResponse> write(
        service.PlayerWriteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getWriteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_READ = 0;
  private static final int METHODID_WRITE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PlayersImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PlayersImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_READ:
          serviceImpl.read((service.Empty) request,
              (io.grpc.stub.StreamObserver<service.PlayersReadResponse>) responseObserver);
          break;
        case METHODID_WRITE:
          serviceImpl.write((service.PlayerWriteRequest) request,
              (io.grpc.stub.StreamObserver<service.PlayerWriteResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PlayersBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PlayersBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.PlayersProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Players");
    }
  }

  private static final class PlayersFileDescriptorSupplier
      extends PlayersBaseDescriptorSupplier {
    PlayersFileDescriptorSupplier() {}
  }

  private static final class PlayersMethodDescriptorSupplier
      extends PlayersBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PlayersMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PlayersGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PlayersFileDescriptorSupplier())
              .addMethod(getReadMethod())
              .addMethod(getWriteMethod())
              .build();
        }
      }
    }
    return result;
  }
}

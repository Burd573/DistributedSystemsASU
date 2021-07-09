// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/players.proto

package service;

public final class PlayersProto {
  private PlayersProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_Player_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_Player_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_PlayersReadResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_PlayersReadResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_PlayerWriteRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_PlayerWriteRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_PlayerWriteResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_PlayerWriteResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\026services/players.proto\022\010services\032\024serv" +
      "ices/story.proto\"0\n\006Player\022\014\n\004name\030\001 \001(\t" +
      "\022\013\n\003num\030\002 \001(\005\022\013\n\003pos\030\003 \001(\t\"Y\n\023PlayersRea" +
      "dResponse\022\021\n\tisSuccess\030\001 \001(\010\022 \n\006player\030\002" +
      " \003(\0132\020.services.Player\022\r\n\005error\030\003 \001(\t\"6\n" +
      "\022PlayerWriteRequest\022 \n\006player\030\001 \001(\0132\020.se" +
      "rvices.Player\"7\n\023PlayerWriteResponse\022\021\n\t" +
      "isSuccess\030\001 \001(\010\022\r\n\005error\030\003 \001(\t2\213\001\n\007Playe" +
      "rs\0228\n\004read\022\017.services.Empty\032\035.services.P" +
      "layersReadResponse\"\000\022F\n\005write\022\034.services" +
      ".PlayerWriteRequest\032\035.services.PlayerWri" +
      "teResponse\"\000B\031\n\007serviceB\014PlayersProtoP\001b" +
      "\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          service.StoryProto.getDescriptor(),
        });
    internal_static_services_Player_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_services_Player_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_Player_descriptor,
        new java.lang.String[] { "Name", "Num", "Pos", });
    internal_static_services_PlayersReadResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_services_PlayersReadResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_PlayersReadResponse_descriptor,
        new java.lang.String[] { "IsSuccess", "Player", "Error", });
    internal_static_services_PlayerWriteRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_services_PlayerWriteRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_PlayerWriteRequest_descriptor,
        new java.lang.String[] { "Player", });
    internal_static_services_PlayerWriteResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_services_PlayerWriteResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_PlayerWriteResponse_descriptor,
        new java.lang.String[] { "IsSuccess", "Error", });
    service.StoryProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/players.proto

package service;

public interface PlayersReadResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:services.PlayersReadResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bool isSuccess = 1;</code>
   * @return The isSuccess.
   */
  boolean getIsSuccess();

  /**
   * <pre>
   * all the tips peers entered so far
   * </pre>
   *
   * <code>repeated .services.Player player = 2;</code>
   */
  java.util.List<service.Player> 
      getPlayerList();
  /**
   * <pre>
   * all the tips peers entered so far
   * </pre>
   *
   * <code>repeated .services.Player player = 2;</code>
   */
  service.Player getPlayer(int index);
  /**
   * <pre>
   * all the tips peers entered so far
   * </pre>
   *
   * <code>repeated .services.Player player = 2;</code>
   */
  int getPlayerCount();
  /**
   * <pre>
   * all the tips peers entered so far
   * </pre>
   *
   * <code>repeated .services.Player player = 2;</code>
   */
  java.util.List<? extends service.PlayerOrBuilder> 
      getPlayerOrBuilderList();
  /**
   * <pre>
   * all the tips peers entered so far
   * </pre>
   *
   * <code>repeated .services.Player player = 2;</code>
   */
  service.PlayerOrBuilder getPlayerOrBuilder(
      int index);

  /**
   * <pre>
   * Error message, a String of your choice to show what went wrong if an error occured
   * </pre>
   *
   * <code>string error = 3;</code>
   * @return The error.
   */
  java.lang.String getError();
  /**
   * <pre>
   * Error message, a String of your choice to show what went wrong if an error occured
   * </pre>
   *
   * <code>string error = 3;</code>
   * @return The bytes for error.
   */
  com.google.protobuf.ByteString
      getErrorBytes();
}
package utils;

import buffers.RequestProtos.Request;
import buffers.ResponseProtos.Entry;
import buffers.ResponseProtos.Response;
import server.Game;

import java.io.IOException;
import java.io.OutputStream;

public class ProtoUtils {

    public static void sendName(String n, OutputStream out) throws IOException {
        Request req = Request.newBuilder()
                .setOperationType(Request.OperationType.NAME)
                .setName(n).build();
        req.writeDelimitedTo(out);
    }

    public static void sendAnswer(String a, OutputStream out) throws IOException {
        Request req = Request.newBuilder()
                .setOperationType(Request.OperationType.ANSWER)
                .setAnswer(a).build();
        req.writeDelimitedTo(out);
    }

    public static void sendLeader(OutputStream out) throws IOException {
        Request req = Request.newBuilder()
                .setOperationType(Request.OperationType.LEADER).build();
        req.writeDelimitedTo(out);
    }

    public static void sendNewGame(OutputStream out) throws IOException {
        Request req = Request.newBuilder()
                .setOperationType(Request.OperationType.NEW).build();
        req.writeDelimitedTo(out);
    }

    public static void sendQuit(OutputStream out) throws IOException {
        Request req = Request.newBuilder()
                .setOperationType(Request.OperationType.QUIT).build();
        req.writeDelimitedTo(out);
    }

    public static Entry entryBuilder(String n, int win, int login) {
        return Entry.newBuilder()
                .setName(n)
                .setWins(win)
                .setLogins(login)
                .build();
    }

    public static void taskResponse(Game g, String t, OutputStream out) throws IOException {
        Response rep = Response.newBuilder()
                .setResponseType(Response.ResponseType.TASK)
                .setImage(g.getImage())
                .setTask(t)
                .build();
        rep.writeDelimitedTo(out);
    }

    public static void wonResponse(OutputStream out) throws IOException {
        Response rep = Response.newBuilder()
                .setResponseType(Response.ResponseType.WON)
                .build();
        rep.writeDelimitedTo(out);

    }

    public static void errorResponse(OutputStream out) throws IOException {
        Response rep = Response.newBuilder()
                .setResponseType(Response.ResponseType.ERROR)
                .build();
        rep.writeDelimitedTo(out);
    }

    public static void byeResponse(OutputStream out) throws IOException {
        Response rep = Response.newBuilder()
                .setResponseType(Response.ResponseType.BYE)
                .build();
        rep.writeDelimitedTo(out);
    }

    public static void greetResponse(String name, OutputStream out) throws IOException {
        Response rep = Response.newBuilder()
                .setResponseType(Response.ResponseType.GREETING)
                .setGreeting( "Hello " + name + " All aboard Blaine the Mono!")
                .build();
        rep.writeDelimitedTo(out);
    }
}

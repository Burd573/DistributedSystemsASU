package example.grpcclient;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerMethodDefinition;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import service.*;
import java.util.Stack;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import buffers.RequestProtos.Request;
import buffers.RequestProtos.Request.RequestType;
import buffers.ResponseProtos.Response;

public class TeamImpl extends PlayersGrpc.PlayersImplBase {

    List<Player> players = new ArrayList<>();

    public TeamImpl() {
        super();
    }

    public synchronized void read (Empty e, StreamObserver<PlayersReadResponse> responseObserver) {
        PlayersReadResponse.Builder response = PlayersReadResponse.newBuilder();
        for (Player p : players) {
            response.addAllPlayer(players);
        }
        response.setIsSuccess(true);
        PlayersReadResponse resp = response.build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public synchronized void write (PlayerWriteRequest req, StreamObserver<PlayerWriteResponse> responseObserver) {
        PlayerWriteResponse.Builder response = PlayerWriteResponse.newBuilder();

        if (!req.hasPlayer()) {
            response.setIsSuccess(false);
            response.setError("There was an issue adding the player");
            PlayerWriteResponse resp = response.build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();

        } else {
            Player newPlayer = req.getPlayer();
            System.out.println("Received from client: " + newPlayer.getName() + ": " + newPlayer.getNum() + ", " + newPlayer.getPos());
            players.add(newPlayer);
            response.setIsSuccess(true);
            PlayerWriteResponse resp = response.build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }
    }
}

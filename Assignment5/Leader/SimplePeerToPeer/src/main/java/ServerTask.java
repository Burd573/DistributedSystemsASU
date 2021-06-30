import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTask extends Thread {
    private BufferedReader bufferedReader;
    private Peer peer = null;
    private PrintWriter out = null;
    private Socket socket = null;

    public ServerTask(Socket socket, Peer peer) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.peer = peer;
        this.socket = socket;
    }

    public void run() {
        while (true) {
            try {
                JSONObject json = new JSONObject(bufferedReader.readLine());

                switch (json.getString("type")) {
                    case ("join"):
                        System.out.println(json.getString("username") + " wants to join the network");
                        peer.updateListenToPeers(json.getString("ip") + "-" + json.getInt("port") + "-" + json.getString("username") + "-" + json.getBoolean("leader"));
                        out.println(("{'type': 'join', 'list': '" + peer.getPeers() + "'}"));
                        if (peer.isLeader()) {
                            peer.pushMessage(json.toString());
                        }
                        break;
                    case ("remove"):
                        System.out.println("Removing user...");
                        peer.removePeer(json.getInt("port"));
                        out.println(json);
                        if (peer.isLeader()) {
                            peer.pushMessage(json.toString());
                        }
                        break;
                    case ("joke"):
                        System.out.println(json.getString("username") + " thinks they're funny...");
                        out.println(("{'type': 'joke', 'message': '"+json.getString("message")+"'}"));
                        if (peer.isLeader()) {
                            if (peer.getJokeAttempt() == null) {
                                peer.setJokeAttempt(json.getString("message"));
                            }
                            peer.pushMessage("{'type': 'jokeQuery', 'message': '"+peer.getJokeAttempt()+"'}");
                        }
                        break;
                    case ("jokeQuery"):
                        System.out.println("Vote to add joke: '"+json.getString("message")+"'");
                        break;
                    case ("yes"):
                        System.out.println(json.getString("username")+"' votes yes");
                        out.println(json);
                        if (peer.isLeader() && peer.getJokeAttempt() != null) {
                            peer.pushMessage("{'type': 'message', 'username': 'LEADER', 'message': '"+peer.getJokeAttempt()+" received a yes vote'}");
                            peer.addVote();
                            if (peer.getVotes() == peer.numPeers()) {
                                peer.addJoke(peer.getJokeAttempt());
                                peer.pushMessage("{'type': 'addJoke', 'message': '"+peer.getJokeAttempt()+"'}");
                                peer.setJokeAttempt(null);
                                peer.setVotes(0);
                            }
                        }
                        break;
                    case ("no"):
                        System.out.println(json.getString("username")+"' votes no");
                        out.println(json);
                        if (peer.isLeader() && peer.getJokeAttempt() != null) {
                            peer.pushMessage("{'type': 'message', 'username': 'LEADER', 'message': '"+peer.getJokeAttempt()+" received a no vote'}");
                            peer.resetVoteCount();
                        }
                        break;
                    case ("addJoke"):
                        System.out.println("Updating Jokes...");
                        peer.addJoke(json.getString("message"));
                        break;
                    default:
                        System.out.println("[" + json.getString("username")+"]: " + json.getString("message"));
                        break;
                }
            }
            catch (Exception e) {
                interrupt();
                break;
            }
        }
    }
}

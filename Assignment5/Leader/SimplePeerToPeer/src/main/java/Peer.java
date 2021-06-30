import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Peer {
    private String username;
    private BufferedReader bufferedReader;
    private ServerThread serverThread;

    private Set<SocketInfo> peers = new HashSet<SocketInfo>();
    private boolean leader = false;
    private SocketInfo leaderSocket;

    private String jokeAttempt = null;
    private List<Joke> jokes = new ArrayList<>();
    private int votes = 0;


    public Peer(BufferedReader bufReader, String username, ServerThread serverThread) {
        this.username = username;
        this.bufferedReader = bufReader;
        this.serverThread = serverThread;
    }


    public void setLeader(boolean leader, SocketInfo leaderSocket) {
        this.leader = leader;
        this.leaderSocket = leaderSocket;
    }

    public void resetVoteCount() {
        this.votes = 0;
    }

    public boolean isLeader() {
        return leader;
    }

    public void addPeer(SocketInfo si) {
        peers.add(si);
    }

    public void setJokeAttempt(String temp) {
        jokeAttempt = temp;
    }

    public String getJokeAttempt() {
        return jokeAttempt;
    }

    public void addVote() {
        votes++;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int count) {
        votes = count;
    }

    public String getPeers() {
        String peers = "";
        for (SocketInfo p : this.peers) {
            peers = peers + p.getHost() + "-" + p.getPort() + "-" + p.getName() + "-" + p.getLeader() + " ";

        }
        return peers;
    }

    public void printPeers() {
        String peers = "";
        for (SocketInfo p : this.peers) {
            System.out.println(p.getHost() + ":" + p.getPort() + " = " + p.getName());
        }
    }


    public String getJokesString() {
        String allJokes = "";
        for (Joke j : jokes) {
            allJokes += j.getJoke() + "\n";
        }
        return allJokes;
    }


    public void addJoke(String joke) {
        Joke newJoke = new Joke();
        newJoke.setJoke(joke);
        jokes.add(newJoke);
    }

    public int numPeers() {
        return peers.size();
    }

    public void updateListenToPeers(String list) throws Exception {
        String[] peerList = list.split(" ");
        for (String p : peerList) {

            String[] hostPort = p.split("-");

            if ((hostPort[0].equals("localhost") || hostPort[0].equals(serverThread.getHost())) && Integer.parseInt(hostPort[1]) == serverThread.getPort()) {
                continue;
            }
            SocketInfo socketInfo = new SocketInfo(hostPort[0], Integer.parseInt(hostPort[1]), hostPort[2], Boolean.parseBoolean(hostPort[3]));
            peers.add(socketInfo);
        }
    }

    public void removePeer(int portToRemove) {
        Set<SocketInfo> toRemove = new HashSet<SocketInfo>();
        for (SocketInfo p : peers) {
            if (p.getPort() == serverThread.getPort()) {
                continue;
            }
            if (p.getPort() == portToRemove) {
                toRemove.add(p);
            }
        }

        for (SocketInfo s : toRemove) {
            System.out.println(s.getName() + " disconnected");
            peers.remove(s);
        }
    }


    public void commLeader(String message) {
        try {
            BufferedReader reader = null;
            Socket socket = null;

            try {
                socket = new Socket(leaderSocket.getHost(), leaderSocket.getPort());
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception c) {
                if (socket != null) {
                    socket.close();
                } else {
                    System.out.println("Could not connect to " + leaderSocket.getHost() + ":" + leaderSocket.getPort());
                }
                return;
            }

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println(message);
            JSONObject json = new JSONObject(reader.readLine());

            switch (json.getString("type")) {
                case "join":
                    updateListenToPeers(json.getString("list"));
                    break;
                case "remove":
                    removePeer(json.getInt("port"));
                    break;
                case "yes":
                    System.out.println("Voted yes to add joke");
                    break;
                case "no":
                    System.out.println("voted no to add joke");
                    break;
            }

        } catch (Exception e) {
            System.out.println("Exception: Could not reach leader!");
            System.exit(2);
        }
    }


    public void pushMessage(String message) {
        try {
            System.out.println("Number of peers connected " + peers.size());

            Set<SocketInfo> toRemove = new HashSet<SocketInfo>();
            BufferedReader reader = null;
            int counter = 0;

            for (SocketInfo socketInfo : peers) {
                Socket socket = null;
                try {
                    socket = new Socket(socketInfo.getHost(), socketInfo.getPort());
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (Exception c) {
                    if (socket != null) {
                        socket.close();
                    } else {
                        System.out.println("Error communicating with peer: " + socketInfo.getHost() + ":" + socketInfo.getPort() + "  " + socketInfo.getName());
                        if (socketInfo.getLeader()) {
                            System.out.println("Removing leader peer!");
                            toRemove.add(socketInfo);
                        } else {
                            System.out.println("Removing pawn peer!");
                            toRemove.add(socketInfo);
                        }
                        continue;
                    }
                    System.out.println("Issue " + c);
                }

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
                counter++;
                socket.close();
            }

            if (!toRemove.isEmpty()) {
                for (SocketInfo s : toRemove) {
                    commLeader("{'type': 'remove','port':'" + s.getPort() + "'}");
                }
            }

            System.out.println("Message sent to " + counter + " peers");

        } catch (Exception e) {
            System.out.println("Could not send message!");
            System.exit(2);
        }
    }


    public void askForInput() throws Exception {
        try {
            System.out.println("> You can now start chatting (exit to exit)");
            label:
            while (true) {
                String message = bufferedReader.readLine();
                switch (message) {
                    case "exit":
                        System.out.println("Thank you for chatting ");
                        break label;
                    case "peers":
                        System.out.println("Peers: ");
                        printPeers();
                        break;
                    case "joke":
                        System.out.println("Enter joke: ");
                        String joke = bufferedReader.readLine();
                        commLeader("{'type': 'joke', 'username': '" + username + "', 'message': '" + joke + "'}");
                        break;
                    case "yes":
                        System.out.println("Yes vote received");
                        commLeader("{'type': 'yes', 'username': '" + username + "'}");
                        break;
                    case "no":
                        System.out.println("No vote received!");
                        commLeader("{'type': 'no', 'username': '" + username + "'}");
                        break;
                    case "jokes":
                        System.out.println("Jokes: ");
                        System.out.println(getJokesString());
                        break;
                    default:
                        System.out.println("Message sent");
                        pushMessage("{'type': 'message', 'username': '" + username + "','message':'" + message + "'}");
                        break;
                }
            }
            System.exit(0);

        } catch (Exception e) {
            System.out.println("No input received!");
            System.exit(2);
        }
    }


    public static void main(String[] args) throws Exception {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        if (args.length == 4) {
            System.out.println("Attempting to connect!");
        } else {
            System.out.println("Expected: <name(String)> <peer(String)> <leader(String)> <isLeader(bool-String)>");
            System.exit(0);
        }

        String username = args[0];
        String peerInfo = args[1];
        String leaderInfo = args[2];
        boolean isLeader = args[3].equalsIgnoreCase("true");

        String peerHost = null;
        int peerPort = 0;
        try {
            peerHost = peerInfo.substring(0, peerInfo.indexOf(":"));
            peerPort = Integer.parseInt(peerInfo.substring(peerInfo.indexOf(":") + 1));
        } catch (Exception e) {
            System.out.println("Error retrieving peer info");
            System.exit(2);
        }

        String leaderHost = null;
        int leaderPort = 0;
        try {
            leaderHost = leaderInfo.substring(0, leaderInfo.indexOf(":"));
            leaderPort = Integer.parseInt(leaderInfo.substring(leaderInfo.indexOf(":") + 1));
        } catch (Exception e) {
            System.out.println("Error retrieving leader info");
            System.exit(2);
        }

        ServerThread serverThread = new ServerThread(peerHost, peerPort, username, isLeader);
        Peer peer = new Peer(bufferedReader, username, serverThread);
        SocketInfo leaderSocket = new SocketInfo(leaderHost, leaderPort, username, true);

        if (isLeader) {
            System.out.println("Leader: " + username);
            peer.setLeader(true, leaderSocket);
        } else {
            peer.addPeer(leaderSocket);
            peer.setLeader(false, leaderSocket);
            peer.commLeader("{'type': 'join', 'username': '" + username + "','ip':'" + serverThread.getHost() + "','port':'" + serverThread.getPort() + "','leader':'" + serverThread.isLeader() + "'}");
        }

        System.out.println("Welcome " + username + "!");
        serverThread.setPeer(peer);
        serverThread.start();
        peer.askForInput();
    }

}


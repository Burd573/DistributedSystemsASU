package tasktwo;

import taskone.Performer;
import taskone.StringList;

import java.net.ServerSocket;
import java.net.Socket;

public class ThreadedServer extends Thread{

    private Socket sock;
    private StringList stringList;

    public ThreadedServer(Socket socket, StringList strings){
        this.sock = socket;
        this.stringList = strings;
    }


    public void run() {
        Performer performer = new Performer(sock, stringList);
        performer.doPerform();
        try {
            System.out.println("close socket of client ");
            this.sock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8000;
        StringList strings = new StringList();

        if (args.length != 1) {
            // gradle runServer -Pport=9099 -q --console=plain
            System.out.println("Usage: gradle runServer -Pport=9099 -q --console=plain");
            System.exit(1);
        }
        port = -1;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be an integer");
            System.exit(2);
        }

        ServerSocket server = new ServerSocket(port);
        System.out.println("Server Started...");
        while (true) {
            System.out.println("Accepting a Request...");
            Socket sock = server.accept();

            ThreadedServer threadedServer = new ThreadedServer(sock,strings);
            threadedServer.start();

        }
    }
}

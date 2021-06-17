package taskthree;
import taskone.Performer;
import taskone.StringList;
import tasktwo.ThreadedServer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker implements Runnable{
    protected Socket sock;
    protected StringList list;

    public Worker(Socket socket, StringList strings){
        this.sock = socket;
        this.list = strings;
    }
    public void run () {
        Performer performer = new Performer(this.sock, this.list);
        performer.doPerform();
        try {
            System.out.println("close socket of client ");
            this.sock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class ThreadPoolServer extends Thread{

    public static void main(String[] args) throws Exception {
        int port = 8000;
        StringList strings = new StringList();
        int numWorkers;
        ExecutorService executor;

        if (args.length != 2) {
            // gradle runServer -Pport=9099 -q --console=plain
            System.out.println("Usage: gradle runServer -Pport=9099 -q --console=plain");
            System.exit(1);
        }
        port = -1;
        numWorkers = -1;

        try {
            port = Integer.parseInt(args[0]);
            numWorkers = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be an integer");
            System.exit(2);
        }

        ServerSocket server = new ServerSocket(port);
        executor = Executors.newFixedThreadPool(numWorkers);
        System.out.println("Server Started...");
        while (true) {
            System.out.println("Accepting a Request...");
            Socket sock = server.accept();

            Runnable worker = new Worker(sock, strings);
            executor.execute(worker);

        }
    }
}

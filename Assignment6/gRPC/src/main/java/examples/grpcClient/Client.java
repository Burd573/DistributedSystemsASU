package example.grpcclient;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import service.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Client that requests `parrot` method from the `EchoServer`.
 */
public class Client {
    private final EchoGrpc.EchoBlockingStub blockingStub;
    private final JokeGrpc.JokeBlockingStub blockingStub2;
    private final RegistryGrpc.RegistryBlockingStub blockingStub3;
    private final CalcGrpc.CalcBlockingStub blockingStub4;
    private final TipsGrpc.TipsBlockingStub blockingStub5;
    private final PlayersGrpc.PlayersBlockingStub blockingStub6;

    /**
     * Construct client for accessing server using the existing channel.
     */
    public Client(Channel channel, Channel regChannel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
        // responsibility to
        // shut it down.

        // Passing Channels to code makes code easier to test and makes it easier to
        // reuse Channels.
        blockingStub = EchoGrpc.newBlockingStub(channel);
        blockingStub2 = JokeGrpc.newBlockingStub(channel);
        blockingStub3 = RegistryGrpc.newBlockingStub(regChannel);
        blockingStub4 = CalcGrpc.newBlockingStub(channel);
        blockingStub5 = TipsGrpc.newBlockingStub(channel);
        blockingStub6 = PlayersGrpc.newBlockingStub(channel);
    }

    public void askServerToParrot(String message) {
        ClientRequest request = ClientRequest.newBuilder().setMessage(message).build();
        ServerResponse response;
        try {
            response = blockingStub.parrot(request);
        } catch (Exception e) {
            System.err.println("RPC failed: " + e.getMessage());
            return;
        }
        System.out.println("Received from server: " + response.getMessage());
    }

    public void askForJokes(int num) {
        JokeReq request = JokeReq.newBuilder().setNumber(num).build();
        JokeRes response;

        try {
            response = blockingStub2.getJoke(request);
        } catch (Exception e) {
            System.err.println("RPC failed: " + e);
            return;
        }
        System.out.println("Your jokes: ");
        for (String joke : response.getJokeList()) {
            System.out.println("--- " + joke);
        }
    }

    public void setJoke(String joke) {
        JokeSetReq request = JokeSetReq.newBuilder().setJoke(joke).build();
        JokeSetRes response;

        try {
            response = blockingStub2.setJoke(request);
            System.out.println(response.getOk());
        } catch (Exception e) {
            System.err.println("RPC failed: " + e);
            return;
        }
    }

    public void getServices() {
        GetServicesReq request = GetServicesReq.newBuilder().build();
        ServicesListRes response;
        try {
            response = blockingStub3.getServices(request);
            System.out.println(response.toString());
        } catch (Exception e) {
            System.err.println("RPC failed: " + e);
            return;
        }
    }

    public void findServer(String name) {
        FindServerReq request = FindServerReq.newBuilder().setServiceName(name).build();
        SingleServerRes response;
        try {
            response = blockingStub3.findServer(request);
            System.out.println(response.toString());
        } catch (Exception e) {
            System.err.println("RPC failed: " + e);
            return;
        }
    }

    public void findServers(String name) {
        FindServersReq request = FindServersReq.newBuilder().setServiceName(name).build();
        ServerListRes response;
        try {
            response = blockingStub3.findServers(request);
            System.out.println(response.toString());
        } catch (Exception e) {
            System.err.println("RPC failed: " + e);
            return;
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 5) {
            System.out
                    .println("Expected arguments: <host(String)> <port(int)> <regHost(string)> <regPort(int)> <message(String)>");
            System.exit(1);
        }
        int port = 9099;
        int regPort = 9003;
        String host = args[0];
        String regHost = args[2];
        String message = args[4];
        try {
            port = Integer.parseInt(args[1]);
            regPort = Integer.parseInt(args[3]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be an integer");
            System.exit(2);
        }

        // Create a communication channel to the server, known as a Channel. Channels
        // are thread-safe
        // and reusable. It is common to create channels at the beginning of your
        // application and reuse
        // them until the application shuts down.
        String target = host + ":" + port;


        // ask the user for input how many jokes the user wants
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));




        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS
                // to avoid
                // needing certificates.
                .usePlaintext().build();

        String regTarget = regHost + ":" + regPort;
        ManagedChannel regChannel = ManagedChannelBuilder.forTarget(regTarget).usePlaintext().build();
        try {

            // ##############################################################################
            // ## Assume we know the port here from the service node it is basically set through Gradle
            // here.
            // In your version you should first contact the registry to check which services
            // are available and what the port
            // etc is.

            /**
             * Your client should start off with
             * 1. contacting the Registry to check for the available services
             * 2. List the services in the terminal and the client can
             *    choose one (preferably through numbering)
             * 3. Based on what the client chooses
             *    the terminal should ask for input, eg. a new sentence, a sorting array or
             *    whatever the request needs
             * 4. The request should be sent to one of the
             *    available services (client should call the registry again and ask for a
             *    Server providing the chosen service) should send the request to this service and
             *    return the response in a good way to the client
             *
             * You should make sure your client does not crash in case the service node
             * crashes or went offline.
             */

            // Just doing some hard coded calls to the service node without using the
            // registry
            // create client
//            example.grpcclient.EchoClient client = new example.grpcclient.EchoClient(channel, regChannel);


            Client client = new Client(channel,regChannel);
            System.out.println("Available Services:");
            client.getServices();

            System.out.println("What would you like to do");






            while (true) {
                int num;
                System.out.println("Please select an option");
                System.out.println("1. Echo");
                System.out.println("2. Joke");
                System.out.println("3. Calc");
                System.out.println("4. Tips");
                System.out.println("5. Team");
                num = Integer.parseInt(reader.readLine());

                switch (num) {
                    case (1):
                        System.out.println("Enter message: ");
                        String in = reader.readLine();
                        client.askServerToParrot(in);
                        break;
                    case (2):
                        // Reading data using readLine
                        System.out.println("How many jokes would you like?"); // NO ERROR handling of wrong input here.
                        String numJokes = reader.readLine();

                        // calling the joked service from the server with num from user input
                        client.askForJokes(Integer.valueOf(numJokes));

                        // adding a joke to the server
                        client.setJoke("I made a pencil with two erasers. It was pointless.");

                        // showing 6 joked
                        client.askForJokes(Integer.valueOf(6));
                        break;
                    case (3):
                        client.Calc();
                        break;
                    case (4):
                        client.Tips();
                        break;
                    case (5):
                        client.Players();
                        break;
                    default:
                        System.out.println("Enter a number between 1-5");
                        break;
                }

            }


        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent
            // leaking these
            // resources the channel should be shut down when it will no longer be used. If
            // it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
            regChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }



    public void Calc() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            LinkedList<Double> list = getInput();
            CalcResponse response = null;
            System.out.println("What would you like to do? \n1. Add \n2. Subtract \n3. Multiply \n4. Divide\n");
            Integer input = Integer.parseInt(reader.readLine());
            CalcRequest request = null;
            switch (input) {
                case (1):
                    request = CalcRequest.newBuilder().addAllNum(list).build();
                    response = blockingStub4.add(request);
                    break;
                case (2):
                    request = CalcRequest.newBuilder().addAllNum(list).build();
                    response = blockingStub4.subtract(request);
                    break;
                case (3):
                    request = CalcRequest.newBuilder().addAllNum(list).build();
                    response = blockingStub4.multiply(request);
                    break;
                case (4):
                    request = CalcRequest.newBuilder().addAllNum(list).build();
                    response = blockingStub4.divide(request);
                    break;
                default:
                    System.out.println("Invalid Input. Please enter a number between 1 and 4");
                    Calc();
            }
            double res = response.getSolution();
            System.out.println("Result: " + res + "\n");

        } catch (IOException e) {
            System.out.println("Invalid Input. Please enter a number between 1 and 4");
        }
    }

    public static LinkedList<Double> getInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LinkedList<Double> list = new LinkedList<>();

        while (true) {
            System.out.println("Enter a number or \"stop\" to stop : ");

            try {
                String input = reader.readLine();
                if (input.equalsIgnoreCase("stop")) {
                    if (list.size() < 2) {
                        System.out.println("Enter at least two numbers");
                    } else {
                        return list;
                    }
                } else {
                    list.add(Double.parseDouble(input));
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a number or \"stop\" to stop");
            } catch (IOException e) {
                System.out.println("Error reading data");
            }

        }
    }

    public void Tips() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("What would you like to do?");
                System.out.println("1. Read tips");
                System.out.println("2. Add a new tip");
                int input = Integer.parseInt(reader.readLine());
                switch (input) {
                    case (1):
                        readTips();
                        return;
                    case (2):
                        addNewTip();
                        return;
                    default:
                        System.out.println("Enter a valid option (1 or 2)");
                }
            } catch (IOException e) {
                System.err.println("Error receiving input");
            } catch (Exception e) {
                System.err.println("Error. Please try again later");
            }
        }
    }

    public void readTips() throws Exception {
        Empty e = service.Empty.newBuilder().build();
        TipsReadResponse response = blockingStub5.read(e);
        if (!response.getIsSuccess()) {
            throw new Exception();
        }
        List<Tip> tips = response.getTipsList();
        for (Tip t : tips) {
            System.out.println(t.getName() + ": " + t.getTip());
        }
    }

    public void addNewTip() throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter tip Name");
            String name = reader.readLine();

            System.out.println("Enter tip");
            String tip = reader.readLine();

            Tip t = Tip.newBuilder().setName(name).setTip(tip).build();
            TipsWriteRequest request = TipsWriteRequest.newBuilder().setTip(t).build();
            TipsWriteResponse response = blockingStub5.write(request);
            if (response.getIsSuccess()) {
                System.out.println("New tip added");
            } else {
                new Exception();
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void Players() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("What would you like to do?");
                System.out.println("1. View players");
                System.out.println("2. Add a new player");
                int input = Integer.parseInt(reader.readLine());
                switch (input) {
                    case (1):
                        viewPlayers();
                        return;
                    case (2):
                        addPlayers();
                        return;
                    default:
                        System.out.println("Enter a valid option (1 or 2)");
                }
            } catch (IOException e) {
                System.err.println("Error receiving input");
            }
        }
    }

    public void viewPlayers() throws Exception {
        Empty e = service.Empty.newBuilder().build();
        PlayersReadResponse response = blockingStub6.read(e);
        if (!response.getIsSuccess()) {
            throw new Exception();
        }
        List<Player> players = response.getPlayerList();
        for (Player p : players) {
            System.out.println(p.getName() + ": " + p.getNum() + " [" + p.getPos() + "]");
        }
    }

    public void addPlayers() throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter player Name: ");
            String name = reader.readLine();

            System.out.println("Enter player number: ");
            Integer num = Integer.parseInt(reader.readLine());

            System.out.println("Enter player position: ");
            String pos = reader.readLine();

            Player p = Player.newBuilder().setName(name).setNum(num).setPos(pos).build();
            PlayerWriteRequest request = PlayerWriteRequest.newBuilder().setPlayer(p).build();
            PlayerWriteResponse response = blockingStub6.write(request);
            if (response.getIsSuccess()) {
                System.out.println("New player added");
            } else {
                new Exception();
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

}

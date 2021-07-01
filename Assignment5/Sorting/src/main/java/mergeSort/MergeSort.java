package mergeSort;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MergeSort {
  /**
   * Thread that declares the lambda and then initiates the work
   */

  public static int message_id = 0;

  public static JSONObject init(int[] array) {
    JSONArray arr = new JSONArray();
    for (var i : array) {
      arr.put(i);
    }
    JSONObject req = new JSONObject();
    req.put("method", "init");
    req.put("data", arr);
    return req;
  }

  public static JSONObject peek() {
    JSONObject req = new JSONObject();
    req.put("method", "peek");
    return req;
  }

  public static JSONObject remove() {
    JSONObject req = new JSONObject();
    req.put("method", "remove");
    return req;
  }

  public static void Test(int port, String host) {
    int[] a = { 5, 1, 6, 2, 3, 4, 10,634,34,23,653, 23,2 ,6};
    JSONObject response = NetworkUtils.send(host, port, init(a));

    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);

    long begin = System.nanoTime();
    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        System.out.println(response);;

      } else{
        break;
      }
    }
    long end = System.nanoTime();
    long totalTime = (end-begin)/1000000;
    System.out.println("Total elements: " + a.length);
    System.out.println("\nTotal Time: " + totalTime);
  }

  public static void test10(int port, String host) {
    Random rand = new Random();
    int[] arr = new int[10];
    for(int i = 0; i < arr.length; i++) {
      arr[i] = rand.nextInt(500);
    }

    JSONObject response = NetworkUtils.send(host, port, init(arr));

    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);
    long begin = System.nanoTime();

    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        System.out.println(response);;

      } else{
        break;
      }
    }
    long end = System.nanoTime();
    long totalTime = (end-begin)/1000000;
    System.out.println("Total elements: " + arr.length);
    System.out.println("\nTotal Time: " + totalTime);
  }

  public static void test20(int port, String host) {
    Random rand = new Random();
    int[] arr = new int[20];
    for(int i = 0; i < arr.length; i++) {
      arr[i] = rand.nextInt(500);
    }

    JSONObject response = NetworkUtils.send(host, port, init(arr));

    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);
    long begin = System.nanoTime();

    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        System.out.println(response);;

      } else{
        break;
      }
    }
    long end = System.nanoTime();
    long totalTime = (end-begin)/1000000;
    System.out.println("Total elements: " + arr.length);
    System.out.println("\nTotal Time: " + totalTime);
  }

  public static void test30(int port, String host) {
    Random rand = new Random();
    int[] arr = new int[30];
    for(int i = 0; i < arr.length; i++) {
      arr[i] = rand.nextInt(500);
    }

    JSONObject response = NetworkUtils.send(host, port, init(arr));

    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);
    long begin = System.nanoTime();

    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        System.out.println(response);;

      } else{
        break;
      }
    }
    long end = System.nanoTime();
    long totalTime = (end-begin)/1000000;
    System.out.println("Total elements: " + arr.length);
    System.out.println("\nTotal Time: " + totalTime);
  }


  public static void test250(int port, String host) {
    Random rand = new Random();
    int[] arr = new int[250];
    for(int i = 0; i < arr.length; i++) {
      arr[i] = rand.nextInt(500);
    }

    JSONObject response = NetworkUtils.send(host, port, init(arr));

    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);
    long begin = System.nanoTime();

    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        System.out.println(response);;

      } else{
        break;
      }
    }
    long end = System.nanoTime();
    long totalTime = (end-begin)/1000000;
    System.out.println("Total elements: " + arr.length);
    System.out.println("\nTotal Time: " + totalTime);
  }

  public static void test500(int port, String host) {
    Random rand = new Random();
    int[] arr = new int[500];
    for(int i = 0; i < arr.length; i++) {
      arr[i] = rand.nextInt(500);
    }

    JSONObject response = NetworkUtils.send(host, port, init(arr));

    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);
    long begin = System.nanoTime();

    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        System.out.println(response);;

      } else{
        break;
      }
    }
    long end = System.nanoTime();
    long totalTime = (end-begin)/1000000;
    System.out.println("Total elements: " + arr.length);
    System.out.println("\nTotal Time: " + totalTime);
  }

  public static void main(String[] args) {

    // use the port of one of the branches to test things
    Test(Integer.valueOf(args[0]), args[1]);
    test10(Integer.valueOf(args[0]), args[1]);
    test20(Integer.valueOf(args[0]), args[1]);
    test30(Integer.valueOf(args[0]), args[1]);
    test250(Integer.valueOf(args[0]), args[1]);
    test500(Integer.valueOf(args[0]), args[1]);

  }
}

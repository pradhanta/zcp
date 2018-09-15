package com.zcp.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * This class contains the main logic for listening to a port and handling any incoming requests
 */
public class Listener {

    /**
     * Stores the port the particular listener instance is listening to
     */
    private int listeningPort;

    /**
     * Default constructor for instantiating a Listener with a port. The listener will listen to the provided
     * port when it is started
     * @param port
     */
    public Listener(int port) {
        listeningPort = port;
    }

    /**
     * Starts the listener on the specified port
     */
    public void start(ExecutorService service) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(listeningPort);
        while(!Thread.currentThread().isInterrupted()) {
            Socket socket = serverSocket.accept();
            service.execute(new Unpacker(socket, new PacketHandler()));
        }
    }


    /**
     * Represents the stand-alone starting point for the Listener.
     * @param args accepts command line arguments for the port number.
     */
    public static void main(String[] args) throws IOException{
        ExecutorService service = Executors.newCachedThreadPool();
        int portNumber = 12345;
        new Listener(portNumber).start(service);
    }
}

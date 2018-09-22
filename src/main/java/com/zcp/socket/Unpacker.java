package com.zcp.socket;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Main class to unpack a transmitted sequence into Packet representation
 */
public class Unpacker implements Runnable {

    final static Pattern FIELD_PATTERN = Pattern.compile("^(\\w+) (.*)$");
    final static byte PACKET_START_MARKER = 0x02;
    final static byte PACKET_END_MARKER = 0x03;
    final static int PACKET_MESSAGE_LENGTH_START = 1;
    final static int PACKET_MESSAGE_LENGTH_SIZE = 4;
    final static int PACKET_HEADER_LENGTH_START = PACKET_MESSAGE_LENGTH_START + PACKET_MESSAGE_LENGTH_SIZE;
    final static int PACKET_HEADER_LENGTH_SIZE = 3;
    final static String STAY_ALIVE_MESSAGE = "STAY";
    final static String QUIT_COMMAND = "PACKET_FINISH"; //no equivalent socket is closed by other party


    /**
     * The socket which to listen to unpack the request
     */
    Socket socket;

    /**
     *
     */
    PacketHandler handler;

    /**
     * Constructor to correctly instantiate an deserializer to handle an inbound connection and
     * create a packet
     */
    public Unpacker(Socket socket, PacketHandler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    private void processPacket(Packet packet) throws IOException {
        handler.receivedPacket(packet);
    }

    @Override
    public void run(){

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream())) {

            do {
                Packet packet = readPacket(bufferedInputStream);
                if(packet != null) {
                    processPacket(packet);
                }
            } while(socket.isConnected());
        } catch (Exception ex) {
            // handle exception
            Logger.getRootLogger().error("Error occurred while handling socket", ex);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * reads the header of
     * @return
     */
    private Packet readPacket(BufferedInputStream inputStream) throws IOException{
        Packet packet = null;
        //header is 8 bytes long containing start byte, message length (4 byte) and header length (3 byte)
        byte [] packetHeaderBuffer = new byte[8];
        inputStream.read(packetHeaderBuffer, 0, 8);
        if(packetHeaderBuffer[0] == PACKET_START_MARKER) {
            packet = new Packet();
            String messageLengthString = new String(Arrays.copyOfRange(packetHeaderBuffer,
                    PACKET_MESSAGE_LENGTH_START, PACKET_MESSAGE_LENGTH_START + PACKET_MESSAGE_LENGTH_SIZE),
                    StandardCharsets.US_ASCII);
            int messageLength = Integer.parseInt(messageLengthString);
            String headerLengthString = new String(Arrays.copyOfRange(packetHeaderBuffer,
                    PACKET_HEADER_LENGTH_START, PACKET_HEADER_LENGTH_START + PACKET_HEADER_LENGTH_SIZE),
                    StandardCharsets.US_ASCII);
            int headerLength = Integer.parseInt(headerLengthString);

            if(headerLength > 0) {
                byte[] header = new byte[headerLength];
                inputStream.read(header, 0, headerLength);
                packet.setHeader(new String(header));
            }

            int messageSize = messageLength - (PACKET_HEADER_LENGTH_SIZE
                    + headerLength + 2);
            if(messageSize > 0) {
                byte[] message = new byte[messageSize];
                inputStream.read(message, 0, messageSize);
                packet.setMessage(new String(message, StandardCharsets.US_ASCII));
            }

            byte[] endByte = new byte[1];
            inputStream.read(endByte, 0, 1);

            if(endByte[0] != PACKET_END_MARKER) {
                throw new EOFException("Invalid packet end");
            }

            Logger.getRootLogger().info("Read message: " + packet.toJSON());

        }

        return packet;
    }
}

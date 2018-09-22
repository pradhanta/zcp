package com.zcp.socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Sender {

    /**
     * Connects to the local port and sends out message
     * @param args
     */
    public static void main(String[] args) throws IOException {
        /*6000 - 6100*/
        try(Socket echoSocket = new Socket("localhost", 12345)) {
            BufferedOutputStream outputStream = new BufferedOutputStream(echoSocket.getOutputStream());
            outputStream.write(0x02);

            int messageLength;

            String header = "HELLOWORLD";

            String message = "{\"BIN\": \"020016\", \"Version\": \"D0\" }";
            byte[] messageBytes = message.getBytes(StandardCharsets.US_ASCII);

            byte [] headerBytes = header.getBytes(StandardCharsets.US_ASCII);

            byte [] headerLengthBytes = String.format("%03d", headerBytes.length).getBytes(StandardCharsets.US_ASCII);

            messageLength = messageBytes.length + 4 + headerBytes.length + 1; //1 for ETX and 4 bytes for message length
            byte [] messageLengthBytes = String.format("%04d", messageLength).getBytes(StandardCharsets.US_ASCII);

            outputStream.write(messageLengthBytes);
            outputStream.write(headerLengthBytes);
            outputStream.write(headerBytes);
            outputStream.write(messageBytes);

            outputStream.write(0x03);
            outputStream.flush();
        }

    }
}

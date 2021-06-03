package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Random;

import ro.pub.cs.systems.eim.practicaltest02.utils.Constants;
import ro.pub.cs.systems.eim.practicaltest02.utils.Utilities;

public class ServerCommunicationThread extends Thread {

    private Socket socket;
    public TextView minuteTextView;
    public TextView hourTextView;

    private Random random = new Random();

    private String expectedWordPrefix = new String();

    public ServerCommunicationThread(Socket socket, TextView hourTextView, TextView minuteTextView) {
        if (socket != null) {
            this.socket = socket;
            Log.d(Constants.TAG, "[SERVER] Created communication thread with: " + socket.getInetAddress() + ":" + socket.getLocalPort());
        }
        this.hourTextView = hourTextView;
        this.minuteTextView = minuteTextView;
    }

    public void run() {
        try {
            if (socket == null) {
                return;
            }
            boolean isRunning = true;
            BufferedReader requestReader = Utilities.getReader(socket);
            PrintWriter responsePrintWriter = Utilities.getWriter(socket);

            while (isRunning) {
                Log.d(Constants.TAG, "[SERVER] Waiting to receive data with prefix \"" + expectedWordPrefix + "\" on socket " + socket);
                String request = requestReader.readLine();
                final String finalizedRequest1 = request;


                Log.d(Constants.TAG, "[SERVER] Received " + request + " on socket " + socket);
            }
            socket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }
}
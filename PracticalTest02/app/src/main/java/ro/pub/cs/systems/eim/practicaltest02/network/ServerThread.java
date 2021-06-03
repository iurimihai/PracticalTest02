package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import cz.msebera.android.httpclient.client.ClientProtocolException;
import ro.pub.cs.systems.eim.practicaltest02.models.Time;
import ro.pub.cs.systems.eim.practicaltest02.utils.Constants;

public class ServerThread extends Thread {

    private boolean isRunning;

    private ServerSocket serverSocket;

    private TextView hourTextView;
    private TextView minuteTextView;

    public ServerThread(ServerSocket socket, TextView hourTextView, TextView minuteTextView) {
        this.serverSocket = socket;
        this.hourTextView = hourTextView;
        this.minuteTextView = minuteTextView;
    }

    public ServerThread(TextView hourTextView, TextView minuteTextView) {
        this.hourTextView = hourTextView;
        this.minuteTextView = minuteTextView;
        try {
            Log.d(Constants.TAG, "[SERVER] Created server thread, listening on port " + Constants.SERVER_PORT);
            serverSocket = new ServerSocket(Constants.SERVER_PORT);
            isRunning = true;
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                Log.d(Constants.TAG, "[SERVER] Incomming communication " + socket.getInetAddress() + ":" + socket.getLocalPort());
            } catch (SocketException socketException) {
                Log.e(Constants.TAG, "An exception has occurred: " + socketException.getMessage());
                if (Constants.DEBUG) {
                    socketException.printStackTrace();
                }
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
            if (socket != null) {
                ServerCommunicationThread serverCommunicationThread = new ServerCommunicationThread(socket, hourTextView, minuteTextView);
                serverCommunicationThread.start();
            }
        }
    }

    public void stopServer() {
        try {
            isRunning = false;
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch(IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }
}

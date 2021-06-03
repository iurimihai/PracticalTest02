package ro.pub.cs.systems.eim.practicaltest02.network;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import ro.pub.cs.systems.eim.practicaltest02.utils.Constants;
import ro.pub.cs.systems.eim.practicaltest02.utils.Utilities;

public class ClientCommunicationThread {

    private Socket socket = null;

    private String mostRecentWordSent = new String();
    private String mostRecentValidPrefix = new String();

    private Context context;
    private Handler handler;
    private Button sendButton;
    private TextView hourTextView;
    private TextView minuteTextView;
    private Button setButton;
    private Button resetButton;
    private Button pollButton;

    public ClientCommunicationThread(Socket socket, Context context, Handler handler,
                               Button setButton, Button pollButton, Button resetButton,
                               TextView minuteTextView, TextView hourTextView) {
        this.socket = socket;
        this.context = context;
        this.handler = handler;
        this.setButton = setButton;
        this.resetButton = resetButton;
        this.pollButton = pollButton;
        this.hourTextView = hourTextView;
        this.minuteTextView = minuteTextView;
        if (socket == null) {
            try {
                socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT);
            } catch (UnknownHostException unknownHostException) {
                Log.e(Constants.TAG, "An exception has occurred: " + unknownHostException.getMessage());
                if (Constants.DEBUG) {
                    unknownHostException.printStackTrace();
                }
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
        }
        Log.d(Constants.TAG, "[CLIENT] Created communication thread with: " + socket.getInetAddress() + ":" + socket.getLocalPort());
    }

    public void run() {
        try {
            BufferedReader responseReader = Utilities.getReader(socket);
            PrintWriter requestPrintWriter = Utilities.getWriter(socket);

            final String response = responseReader.readLine();
            Log.d(Constants.TAG, "[CLIENT] Received \"" + response + "\", most recent word was \"" + mostRecentWordSent + "\" on socket " + socket);

        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }
}
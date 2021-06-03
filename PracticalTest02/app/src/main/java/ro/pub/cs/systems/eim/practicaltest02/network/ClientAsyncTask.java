package ro.pub.cs.systems.eim.practicaltest02.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02.utils.Constants;
import ro.pub.cs.systems.eim.practicaltest02.utils.Utilities;

public class ClientAsyncTask extends AsyncTask<String, String, Void> {

    public TextView minuteMessageTextView;
    public TextView hourMessageTextView;

    public ClientAsyncTask(TextView hourMessageTextView, TextView minuteMessageTextView) {
        this.minuteMessageTextView = minuteMessageTextView;
        this.hourMessageTextView = hourMessageTextView;
    }

    @Override
    protected Void doInBackground(String... params) {
        Socket socket = null;
        try {
            String serverAddress = params[0];
            int serverPort = Integer.parseInt(params[1]);
            socket = new Socket(serverAddress, serverPort);
            if (socket == null) {
                return null;
            }
            Log.v(Constants.TAG, "Connection opened with " + socket.getInetAddress() + ":" + socket.getLocalPort());
            BufferedReader bufferedReader = Utilities.getReader(socket);
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                publishProgress(currentLine);
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                Log.v(Constants.TAG, "Connection closed");
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(String... progress) {
    }

    @Override
    protected void onPostExecute(Void result) {}

}

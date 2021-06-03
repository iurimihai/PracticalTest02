package ro.pub.cs.systems.eim.practicaltest02;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ro.pub.cs.systems.eim.practicaltest02.network.ServerThread;

public class ServerFragment extends Fragment {

    private TextView hourMessageTextView;
    private TextView minuteMessageTextView;

    private ServerThread serverThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        return inflater.inflate(R.layout.activity_main, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        hourMessageTextView = (TextView)getActivity().findViewById(R.id.hour_text);
        minuteMessageTextView = (TextView)getActivity().findViewById(R.id.minute_text);
    }

    @Override
    public void onDestroy() {
        if (serverThread != null) {
            serverThread.stopServer();
        }
        super.onDestroy();
    }

}
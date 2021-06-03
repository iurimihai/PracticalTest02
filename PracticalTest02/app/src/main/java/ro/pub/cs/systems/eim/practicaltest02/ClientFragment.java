package ro.pub.cs.systems.eim.practicaltest02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ro.pub.cs.systems.eim.practicaltest02.network.ClientAsyncTask;

public class ClientFragment extends Fragment {

    private TextView hourMessageTextView;
    private TextView minuteMessageTextView;
    private Button SetButton;
    private Button ResetButton;
    private Button PollButton;


    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ClientAsyncTask clientAsyncTask = new ClientAsyncTask(hourMessageTextView, minuteMessageTextView);
            clientAsyncTask.execute(hourMessageTextView.getText().toString(), minuteMessageTextView.getText().toString());
        }
    }

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

}
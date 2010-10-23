package com.amplafi.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.amplafi.android.task.SendTextTask;
import com.amplafi.android.task.SendTextTask.SendTextRequestor;

public class MessageEditorActivity extends Activity
implements SendTextRequestor {

    private static final String TAG = "MessageEditorActivity";
    private Button mSubmitButton;
    private Button mEditButton;
    private Button mGetButton;

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_edit_screen);
        mContext = this;

        mSubmitButton = (Button) findViewById(R.id.send_text_to_server);
        mEditButton   = (Button) findViewById(R.id.send_edit_to_server);
        mGetButton    = (Button) findViewById(R.id.get_texts_from_server);

        mSubmitButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sendTextToServer();
            }

        });
    }

    private void sendTextToServer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Send To Server");
        builder.setMessage("Send my message to the Server")
            .setPositiveButton("of course", new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .create().show();

        /*
         * start an asynchronous task that will send the message to the server
         * implement an interface that will receive the notification when the
         * server responds
         */
        SendTextTask sendTask = new SendTextTask(this);
        sendTask.execute("My text to send");

    }

    /**
     * @see com.amplafi.android.task.SendTextTask.SendTextRequestor#taskStarted()
     */
    @Override
    public void taskStarted() {
    }

    /**
     * @see com.amplafi.android.task.SendTextTask.SendTextRequestor#taskCancelled()
     */
    @Override
    public void taskCancelled() {
        throw new UnsupportedOperationException("SendTextRequestor.taskCancelled");
    }

    /**
     * @see com.amplafi.android.task.SendTextTask.SendTextRequestor#taskCompleted(java.lang.String)
     */
    @Override
    public void taskCompleted(String result) {
        Log.d(TAG, "I got '" + result + "'");

    }

}
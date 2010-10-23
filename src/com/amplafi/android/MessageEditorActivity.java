package com.amplafi.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MessageEditorActivity extends Activity {
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
            .create().show();
    }
}
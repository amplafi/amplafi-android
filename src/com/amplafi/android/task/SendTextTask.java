/**
 * Copyright 2006-2011 by Amplafi. All rights reserved.
 * Confidential.
 */
package com.amplafi.android.task;

import android.os.AsyncTask;

/**
 * @author patmoore
 *
 */
public class SendTextTask extends AsyncTask<String, Void, String> {
    public interface SendTextRequestor {
        public void taskStarted();
        public void taskCancelled();
        public void taskCompleted(String result);
    }

    private SendTextRequestor mRequestor;

    public SendTextTask(SendTextRequestor requestor) {
        mRequestor = requestor;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mRequestor.taskStarted();
    }

    @Override
    protected String doInBackground(String... testToSend) {
        // create an Http call to the service
        // POST
        // get the answer
        // return the JSON from the answer as the return value of this method
        return "I'm done";
    }

    @Override
    protected void onPostExecute(String result) {
        mRequestor.taskCompleted(result);
    }


}

/**
 * Copyright 2006-2011 by Amplafi. All rights reserved.
 * Confidential.
 */
package com.amplafi.android.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;

/**
 * @author patmoore
 *
 */
public class SendTextTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "SendTextTask";
    
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
    protected String doInBackground(String... params) {
        String output = "I'm done.";
        try {
            JSONObject jsonObject = new JSONObject(getData(params));
        } catch (Exception e) {
            throw new IllegalStateException("Error while trying to access " + params);
        }
        
        return output;
    }
    
    private String getData(String[] params) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(URI.create(makeAmplafiUrl(params)));
        HttpResponse response = client.execute(request);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);
        return out.toString();
    }
    
    static private String makeAmplafiUrl(String[] params) {
        URL amplafiUrl = null;
        try {
            amplafiUrl = new URL("http://amplafi.net/apiv1/" 
                + params[0] + "?" + params[1]);
        } catch (MalformedURLException malEx) {
            malEx.printStackTrace();
        }
        
        return amplafiUrl.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        mRequestor.taskCompleted(result);
    }
}


/**
 * Copyright 2006-2011 by Amplafi. All rights reserved.
 * Confidential.
 */
package com.amplafi.android.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

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
        InputStream iStream = getData(params);
        try {
            output = IOUtils.toString(iStream);
        } catch (IOException e) {
            output = e.getMessage();
        }
        
        return output;
    }
    
    private InputStream getData(String[] params) {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(URI.create(makeAmplafiUrl(params)));
        HttpResponse response;
        InputStream iStream = null;
        try {
            response = client.execute(request);
            iStream = response.getEntity().getContent();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return iStream;
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


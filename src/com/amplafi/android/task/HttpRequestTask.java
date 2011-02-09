/**
 * Copyright 2006-2011 by Amplafi. All rights reserved.
 * Confidential.
 */
package com.amplafi.android.task;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

/**
 * @author patmoore
 *
 */
public abstract class HttpRequestTask extends AsyncTask<Void, Void, String> {
    
	private String requestString;

    public HttpRequestTask(URI requestUri, String... params) {
    	StringBuilder request = new StringBuilder(requestUri.toString());
        if(params.length > 0) {
	        request.append("?");
	        for (int i = 0; i < params.length / 2; i++) {
	        	request.append(params[i*2]).append("=");
	        	try {
					request.append(URLEncoder.encode(params[i*2+1], "UTF-8"));
				} catch (UnsupportedEncodingException e) {
				}
	        	if((i + 1) < params.length / 2){
	        		request.append("&");
	        	}
			}
        }
        requestString = request.toString();
    }

	@Override
    protected String doInBackground(Void... params) {
        String output = null;
        try {
            HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(requestString);
			HttpResponse response = client.execute(request);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			response.getEntity().writeTo(out);
			output = out.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Error while trying to access " + requestString, e);
        }
        return output;
    }
}


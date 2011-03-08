/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the
 * License.
 */
package org.amplafi.android.task;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author patmoore
 *
 */
public abstract class HttpGetRequestTask extends AsyncTask<Void, Void, HttpRequestResult> {

	private String requestString;

    public HttpGetRequestTask(URI requestUri, Object... params) {
    	StringBuilder request = new StringBuilder(requestUri.toString());
        if(params.length > 0) {
	        request.append("?");
	        for (int i = 0; i < params.length / 2; i++) {
	        	request.append(params[i*2]).append("=");
	        	try {
					request.append(URLEncoder.encode(params[i*2+1].toString(), "UTF-8"));
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
    protected HttpRequestResult doInBackground(Void... params) {
        String output = null;
        try {
            HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(requestString);
			HttpResponse response = client.execute(request);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			response.getEntity().writeTo(out);
			output = out.toString();
        } catch (Exception e) {
        	return new HttpRequestResult("Error while trying to access " + requestString, e);
        }
        return new HttpRequestResult(output);
    }
}


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

public class HttpRequestResult {

	private final String result;

	private Exception e;

	public HttpRequestResult(String result){
		this.result = result;
	}

	public HttpRequestResult(String result, Exception exception){
		this(result);
		this.e = exception;
	}

	public Exception getException() {
		return e;
	}

	public String getResult() {
		return result;
	}

	public boolean isSuccessful(){
		return e == null;
	}
}

package com.amplafi.android.task;

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

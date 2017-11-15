package com.sommers.demo.beans;

public class WebhookRequest {

	private WebhookRequestResult result;
	
	private WebhookOriginalRequest originalRequest;

	public WebhookRequestResult getResult() {
		return result;
	}

	public void setResult(WebhookRequestResult result) {
		this.result = result;
	}

	public WebhookOriginalRequest getOriginalRequest() {
		return originalRequest;
	}

	public void setOriginalRequest(WebhookOriginalRequest originalRequest) {
		this.originalRequest = originalRequest;
	}
	
}


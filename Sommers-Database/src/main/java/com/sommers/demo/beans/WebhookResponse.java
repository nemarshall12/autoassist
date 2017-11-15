package com.sommers.demo.beans;

import java.util.List;

public class WebhookResponse {

	private String speech;
	
	private String displayText;
	
	private List<ContextOut> contextOut;
	
	private final String source = "java-webhook";
	
	public WebhookResponse() {
		
	}
	
	public WebhookResponse(String speech, String displayText) {
		this.speech = speech;
		this.displayText = displayText;
	}

	public WebhookResponse(String speech, String displayText, List<ContextOut> contextOut) {
		this.speech = speech;
		this.displayText = displayText;
		this.contextOut = contextOut;
	}
	
	public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public List<ContextOut> getContextOut() {
		return contextOut;
	}

	public void setContextOut(List<ContextOut> contextOut) {
		this.contextOut = contextOut;
	}

	public String getSource() {
		return source;
	}
	
}

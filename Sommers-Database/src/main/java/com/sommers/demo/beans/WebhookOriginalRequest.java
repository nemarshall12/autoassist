package com.sommers.demo.beans;

import java.util.Map;

public class WebhookOriginalRequest {
	
	private String source;
	
	private Map<String, String> data;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
}

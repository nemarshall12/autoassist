package com.sommers.demo.beans;

import java.util.List;

import ai.api.model.Fulfillment;

public class WebhookRequestResult {

	private String source;
	
	private String action;
	
	private String resolvedQuery;
	
	private List<ContextOut> contexts;
	
	private Fulfillment fulfillment;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getResolvedQuery() {
		return resolvedQuery;
	}

	public void setResolvedQuery(String resolvedQuery) {
		this.resolvedQuery = resolvedQuery;
	}

	public List<ContextOut> getContexts() {
		return contexts;
	}

	public void setContexts(List<ContextOut> contexts) {
		this.contexts = contexts;
	}

	public Fulfillment getFulfillment() {
		return fulfillment;
	}

	public void setFulfillment(Fulfillment fulfillment) {
		this.fulfillment = fulfillment;
	}
	
	public ContextOut getContext(String name) {
		if (contexts != null) {
			for (ContextOut context : contexts) {
				if (context.getName() != null & context.getName().equals(name)) {
					return context;
				}
			}
		}
		return null;
	}
	
}

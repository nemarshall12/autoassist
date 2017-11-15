package com.sommers.demo.beans;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;

import ai.api.model.AIOutputContext;

public class ContextOut {

	private String name;
	
	private Integer lifespan;

	private Map<String, String> parameters;
	
	public ContextOut() {
		
	}
	
	public ContextOut(AIOutputContext aiContext) {
		this.setName(aiContext.getName());
		this.setLifespan(aiContext.getLifespan());
		this.setParameters(new HashMap<String, String>());
		for (Map.Entry<String, JsonElement> entry : aiContext.getParameters().entrySet()) {
			this.getParameters().put(entry.getKey(), entry.getValue().getAsString());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLifespan() {
		return lifespan;
	}

	public void setLifespan(Integer lifespan) {
		this.lifespan = lifespan;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	
}

package com.rawad.jfxengine.client.input;

import java.util.ArrayList;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public final class InputBindings {
	
	private static final Input DEFAULT_INPUT = new Input(KeyCode.UNDEFINED);
	
	private final ObservableMap<Object, Input> bindings = FXCollections.<Object, Input>observableHashMap();
	
	private Object defaultAction = new Object();
	
	public Input put(Object action, KeyCode input) {
		return put(action, new Input(input));
	}
	
	public Object get(KeyCode input) {
		return get(new Input(input));
	}
	
	public Input put(Object action, MouseButton input) {
		return put(action, new Input(input));
	}
	
	public Object get(MouseButton input) {
		return get(new Input(input));
	}
	
	public Input put(Object action, Input input) {
		return bindings.put(action, input);
	}
	
	/**
	 * Returns the {@code Object} that represents the key to the given {@code input} only if there is a 1:1 
	 * correspondance.
	 * 
	 * @param input
	 * @return
	 */
	public Object get(Input input) {
		
		ArrayList<Object> matchingKeys = new ArrayList<Object>();
		
		for(Entry<Object, Input> entry: bindings.entrySet()) {
			if(input.equals(entry.getValue())) matchingKeys.add(entry.getKey());
		}
		
		if(matchingKeys.isEmpty() || matchingKeys.size() > 1) return defaultAction;
		
		return matchingKeys.get(0);
		
	}
	
	public Input get(Object action) {
		return bindings.getOrDefault(action, DEFAULT_INPUT);
	}
	
	public ObservableMap<Object, Input> getBindings() {
		return bindings;
	}
	
	public void setDefaultAction(Object defaultAction) {
		this.defaultAction = defaultAction;
	}
	
}

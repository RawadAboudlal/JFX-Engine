package com.rawad.jfxengine.gui;

import javafx.application.Platform;
import javafx.scene.Node;

/**
 * 
 * This interface has serveral default methods that can be used to show and hide JavaFX Node objects. Any implementing class
 * must be a subclass of Node.
 * 
 * @author Rawad
 *
 */
public interface Hideable {
	
	public default void show() {
		
		Node node = (Node) this;
		
		Platform.runLater(() -> {
			node.setVisible(true);
			node.requestFocus();
		});
		
	}
	
	public default void hide() {
		
		Node node = (Node) this;
		
		Platform.runLater(() -> {
			node.setVisible(false);
			node.getParent().requestFocus();
		});
		
	}
	
	public default void toggleVisible() {
		
		Node node = (Node) this;
		
		if(node.isVisible()) hide();
		else show();
		
	}
	
}

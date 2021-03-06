package com.rawad.jfxengine.gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.rawad.gamehelpers.client.states.State;
import com.rawad.gamehelpers.log.Logger;
import com.rawad.jfxengine.loader.GuiLoader;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Holds bindings of {@code State} objects to {@code Root} root objects which is what the {@code Client} needs to use
 * when changing between states.
 * 
 * @author Rawad
 *
 */
public final class GuiRegister {
	
	private static final HashMap<State, Root> roots = new HashMap<State, Root>();
	
	private static final String DEFAULT_STYLESHEET = "StyleSheet";
	
	private GuiRegister() {}
	
	public static Root loadGui(State state, String styleSheet) {
		
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setController(state);
		
		StackPane guiContainer = new StackPane();
		fxmlLoader.setRoot(guiContainer);
		
		Root root = new Root(guiContainer, styleSheet);
		root.getStylesheets().add(GuiLoader.getStyleSheetLocation(state.getClass(), styleSheet));
		
		try (InputStream inputStream = GuiLoader.streamLayoutFile(state.getClass())) {
			
			fxmlLoader.load(inputStream);
			
		} catch(IOException ex) {
			ex.printStackTrace();
			
			guiContainer.getChildren().add(new Label("Error initializing this state " + state.getClass().getSimpleName() 
					+ "; " + ex.getMessage()));
			
		}
		
		roots.put(state, root);
		
		return root;
		
	}
	
	public static Root loadGui(State state) {
		return GuiRegister.loadGui(state, DEFAULT_STYLESHEET);
	}
	
	public static Root getRoot(State state) {
		Root root = roots.get(state);
		
		if(root == null) {
			root = new Root(new StackPane(), "");
			Logger.log(Logger.WARNING, "GUI for " + state + " wasn't initialized.");
		}
		
		return root;
	}
	
}

package com.rawad.jfxengine.loader;

import java.io.InputStream;

import com.rawad.gamehelpers.resources.AbstractLoader;

public final class GuiLoader extends AbstractLoader {
	
	private static final String EXTENSION_LAYOUT_FILE = "fxml";
	private static final String EXTENSION_STYLESHEET_FILE = "css";
	
	private GuiLoader() {}
	
	public static InputStream streamLayoutFile(Class<? extends Object> clazz, String layoutName) {
		return clazz.getResourceAsStream(layoutName + AbstractLoader.REGEX_EXTENSION + EXTENSION_LAYOUT_FILE);
	}
	
	public static InputStream streamLayoutFile(Class<? extends Object> clazz) {
		return GuiLoader.streamLayoutFile(clazz, clazz.getSimpleName());
	}
	
	public static String getStyleSheetLocation(Class<? extends Object> clazz, String styleSheetName) {
		return clazz.getResource(styleSheetName + AbstractLoader.REGEX_EXTENSION + EXTENSION_STYLESHEET_FILE)
				.toExternalForm();
	}
	
}

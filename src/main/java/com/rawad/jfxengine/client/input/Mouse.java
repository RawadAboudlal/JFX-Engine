package com.rawad.jfxengine.client.input;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import com.rawad.gamehelpers.log.Logger;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.layout.Region;

/**
 * Optional implementation of a mouse that can be used with JavaFX components. Supports clamping and statically storing
 * cursor coordinates. Since this is optional, the {@ Proxy} implementation must call the {@link #update(Region)} method
 * from withing its {@link com.rawad.gamehelpers.game.Proxy#tick()} method (usually before anything else is updated).
 * 
 * @author Rawad
 *
 */
public final class Mouse {
	
	private static Robot bot;
	
	private static Region region;
	
	private static double x;
	private static double y;
	
	private static double clampX;
	private static double clampY;
	
	private static double dx;
	private static double dy;
	
	private static boolean clamped;
	
	private Mouse() {}
	
	static {
		
		try {
			
			bot = new Robot();
			
		} catch(AWTException ex) {
			Logger.log(Logger.SEVERE, ex.getLocalizedMessage() + "; Robot wasn't initialized");
		}
		
	}
	
	public static void update(Region region) {
		
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
		
		x = mouseLocation.getX();
		y = mouseLocation.getY();
		
		if(region != null) {
			
			Mouse.region = region;
			
			Point2D relativeMouseLocation = region.screenToLocal(mouseLocation.getX(), mouseLocation.getY());
			
			if(relativeMouseLocation != null) {
				x = relativeMouseLocation.getX();
				y = relativeMouseLocation.getY();
			}
			
		}
		
		if(clamped && bot != null && region.isFocused()) {
			
			clampX = region.getWidth() / 2d;
			clampY = region.getHeight() / 2d;
			
			dx = x - clampX;
			dy = y - clampY;
			
			Point2D pos = region.localToScreen(clampX, clampY);
			
			bot.mouseMove((int) (pos.getX()), (int) (pos.getY()));
			
		}
	}
	
	public static void clamp() {
		
		Mouse.clamped = true;
		
		Mouse.clampX = region.getWidth() / 2d;
		Mouse.clampY = region.getHeight() / 2d;
		
		region.setCursor(Cursor.DISAPPEAR);
		
	}
	
	public static void unclamp() {
		
		Mouse.clamped = false;
		
		region.setCursor(Cursor.DEFAULT);
		
	}
	
	public static double getX() {
		return x;
	}
	
	public static double getY() {
		return y;
	}
	
	public static double getDx() {
		return dx;
	}
	
	public static double getDy() {
		return dy;
	}
	
	public static double getClampX() {
		return clampX;
	}
	
	public static double getClampY() {
		return clampY;
	}
	
	public static boolean isClamped() {
		return clamped;
	}
	
}

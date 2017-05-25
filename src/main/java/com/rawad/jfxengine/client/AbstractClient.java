package com.rawad.jfxengine.client;

import com.rawad.gamehelpers.client.renderengine.Renderable;
import com.rawad.gamehelpers.client.renderengine.RenderingTimer;
import com.rawad.gamehelpers.client.states.State;
import com.rawad.gamehelpers.client.states.StateChangeListener;
import com.rawad.gamehelpers.client.states.StateManager;
import com.rawad.gamehelpers.game.Game;
import com.rawad.gamehelpers.game.Proxy;
import com.rawad.jfxengine.client.input.InputBindings;
import com.rawad.jfxengine.client.input.Mouse;
import com.rawad.jfxengine.gui.GuiRegister;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractClient extends Proxy implements StateChangeListener, Renderable {
	
	protected Stage stage;
	
	protected Scene scene;
	
	protected RenderingTimer renderingTimer;// According to fraps, this makes game go from ~20 to 50-60 fps.
	
	protected StateManager sm;
	
	protected InputBindings inputBindings;
	
	@Override
	public void preInit(Game game) {
		super.preInit(game);
		
		this.initGui();
		
		/*
		 *  TODO: Conider adding postInit() -> starts rendering timer and sets update = true because each subclass does that
		 *  right now at the same spot, anyway.
		 */
		
		renderingTimer = new RenderingTimer(this, this.getTargetFps());
		
		sm = new StateManager(game, this);
		
		inputBindings = new InputBindings();
		
	}
	
	@Override
	public void tick() {
		
		Mouse.update(GuiRegister.getRoot(sm.getCurrentState()));
		
		sm.update();
		
	}
	
	@Override
	public void render() {
		Platform.runLater(() -> sm.render());
	}
	
	@Override
	public void terminate() {
		
		update = false;
		
		renderingTimer.stop();
		
	}
	
	@Override
	public void onStateChange(State oldState, State newState) {
		// Some nice default behaviour.
		Platform.runLater(() -> {
			stage.getScene().setRoot(GuiRegister.getRoot(newState));
			stage.getScene().getRoot().requestFocus();
			stage.sizeToScene();
			stage.centerOnScreen();
		});
	}
	
	/**
	 * Called at the very beginning of {@link AbstractClient#preInit(Game)}. GUI made here should not depend on any other 
	 * object made after this. GUI should be updated as rest of game is initialized or once after everything is done being
	 * initialized.
	 */
	protected abstract void initGui();
	
	protected abstract int getTargetFps();
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public RenderingTimer getRenderingTimer() {
		return renderingTimer;
	}
	
	public InputBindings getInputBindings() {
		return inputBindings;
	}
	
	/**
	 * @return the scene
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}
	
}

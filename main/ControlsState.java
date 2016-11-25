package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class ControlsState extends BasicGameState{
	private int ID = 3;	
	
	// background image
	private Image image;
	
	// sound effects
	private Sound back;
	
	// TODO: To be implemented
	public ControlsState(int id) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		// initializing disaplay and sound
		image = new Image("res/controlsImgs/controls.png");
		back = new Sound("res/soundEffects/close.wav");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		// drawing the background
		image.draw();
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		Input in = gc.getInput();
		
		// when the player feels the need to go back to the main menu
		if (in.isKeyPressed(Input.KEY_ESCAPE)){
			if (OptionState.sfxActv)        // when sfx is activated
				back.play();
			sbg.enterState(GameTester.menu, new FadeOutTransition(), new FadeInTransition());
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}

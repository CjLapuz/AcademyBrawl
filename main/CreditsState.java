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

public class CreditsState extends BasicGameState {
	private int ID = 5;	
	private int delta;
	
	// foreground image for credits
	private Image credits;
	
	// sound effects when exiting the credits
	private Sound back;
	
	public CreditsState (int state) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// initialization of image and sfx
		//TODO: the serious credits page
		credits = new Image("res/creditsImgs/credits.png");
		back = new Sound("res/soundEffects/close.wav");
	
	}/*End of init*/
	

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		// draw the image
		credits.draw();
		
		// instructions to return to the main menu
		g.drawString("PRESS ESCAPE TO RETURN TO THE MAIN MENU", 400, 10);
	
	}/*End of render*/
	

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		this.delta += delta;        // amount of time passed
		
		// Timing the music to start when the screen has loaded
		if (this.delta == 30){
		 	TitleScreen.musicCredits();
		}
		
		Input in = gc.getInput();
		
		// when the player feels the need to go back to the main menu
		if (in.isKeyPressed(Input.KEY_ESCAPE)){
			if (OptionState.sfxActv)        // when sfx is activated
				back.play();
			
			sbg.enterState(GameTester.menu, new FadeOutTransition(), new FadeInTransition());
		}
		
	}/*End of update*/

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}

package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class CreditsState extends BasicGameState {
	private int ID = 5;	
	
	// foreground image for credits
	private Image credits;
	
	// sound effects when exiting the credits
	private Sound back;
	
	public CreditsState (int state) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// initialization of image and sfx
		//TODO: the serious credits page
		credits = new Image("res/background/credits.png");
		back = new Sound("res/soundEffects/close.wav");
	
	}/*End of init*/
	

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		// draw the image
		credits.draw();
		
		// instructions to return to the main menu
		g.drawString("PRESS ESCAPE OR BACKSPACE TO RETURN TO THE MAIN MENU", 600, 10);
	
	}/*End of render*/
	

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		Input in = gc.getInput();
		
		// when the player feels the need to go back to the main menu
		if (in.isKeyPressed(Input.KEY_ESCAPE) || in.isKeyPressed(Input.KEY_BACK)){
			back.play();
			
			//resets back the music to the main menu music
			TitleScreen.musicMenu();
			sbg.enterState(GameTester.menu, new FadeOutTransition(), new FadeInTransition());
		}
		
	}/*End of update*/

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}

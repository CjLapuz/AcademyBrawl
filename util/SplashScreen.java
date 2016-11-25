package util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import main.GameTester;

public class SplashScreen extends BasicGameState{

	private int ID = -1;
	
	private Image img;  // splashscreen / background image
	private int delta;  // to time transistions
	private Sound ding; // sfx
	public static boolean entered = false;
	
	public SplashScreen(int id) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		img = new Image("res/titleImgs/splash.png");
		ding = new Sound("res/soundEffects/select.wav");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// draw the image
		img.draw();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		this.delta += delta; 			// update time
		
		if (this.delta / 100 == 5){
			ding.play();
		}
		
		if (this.delta / 1000 == 3){
			entered = true;
			sbg.enterState(GameTester.empty, new FadeOutTransition(), new EmptyTransition());
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
}

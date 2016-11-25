package util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import main.GameTester;

// Used for start up to avoid graphical glitches
public class EmptyState extends BasicGameState{

	private int ID = -2;
	
	private int delta;
	
	public EmptyState(int id) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		this.delta += delta;
		
		if (this.delta / 1000 == 2){
			if (SplashScreen.entered == true)
				sbg.enterState(GameTester.titleScreen, new FadeOutTransition(), new EmptyTransition());
			else 
				sbg.enterState(GameTester.splash, new FadeOutTransition(), new EmptyTransition());
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}

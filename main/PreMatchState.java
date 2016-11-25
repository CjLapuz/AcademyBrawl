package main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class PreMatchState extends BasicGameState{

	private int ID = 8;
	
	private int delta;    // to time transitions and animations
	
	//TODO: Implement this 
	
	public PreMatchState(int id){}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		g.setBackground(Color.gray);
		g.drawString("PLAYER 1", 250, 200);
		g.drawString("VERSUS", 350, 200);
		g.drawString("PLAYER 2", 450, 200);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		this.delta += delta;
		
		if (this.delta / 1000 == 3){
			TitleScreen.musicArena1();            // TODO: add a Splash Screen to show case both characters before a match
			sbg.enterState(GameTester.play, new FadeOutTransition(), new FadeInTransition());
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}

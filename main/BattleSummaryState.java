package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
//import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
//import org.newdawn.slick.state.transition.FadeInTransition;
//import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.CombinedTransition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;

public class BattleSummaryState extends BasicGameState{
	private int ID = 7;
	
	int delta;		//to store the amount of time passed
	
	private Sound back;
	
	private Image img;
	
	public BattleSummaryState(int state) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		img = new Image("res/PlayImgs/endBGD.png");
		back = new Sound("res/soundEffects/close.wav");
		
	}/*End of init*/

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		img.draw();
		
	}/*End of render*/

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		Input in = gc.getInput();
		
		
		if (in.isKeyPressed(Input.KEY_ENTER)){
			back.play();
			TitleScreen.pauseMusic();
			CharacterSelectState.reset();
			sbg.enterState(GameTester.characterSelect,  new CombinedTransition(), new VerticalSplitTransition());
		}
	}/*End of update*/

	@Override
	public int getID() {
		return ID;
	}

}

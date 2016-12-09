package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class PreMatchState extends BasicGameState{

	private int ID = 8;
	
	private int delta;    // to time transitions and animations
	
	private Image player1, player2, scienceL, socsciL, artsL, managementL, 
							   back, scienceR, socsciR, artsR, managementR;
	
	private Animation vs;
	
	public PreMatchState(int id){}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		vs = new Animation(new SpriteSheet("res/PlayImgs/vs.png" ,100, 100), 100);
		back = new Image("res/PlayImgs/vsBGD.png");
		
		scienceL = new Image("res/PlayImgs/scnsL.png");
		scienceR = new Image("res/PlayImgs/scnsR.png");
		
		socsciL = new Image("res/PlayImgs/stalL.png");
		socsciR = new Image("res/PlayImgs/stalR.png");
		
		artsL = new Image("res/PlayImgs/bltsL.png");
		artsR = new Image("res/PlayImgs/bltsR.png");
		
		managementL = new Image("res/PlayImgs/tycL.png");
		managementR = new Image("res/PlayImgs/tycR.png");
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		back.draw();
		
		vs.setPingPong(true);
		vs.draw(275, 175, 250, 250);
		
		if(CharacterSelectState.index1 == 1)
			player1 = scienceL;
		else if (CharacterSelectState.index1 == 2)
			player1 = socsciL;
		else if (CharacterSelectState.index1 == 3)
			player1 = artsL;
		else if (CharacterSelectState.index1 == 4)
			player1 = managementL;
		
		if(CharacterSelectState.index2 == 1)
			player2 = scienceR;
		else if (CharacterSelectState.index2 == 2)
			player2 = socsciR;
		else if (CharacterSelectState.index2 == 3)
			player2 = artsR;
		else if (CharacterSelectState.index2 == 4)
			player2 = managementR;
		
		player1.draw();
		player2.draw();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		this.delta += delta;
		
		if (this.delta / 1000 == 3){
			TitleScreen.musicArena1();
			this.delta = 0;// TODO: add a Splash Screen to show case both characters before a match
			
			//
			if(CharacterSelectState.index1 == 3 && CharacterSelectState.index2 == 1)
				sbg.enterState(10, new FadeOutTransition(), new FadeInTransition());
			//
			else if(CharacterSelectState.index1 == 3 && CharacterSelectState.index2 == 2)
				sbg.enterState(14, new FadeOutTransition(), new FadeInTransition());
			else if(CharacterSelectState.index1 == 3 && CharacterSelectState.index2 == 3)
				sbg.enterState(10, new FadeOutTransition(), new FadeInTransition());
			//
			else if(CharacterSelectState.index1 == 3 && CharacterSelectState.index2 == 4)
				sbg.enterState(13, new FadeOutTransition(), new FadeInTransition());
			
			//
			else if(CharacterSelectState.index1 == 1 && CharacterSelectState.index2 == 1)
				sbg.enterState(12, new FadeOutTransition(), new FadeInTransition());
			//
			else if(CharacterSelectState.index1 == 1 && CharacterSelectState.index2 == 2)
				sbg.enterState(15, new FadeOutTransition(), new FadeInTransition());
			//
			else if(CharacterSelectState.index1 == 1 && CharacterSelectState.index2 == 3)
				sbg.enterState(11, new FadeOutTransition(), new FadeInTransition());
			//
			else if(CharacterSelectState.index1 == 1 && CharacterSelectState.index2 == 4)
				sbg.enterState(16, new FadeOutTransition(), new FadeInTransition());
			
			else if(CharacterSelectState.index1 == 2 && CharacterSelectState.index2 == 1)
				sbg.enterState(21, new FadeOutTransition(), new FadeInTransition());
			//
			else if(CharacterSelectState.index1 == 2 && CharacterSelectState.index2 == 2)
				sbg.enterState(22, new FadeOutTransition(), new FadeInTransition());
			//
			else if(CharacterSelectState.index1 == 2 && CharacterSelectState.index2 == 3)
				sbg.enterState(23, new FadeOutTransition(), new FadeInTransition());
			//
			else if(CharacterSelectState.index1 == 2 && CharacterSelectState.index2 == 4)
				sbg.enterState(24, new FadeOutTransition(), new FadeInTransition());
			
			//
			else if(CharacterSelectState.index1 == 4 && CharacterSelectState.index2 == 1)
				sbg.enterState(18, new FadeOutTransition(), new FadeInTransition());
			//
			else if(CharacterSelectState.index1 == 4 && CharacterSelectState.index2 == 2)
				sbg.enterState(17, new FadeOutTransition(), new FadeInTransition());
			//
			else if(CharacterSelectState.index1 == 4 && CharacterSelectState.index2 == 3)
				sbg.enterState(19, new FadeOutTransition(), new FadeInTransition());
			//
			else if(CharacterSelectState.index1 == 4 && CharacterSelectState.index2 == 4)
				sbg.enterState(20, new FadeOutTransition(), new FadeInTransition());
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated m      ethod stub
		return ID;
	}

}

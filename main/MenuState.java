package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.CombinedTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;;


public class MenuState extends BasicGameState {
	
	// sound effects to be used
	Sound scroll ,choose, error, close;
	
	private int ID = 1;	
	private int delta;     // to store the amount of time passed
	
	// pointer to where the focus is on from 2 (play) to 6 (exit) 
	// pointer is by default pointing to play
	private int select = 2;
	
	// Images to be used
	private Image display, play, options, controls, credits, exit;
	
	// Pop up message for confirmation
	private Image confirmMsg, confirmYes, confirmNo;
			
	private boolean confirm = false; // to confirm a if a player wants to exit back to the main menu
	private int choice = 0; // if the player accepts then 0, 1 if otherwise
	
	public MenuState(int state) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// Initialization of Sound Effects
		scroll = new Sound("res/soundEffects/select.wav");
		choose = new Sound("res/soundEffects/choose.wav");
		close = new Sound("res/soundEffects/close.wav");
		
		// initialization of images
		play = new Image("res/menuImgs/play.png");
		options = new Image("res/menuImgs/options.png");
		controls = new Image("res/menuImgs/controls.png");
		credits = new Image("res/menuImgs/credits.png");
		exit = new Image("res/menuImgs/exit.png");
		
		// pop up message initialization
		confirmMsg = confirmYes = new Image("res/optionsImgs/confirmYes.png");
		confirmNo = new Image("res/optionsImgs/confirmNo.png");
		
		// sets the main display to be pointing o play by default 
		display = play;
		
	}/*End of init*/

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		display.draw();		// draws the display

		if (confirm){  // once the pop up animation is done then it shows the message
			confirmMsg.draw(235, 150);
		}
		
	}/*End of render*/

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		this.delta += delta;    /// computes the amount of time passed
		Input in = gc.getInput();
		
		if (confirm){
			// Toggling between yes or no
			if(in.isKeyPressed(Input.KEY_LEFT) || in.isKeyPressed(Input.KEY_RIGHT)){
				if (OptionState.sfxActv)        // when sfx is activated
					scroll.play();
				if (choice == 0){
					confirmMsg = confirmNo;
					choice = 1;
				} else {
					confirmMsg = confirmYes;
					choice = 0;
				}
			}
			
			// When confirming a choice to either yes or no
			if (in.isKeyPressed(Input.KEY_ENTER)){
				if (OptionState.sfxActv)        // when sfx is activated
					choose.play();
				if (choice == 0){
					gc.exit();		
				}else
					choice = 0;
				confirm = false;
			}
		}
		
		// Timing the music to start when the screen has loaded
		if (this.delta == 30){
			if (OptionState.soundActv)        // when sound is activated
				TitleScreen.musicMenu();
		}
				
		// When a player has chosen what state he wants to enter
		if (in.isKeyPressed(Input.KEY_ENTER)){
			switch (select){
				case 2:
					// plays the appropriate sound effect
					if (OptionState.sfxActv)        // when sfx is activated
						choose.play();
					
					TitleScreen.pauseMusic();
					this.delta = 0;
					sbg.enterState(select,  new CombinedTransition(), new VerticalSplitTransition());
					break;
				case 3:
					if (OptionState.sfxActv)        // when sfx is activated
						choose.play();
					
					sbg.enterState(select,  new FadeOutTransition(), new FadeInTransition());
					break;
				case 4:
					if (OptionState.sfxActv)        // when sfx is activated
						choose.play();
					
					sbg.enterState(select,  new FadeOutTransition(), new FadeInTransition());
					break;
				case 5:
					if (OptionState.sfxActv)        // when sfx is activated
						choose.play();
					this.delta = 0;
					TitleScreen.pauseMusic();                
					sbg.enterState(select,  new FadeOutTransition(), new FadeInTransition());
					break;
				default:
					if (OptionState.sfxActv)        // when sfx is activated
						close.play();
					
					confirm = true;
			}
		}
		
		// when a player is still choosing upon an action
		
		if (in.isKeyPressed(Input.KEY_UP) || in.isKeyPressed(Input.KEY_W)){
			if (OptionState.sfxActv)        // when sfx is activated
				scroll.play();
			
			// when a player is already at the topmost pointer it points him to the lowest pointer
			if (select == 2)
				select = 6;
			// else the pointer goes up
			else 
				select--;
		}
		
		else if (in.isKeyPressed(Input.KEY_DOWN) || in.isKeyPressed(Input.KEY_S)){
			if (OptionState.sfxActv)        // when sfx is activated
				scroll.play();
			
			// when a player is already at the bottom most pointer it points him to the highest pointer
			if (select == 6)
				select = 2;
			// else the pointer goes down
			else
				select++;
		}
		
		// To change the current display depending on the pointer
		if (select == 2)
			display = play;
		else if (select == 3)
			display = controls;
		else if (select == 4)
			display = options;
		else if (select == 5)
			display = credits;
		else 
			display = exit;
		
	}/*End of update*/

	@Override
	public int getID() {
		return ID;
	}
	
}

package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
//import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import util.Window;

public class OptionState extends BasicGameState{

	
	private int ID = 4;	
	
	public static boolean soundActv = true;		// check if sound 
	public static boolean sfxActv = true;			// or sfx is on
	
	private int volumeLvl = 5;				// to adjsut the volume strength	
	private int point = 0;					// to set the current pointer	
	
	// sound effects to be used
	private Sound error, scroll, selected, cancel;					
	
	// images used to display the current state of the options
	private Image back, soundOn, sfxOn, pointer, pointSound, pointVolume, pointSFX, volumeDisp;
	
	private SpriteSheet volume; 			// to display the current volume strength
	
	// Pop up message for confirmation
	private Image confirmMsg, confirmYes, confirmNo;
		
	private boolean confirm = false; // to confirm a if a player wants to exit back to the main menu
	private int choice = 0; // if the player accepts then 0, 1 if otherwise
	
	public OptionState (int state) {}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		// background image
		back = new Image("res/optionsImgs/options.png");
		
		// other images to display
		soundOn = new Image("res/optionsImgs/musicOn.png");
		sfxOn = new Image("res/optionsImgs/sfxOn.png");
		pointSound = new Image("res/optionsImgs/point0.png");
		pointVolume = new Image("res/optionsImgs/point1.png");
		pointSFX = new Image("res/optionsImgs/point2.png");
		
		// to be used to set the current state of the music volume strength
		volume = new SpriteSheet("res/optionsImgs/volume.png", Window.WIDTH, Window.HEIGHT);
		
		// Sound effects to be used
		error = new Sound("res/soundEffects/error.wav");
		scroll = new Sound("res/soundEffects/select.wav");
		selected = new Sound("res/soundEffects/choose.wav");
		cancel = new Sound("res/soundEffects/close.wav");
		
		// default display of volume and pointer
		pointer = pointSound;
		volumeDisp = volume.getSubImage(1, 2);
		
		// pop up message initialization
		confirmMsg = confirmYes = new Image("res/optionsImgs/confirmYes.png");
		confirmNo = new Image("res/optionsImgs/confirmNo.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		back.draw();				// draw background
		
		// Additional instructions
		g.drawString("<LEFT AND RIGHT ARROW KEYS TO TOGGLE VOLUME>", 220, 30);
		
		// if sound is on draw the checkbox and do not draw if otherwise
		if (soundActv)
			soundOn.draw();
		
		// if sfx is on draw the checkbox and do not draw if otherwise
		if(sfxActv)
			sfxOn.draw();

		
		pointer.draw();
		volumeDisp.draw();
		
		if (confirm){  // once the pop up animation is done then it shows the message
			confirmMsg.draw(235, 150);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
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
					selected.play();
				if (choice == 0){
					sbg.enterState(GameTester.menu, new FadeOutTransition(), new FadeInTransition());
				}else
					choice = 0;
				confirm = false;
			}
		}
		
		//When the player feels the need to go back to the main menu
		if (in.isKeyPressed(Input.KEY_ESCAPE)){
			if (OptionState.sfxActv)        // when sfx is activated
				cancel.play();
			confirm = true;       // pops up the confirm message
		}
		
		else if (in.isKeyPressed(Input.KEY_ENTER)){ 
			if (sfxActv && point != 2)  // when sfx is activated and pointer is not at sfx
				selected.play();
			
			if (point == 0){			// toggling music
				if (soundActv){
					TitleScreen.pauseMusic();
					soundActv = false;
				
				}else{  
					soundActv = true;
					TitleScreen.resumeMusic();
				}
			
			}else if (point == 1){	 // locking unto toggling volume
				point = -1;
				
			}else if  (point == 2){ // toggling sfx
				if (sfxActv)
					sfxActv = false;
				else{
					selected.play();
					sfxActv = true;
				}
			}else if (point == -1)
				point = 1;
		}
		
		// when it is not locked unto toggling volume
		else if (in.isKeyPressed(Input.KEY_DOWN) && point != -1){
			if (sfxActv)         // when sfx is activated
				scroll.play();
			if (point == 2)
				point = 0;
			else 
				point++;
				
		} else if (in.isKeyPressed(Input.KEY_UP) && point != -1){
			if (sfxActv)        // when sfx is activated
				scroll.play();
			if (point == 0)
				point = 2;
			else 
				point--;
		}
		
		// When the player is toggling volume
		if (in.isKeyPressed(Input.KEY_LEFT) && point == -1){
			if (volumeLvl == 0){    // cannot decrease anymore when volume is already at 0
				if (sfxActv)        // when sfx is activated    
					error.play();
			
			} else{ 
				TitleScreen.lowerVolume();
				volumeLvl--;
			}
		} else if (in.isKeyPressed(Input.KEY_RIGHT) && point == -1){
			if (volumeLvl == 5){	// cannot decrease anymore when volume is already at maximum
				if (sfxActv)        // when sfx is activated
					error.play();
			
			}else{
				TitleScreen.increaseVolume();
				volumeLvl++;
			}
		}		
		
		// Update the pointer
		if (point == 0)
			pointer = pointSound;
		else if (point == 1)
			pointer = pointVolume;
		else if (point == 2)
			pointer = pointSFX;
		
		// Update the volume strength display
		if (volumeLvl == 5)
			volumeDisp = volume.getSubImage(1, 2);
		else if (volumeLvl == 4)
			volumeDisp = volume.getSubImage(0, 2);
		else if (volumeLvl == 3)
			volumeDisp = volume.getSubImage(1, 1);
		else if (volumeLvl == 2)
			volumeDisp = volume.getSubImage(0, 1);
		else if (volumeLvl == 1)
			volumeDisp = volume.getSubImage(1, 0);
		else
			volumeDisp = volume.getSubImage(0, 0);
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}

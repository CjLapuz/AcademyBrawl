package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.CombinedTransition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;

import util.Window;

public class CharacterSelectState extends BasicGameState{
	private int ID = 2;	
	private int delta;  // amount of time passed
	
	public int index1 = 1;  		        //the pointer of which character is currently highlighted for player 1
	private boolean hasSelected1 = false;	// to indicate if a player 1 has already chosen a character
	
	public int index2 = 1;			//the pointer of which character is currently highlighted for player 1
	private boolean hasSelected2 = false;	// to indicate if a player 2 has already chosen a character
	
	// background image
	private Image background;
	
	// Pop up message for confirmation
	private Image confirmMsg, confirmYes, confirmNo;
	
	private static boolean confirm = false; // to confirm a if a player wants to exit back to the main menu
	private static int choice = 0; // if the player accepts then 0, 1 if otherwise
	
	// animation for highlighting the currently focused character for player 1
	private SpriteSheet p1one, p1two, p1three, p1four, 
						p1five, p1six ,p1seven, p1eight, 
						p1nine, p1ten, p1eleven, p1twelve,
						p1Ran1, p1Ran2;
	private Animation p1select1, p1select2, p1select3, p1select4, 
					  p1select5, p1select6, p1select7, p1select8, 
					  p1select9, p1select10, p1select11, p1select12,
					  p1selectRan1, p1selectRan2; 
	
	// animation for highlighting the currently focused character for player 2
	private SpriteSheet p2one, p2two, p2three, p2four, 
						p2five, p2six ,p2seven, p2eight, 
						p2nine, p2ten, p2eleven, p2twelve,
						p2Ran1, p2Ran2;
	private Animation p2select1, p2select2, p2select3, p2select4, 
	  				  p2select5, p2select6, p2select7, p2select8, 
	  				  p2select9, p2select10, p2select11, p2select12,
	  				  p2selectRan1, p2selectRan2; 
	
	// main animation to display the highlighted characer of each player
	private Animation selected1, selected2; 
	
	// appropriate sound effects
	private Sound selecting ,chosen, cancel, error;
	
	public CharacterSelectState(int state){}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// background image initialization
		background = new Image("res/CharSelectionImgs/select.png");
		
		// sound effect initalization
		selecting = new Sound("res/soundEffects/charSelect.wav");
		chosen = new Sound("res/soundEffects/charSelected.wav");
		cancel = new Sound("res/soundEffects/close.wav");
		error = new Sound("res/soundEffects/error.wav");
		
		// pop up message initialization
		confirmMsg = confirmYes = new Image("res/optionsImgs/confirmYes.png");
		confirmNo = new Image("res/optionsImgs/confirmNo.png");

		
		// Sprite sheet initalization for player 1
		p1one = new SpriteSheet("res/CharSelectionImgs/p1select0.png", Window.WIDTH, Window.HEIGHT);
		p1two = new SpriteSheet("res/CharSelectionImgs/p1select1.png", Window.WIDTH, Window.HEIGHT);
		p1three = new SpriteSheet("res/CharSelectionImgs/p1select2.png", Window.WIDTH, Window.HEIGHT);
		p1four = new SpriteSheet("res/CharSelectionImgs/p1select3.png", Window.WIDTH, Window.HEIGHT);
		p1five = new SpriteSheet("res/CharSelectionImgs/p1select4.png", Window.WIDTH, Window.HEIGHT);
		p1six = new SpriteSheet("res/CharSelectionImgs/p1select5.png", Window.WIDTH, Window.HEIGHT);
		p1seven = new SpriteSheet("res/CharSelectionImgs/p1select6.png", Window.WIDTH, Window.HEIGHT);
		p1eight = new SpriteSheet("res/CharSelectionImgs/p1select7.png", Window.WIDTH, Window.HEIGHT);
		p1nine = new SpriteSheet("res/CharSelectionImgs/p1select8.png", Window.WIDTH, Window.HEIGHT);
		p1ten = new SpriteSheet("res/CharSelectionImgs/p1select9.png", Window.WIDTH, Window.HEIGHT);
		p1eleven = new SpriteSheet("res/CharSelectionImgs/p1select10.png", Window.WIDTH, Window.HEIGHT);
		p1twelve = new SpriteSheet("res/CharSelectionImgs/p1select11.png", Window.WIDTH, Window.HEIGHT);
		p1Ran1 = new SpriteSheet("res/CharSelectionImgs/p1select-1.png", Window.WIDTH, Window.HEIGHT);
		p1Ran2 = new SpriteSheet("res/CharSelectionImgs/p1select-2.png", Window.WIDTH, Window.HEIGHT);
		
		// Sprite sheet initalization for player 2
		p2one = new SpriteSheet("res/CharSelectionImgs/p2select0.png", Window.WIDTH, Window.HEIGHT);
		p2two = new SpriteSheet("res/CharSelectionImgs/p2select1.png", Window.WIDTH, Window.HEIGHT);
		p2three = new SpriteSheet("res/CharSelectionImgs/p2select2.png", Window.WIDTH, Window.HEIGHT);
		p2four = new SpriteSheet("res/CharSelectionImgs/p2select3.png", Window.WIDTH, Window.HEIGHT);
		p2five = new SpriteSheet("res/CharSelectionImgs/p2select4.png", Window.WIDTH, Window.HEIGHT);
		p2six = new SpriteSheet("res/CharSelectionImgs/p2select5.png", Window.WIDTH, Window.HEIGHT);
		p2seven = new SpriteSheet("res/CharSelectionImgs/p2select6.png", Window.WIDTH, Window.HEIGHT);
		p2eight = new SpriteSheet("res/CharSelectionImgs/p2select7.png", Window.WIDTH, Window.HEIGHT);
		p2nine = new SpriteSheet("res/CharSelectionImgs/p2select8.png", Window.WIDTH, Window.HEIGHT);
		p2ten = new SpriteSheet("res/CharSelectionImgs/p2select9.png", Window.WIDTH, Window.HEIGHT);
		p2eleven = new SpriteSheet("res/CharSelectionImgs/p2select10.png", Window.WIDTH, Window.HEIGHT);
		p2twelve = new SpriteSheet("res/CharSelectionImgs/p2select11.png", Window.WIDTH, Window.HEIGHT);
		p2Ran1 = new SpriteSheet("res/CharSelectionImgs/p2select-1.png", Window.WIDTH, Window.HEIGHT);
		p2Ran2 = new SpriteSheet("res/CharSelectionImgs/p2select-2.png", Window.WIDTH, Window.HEIGHT);		
		
		// Animation initialization for player 1
		p1select1 = new Animation(p1one, 100);
		p1select2 = new Animation(p1two, 100);
		p1select3 = new Animation(p1three, 100);
		p1select4 = new Animation(p1four, 100);
		p1select5 = new Animation(p1five, 100);
		p1select6 = new Animation(p1six, 100);
		p1select7 = new Animation(p1seven, 100);
		p1select8 = new Animation(p1eight, 100);
		p1select9 = new Animation(p1nine, 100);
		p1select10 = new Animation(p1ten, 100);
		p1select11 = new Animation(p1eleven, 100);
		p1select12 = new Animation(p1twelve, 100);
		p1selectRan1 = new Animation(p1Ran1, 100);
		p1selectRan2 = new Animation(p1Ran2, 100);
		
		// Animation initialization for player 2
		p2select1 = new Animation(p2one, 100);
		p2select2 = new Animation(p2two, 100);
		p2select3 = new Animation(p2three, 100);
		p2select4 = new Animation(p2four, 100);
		p2select5 = new Animation(p2five, 100);
		p2select6 = new Animation(p2six, 100);
		p2select7 = new Animation(p2seven, 100);
		p2select8 = new Animation(p2eight, 100);
		p2select9 = new Animation(p2nine, 100);
		p2select10 = new Animation(p2ten, 100);
		p2select11 = new Animation(p2eleven, 100);
		p2select12 = new Animation(p2twelve, 100);
		p2selectRan1 = new Animation(p2Ran1, 100);
		p2selectRan2 = new Animation(p2Ran2, 100);
	
		// default, pointing to the first character
		selected1 = p1select1;
		selected2 = p2select1;
	
	}/*End of init*/

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		
		background.draw();			 // background drawing
		
		selected1.setPingPong(true);	 // this means that the animation loops from start to finish and also vice versa	  	
		selected1.draw();			 	 // draws the title screen
		
		selected2.setPingPong(true);	 // this means that the animation loops from start to finish and also vice versa	  	
		selected2.draw();			 	 // draws the title screen
		
		if (confirm){  // once the pop up animation is done then it shows the message
			confirmMsg.draw(235, 150);
		}
		
	}/*End of render*/

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		this.delta += delta;
		
		// Timing the music to start when the screen has loaded
		if (this.delta == 30){
			TitleScreen.musicCharSelect();
		}
		
		// if both players have chosen a character then a confrimation message will appear
		if (hasSelected1 && hasSelected2){
			reset();
			sbg.enterState(GameTester.versus, new CombinedTransition(), new VerticalSplitTransition());
		}
		
		selected1.update(delta);  			// to adjust the frame rate	
		
		selected2.update(delta);  			// to adjust the frame rate	
		
		Input in = gc.getInput();
		
		// added in case the players want to go back to the main menu state
		// TODO: pop up a confirmation message
		if (in.isKeyPressed(Input.KEY_ESCAPE)){
			if (OptionState.sfxActv)        // when sfx is activated
				cancel.play();
			confirm = true;
		}
		if (confirm){
		// Toggling between yes or no
			if(in.isKeyPressed(Input.KEY_LEFT) || in.isKeyPressed(Input.KEY_A) || in.isKeyPressed(Input.KEY_RIGHT) || in.isKeyPressed(Input.KEY_D)){
				if (OptionState.sfxActv)        // when sfx is activated
					selecting.play();
				if (choice == 0){
					confirmMsg = confirmNo;
					choice = 1;
				} else {
					confirmMsg = confirmYes;
					choice = 0;
				}
			}
		
			// When confirming a choice to either yes or no
			if (in.isKeyPressed(Input.KEY_ENTER) || in.isKeyPressed(Input.KEY_J) || in.isKeyPressed(Input.KEY_NUMPAD1)){
				if (OptionState.sfxActv)        // when sfx is activated
					chosen.play();
				if (choice == 0){
					if (OptionState.soundActv == true)
						TitleScreen.musicMenu();
					reset();
					this.delta = 0;
					sbg.enterState(GameTester.menu, new CombinedTransition(), new VerticalSplitTransition());
				}else
					confirm = false;
					choice = 0;
			}
		} else {
			// logic when scrolling through characters for player 1
			if (in.isKeyPressed(Input.KEY_W) && !hasSelected1){
				if (index1 == -1 || index1 == -2) {
					if (OptionState.sfxActv)                    // when sfx is activated
						error.play();
				} else {
					if (OptionState.sfxActv)        // when sfx is activated	
						selecting.play();
					if (index1 != 1 && index1 != 2 && index1 != 3 && index1 != 4)	// if index is not one of the topmost the index is moved upward in the column
						index1 -= 4;									
				    else 										// else the pointer is moved to the lowest box in the column
				    	index1 += 8;	
				}
			}
		
			else if (in.isKeyPressed(Input.KEY_S) && !hasSelected1){
				if (index1 == -1 || index1 == -2) {
					if (OptionState.sfxActv)                    // when sfx is activated
						error.play();
				} else {
					if (OptionState.sfxActv)        // when sfx is activated	
						selecting.play();
					if (index1 != 9 && index1 != 10 && index1 != 11 && index1 != 12 && index2 != -1 && index2 != -2)   // if the index is not the lowest in the column then the pointer can move downward 
						index1 += 4;
				    else 
				    	index1 -= 8;
				}
				
			} else if (in.isKeyPressed(Input.KEY_A) && !hasSelected1){
				if (OptionState.sfxActv)        // when sfx is activated
					selecting.play();
				if (index1 == -2 || index1 == -1){
					if (index1 == -1)
						index1 = -2;
					else if (index1 == -2)
						index1 = 8;
				} else { 
					if (index1 != 1 && index1 != 5 && index1 != 9)    // if the index is not in the rightmost edges then the pointer can move right
						index1 -= 1;
					else 										// else, the pointer gets moved to the leftmost boxes in the row
						index1 = -1;
				}
				
			}else if (in.isKeyPressed(Input.KEY_D) && !hasSelected1){
				if (OptionState.sfxActv)        // when sfx is activated
					selecting.play();
				if (index1 == -2 || index1 == -1){
					if (index1 == -2)
						index1 = -1;
					else if (index1 == -1)
						index1 = 5;
				} else { 
					if (index1 != 4 && index1 != 8 && index1 != 12)    // if the index is not in the rightmost edges then the pointer can move right
						index1 += 1;
					else 										// else, the pointer gets moved to the leftmost boxes in the row
						index1 = -2;
				}
			}
		
			// when a player 1 has decided on a character
			if (in.isKeyPressed(Input.KEY_J) && !hasSelected1){
				if (OptionState.sfxActv)        // when sfx is activated
					chosen.play();
				hasSelected1 = true;     // to stop movement of player 1
				// 	TODO add animation for character when selected
			
			// when player 1 cancels his choice
			} else if (in.isKeyPressed(Input.KEY_K) && hasSelected1){
				if (OptionState.sfxActv)        // when sfx is activated
					cancel.play();
				hasSelected1 = false;     // to resume movement of player 1
				// TODO return character portrait back to idle
			}
		
		
			// logic for scrolling through characters fro player 2
			if (in.isKeyPressed(Input.KEY_UP) && !hasSelected2){
				if (index2 == -1 || index2 == -2) {
					if (OptionState.sfxActv)       			    // when sfx is activated
						error.play();
				} else{
					if (OptionState.sfxActv)        // when sfx is activated	
						selecting.play();
					if (index2 != 1 && index2 != 2 && index2 != 3 && index2 != 4)	// if index is not one of the topmost the index is moved upward in the column
						index2 -= 4;									
					else 										// else the pointer is moved to the lowest box in the column
						index2 += 8;	
				}
			}
		
			else if (in.isKeyPressed(Input.KEY_DOWN) && !hasSelected2){
				if (index2 == -1 || index2 == -2) {
					if (OptionState.sfxActv)       			    // when sfx is activated
						error.play();
				} else {
					if (OptionState.sfxActv)        // when sfx is activated	
						selecting.play();
					if (index2 != 9 && index2 != 10 && index2 != 11 && index2 != 12)    // if the index is not the lowest in the column then the pointer can move downward
						index2 += 4;
					else 										// else, moves the pointer to the topmost box in the column
					index2 -= 8;
				}
			
			} else if (in.isKeyPressed(Input.KEY_LEFT) && !hasSelected2){
				if (OptionState.sfxActv)        // when sfx is activated
					selecting.play();
				if (index2 == -2 || index2 == -1){
					if (index2 == -1)
						index2 = -2;
					else if (index2 == -2)
						index2 = 8;
				} else { 
					if (index2 != 1 && index2 != 5 && index2 != 9)    // if the index is not in the rightmost edges then the pointer can move right
						index2 -= 1;
					else 										// else, the pointer gets moved to the leftmost boxes in the row
						index2 = -1;
				}
			
			}else if (in.isKeyPressed(Input.KEY_RIGHT) && !hasSelected2){
				if (OptionState.sfxActv)        // when sfx is activated
					selecting.play();
				if (index2 == -2 || index2 == -1){
					if (index2 == -2)
						index2 = -1;
					else if (index2 == -1)
						index2 = 5;
				} else { 
					if (index2 != 4 && index2 != 8 && index2 != 12)    // if the index is not in the rightmost edges then the pointer can move right
						index2 += 1;
					else 										// else, the pointer gets moved to the leftmost boxes in the row
						index2 = -2;
				}
			}
		
			// when a player 2 has decided on a character
			if (in.isKeyPressed(Input.KEY_NUMPAD1) && !hasSelected2){
				if (OptionState.sfxActv)        // when sfx is activated
					chosen.play();
				hasSelected2 = true;     // to stop movement of player 1
				// TODO add animation for character when selected
			
			// when player 2 cancels his choice		
			} else if (in.isKeyPressed(Input.KEY_NUMPAD2) && hasSelected2){
				if (OptionState.sfxActv)        // when sfx is activated
					cancel.play();
				hasSelected2 = false;     // to resume movement of player 1
				// TODO return character portrait back to idle
			}
	}
		// Updates the highlighted character depending on the selected index for player 1
		if (index1 == 1)
			selected1 = p1select1;
		else if (index1 == 2)
			selected1 = p1select2;
		else if (index1 == 3)
			selected1 = p1select3;
		else if (index1 == 4)
			selected1 = p1select4;
		else if (index1 == 5)
			selected1 = p1select5;
		else if (index1 == 6)
			selected1 = p1select6;
		else if (index1 == 7)
			selected1 = p1select7;
		else if (index1 == 8)
			selected1 = p1select8;
		else if (index1 == 9)
			selected1 = p1select9;
		else if (index1 == 10)
			selected1 = p1select10;
		else if (index1 == 11)
			selected1 = p1select11;
		else if (index1 == 12)
			selected1 = p1select12;
		else if (index1 == -1)
			selected1 = p1selectRan1;
		else if (index1 == -2)
			selected1 = p1selectRan2;
		
		// Updates the highlighted character depending on the selected index for player 1
		if (index2 == 1)
			selected2 = p2select1;
		else if (index2 == 2)
			selected2 = p2select2;
		else if (index2 == 3)
			selected2 = p2select3;
		else if (index2 == 4)
			selected2 = p2select4;
		else if (index2 == 5)
			selected2 = p2select5;
		else if (index2 == 6)
			selected2 = p2select6;
		else if (index2 == 7)
			selected2 = p2select7;
		else if (index2 == 8)
			selected2 = p2select8;
		else if (index2 == 9)
			selected2 = p2select9;
		else if (index2 == 10)
			selected2 = p2select10;
		else if (index2 == 11)
			selected2 = p2select11;
		else if (index2 == 12)
			selected2 = p2select12;
		else if (index2 == -1)
			selected2 = p2selectRan1;
		else if (index2 == -2)
			selected2 = p2selectRan2;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	// Reset all important variables to their default values
	private void reset(){
		index1 = 1;  		        
		hasSelected1 = false;	
		
		index2 = 1;			
		hasSelected2 = false;	
		
		confirm = false;
		choice = 0; 
		
		delta = 0;
	}
	
	public int getPlayer1Char() { return index1; }
	
	public int getPlayer2Char() { return index2; }
	

}

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
	private static int delta;  // amount of time passed
	private int charDrawTiming1; // to time the drawing of character's portrait for player 1
	private int charDrawTiming2; // to time the drawing of character's portrait for player 2
	
	public static int index1 = 1;  		        //the pointer of which character is currently highlighted for player 1
	public int indexp2;
	private static boolean hasSelected1 = false;	// to indicate if a player 1 has already chosen a character
	
	public static int index2 = 1;			//the pointer of which character is currently highlighted for player 1
	public int indexp1;
	private static boolean hasSelected2 = false;	// to indicate if a player 2 has already chosen a character
	
	// background image
	private Image background;
	
	// Pop up message for confirmation
	private Image confirmMsg, confirmYes, confirmNo;
	
	private static boolean confirm = false; // to confirm a if a player wants to exit back to the main menu
	private static int choice = 0; // if the player accepts then 0, 1 if otherwise
	
	// animation for highlighting the currently focused character for player 1
	private SpriteSheet p1one, p1two, p1three, p1four, 
						p1five, p1six ,p1seven, p1eight;
	
	private static Animation p1select1;
	private static Image p1Character;
	private Animation p1select2;
	private Animation p1select3;
	private Animation p1select4;
	private Animation p1select5;
	private Animation p1select6;
	private Animation p1select7;
	private Animation p1select8;
	
	// animation for highlighting the currently focused character for player 2
	private SpriteSheet p2one, p2two, p2three, p2four, 
						p2five, p2six ,p2seven, p2eight;

	private static Animation p2select1;
	private static Image p2Character;
	private Animation p2select2;
	private Animation p2select3;
	private Animation p2select4;
	private Animation p2select5;
	private Animation p2select6;
	private Animation p2select7;
	private Animation p2select8;
	
	// main animation to display the highlighted character of each player
	private static Animation selected1;
	private static Animation selected2; 
	
	// Animation when character is highlighted
	private Image missingL, scienceL, socsciL, artsL, managementL,
				  missingR, scienceR, socsciR, artsR, managementR;
	
	// appropriate sound effects
	private Sound selecting ,chosen, cancel, error;
	
	public CharacterSelectState(int state){}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		//Character images
		missingL = new Image("res/CharSelectionImgs/missL.png");
		missingR = new Image("res/CharSelectionImgs/missR.png");
		scienceL = new Image("res/CharSelectionImgs/scnsL.png");
		scienceR = new Image("res/CharSelectionImgs/scnsR.png");
		socsciL = new Image("res/CharSelectionImgs/stalL.png");
		socsciR = new Image("res/CharSelectionImgs/stalR.png");
		artsL = new Image("res/CharSelectionImgs/bltsL.png");
		artsR = new Image("res/CharSelectionImgs/bltsR.png");
		managementL = new Image("res/CharSelectionImgs/tycL.png");
		managementR = new Image("res/CharSelectionImgs/tycR.png");
		
		
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
		p1one = new SpriteSheet("res/CharSelectionImgs/1p0.png", Window.WIDTH, Window.HEIGHT);
		p1two = new SpriteSheet("res/CharSelectionImgs/1p1.png", Window.WIDTH, Window.HEIGHT);
		p1three = new SpriteSheet("res/CharSelectionImgs/1p2.png", Window.WIDTH, Window.HEIGHT);
		p1four = new SpriteSheet("res/CharSelectionImgs/1p3.png", Window.WIDTH, Window.HEIGHT);
		p1five = new SpriteSheet("res/CharSelectionImgs/1p4.png", Window.WIDTH, Window.HEIGHT);
		p1six = new SpriteSheet("res/CharSelectionImgs/1p5.png", Window.WIDTH, Window.HEIGHT);
		p1seven = new SpriteSheet("res/CharSelectionImgs/1p6.png", Window.WIDTH, Window.HEIGHT);
		p1eight = new SpriteSheet("res/CharSelectionImgs/1p7.png", Window.WIDTH, Window.HEIGHT);
		
		// Sprite sheet initalization for player 2
		p2one = new SpriteSheet("res/CharSelectionImgs/2p0.png", Window.WIDTH, Window.HEIGHT);
		p2two = new SpriteSheet("res/CharSelectionImgs/2p1.png", Window.WIDTH, Window.HEIGHT);
		p2three = new SpriteSheet("res/CharSelectionImgs/2p2.png", Window.WIDTH, Window.HEIGHT);
		p2four = new SpriteSheet("res/CharSelectionImgs/2p3.png", Window.WIDTH, Window.HEIGHT);
		p2five = new SpriteSheet("res/CharSelectionImgs/2p4.png", Window.WIDTH, Window.HEIGHT);
		p2six = new SpriteSheet("res/CharSelectionImgs/2p5.png", Window.WIDTH, Window.HEIGHT);
		p2seven = new SpriteSheet("res/CharSelectionImgs/2p6.png", Window.WIDTH, Window.HEIGHT);
		p2eight = new SpriteSheet("res/CharSelectionImgs/2p7.png", Window.WIDTH, Window.HEIGHT);		
		
		// Animation initialization for player 1
		p1select1 = new Animation(p1one, 100);
		p1select2 = new Animation(p1two, 100);
		p1select3 = new Animation(p1three, 100);
		p1select4 = new Animation(p1four, 100);
		p1select5 = new Animation(p1five, 100);
		p1select6 = new Animation(p1six, 100);
		p1select7 = new Animation(p1seven, 100);
		p1select8 = new Animation(p1eight, 100);
		
		// Animation initialization for player 2
		p2select1 = new Animation(p2one, 100);
		p2select2 = new Animation(p2two, 100);
		p2select3 = new Animation(p2three, 100);
		p2select4 = new Animation(p2four, 100);
		p2select5 = new Animation(p2five, 100);
		p2select6 = new Animation(p2six, 100);
		p2select7 = new Animation(p2seven, 100);
		p2select8 = new Animation(p2eight, 100);
	
		// default, pointing to the first character
		selected1 = p1select1;
		selected2 = p2select1;
		
		p1Character = scienceL;
		p2Character = scienceR;
	}/*End of init*/

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		
		background.draw();			 // background drawing
		
		selected1.setPingPong(true);	 // this means that the animation loops from start to finish and also vice versa	  	
		selected1.draw();			 	 // draws the title screen
		
		selected2.setPingPong(true);	 // this means that the animation loops from start to finish and also vice versa	  	
		selected2.draw();			 	 // draws the title screen
		
		if(charDrawTiming1 >= 90)
			p1Character.draw();				// draw player 1's highlighted character
		if(charDrawTiming2 >= 90)
			p2Character.draw();				// draw player 2's highlighted character
		
		if (confirm){  // once the pop up animation is done then it shows the message
			confirmMsg.draw(235, 150);
		}
		
	}/*End of render*/

	@SuppressWarnings("static-access")
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		this.delta += delta;
		charDrawTiming1 += delta;
		charDrawTiming2 += delta;
		
		// Timing the music to start when the screen has loaded
		if (this.delta == 30){
			TitleScreen.musicCharSelect();
		}
		
		// if both players have chosen a character then a confrimation message will appear
		if (hasSelected1 && hasSelected2){
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
					this.delta = 0;
					sbg.enterState(GameTester.menu, new CombinedTransition(), new VerticalSplitTransition());
				}else
					confirm = false;
					choice = 0;
			}
		} else {
			// logic when scrolling through characters for player 1
			if (in.isKeyPressed(Input.KEY_W) && !hasSelected1){
				charDrawTiming1 = 0; 			//reset character drawing time
				if (OptionState.sfxActv)        // when sfx is activated	
					selecting.play();
				if (index1 != 1 && index1 != 2 && index1 != 3 && index1 != 4)	// if index is not one of the topmost the index is moved upward in the column
					index1 -= 4;									
			    else 										// else the pointer is moved to the lowest box in the column
			    	index1 += 4;	
			}		
		
			else if (in.isKeyPressed(Input.KEY_S) && !hasSelected1){
				charDrawTiming1 = 0; 			//reset character drawing time
				if (OptionState.sfxActv)        // when sfx is activated	
					selecting.play();
				if (index1 != 5 && index1 != 6 && index1 != 7 && index1 != 8)   // if the index is not the lowest in the column then the pointer can move downward 
					index1 += 4;
			    else 										// else the pointer is moved to the topmost box in the column
			    	index1 -= 4;
				
			} else if (in.isKeyPressed(Input.KEY_A) && !hasSelected1){
				charDrawTiming1 = 0; 			//reset character drawing time
				if (OptionState.sfxActv)        // when sfx is activated
					selecting.play();

				if (index1 != 1 && index1 != 5)    // if the index is not in the leftmost edges then the pointer can move right
					index1 -= 1;
				else 											// else, the pointer gets moved to the leftmost boxes in the row
					index1 += 3;
				
			}else if (in.isKeyPressed(Input.KEY_D) && !hasSelected1){
				charDrawTiming1 = 0; 			//reset character drawing time
				if (OptionState.sfxActv)        // when sfx is activated
					selecting.play();
				if (index1 != 4 && index1 != 8 )    // if the index is not in the rightmost edges then the pointer can move right
					index1 += 1;
				else 										// else, the pointer gets moved to the leftmost boxes in the row
					index1 -= 3;
			}
		
			// when a player 1 has decided on a character
			if (in.isKeyPressed(Input.KEY_J) && !hasSelected1){
				if (index1 != 1 && index1 != 2 && index1 != 3 && index1 != 4){
					if (OptionState.sfxActv)        // when sfx is activated
						error.play();
				} else {
					if (OptionState.sfxActv)        // when sfx is activated
						chosen.play();
					hasSelected1 = true;     // to stop movement of player 1
				}
			
			// when player 1 cancels his choice
			} else if (in.isKeyPressed(Input.KEY_K) && hasSelected2){
				if (OptionState.sfxActv)        // when sfx is activated
					cancel.play();
				hasSelected1 = false;     // to resume movement of player 1
				// TODO return character portrait back to idle
			}
		
		
			// logic when scrolling through characters for player 2
			if (in.isKeyPressed(Input.KEY_UP) && !hasSelected2){
				charDrawTiming2 = 0; 			//reset character drawing time
				if (OptionState.sfxActv)        // when sfx is activated	
					selecting.play();
				if (index2 != 1 && index2 != 2 && index2 != 3 && index2 != 4)	// if index is not one of the topmost the index is moved upward in the column
					index2 -= 4;									
			    else 										// else the pointer is moved to the lowest box in the column
			    	index2 += 4;	
			}		
				
			else if (in.isKeyPressed(Input.KEY_DOWN) && !hasSelected2){
				charDrawTiming2 = 0; 			//reset character drawing time
				if (OptionState.sfxActv)        // when sfx is activated	
					selecting.play();
				if (index2 != 5 && index2 != 6 && index2 != 7 && index2 != 8)   // if the index is not the lowest in the column then the pointer can move downward 
					index2 += 4;
			    else 										// else the pointer is moved to the topmost box in the column
			    	index2 -= 4;
							
			} else if (in.isKeyPressed(Input.KEY_LEFT) && !hasSelected2){
				charDrawTiming2 = 0; 			//reset character drawing time
				if (OptionState.sfxActv)        // when sfx is activated
					selecting.play();

				if (index2 != 1 && index1 != 5)    // if the index is not in the leftmost edges then the pointer can move right
					index2 -= 1;
				else 											// else, the pointer gets moved to the leftmost boxes in the row
					index2 += 3;
					
			}else if (in.isKeyPressed(Input.KEY_RIGHT) && !hasSelected2){
				charDrawTiming2 = 0; 			//reset character drawing time
				if (OptionState.sfxActv)        // when sfx is activated
					selecting.play();
				if (index2 != 4 && index2 != 8 )    // if the index is not in the rightmost edges then the pointer can move right
					index2 += 1;
				else 										// else, the pointer gets moved to the leftmost boxes in the row
					index2 -= 3;
			}

			// when a player 2 has decided on a character
			if (in.isKeyPressed(Input.KEY_NUMPAD1) && !hasSelected2){
				if (index2 != 1 && index2 != 2 && index2 != 3 && index2 != 4){
					if (OptionState.sfxActv)        // when sfx is activated
						error.play();
				} else {
					if (OptionState.sfxActv)        // when sfx is activated
						chosen.play();
					hasSelected2 = true;     // to stop movement of player 1
				}
			
			// when player 2 cancels his choice		
			} else if (in.isKeyPressed(Input.KEY_NUMPAD2) && hasSelected2){
				if (OptionState.sfxActv)        // when sfx is activated
					cancel.play();
				hasSelected2 = false;  
			}
	}
		// Updates the highlighted character depending on the selected index for player 1
		if (index1 == 1){
			selected1 = p1select1;
			p1Character = scienceL;
		}else if (index1 == 2){
			selected1 = p1select2;
			p1Character = socsciL;
		}else if (index1 == 3){
			selected1 = p1select3;
			p1Character = artsL;
		}else if (index1 == 4){
			selected1 = p1select4;
			p1Character = managementL;
		}else if (index1 == 5){
			selected1 = p1select5;
			p1Character = missingL;
		}else if (index1 == 6){
			selected1 = p1select6;
			p1Character = missingL;
		}else if (index1 == 7){
			selected1 = p1select7;
			p1Character = missingL;
		}else if (index1 == 8){
			selected1 = p1select8;
			p1Character = missingL;
		}
		// Updates the highlighted character depending on the selected index for player 1
		if (index2 == 1){
			selected2 = p2select1;
			p2Character = scienceR;
		}else if (index2 == 2){
			selected2 = p2select2;
			p2Character = socsciR;
		}else if (index2 == 3){
			selected2 = p2select3;
			p2Character = artsR;
		}else if (index2 == 4){
			selected2 = p2select4;
			p2Character = managementR;
		}else if (index2 == 5){
			selected2 = p2select5;
			p2Character = missingR;
		}else if (index2 == 6){
			selected2 = p2select6;
			p2Character = missingR;
		}else if (index2 == 7){
			selected2 = p2select7;
			p2Character = missingR;
		}else if (index2 == 8){
			selected2 = p2select8;
			p2Character = missingR;
		}
 
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	// Reset all important variables to their default values
	public static void reset(){
		index1 = 1;  		        
		hasSelected1 = false;	
		selected1 = p1select1;
		
		index2 = 1;			
		hasSelected2 = false;	
		selected2 = p2select1;
		
		confirm = false;
		choice = 0; 
		
		delta = 0;
	}
	
	public int getPlayer1Char() { return index1; }
	
	public int getPlayer2Char() { return index2; }
	

}

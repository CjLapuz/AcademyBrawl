package characters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import util.HitBox;

public class Science extends HitBox{
	private int attackTiming;			   // Timing when to stop an attack animation
	
	public float x;                        // position of player
	public static float xa = 0;                   // movement across the x axis

	private float y = 320;                 // position along the y-axis (constant except when jumping, duh)
	private float ya = 0;                  // movement across the y axis
	
	private int view = 1;                  // 0 for facing left and 1 for facing right
	private int player;                    // which player is this player (1 or 2)
	public int attkType;				   // 1 for light attacks, 2 for heavy, and three for projectiles
	
	//private float hP;                      // player hp
	//private float energy;                  // player energy gauge for use of ultimate skills
	
	public boolean hit = false;            // if a player is hit
	public boolean moving = false;        // if a player is blocked by another player
	public boolean stopped = false;
	public boolean attacking = false;
	private boolean falling = false; 
	private boolean jumping = false; 
	private boolean ducking = false;
	public boolean edged = false;
	public boolean defeated = false;
	public boolean won = false;
	public boolean skillAble = false;
	
	// Animations to be used
	private Animation kAnimation, kAnimationIL, kAnimationIR, kAnimationWL, kAnimationWR, kAnimationJL, kAnimationJR,
					  kAnimationLPL, kAnimationLPR, kAnimationSPL, kAnimationSPR,
					  kAnimationLKL,kAnimationSKL, kAnimationSKR, kAnimationLKR, kAnimationDPL,kAnimationDPR, 
					  kAnimationDKL, kAnimationDKR, kAnimationTL, kAnimationTR, kAnimationWin;

	// Images to be used
	private Image hitL, hitR, deadL, deadR, duckL, duckR, hitDL, hitDR;
	
	// constructor for the class
	public Science(int player){
		this.player = player;
		if (player == 2){
			x = 660;       // If 2nd player the view is towards the first player 
			view = 0;	  //and is positioned near the end of the screen
		}
	}
	
	// Initialization of all variables
	public void init() throws SlickException {
		if (player == 1)
			super.rePosition(x + 50, y); // sets the current players position for collision detection
		else 
			super.rePosition(x - super.getWidth(), y); // sets the current players position for collision detection		
		
		// initialization of Animations per action 
		kAnimationIL = new Animation(new SpriteSheet("res/characters/spritesScns/idleL.png", 255, 250), 100);
		kAnimationIR = new Animation(new SpriteSheet("res/characters/spritesScns/idleR.png", 255, 250), 100);
		kAnimationWL = new Animation(new SpriteSheet("res/characters/spritesScns/walkL.png", 255, 250), 100);
		kAnimationWR = new Animation(new SpriteSheet("res/characters/spritesScns/walkR.png", 255, 250), 100);
		kAnimationJL = new Animation(new SpriteSheet("res/characters/spritesScns/jumpL.png", 200, 250), 100);
		kAnimationJR = new Animation(new SpriteSheet("res/characters/spritesScns/jumpR.png", 200, 250), 100);
		kAnimationLPL = new Animation(new SpriteSheet("res/characters/spritesScns/punchLL.png", 255, 250), 100);
		kAnimationLPR = new Animation(new SpriteSheet("res/characters/spritesScns/punchLR.png", 255, 250), 100);
		kAnimationSPL = new Animation(new SpriteSheet("res/characters/spritesScns/punchSL.png", 255, 250), 100);
		kAnimationSPR = new Animation(new SpriteSheet("res/characters/spritesScns/punchSR.png", 255, 250), 100);
		kAnimationLKL = new Animation(new SpriteSheet("res/characters/spritesScns/kickLL.png", 255, 250), 100);
		kAnimationLKR = new Animation(new SpriteSheet("res/characters/spritesScns/kickLR.png", 255, 250), 100);
		kAnimationSKL = new Animation(new SpriteSheet("res/characters/spritesScns/kickSL.png", 255, 250), 100);
		kAnimationSKR = new Animation(new SpriteSheet("res/characters/spritesScns/kickSR.png", 255, 250), 100);
		kAnimationDPL = new Animation(new SpriteSheet("res/characters/spritesScns/duckPL.png", 255, 250), 100); 
		kAnimationDPR = new Animation(new SpriteSheet("res/characters/spritesScns/duckPR.png", 255, 250), 100); 
		kAnimationDKL = new Animation(new SpriteSheet("res/characters/spritesScns/duckKL.png", 255, 250), 100);
		kAnimationDKR = new Animation(new SpriteSheet("res/characters/spritesScns/duckKR.png", 255, 250), 100);
		kAnimationTL = new Animation(new SpriteSheet("res/characters/spritesScns/throwL.png", 255, 250), 100);
		kAnimationTR = new Animation(new SpriteSheet("res/characters/spritesScns/throwR.png", 255, 250), 100);
		kAnimationWin = new Animation(new SpriteSheet("res/characters/spritesScns/win.png", 300, 250), 250);
		
		hitL = new Image("res/characters/spritesScns/hitBL.png");
		hitR = new Image("res/characters/spritesScns/hitBR.png");
		hitDL = new Image("res/characters/spritesScns/hitDL.png");
		hitDR = new Image("res/characters/spritesScns/hitDR.png");
		deadL = new Image("res/characters/spritesScns/deadL.png");
		deadR = new Image("res/characters/spritesScns/deadR.png");
		duckL = new Image("res/characters/spritesScns/duckL.png");
		duckR = new Image("res/characters/spritesScns/duckR.png");
		
		kAnimationIL.setSpeed(kAnimationIL.getSpeed() / 3f);
		kAnimationIR.setSpeed(kAnimationIR.getSpeed() / 3f);
		// main animation to be drawn
		if (player == 1) // set as idly facing right by default for player 1
			kAnimation = kAnimationIR;
		else			 // set as idly facing left by default for player 2
			kAnimation = kAnimationIL;
		
		kAnimation.setSpeed(kAnimation.getSpeed() / 2);
	}
	
	// Rendering of graphics
	public void render(GameContainer gc, Graphics g) {
		
		if(defeated){
			if (view == 0){
				g.drawImage( deadL, x, y);
			}
			else{
				g.drawImage( deadR, x, y);
			}
		} else if (won){
			g.drawAnimation(kAnimationWin, x, y);
		}
			
		// if the player is not falling then there is animation
		if (!falling && !hit && !ducking || (ducking && attacking)){
			if(!defeated && !won){
				g.drawAnimation(kAnimation, x, y);
			}
		}else if (falling){                                                  // the moment the player is falling the player is shown to be free falling
			if (view == 0)
				g.drawImage(kAnimationJL.getImage(3), x, y);  // gets the image where a free falling image is seen
			else 
				g.drawImage(kAnimationJR.getImage(3), x, y); // gets the image where a free falling image is seen
		}
		else if (hit){
			if (ducking){
			    if (view == 0)
					g.drawImage(hitDL, x, y);
				else
					g.drawImage(hitDR, x, y);
			}else{ 
				if (view == 0)
					g.drawImage(hitL, x, y);
				else
					g.drawImage(hitR, x, y);
			}
		} else if (ducking && !attacking && !defeated && !won){
			if (view == 0)
				g.drawImage(duckL, x, y);
			else
				g.drawImage(duckR, x, y);
		}
	}

	// Update every new action
	public void update(GameContainer gc, int delta) {
		
		if (attacking){		      // When attacking the timer starts and the timing of stopping the attack animation starts
			attackTiming += delta;
			attackLogic();
		}
		
		if (hit){
			hitLogic();
		}
		
		if (gc.isPaused())
			kAnimation.stop();
	    else{
	    	kAnimation.start();
	    	//kAnimation.update(delta);
		
	    	if(!attacking)
	    		super.rePosition(x + 25, y);
	    	
	    	if (!attacking && !jumping && !ducking && !falling)
	    		super.resize(100, 250);
		
	    	// user input
	    	Input in = gc.getInput();
		
    	
	    	// Player 1 controls
	    	if (player == 1){
	    		// if player is not hit he can attack
	    		if (!hit && !attacking && !jumping && !falling){
	    			
	    			//light punch
	    			if (in.isKeyPressed(Input.KEY_U) && !attacking) {
	    				attacking = true;
	    				
	    				// duck punching
	    				if (ducking){		
	    					if (view == 0) // if facing left
	    						kAnimation = kAnimationDPL;
	    					else           // if facing right
	    						kAnimation = kAnimationDPR;
	    				// normal punching	
	    				}else {			   
	    					if (view == 0) // if facing left
	    						kAnimation = kAnimationLPL;
	    					else           // if facing right
	    						kAnimation = kAnimationLPR;
	    				}
	    				
	    			//light kick 
	    			}else if (in.isKeyPressed(Input.KEY_J) && !attacking) {
	    				attacking = true;
	    				
	    				//duck kicking
	    				if (ducking){
	    					if (view == 0) // if facing left
	    						kAnimation = kAnimationDKL;
	    					else           // if facing right
	    						kAnimation = kAnimationDKR;
	    				//normal kicking	
	    				}else {
	    					if (view == 0)	// if facing left
	    						kAnimation = kAnimationLKL;
	    					else 			// if facing right
	    						kAnimation = kAnimationLKR;
	    				}
	    				//heavy punch
	    			}else if (in.isKeyPressed(Input.KEY_I) && !attacking) {
	    				attacking = true;
	    				
	    				//duck punching
	    				if (ducking){
	    					if (view == 0) // if facing left
	    						kAnimation = kAnimationDPL;
	    					else           // if facing right
	    						kAnimation = kAnimationDPR;
	    				//normal kicking
	    				}else {
	    					if (view == 0)	// if facing left
	    						kAnimation = kAnimationSPL;
	    					else 			// if facing right
	    						kAnimation = kAnimationSPR;
	    				}
	    				//heavy kick	
	    			}else if (in.isKeyPressed(Input.KEY_K) && !attacking) {
	    				attacking = true;
	    				
	    				//duck kicking
	    				if (ducking){
	    					if (view == 0) // if facing left
	    						kAnimation = kAnimationDKL;
	    					else           // if facing right
	    						kAnimation = kAnimationDKR;
	    				//normal kicking	
	    				}else {
	    					if (view == 0)	// if facing left
	    						kAnimation = kAnimationSKL;
	    					else 			// if facing right
	    						kAnimation = kAnimationSKR;
	    				}
	    			}
	    			
	    			//throw projectile
    			    else if (in.isKeyPressed(Input.KEY_O) && !attacking) {
    			    	if(skillAble){
    			    		attacking = true;
    			    		if (view == 0)	// if facing left
    			    			kAnimation = kAnimationTL;
    			    		else 			// if facing right
    			    			kAnimation = kAnimationTR;
    			    	}
    				}
	    		}

		    	
	    		// If the player is either not hit and not attacking he can move
	    		if (!attacking && !hit){
				
	    			//jumping
	    			// jump only works if the player is not currently jumping
	    			if (in.isKeyPressed(Input.KEY_W) && !jumping && !falling) {
	    				ya = 15;        // sets the y-velocity
	    				if (view == 0) // if facing left
	    					kAnimation = kAnimationJL;
	    				else           // if facing right
	    					kAnimation = kAnimationJR;
	    				super.resize(125, 65);
	    				jumping = true;
	    				ducking = false;
	    				
	    			//ducking 	
	    			}else if (in.isKeyDown(Input.KEY_S)) {
	    				xa = 0; 		 // stops the player at that point 
	  
	    				moving = false;
	    				ducking = true;
	    				super.rePosition(x, super.getCenterY());
	    				
	    			//moving left
	    			}else if (in.isKeyDown(Input.KEY_A) && !jumping && !falling) {
	    				view = 0; 		                     // changes the view of the player to facing right
	    				moving = true;
	    				ducking = false;
	    				if (!stopped)
	    					xa = (200 / 1000.0f) * -delta; // sets the x-velocity
	    				kAnimation = kAnimationWL;
					
	    			//moving right
	    			} else if (in.isKeyDown(Input.KEY_D) && !jumping && !falling) {
	    				view = 1;            			  // changes the view to left
	    				moving = true;
	    				ducking = false;
	    				if (!stopped)
	    					xa = (200 / 1000.0f) * delta; // sets the x-velocity
	    				kAnimation = kAnimationWR;
					
	    			// if player is not doing any actions
	    			} else if (!jumping && !falling && !attacking){
	    				xa = 0;                         // player is not moving
	    				if (view == 0)                  // if facing left
	    					kAnimation = kAnimationIL;  
	    				else                            // if facing right
	    					kAnimation = kAnimationIR;
	    				moving = false;
	    				ducking = false;
	    			}
	    			x += xa;						 // updates the current position of the player
	    			logic();                         // external function used to keep the player in bounds
	    		}
	    
	    		// Player 2 controls
	    	} else {
	    		// if player is not hit or currently attacking he can attack
	    		if (!hit && !attacking && !jumping && !falling){
	    			//light punch
	    			if (in.isKeyPressed(Input.KEY_NUMPAD4) && !attacking) {
	    				attacking = true;
	    				
	    				// duck punching
	    				if (ducking){		
	    					if (view == 0) // if facing left
	    						kAnimation = kAnimationDPL;
	    					else           // if facing right
	    						kAnimation = kAnimationDPR;
	    				// normal punching	
	    				}else {			   
	    					if (view == 0) // if facing left
	    						kAnimation = kAnimationLPL;
	    					else           // if facing right
	    						kAnimation = kAnimationLPR;
	    				}
	    				
	    			//light kick 
	    			}else if (in.isKeyPressed(Input.KEY_NUMPAD1) && !attacking) {
	    				attacking = true;
	    				
	    				//duck kicking
	    				if (ducking){
	    					if (view == 0) // if facing left
	    						kAnimation = kAnimationDKL;
	    					else           // if facing right
	    						kAnimation = kAnimationDKR;
	    				//normal kicking	
	    				}else {
	    					if (view == 0)	// if facing left
	    						kAnimation = kAnimationLKL;
	    					else 			// if facing right
	    						kAnimation = kAnimationLKR;
	    				}
	    				//heavy punch
	    			}else if (in.isKeyPressed(Input.KEY_NUMPAD5) && !attacking) {
	    				attacking = true;
	    				
	    				//duck punching
	    				if (ducking){
	    					if (view == 0) // if facing left
	    						kAnimation = kAnimationDPL;
	    					else           // if facing right
	    						kAnimation = kAnimationDPR;
	    				//normal kicking
	    				}else {
	    					if (view == 0)	// if facing left
	    						kAnimation = kAnimationSPL;
	    					else 			// if facing right
	    						kAnimation = kAnimationSPR;
	    				}
	    				//heavy kick	
	    			}else if (in.isKeyPressed(Input.KEY_NUMPAD2) && !attacking) {
	    				attacking = true;
	    				
	    				//duck kicking
	    				if (ducking){
	    					if (view == 0) // if facing left
	    						kAnimation = kAnimationDKL;
	    					else           // if facing right
	    						kAnimation = kAnimationDKR;
	    				//normal kicking	
	    				}else {
	    					if (view == 0)	// if facing left
	    						kAnimation = kAnimationSKL;
	    					else 			// if facing right
	    						kAnimation = kAnimationSKR;
	    				}
	    			
	    			
	    			//light kick 
			    }else if (in.isKeyPressed(Input.KEY_NUMPAD6) && !attacking) {
			    	if(skillAble){
			    		attacking = true;
			    		if (view == 0)	// if facing left
							kAnimation = kAnimationTL;
						else 			// if facing right
							kAnimation = kAnimationTR;
			    	}
				}
	    	}
		    
	    		// If the player is either not hit or not attacking he can move
	    		if (!attacking && !hit){
	    			//jumping
	    			// jump only works if the player is not currently jumping
	    			if (in.isKeyPressed(Input.KEY_UP) && !jumping && !falling) {
	    				ya = 15;        // sets the y-velocity

	    				if (view == 0) // if facing left
	    					kAnimation = kAnimationJL;
	    				else           // if facing right
	    					kAnimation = kAnimationJR;
	    				jumping = true;
	    				ducking = false;
					
	    			//ducking 	
	    			}else if (in.isKeyDown(Input.KEY_DOWN)) {
	    				xa = 0; 		 // stops the player at that point 

	    				moving = false;
	    				super.rePosition(x, super.getCenterY());
	    				ducking = true;
				
	    			//moving left
	    			}else if (in.isKeyDown(Input.KEY_LEFT) && !jumping && !falling) {
	    				view = 0; 		                     // changes the view of the player to facing right
	    				if (!stopped)
	    					xa = (200 / 1000.0f) * -delta; // sets the x-velocity
	    				kAnimation = kAnimationWL;
	    				moving = true;
	    				ducking = false;
					
	    			//moving right
	    			} else if (in.isKeyDown(Input.KEY_RIGHT) && !jumping && !falling) {
	    				view = 1;            			  // changes the view to left
	    				if (!stopped)
	    					xa = (200 / 1000.0f) * delta; // sets the x-velocity
	    				kAnimation = kAnimationWR;
	    				moving = true;
	    				ducking = false;
					
	    			// if player is not doing any actions
	    			} else if (!jumping && !falling && !attacking){
	    				xa = 0;                         // player is not moving
	    				if (view == 0)                  // if facing left
	    					kAnimation = kAnimationIL;  
	    				else                            // if facing right
	    					kAnimation = kAnimationIR;
	    				moving = false;
	    				ducking = false;
	    			}
	    			x += xa;						 // updates the current position of the player
	    			logic();                         // external function used to keep the player in bounds
	    		}
	    	}
	    }
	}
	
	  // End of update()
	
	// Contains some game logics that are updated every update call
	public void logic(){
		if (x > -40 && x <670)
			edged = false;
		if (x <= -40){  		 		// if the player is nearing the left side of the screen
			x = -40;					// gets repositioned to the left edge of the screen
			edged = true;
		}
		if (x >= 670){				// if the player is nearing the right side of the screen
			x = 670;				// gets repositioned to the right edge of the screen
			edged = true;
		}
		if (jumping && !falling){    // if the player is starting to jump
			if(moving)
				if(view == 0)
					x -= 10;
				else
					x += 10;
			super.resize(125, 65);
			y -=  ya;			    // y-position of is updated by the y-velocity	
		}
		
		if (y == 80 && jumping){   // when the player reaches the peak of his jump 
			falling = true;         // falling occurs and the player momentarily stops in air
		    jumping = false;
		    ya = 0;				    // falling occurs and the player momentarily stops in air
		    if(moving)
				if(view == 0)
					x -= 10;
				else
					x += 10;
		}   
		  
		if (y >= 200 && falling){  // checks if the player hits the ground
			jumping = false;      
			falling = false;
			
			if (view == 0)
				kAnimation = kAnimationIL;
			else 
				kAnimation = kAnimationIR;
			
			y = 320;			   // repositions the player to the default y-position
			ya = 0;                // player stops going up or down
		}
		
		if (falling && !jumping){
			ya += 1.5;			  // if the player is currently falling ya or gravity gradually increases
			y += ya;			  // and the player's descent gets faster
			if(moving)
				if(view == 0)
					x -= 10;
				else
					x += 10;
		}
		
	}// End of logic()
	
	// gets the current view of the player (facing left or right)
	public int getView(){
		return view;
	}
	
	// sets the current view of the player (facing left or right)
	public void setView(int view){
		this.view = view;
	}
	
	public void attackLogic(){
		// timing for light punch and light kick since both have only two frames
		if(kAnimation == kAnimationLPL || kAnimation == kAnimationLPR || kAnimation == kAnimationLKL || kAnimation == kAnimationLKR ||
		   kAnimation == kAnimationDPL || kAnimation == kAnimationDPR || kAnimation == kAnimationDKL || kAnimation == kAnimationDKR){
			if (attackTiming >= 260){
				attacking = false;
				attackTiming = 0;
				resetAnim(kAnimation);
			} else if (attackTiming == 255){
				if (view == 1)
					super.resize(185, 250);
				else 
					super.rePosition(x - 20, y);
				attkType = 1;
			}
		}
		
		else if(kAnimation == kAnimationSPL || kAnimation == kAnimationSPR || kAnimation == kAnimationSKL || kAnimation == kAnimationSKR){
			if (attackTiming >= 375){
				attacking = false;
				attackTiming = 0;
				resetAnim(kAnimation);
			} else if (attackTiming == 360){
				if (view == 1)
					super.resize(185, 250);
				else 
					super.rePosition(x - 20, y);
				attkType = 2;
			}
		}
		
		else if(kAnimation == kAnimationTL || kAnimation == kAnimationTR){
			super.resize(0, 0);
			if (attackTiming >= 475){
				attacking = false;
				attackTiming = 0;
				resetAnim(kAnimation);
			} 
		}
	}
	public void hitLogic(){
		if (!edged){
			if (view == 0){
				x += 40;
			
			} else{
				x -= 40;
			}
		}
	}
	
	public void resetAnim(Animation anim){
		if (anim == kAnimationLPL)
			kAnimationLPL.restart();
		else if (anim == kAnimationLPR)
			kAnimationLPR.restart();
		else if (anim == kAnimationDPL)
			kAnimationDPL.restart();
		else if (anim == kAnimationDPR)
			kAnimationDPR.restart();
		else if (anim == kAnimationLKL)
			kAnimationLKL.restart();
		else if (anim == kAnimationDKL)
			kAnimationDKL.restart();
		else if (anim == kAnimationLKR)
			kAnimationLKR.restart();
		else if (anim == kAnimationDKR)
			kAnimationDKR.restart();
		else if (anim == kAnimationSPL)
			kAnimationSPL.restart();
		else if (anim == kAnimationSPR)
			kAnimationSPR.restart();
		else if (anim == kAnimationSKL)
			kAnimationSKL.restart();
		else if (anim == kAnimationSKR)
			kAnimationSKR.restart();
		else if (anim == kAnimationTL)
			kAnimationTL.restart();
		else if (anim == kAnimationTR)
			kAnimationTR.restart();
	}
}

package main;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import characters.ComSci;


public class PlayState extends BasicGameState {
	private int ID = 6;
	private int delta;
	private int time = 0;
	
	private int indx = 0;    // for the pause selections
	
	private Image UI, pauseDisplay, resumeSelct, charSelct, menuSelct, arena;  
	
	private Sound pause, resume, scroll, hit;
	
	private ComSci player1, player2;
	
	private boolean paused;
	
	// TODO add UI to improve health bars
	private float hbx1 = 29;	 // health bar coodinates
	private float hby = 42;
	
	private float hbwM = 292;    // health bar length
	private float hbwM2 = -292;  // health bar length
	private float hbh = 28;      // health bar height
	
	private Rectangle hbIn;	     // health foreground
	private Rectangle hb2In;     // health foreground 
	public PlayState(int state) {}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player1 = new ComSci(1);   // Make player 1
		player2 = new ComSci(2);   // Make player 2
		player1.init();               // Initialize player 1
		player2.init();               //Initialize player 2
		
		// sound effects when pausing and unpausing
		pause = new Sound("res/soundEffects/pause.wav");
		resume = new Sound("res/soundEffects/resume.wav");
		scroll = new Sound("res/soundEffects/choose.wav");
		hit = new Sound("res/soundEffects/hit.wav");
		
		// images initialization
		UI = new Image("res/PlayImgs/Gui.png");
		pauseDisplay = resumeSelct = new Image("res/PlayImgs/resume.png");
		charSelct = new Image("res/PlayImgs/charSelect.png");
		menuSelct = new Image("res/PlayImgs/menu.png");
		arena = new Image("res/Arenas/arena1.png");
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		arena.draw();
		player1.render(gc, g);
		player2.render(gc, g);
		
		// show amount of time passed and how to puase and resume
		g.drawString("" +time, 385, 50);
		
		if (paused){
			pauseDisplay.draw(200, 100);
		}
		
		// initialize health bars
		hbIn = new Rectangle(hbx1 + 4, hby + 4, hbwM, hbh - 8);
		hb2In = new Rectangle(790 - 22, hby + 4, hbwM2, hbh - 8);
		
		
		// coloring and filling the health bar
		g.setColor(Color.red);
		g.setLineWidth(0);
		g.fill(hbIn);
		
		// to keep the health bar from overflowing
		if (hbwM > 0) {
			g.draw(hbIn);
		} else {
			hbwM = 0;
		}
		
		// coloring and filling the health bar
		g.setColor(Color.blue);
		g.fill(hb2In);
		
		// to keep the health bar from overflowing
		if (hbwM2 < 0) {
			g.draw(hb2In);
		} else {
			hbwM2 = 0;
		}
		g.setColor(Color.cyan);  
		
		g.setBackground(Color.gray);
		
		// For debugging purposes
		player1.draw(g);
		player2.draw(g);
				
		UI.draw();				//draw the player UI
		

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		this.delta += delta;		// update the amount of time passed in milliseconds
		if (this.delta % 1000  == 0)
			time += 1;   			// update the amount of time passed in seconds
		
		player1.update(gc, delta);
		player2.update(gc, delta);
		
		Input in = gc.getInput();
		
		
		if (paused){
			if (in.isKeyPressed(Input.KEY_ENTER) || in.isKeyPressed(Input.KEY_J)){
				paused = false;
				gc.setPaused(false);
				if (indx == 0){
					if (OptionState.sfxActv)        // when sfx is activated
						resume.play();
					TitleScreen.resumeMusic();
				} else if (indx == 1){
					reset();
					if (OptionState.sfxActv)        // when sfx is activated
						resume.play();
					sbg.enterState(GameTester.characterSelect, new FadeOutTransition(), new FadeInTransition());
				} else {
					reset();
					if (OptionState.sfxActv)        // when sfx is activated
						resume.play();
					sbg.enterState(GameTester.menu, new FadeOutTransition(), new FadeInTransition());
				}
			} else if (in.isKeyPressed(Input.KEY_UP) || in.isKeyPressed(Input.KEY_W)){
				if (OptionState.sfxActv)        // when sfx is activated
					scroll.play();
				if (indx == 0){
					indx = 2;
					pauseDisplay = menuSelct;
				} else if (indx == 1){
					indx = 0;
					pauseDisplay = resumeSelct;
				} else {
					indx = 1;
					pauseDisplay = charSelct;
				}
			} else if (in.isKeyPressed(Input.KEY_DOWN) || in.isKeyPressed(Input.KEY_S)){
				if (OptionState.sfxActv)        // when sfx is activated
					scroll.play();
				if (indx == 0){
					indx = 1;
					pauseDisplay = charSelct;
				} else if (indx == 1){
					indx = 2;
					pauseDisplay = menuSelct;
				} else {
					indx = 0;
					pauseDisplay = resumeSelct;
				}
			}
		}
		else {
			if (in.isKeyPressed(Input.KEY_ESCAPE) || in.isKeyPressed(Input.KEY_BACK)){
				if (!gc.isPaused()){
					paused = true;
					if (OptionState.sfxActv)        // when sfx is activated
						pause.play();
					TitleScreen.pauseMusic();
					gc.pause();
				}
			}
		}
			if (player1.attacking){
				if (player1.getView() == 0)      // the players hit box extends when attacking
					player1.resize(175, 175);
				else 
					player2.resize(175, 175);    // the players hit box extends when attacking
				if (player1.hitTest(player2)){
					hit.play();
					player2.hit = true;          // if player 1 hits player 2
					hbwM2 += 3;                  // player 1loses hp
				}
			}
			
			if (hbwM2 >= 0){
				System.out.println("PLAYER 2 LOSE");    // if hp is zero you are led to the game analysis state 
				endGame(sbg);
			}
			
			else if (player2.attacking){
				if (player2.getView() == 0)		// the players hit box extends when attacking
					player2.resize(175, 175);
				else 
					player1.resize(175, 175);  // the players hit box extends when attacking
				if (player2.hitTest(player1)){
					hit.play();
					player1.hit = true;        // if player 2 hits player 1
					hbwM -= 3;                 // player 1loses hp
				}
			if (hbwM <= 0){
				System.out.println("PLAYER 1 LOSE");    // if hp is zero you are led to the game analysis state 
				endGame(sbg);
			}
			
			}
			if (player1.hitTest(player2) || player2.hitTest(player1)){
				// when hit repositions the player just a bit to falsify the collision
				if (player1.moving && !player2.moving){
					if (player1.x < player2.x)
						player1.x = player2.x - 100;
					else 
						player1.x = player2.x + 100;
					//FIXME too much acceleration when landing from a jump
					//player2.x += (3 * player1.xa);
				} else if (!player1.moving && player2.moving){
					if (player2.x < player1.x)
						player2.x = player1.x - 100;
					else 
						player2.x = player1.x + 100;
					//FIXME too much acceleration when landing from a jump
					//player1.x += (3 * player2.xa);
				} else{
					// if both players are moving and they collide, stops them 
					player1.xa = 0;
					player2.xa = 0;
					player1.stopped = true;
					player2.stopped = true;
				}
			} else {
				// allows them to move when not collided
				player1.stopped = false;
				player2.stopped = false;
			}
			
		
		// Makes both players face each other
		if (player1.x > player2.x){
			player1.setView(1);
			player2.setView(0);
		} else {
			player1.setView(0);
			player2.setView(1);
		}
		
	}
	
	private void reset(){
		delta = 0;
	    time = 0;
		

		hbwM = 292;    
		hbwM2 = -292;  
		player1.x = 0;
		player2.x = 800;
	}
	
	private void endGame(StateBasedGame sbg){
		reset();
		TitleScreen.musicCredits();				// TODO add some victory taunt and victory UI
		sbg.enterState(GameTester.summary, new FadeOutTransition(), new FadeInTransition());
	}
	
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
}

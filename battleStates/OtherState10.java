package battleStates;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import characters.Arts;
import characters.Management;
import main.CharacterSelectState;
import main.GameTester;
import main.OptionState;
import main.TitleScreen;
import util.HealthBar;


public class OtherState10 extends BasicGameState {
	private int ID = 19;
	static int delta;
	static int stageTime = 99;			// timer for each round
	static int stateTime;              // timer for the game state 
	
	public static int p1Wins;
	public static int p2Wins;
	
	private int roundNo = 1;
	
	private int indx = 0;    // for the pause selections
	
	private Image UI, pauseDisplay, resumeSelct, charSelct, menuSelct, arena;  
	
	private Animation round1, round2, round3, fight, ko;
	
	private Sound pause, resume, scroll, hit;
	
	private Management player1;
	private Arts player2; 
	
	
	private static boolean paused = false;
	static boolean ready = false;
	static boolean started = false;
	private static boolean hasWon = false;
	
	private static HealthBar hp1;		 // Player 1 hp
	private static HealthBar hp2;		 // Player 2 hp
	
	UnicodeFont fontTime;
	
	public OtherState10(int state) {}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player1 = new Management(1);
		player2 = new Arts(2);
		
		player1.init();
		player2.init();
		
		
		hp1 = new HealthBar(1, 292, 29, 51);		// Initialize player 1 hp
		hp2 = new HealthBar(2, -292, 768, 51);		// Initialize player 2 hp
		
		
		round1 = new Animation((new SpriteSheet("res/PlayImgs/round1.png", 150, 100)), 250);
		round2 = new Animation((new SpriteSheet("res/PlayImgs/round2.png", 150, 100)), 250);
		round3 = new Animation((new SpriteSheet("res/PlayImgs/round3.png", 150, 100)), 250);
		fight = new Animation((new SpriteSheet("res/PlayImgs/fight.png", 150, 100)), 150);
		ko = new Animation((new SpriteSheet("res/PlayImgs/ko.png", 150, 100)), 100);
		
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
		
		fontTime = new UnicodeFont("res/PlayImgs/font.ttf", 34, false, false);
        fontTime.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontTime.addAsciiGlyphs();
        fontTime.loadGlyphs();
		
        CharacterSelectState.reset();
        
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		arena.draw();
		
		player1.render(gc, g);
		player2.render(gc, g);
	
		// shows the amount of time passed and how to pause and resumeMusic
		fontTime.drawString(383, 41, "" +stageTime, Color.yellow);
		
		if (paused){
			pauseDisplay.draw(200, 100);
		}
		hp1.draw(g);
		hp2.draw(g);
		
		UI.draw();				//draw the player UI
		
		if(!ready){
			if (roundNo == 1)
				round1.draw(250, 100, 300, 200);
			else if (roundNo == 2)
				round2.draw(250, 100, 300, 200);
			else if (roundNo == 3)
				round3.draw(250, 100, 300, 200);
		} else if (ready && !started){
			fight.draw(250, 100, 300, 200);
		} else if(player1.won || player2.won)
			ko.draw(250, 100, 300, 200);
	}

	@SuppressWarnings("static-access")
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(stateTime >= 930 && !ready){
			ready = true;
			stateTime = 0;
		}
		
		if(player1.defeated || player2.defeated){
			if (stateTime >= 1750){
				hasWon = true;
			}
		}			
		
		
		else if(stateTime >= 990 && ready){
			started = true;
			stateTime = 0;
		}
		
		if (!started){
			player1.stopped = true;
			player2.stopped = true;			
		}else {
			player1.stopped = false;
			player2.stopped = false;			
		}
		
		if((p1Wins == 0 && p2Wins == 2) || (p2Wins == 0 && p1Wins == 2) || (p1Wins == 1 && p2Wins == 3) || (p1Wins == 3 && p2Wins == 1)){
			endGame(sbg);
		}
		
		else if (hp2.getCurrentHp() == 0 || (stageTime == 0 && ( -(hp2.getCurrentHp()) < hp1.getCurrentHp()))){
			player1.won = true;
			player2.defeated = true;			
			started = false;
			p1Wins();
		} else if (hp1.getCurrentHp() == 0 || (stageTime == 0 && ( -(hp2.getCurrentHp()) > hp1.getCurrentHp()))){
			player2.won = true;
			player1.defeated = true;			
			

			started = false;
			p2Wins();
		} 
		
		if (stageTime == 0 && -hp1.getCurrentHp() == hp2.getCurrentHp()){ // TODO: do something when a tie occurs
			resetMatch();
		}
		
		this.delta += delta;		// update the amount of time passed in milliseconds
		if (!started)
			stateTime += delta;			// for timing of stage animations
		if (this.delta % 960  == 0){
			if (stageTime != 0 && started){
				stageTime -= 1;   			// update the amount of time passed in seconds

			}
		}
	
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
					resetMatch();
					if (OptionState.sfxActv)        // when sfx is activated
						resume.play();
					CharacterSelectState.reset();
					sbg.enterState(GameTester.characterSelect, new FadeOutTransition(), new FadeInTransition());
				} else {
					resetMatch();
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
			if(player1.hitTest(player2)){
				if (!player1.attacking && !player2.attacking ){				
					if(player1.moving){
						if(player1.x < player2.x)
							player1.x -= 10;
						else 
							player1.x += 10;
					}				
					else if(player2.moving){
						if(player2.x < player1.x)
							player2.x -= 10;
						else 
							player2.x += 10;
					}
					
					else if(player2.edged){
						if(player2.x > 400)
							player1.x -= 50;
						else 
							player1.x += 50;
					}
					
					else if(player1.edged){
						if(player1.x > 400)
							player2.x -= 50;
						else 
							player2.x += 50;
					}
				} else {
					if(player1.attacking){
						if(OptionState.sfxActv)
							hit.play();
						if(player2.edged){
							if(player1.getView() == 0)
								player1.x += 40;
							else 
								player1.x -= 40;
						}
						player2.hit = true;
						player1.resize(0, 0);
						if (player1.attkType == 1)
							hp2.takeDamage(15);
						else if (player1.attkType == 2)
							hp2.takeDamage(29);
						else{
							
						}
					}
					if (player2.attacking){
						if(OptionState.sfxActv)
							hit.play();
						if(player1.edged){
							if(player2.getView() == 0)
								player2.x += 40;
							else 
								player2.x -= 40;
						}
						player1.hit = true;
						player2.resize(0, 0);
						if (player2.attkType == 1)
							hp1.takeDamage(15);
						else if (player2.attkType == 2)
							hp1.takeDamage(29);
						else{
							
							}
						}
				}			
				
			} else {
				player1.stopped = false;
				player1.hit = false;
				player2.stopped = false;
				player2.hit = false;
			}
			
		
		// Makes both players face each other	
			if(!player1.moving){
				if (player1.x > player2.x){
					player1.setView(0);
				} else {
					player1.setView(1);
				}
			}
			if(!player2.moving){
				if (player2.x > player1.x){
					player2.setView(0);
				} else {
					player2.setView(1);
				}
			}
		}
	}
	
	private void resetMatch(){
		delta = 0;
	    stageTime = 99;
	    stateTime = 0;
		ready = false;
		started = false;

		hp1.setCurrentHp(292);    
		hp2.setCurrentHp(-292);
		
		player1.x = 0;
		player1.defeated = false;
		player2.x = 800;
		player2.defeated = false;
		player1.won = false;
		player2.won = false;
		hasWon = false;
	}
	
	private void endGame(StateBasedGame sbg){
		resetMatch();
		p1Wins = 0;
		p2Wins = 0;
		TitleScreen.musicCredits();				// TODO add some victory taunt and victory UI
		sbg.enterState(GameTester.summary, new FadeOutTransition(), new FadeInTransition());
	}
	
	public void p1Wins(){
		if(hasWon){
			roundNo++;
			resetMatch();
			p1Wins++;
		}
	}
	
	public void p2Wins(){
		if(hasWon){
			roundNo++;
			resetMatch();
			p2Wins++;
		}
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
}


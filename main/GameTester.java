package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

import util.EmptyState;
import util.SplashScreen;
import util.Window;

public class GameTester extends StateBasedGame {
	
	public static final String GAME_NAME = "Academy BrawL";
	public static final int empty = -2;
	public static final int splash = -1;
	public static final int titleScreen = 0;
	public static final int menu = 1;
	public static final int characterSelect = 2;
	public static final int controls = 3;
	public static final int options = 4;
	public static final int credits = 5;
	public static final int play = 6;
	public static final int summary = 7;
	public static final int versus = 8;
	public static final int stage = 9;
	
	
	public GameTester(String name) {
		super(name);
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
        gc.setTargetFrameRate(120);                  // set desired frame rate
        gc.setAlwaysRender(true);                   // always rendering even if alt-tabbed
        gc.setMinimumLogicUpdateInterval(15);      // min update interval
        gc.setMaximumLogicUpdateInterval(15);      // max update interval
        gc.setVSync(true);							// to enable Vertical syncing
        gc.setShowFPS(false);                       // to remove fps display
        
        // adding the first state to the Game container
        this.addState(new SplashScreen(splash));
        this.addState(new EmptyState(empty));
        this.addState(new TitleScreen(titleScreen));
        this.addState(new MenuState(menu));
		this.addState(new CharacterSelectState(characterSelect));
		this.addState(new OptionState(options));
		this.addState(new CreditsState(credits));
		this.addState(new PlayState(play));
		this.addState(new ControlsState(controls));
		this.addState(new BattleSummaryState(summary));
		this.addState(new PreMatchState(versus));
		
		// enter the title screen
		this.enterState(empty, new FadeInTransition(), new EmptyTransition());
    }
	
	public static void main(String[] args) {
		AppGameContainer apgc;
		
		try {
			apgc = new AppGameContainer(new GameTester(GAME_NAME));
			
			//FIXME aint workin fam
			//String[] icon = { "res/titleImgs/icon2.png", "res/titleImgs/icon1.png" };
			//apgc.setIcons(icon);
			
			apgc.setDisplayMode(Window.WIDTH, Window.HEIGHT, false);   // set window border to 800 x 600 (WidthxHeight)
			apgc.start();
		} catch (SlickException e) {	
			e.printStackTrace();
		}
	}

}

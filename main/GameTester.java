package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

import battleStates.OtherState1;
import battleStates.OtherState10;
import battleStates.OtherState11;
import battleStates.OtherState12;
import battleStates.OtherState13;
import battleStates.OtherState14;
import battleStates.OtherState15;
import battleStates.OtherState2;
import battleStates.OtherState3;
import battleStates.OtherState4;
import battleStates.OtherState5;
import battleStates.OtherState6;
import battleStates.OtherState7;
import battleStates.OtherState8;
import battleStates.OtherState9;
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
	/*public static final int play = 6;*/
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
		this.addState(new ControlsState(controls));
		this.addState(new BattleSummaryState(summary));
		this.addState(new PreMatchState(versus));
		this.addState(new OtherState1(10));
		this.addState(new OtherState2(11));
		this.addState(new OtherState3(12));
		this.addState(new OtherState4(13));
		this.addState(new OtherState5(14));
		this.addState(new OtherState6(15));
		this.addState(new OtherState7(16));
		this.addState(new OtherState8(17));
		this.addState(new OtherState9(18));
		this.addState(new OtherState10(19));
		this.addState(new OtherState11(20));
		this.addState(new OtherState12(21));
		this.addState(new OtherState13(22));
		this.addState(new OtherState14(23));
		this.addState(new OtherState15(24));
		
		// enter the title screen
		this.enterState(empty, new FadeInTransition(), new EmptyTransition());
    }
	
	public static void main(String[] args) {
		AppGameContainer apgc;
		
		try {
			apgc = new AppGameContainer(new GameTester(GAME_NAME));
			
			
			String[] icon = { "res/controlsImgs/32xx32.tga", "res/controlsImgs/24xx24.tga", "res/controlsImgs/16xx16.tga" };
			apgc.setIcons(icon);
			
			apgc.setDisplayMode(Window.WIDTH, Window.HEIGHT, false);   // set window border to 800 x 600 (WidthxHeight)
			apgc.start();
		} catch (SlickException e) {	
			e.printStackTrace();
		}
	}

}

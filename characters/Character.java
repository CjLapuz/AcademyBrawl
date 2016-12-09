package characters;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import util.HitBox;

public abstract class Character extends HitBox{
	public int player;
	public int view = 1;                  // 0 for facing left and 1 for facing right
	public int attkType;				   // 1 for light attacks, 2 for heavy, and three for projectiles
	
	//private float hP;                      // player hp
	//private float energy;                  // player energy gauge for use of ultimate skills
	
	public boolean hit = false;            // if a player is hit
	public boolean moving = false;        // if a player is blocked by another player
	public boolean stopped = false;
	public boolean attacking = false;
	public boolean falling = false; 
	public boolean jumping = false; 
	public boolean ducking = false;
	public boolean edged = false;
	public boolean defeated = false;
	public boolean won = false;
	public boolean skillAble = false;
	
	public Character(int player) {
		this.player = player;
	}
	
	public abstract void init() throws SlickException;
	
	public abstract void render(GameContainer gc, Graphics g);
	
	public abstract void update(GameContainer gc, int delta);
}

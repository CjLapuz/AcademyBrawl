package util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SkillGauge {
	float currentCharge;
	float x;
	float y;
	private int player;

	public SkillGauge(int player, float x, float y) {
		this.player = player;
		setCurrentCharge(0);
		setX(x);
		setY(y);

	}

	public void charge(int charge) {
		if (player == 1){
			currentCharge += charge;
			if (currentCharge >= 295)
				currentCharge = 295;
		}else{
			currentCharge -= charge;
			if (currentCharge <= -295)
				currentCharge = -295;
		}
	}
	
	public void draw(Graphics g) throws SlickException {
		g.setColor(Color.cyan);
		g.fillRect(x, y, currentCharge, 18);

	}

	public void setX(float x) {
		this.x = x;
	}

	public float getX() {
		return x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public float getY() {
		return y;
	}
	
	public void setCurrentCharge(int hp) {
		this.currentCharge = hp;
	}

	public float getCurrentCharge() {
		return currentCharge;
	}
}

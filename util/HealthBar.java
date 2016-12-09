package util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Color;

public class HealthBar {
	int maxHealth;
	float currentHealth;
	float x;
	float y;
	private int player;

	public HealthBar(int player, int hp, float x, float y) {
		this.player = player;
		setMaxHp(hp);
		setCurrentHp(maxHealth);
		setX(x);
		setY(y);

	}

	public void takeDamage(int damage) {
		if (player == 1){
			currentHealth -= damage;
			if (currentHealth <= 0)
				currentHealth = 0;
		}else{
			currentHealth += damage;
			if (currentHealth >= 0)
				currentHealth = 0;
		}
	}
	
	public void draw(Graphics g) throws SlickException {
		g.setColor(Color.red);
		g.fillRect(x, y, maxHealth, 18);
		g.setColor(Color.green);
		g.fillRect(x, y, currentHealth, 18);

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
	
	public void setCurrentHp(int hp) {
		this.currentHealth = hp;
	}

	public float getCurrentHp() {
		return currentHealth;
	}
	
	public void setMaxHp(int maxHealth) { 
		this.maxHealth = maxHealth;
	}

	public int getMaxHp() {
		return maxHealth;
	}
}

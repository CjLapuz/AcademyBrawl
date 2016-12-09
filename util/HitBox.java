package util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;

public class HitBox {
	private float x, y, width, height;
	
	public HitBox(){
		
	}
	
	public HitBox(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}

	public float getCenterX() {
		return x + (width / 2.0f);
	}

	public float getCenterY() {
		return y + (height / 2.0f);
	}

	public void resize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void rePosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(x, y, width, height);
	}

	public void update(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public Rectangle getBounds(){ return new Rectangle(x, y, width, height);}
	
	public boolean hitTest(HitBox b){
		return getBounds().intersects(b.getBounds());
	}
}
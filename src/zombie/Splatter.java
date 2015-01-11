package zombie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


public class Splatter {

	private Rectangle position;
	
	private static int width = 10;
	
	private Sprite sprite;
	
	public Splatter(){
		position = new Rectangle();
	}
	
	public Splatter(Point p){
		position = new Rectangle(p,new Dimension(width, width));
		sprite = SpriteStore.get().getSprite("res/splat.gif");
	}
	
	public void draw(Graphics g, Point offset){
		//g.setColor(Color.red);
		//g.fill3DRect((int)position.getX()-(int)offset.getX(), (int)position.getY()-(int)offset.getY(), width, width, true);
	
		sprite.draw(g, (int)position.getX()-(int)offset.getX(), (int)position.getY()-(int)offset.getY());
	}
	
}

package zombie;

import items.ExplosiveWeapon;
import items.ProjectileWeapon;

import java.awt.Point;

public class ExplosiveProjectile extends Projectile {

	private Game game;

	public ExplosiveProjectile() {
		game=null;
	}

	public ExplosiveProjectile(Game g, double a, ExplosiveWeapon weapon) {
		super(a, weapon);
		game=g;
	}

	public ExplosiveProjectile(Game g, Point p, Tile c, double a,
			ExplosiveWeapon weapon) {
		super(p, c, a, weapon);
		game=g;
	}
	
	public void explode(){
		
		ProjectileWeapon tempWeapon = new ProjectileWeapon(game, "Blast", 100, 10, 100, 100, 5);
		Point loc;
		double theta;
		
		for (int i = 1; i < 40; i++) {
			theta=(double)i*2.0*Math.PI/40.0;
			loc = new Point(getPosition().getLocation());
			loc.translate((int)(10*Math.cos(theta)),(int)(10*Math.sin(theta)));
			game.addEntity(new Projectile(loc, getCurrentTile(), (double)i*2.0*Math.PI/40.0, tempWeapon));
		}
		System.out.println("Blast");
	}

}

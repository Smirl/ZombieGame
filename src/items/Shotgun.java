package items;

import zombie.Game;
import zombie.Projectile;

public class Shotgun extends ProjectileWeapon{

	public Shotgun() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Shotgun(Game g, int speed) {
		super(g, speed);
		// TODO Auto-generated constructor stub
	}

	public Shotgun(Game g, String l, double dam, double range, long rate,
			int ammo, int speed) {
		super(g, l, dam, range, rate, ammo, speed);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tryToAction(double angle){
		setAmmo(getAmmo()-1);
		if(getAmmo()>0){
			getGame().addEntity(new Projectile(getGame().getCurrentEntities().get(0).getPosition().getLocation(), getGame().getCurrentEntities().get(0).getCurrentTile(), angle, this));
			getGame().addEntity(new Projectile(getGame().getCurrentEntities().get(0).getPosition().getLocation(), getGame().getCurrentEntities().get(0).getCurrentTile(), angle+0.3, this));
			getGame().addEntity(new Projectile(getGame().getCurrentEntities().get(0).getPosition().getLocation(), getGame().getCurrentEntities().get(0).getCurrentTile(), angle+0.2, this));
			getGame().addEntity(new Projectile(getGame().getCurrentEntities().get(0).getPosition().getLocation(), getGame().getCurrentEntities().get(0).getCurrentTile(), angle-0.3, this));
			getGame().addEntity(new Projectile(getGame().getCurrentEntities().get(0).getPosition().getLocation(), getGame().getCurrentEntities().get(0).getCurrentTile(), angle-0.2, this));
		}
	}

}

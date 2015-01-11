package items;

import zombie.ExplosiveProjectile;
import zombie.Game;
import zombie.Projectile;

public class ExplosiveWeapon extends ProjectileWeapon {

	public ExplosiveWeapon() {
	}

	public ExplosiveWeapon(Game g, int speed) {
		super(g, speed);
	}

	public ExplosiveWeapon(Game g, String l, double dam, double range,long rate, int ammo, int speed) {
		super(g, l, dam, range, rate, ammo, speed);
	}

	@Override
	public void tryToAction(double angle){
		setAmmo(getAmmo()-1);
		if(getAmmo()>0){
			getGame().addEntity(new ExplosiveProjectile(getGame(),getGame().getCurrentEntities().get(0).getPosition().getLocation(), getGame().getCurrentEntities().get(0).getCurrentTile(), angle, this));
		}
	}
	
}

package items;

import zombie.Game;
import zombie.Projectile;

public class ProjectileWeapon extends Item {

	private Game game;

	private double speed;

	public ProjectileWeapon(){
		super();
		speed=0;
		game=null;
	}
	public ProjectileWeapon(Game g, int speed) {
		super();
		this.setSpeed(speed);
		game=g;
	}

	/**
	 * New Projectile Weapon
	 * @param g Game
	 * @param l Label
	 * @param dam The Damage it does
	 * @param range Range Multipler
	 * @param rate Min time between shots
	 * @param ammo Starting ammo
	 * @param speed Projectile speed
	 */
	public ProjectileWeapon(Game g,String l, double dam, double range, long rate, int ammo, int speed) {
		super(l, dam, range,rate,ammo);
		this.setSpeed(speed);
		game=g;
	}

	@Override
	public void tryToAction(double angle) {
		setAmmo(getAmmo()-1);
		if(getAmmo()>0){
			game.addEntity(new Projectile(game.getCurrentEntities().get(0).getPosition().getLocation(), game.getCurrentEntities().get(0).getCurrentTile(), angle, this));
		}
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeed() {
		return speed;
	}
	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}
	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}
}

package items;

import zombie.Projectile;

public abstract class Item {

	/** */
	private double damage;
	/** the higher the number the quicker a projectile's health decrease*/
	private double inverseRange;
	/** */
	private String name;
	/** */
	private int ammo;
    /** Min ms */
	private long rateOfFire;
	
	/****************************************************************************/
	// Constructors
	/****************************************************************************/
	
	public Item(){
		damage=0;
		inverseRange=0;
		name="";
		ammo=1;
		rateOfFire=50;
	}
	
	public Item(String l, double dam, double range, long rate, int ammo){
		name=l;
		damage=dam;
		inverseRange=range;
		rateOfFire=rate;
		this.ammo=ammo;
	}
	
	public abstract void tryToAction(double angle);
	
	/****************************************************************************/
	// Getters and Setters
	/****************************************************************************/

	/**
	 * @return the damage
	 */
	public double getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(double damage) {
		this.damage = damage;
	}

	/**
	 * @return the range
	 */
	public double getRange() {
		return inverseRange;
	}

	/**
	 * @param range the range to set
	 */
	public void setRange(double range) {this.inverseRange = range;}

	/**
	 * @return the name
	 */
	public String getName() {return name;}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {this.name = name;	}

	/**
	 * @return the ammo
	 */
	public int getAmmo() {return ammo;	}

	/**
	 * @param ammo the ammo to set
	 */
	public void setAmmo(int ammo) {this.ammo = ammo;	}

	public void setRateOfFire(long rateOfFire) {
		this.rateOfFire = rateOfFire;
	}

	public long getRateOfFire() {
		return rateOfFire;
	}
	
}

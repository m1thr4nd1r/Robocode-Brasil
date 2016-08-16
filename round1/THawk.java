
package orion.round1;

import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.util.Random;

public class THawk extends Robot{

	private double gunTurnAmt = 10;
	private boolean movingForward = true;
	private double factor = 40;
	private double maxDistanceFire;
	private double firePower = 1;
	private boolean hit = false;
	private double hitFire = 0;
	private Random randomGenerator = new Random();

	/**
	 * MyFirstRobot's run method - Seesaw
	 */
	public void run() {

		double energy = this.getEnergy();

		maxDistanceFire = Math.min(getBattleFieldWidth(), getBattleFieldHeight());

		while (true) {
			if (this.getEnergy() - energy > 0) {
				hit = true;
				hitFire++;
			} else {
				hit = false;
				hitFire--;
			}
			energy = this.getEnergy();
			ahead(randomGenerator.nextInt(100)); // Move ahead 100
			turnGunRight(360); // Spin gun around
			back(randomGenerator.nextInt(100)); // Move back 100
			if (!hit) {
				decrementFirePower();
			}
		}
	}

	/**
	 * Fire when we see a robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// System.out.println("Get Name: " + e.getName());
		// System.out.println("Get Energy: " + e.getEnergy());
		// System.out.println("Get Bearing: " + e.getBearing());
		// System.out.println("Get Heading: " + e.getHeading());
		// System.out.println("Get Velocity: " + e.getVelocity());
		// System.out.println("Get Distance: " + e.getDistance());

		// System.out.println("My Energy: " + getEnergy());
		// System.out.println("My Heading: " + getHeading());
		// System.out.println("My Velocity: " + getVelocity());

		if (e.getDistance() < maxDistanceFire && e.getEnergy() > 0.5) {

			// calculate firepower based on distance
			firePower = Math.min(500 / e.getDistance(), 3);

			// Our target is close.
			gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
			turnGunRight(gunTurnAmt);

			if (e.getDistance() < 100) {

				if (hitFire % 5 == 0) {
					incrementFirePower();
				}

				if (e.getBearing() > -90 && e.getBearing() <= 90) {
					back(factor);
				} else {
					ahead(factor);
				}
			}

		} else {
			firePower = 0.5;
		}

		fire(firePower);
		scan();
	}

	public void onHitByBullet(HitByBulletEvent e) {
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmt);
		turnLeft(90 - e.getBearing());
		System.out.println("Hit By: " + e.getBearing() + ", " + e.getHeading());
		if (e.getBearing() > -90 && e.getBearing() <= 90) {
			back(factor);
		} else {
			ahead(factor);
		}
	}

	public void onHitWall(HitWallEvent e) {
		System.out.println("Ops, Hit Wall!! " + e.getBearing());
		reverseDirection(90 - e.getBearing());
	}

	public void onHitRobot(HitRobotEvent e) {
		System.out.println("Ops, Hit Robot!! " + e.getBearing());
		reverseDirection(90 - e.getBearing());
	}

	/**
	 * reverseDirection:  Switch from ahead to back & vice versa
	 */
	public void reverseDirection(double bearing) {
		if (movingForward) {
			back(bearing);
			movingForward = false;
		} else {
			ahead(bearing);
			movingForward = true;
		}
	}

	public void decrementFirePower() {
		firePower--;
		if (firePower < 0) {
			firePower = 0.5;
		}
	}

	public void incrementFirePower() {
		firePower = (firePower + 1) % 4 + 1;
	}

}

/**
 * Copyright (c) 2001-2016 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package orion;

import robocode.*;
import robocode.util.Utils;

/**
 * MyFirstRobot - a sample robot by Mathew Nelson.
 * <p/>
 * Moves in a seesaw motion, and spins the gun around at each end.
 *
 * @author Mathew A. Nelson (original)
 */
public class Orion extends AdvancedRobot {
	
	private byte scanDirection = 1;
	private byte moveDirection = 1;
	private double enemyBearing;

	public void run() {
		
		setAdjustGunForRobotTurn(true);
    	setAdjustRadarForGunTurn(true);
    	setAdjustRadarForRobotTurn(true);

		while (true) {
			doMove();
			setTurnRadarRight(360);
			execute();
		}
	}

	// normalizes a bearing to between +180 and -180
	double normalizeBearing(double angle) {
		while (angle >  180) angle -= 360;
		while (angle < -180) angle += 360;
		return angle;
	}

	public void doMove()
	{
		setAhead(100 * moveDirection);
	
		// always square off against our enemy
		//setTurnRight(enemyBearing + 90);
		setTurnRight(normalizeBearing(enemyBearing + 90 - (15 * moveDirection)));

		// strafe by changing direction every 20 ticks
		if (getTime() % 2 == 0) {
			moveDirection *= -1;
			setAhead(150 * moveDirection);
		}
		
	}

	/**
	 * Fire when we see a robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		/*
		setTurnRadarRight(getHeading() - getRadarHeading() + e.getBearing());
		scanDirection *= -1; // changes value from 1 to -1
		//setTurnRadarRight(360 * scanDirection);
		enemyBearing = e.getBearing();
		//setTurnRight(enemyBearing + 90);
		setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
		if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10)
			setFire(Math.min(400 / e.getDistance(), 3));
		*/

		double radarTurn =
		        // Absolute bearing to target
	 			getHeadingRadians() + e.getBearingRadians()
		        // Subtract current radar heading to get turn required
	 			- getRadarHeadingRadians();
		
		setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));

		double gunTurn = 
				getHeadingRadians() + e.getBearingRadians()
				- getGunHeadingRadians();
		
		setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn));
		setFire(1f);
		execute();
	}

	/**
	 * We were hit!  Turn perpendicular to the bullet,
	 * so our seesaw might avoid a future shot.
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		//turnLeft(90 - e.getBearing());
	}

	public void onHitWall(HitWallEvent e) { moveDirection *= -1; }
	public void onHitRobot(HitRobotEvent e) { moveDirection *= -1; }
}												


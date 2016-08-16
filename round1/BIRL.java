package orion.round1;
import robocode.*;
import robocode.util.Utils;
import java.util.Random;
import java.awt.Color;


public class BIRL extends AdvancedRobot {
  boolean targetLocked = false;
  boolean greatLock = false;
  boolean movingForward = true;
  boolean robotClose = false;
  int bulletMiss = 0;

  public void run() {
    setColors(Color.black, Color.red, Color.white, Color.cyan, Color.green);
    setAdjustGunForRobotTurn(true);
    setAdjustRadarForGunTurn(true);
    setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
    int tickMove = 1;
    double forwardSpeed = Math.max(getBattleFieldWidth(),getBattleFieldHeight());
    double backwardSpeed = forwardSpeed/2;
    Random random = new Random();
    int randomReverse = 1 + random.nextInt(64);

    do {
      targetLocked = false;
      scan();
      if (!targetLocked) {
        setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
      }

      if (tickMove % randomReverse == 0) {
        movingForward = true;
        randomReverse = 1 + random.nextInt(64);
        if (!greatLock && !robotClose && !x1()) setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
      } else if (tickMove % randomReverse == randomReverse / 2) movingForward = false;
      tickMove++;

      if (movingForward) setAhead(forwardSpeed);
      else setAhead(-backwardSpeed);


    } while (true);
  }

  private boolean x1() {
    return getOthers() == 1;
  }

  private void pointRadar(double bearingRadians) {
    double absBearing = bearingRadians + getHeadingRadians();
    setTurnRadarRightRadians(Utils.normalRelativeAngle(absBearing - getRadarHeadingRadians()) * 2);
  }

  private void pointGun(double bearingRadians, double headingRadians, double velocity, double magicNumber) {
    double absoluteBearing = getHeadingRadians() + bearingRadians;
    setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing -
      getGunHeadingRadians() + (velocity * Math.sin(headingRadians -
      absoluteBearing) / magicNumber)));
  }

  public void onScannedRobot(ScannedRobotEvent e) {
    targetLocked = true;
    greatLock = e.getEnergy() < 20;
    robotClose = e.getDistance() < 100;

    double distance = e.getDistance();
    double skew = (distance - (300 - bulletMiss * 5)) / 5;
    double angleAdjuster = 0;

    double bulletPower = 3.0;
    if ((distance > 200) && (distance <= 400)) {
      bulletPower = 2;
    } else if (distance > 400) {
      bulletPower = 1;
    }

    if (getEnergy() < 10)
      bulletPower = getEnergy() < 5 ? 0.1 : 1.0;
    double magicNumber = 21 - bulletPower * 3;

    if (e.getBearing() < 0) angleAdjuster = - 180;
    if (!movingForward) skew *= -1;

    pointRadar(e.getBearingRadians());
    pointGun(e.getBearingRadians(), e.getHeadingRadians(), e.getVelocity(), magicNumber);
    setFire(bulletPower);
    setTurnLeft(90 - e.getBearing() - angleAdjuster - skew);
  }

  public void onHitRobot(HitRobotEvent e) {
    if (x1()) return;
    robotClose = true;
    movingForward = !movingForward;
    pointRadar(e.getBearingRadians());
    pointGun(e.getBearingRadians(), 0, 0, 11.0);
    setFire(3);
    scan();
  }

  public void onHitByBullet(HitByBulletEvent e) {
    bulletMiss = 0;
    if (!greatLock && !x1()) {
      double gunBearing = getGunHeading() - getHeading();
      double angleDistance = Math.abs(gunBearing - e.getBearing());
      System.out.println(angleDistance);
      if (45 < angleDistance && angleDistance < 315) {
        System.out.println("Changed target");
        targetLocked = false;

        pointRadar(e.getBearingRadians());
        scan();
      }
    }
  }

  public void onHitWall(HitWallEvent e) {
    movingForward = !movingForward;
  }

  public void onBulletMissed(BulletMissedEvent event) {
    bulletMiss = Math.min(bulletMiss + 1, 40);
  }

  public void onBulletHit(BulletHitEvent event) {
    bulletMiss = Math.max(bulletMiss - 1, 0);
  }

  public void onWin(WinEvent e) {
    for (int i = 0; i < 50; i++) {
      turnRight(30);
      turnLeft(30);
    }
  }
}

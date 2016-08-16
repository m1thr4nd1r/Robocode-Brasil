package orion.round1;
import java.awt.Color;
import robocode.*;

public class Terminator extends Robot
{
 	public double speed = 5000;
 	public double pontoCanto1 = 18;
 	public double pontoCanto2 = 582;
 	public double pontoCanto3 = 782;
 	public  double angulo_parede;
	public boolean hitRobot;
	public boolean deFrente;
	public void run() {
		setRadarColor(new Color(0, 0, 150));
		setBodyColor(new Color(0, 50, 50));
		setScanColor(new Color(50, 50, 50));
		setGunColor(new Color(50, 0, 0));
		
    	while(true) {
        	back(speed);
			double heading = getHeading();
			System.out.println(heading);
        	if(speed == 0 && hitRobot == false){
			if(deFrente == true){
				turnGunLeft(90);
				turnGunRight(90);    
			} 
			else{
            	turnGunRight(90);
            	turnGunLeft(90);        
        			}
				}
    		}
		}
	public void onScannedRobot(ScannedRobotEvent e) {
	if(speed == 0 || hitRobot == true){
    	fire(3);
		}
	else{
		fire(2);
		}
	}

	public void onHitByBullet(HitByBulletEvent e) {
	if(speed == 0){
		varreCanto(angulo_parede);
    	back(382);
		//turnGunRight(90);
		hitRobot = false;
		deFrente = false;
		}    
	}    
    
	public void onHitRobot(HitRobotEvent e){
		turnRight(180);
		hitRobot = true;
		deFrente = true;
	}    

public void onHitWall(HitWallEvent e) {
    	double pontoX = getX();
    	double pontoY = getY();
    	angulo_parede = e.getBearing();
    
	if(hitRobot == true ){
		if((pontoX == 18 && pontoY == 18 ) ||
   		(pontoX == 18 && pontoY == 582) ||
   		(pontoX == 782 && pontoY == 18) ||
   		(pontoX == 782 && pontoY == 582)){
    		speed = 0;
			hitRobot = false;
     			}
			}
  	else if(hitRobot == false &&(pontoX == 18 && pontoY == 18 ) ||
   		(pontoX == 18 && pontoY == 582) ||
   		(pontoX == 782 && pontoY == 18) ||
   		(pontoX == 782 && pontoY == 582)){
    		speed = 0;
     		}
    	else{
        	varreCanto(angulo_parede);    
        	}
		}
	public void varreCanto(double e){      
    	turnLeft((270 - e) - 360);    
    	speed = 5000;            
	}
}

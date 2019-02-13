
public class WillowParticle extends FireworkParticle{

	public WillowParticle(){
		super();
	}
	
	public WillowParticle(int[] pos, int velocity, int radius, int historyLength, int angle){
		super(pos, velocity, radius, historyLength, angle);
	}
	
	public void accelerationFactor(){
		velocity[0] *= 0.99;
		velocity[1] += 0.05;
	}
	
}

import java.awt.Color;
import java.util.Random;

public class WillowFirework extends Firework{

	public WillowFirework(){
		super();
	}
	
	public WillowFirework(boolean random, int[] frameSize){
		super(random, frameSize);
		
		velocity *= 2;
		
		numParticles /= 5;
		
		particles = new FireworkParticle[numParticles];
		
		burnTime *= 5;
	}
	
	public WillowFirework(int entryPoint, int currentHight, int detonationHight, int velocity, int numParticles, int burnTime, int radius, int historyLength, Color color){
		super(entryPoint, currentHight, detonationHight, velocity, numParticles, burnTime, radius, historyLength, color);
	}
		
	
	public void addParticles(){
		
		Random rand = new Random();
		int particleVelocity = 1 + rand.nextInt(2);
		//int angle;// = 135;//135 270
		
		for(int i = 0; i < numParticles; i++){
			//angle = rand.nextInt(5);
			particles[i] = new WillowParticle((new int[] {entryPoint, currentHight}), particleVelocity, burnTime, historyLength, rand.nextInt(180) + 180);
		}
	}
	
}

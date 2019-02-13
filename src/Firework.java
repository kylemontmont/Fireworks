import java.awt.Color;
import java.util.Random;

public class Firework {

	private long creationTime;
	
	int[] pos;// = new int[]{0,0};
	int entryPoint;
	int velocity = 5;
	int detonationHight;
	int currentHight;
	int radius;
	int numParticles = 25;
	Color color;
	
	int burnTime;
	
	int historyLength;
	int[][] history = new int[historyLength][2];
	
	Random rand = new Random();
	
	FireworkParticle[] particles = new FireworkParticle[numParticles];
	
	private boolean completed = false;
	private boolean isDetonated = false;
	
	long detonationTime;
	
	public Firework(){
		creationTime = System.currentTimeMillis();
	}
	
	public Firework(boolean random, int[] frameSize){
		this();
		
		Random rand = new Random();
		
		entryPoint = rand.nextInt(frameSize[0]);
		currentHight = frameSize[0];
		detonationHight = rand.nextInt(frameSize[1]);
		
		velocity = 1 + rand.nextInt(5);
		
		numParticles = 20 + rand.nextInt(100);
		particles = new FireworkParticle[numParticles];
		
		burnTime = rand.nextInt(2 * 1000) + 1000;
		
		radius = velocity * burnTime / (1000/30);
		
		historyLength = 10 + rand.nextInt(radius/2 - (int)(radius*0.05));
		history = new int[historyLength][2];
		
		color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
		
	}
	
	public Firework(int entryPoint, int detonationHight){
		this();
		this.entryPoint = entryPoint;
		this.detonationHight = detonationHight;
	}
	
	public Firework(int entryPoint, int detonationHight, Color color){
		this();
		this.entryPoint = entryPoint;
		this.detonationHight = detonationHight;
		this.color = color;
	}
	
	public Firework(int entryPoint, int currentHight, int detonationHight, Color color){
		this();
		this.entryPoint = entryPoint;
		this.currentHight = currentHight;
		this.detonationHight = detonationHight;
		this.color = color;
	}
	
	public Firework(int entryPoint, int currentHight, int detonationHight, int radius, Color color){
		this();
		this.entryPoint = entryPoint;
		this.currentHight = currentHight;
		this.detonationHight = detonationHight;
		this.radius = radius;
		this.color = color;
	}
	
	public Firework(int entryPoint, int currentHight, int detonationHight, int velocity, int numParticles, int radius, Color color){
		this();
		this.entryPoint = entryPoint;
		this.currentHight = currentHight;
		this.detonationHight = detonationHight;
		this.velocity = velocity;
		this.numParticles = numParticles;
		particles = new FireworkParticle[numParticles];
		this.radius = radius;
		this.color = color;
	}
	
	public Firework(int entryPoint, int currentHight, int detonationHight, int velocity, int numParticles, int radius, int historyLength, Color color){
		this();
		this.entryPoint = entryPoint;
		this.currentHight = currentHight;
		this.detonationHight = detonationHight;
		this.velocity = velocity;
		this.numParticles = numParticles;
		particles = new FireworkParticle[numParticles];
		this.radius = radius;
		this.historyLength = historyLength;
		history = new int[historyLength][2];
		this.color = color;
	}
	
	public Firework(int entryPoint, int currentHight, int detonationHight, int velocity, int numParticles, int burnTime, int radius, int historyLength, Color color){
		this();
		this.entryPoint = entryPoint;
		this.currentHight = currentHight;
		this.detonationHight = detonationHight;
		this.velocity = velocity;
		this.numParticles = numParticles;
		particles = new FireworkParticle[numParticles];
		this.burnTime = burnTime;
		this.radius = radius;
		this.historyLength = historyLength;
		history = new int[historyLength][2];
		this.color = color;
	}
	
	public void updateCreationTime(){
		creationTime = System.currentTimeMillis();
	}
	
	public long getCreationTime(){
		return creationTime;
	}
	
	public int[] getCenter(){
		return new int[] {entryPoint, currentHight};
	}
	
	public void addHistory(){
		for(int i = 0; i < historyLength - 1; i++){
			history[i] = history[i + 1];
		}
		history[historyLength-1] = getCenter();
	}
	
	public void update(){
		
		if(getIsDetonated()){
			//for(FireworkParticle p : particles)
				//p.update();
			//if(radius - particles[0].getDistanceFromStart() < radius / 4 && particles[0].shouldDarken())
			if(burnTime -(System.currentTimeMillis() - detonationTime) < 500)
				color = color.darker();
		}else if(currentHight > detonationHight){  //if below detonation height move rocket
			currentHight -= velocity;
			//addHistory();
		}else  //we should detonate it
			detonation();
		
	}
	
	public Color getColor(){
		return color;
	}
	
	public boolean checkCompleted(){
		
		if(!getIsDetonated())
			return false;
		
		for(FireworkParticle f : particles){
			if(!f.checkFinished())
				return false;
		}
		return true;
		
		//return (System.currentTimeMillis() - creationTime) > (25 * 1000);
	}
	
	public boolean getIsDetonated(){
		return isDetonated;
	}
	
	public FireworkParticle[] getParticles(){
		return particles;
	}
	
	public int[][] getHistory(){
		return history;
	}
	
	public void detonation(){
		
		isDetonated = true;
		detonationTime = System.currentTimeMillis();
		
		addParticles();
		
	}
	
	public void addParticles(){
		
		Random rand = new Random();
		int particleVelocity = 1 + rand.nextInt(2);
		
		for(int i = 0; i < numParticles; i++)
			particles[i] = new FireworkParticle((new int[] {entryPoint, currentHight}), particleVelocity, burnTime, historyLength, (rand.nextInt(360) + 1) );
	
	}
	
	public String toString(){
		return creationTime + "";
	}
	
	public boolean equals(Object o){
		return (getCreationTime() == ((Firework)o).getCreationTime());
	}
	
}
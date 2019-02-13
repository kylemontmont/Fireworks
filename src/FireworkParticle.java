
public class FireworkParticle {

	double[] pos;
	int[] startPos;
	int absoluteVelocity = 5;
	double[] velocity = new double[2];
	int angle = 90;
	
	int darknessaFactor = 0;
	
	int radius;
	
	int historyLength = 50;
	int[][] history = new int[historyLength][2];
	
	long burnTime;// = 100 * 1000;
	long creationTime;
	
	
	public FireworkParticle(){
		creationTime = System.currentTimeMillis();
	}
	
	public FireworkParticle(int[] pos){
		this();
		this.pos = new double[]{pos[0], pos[1]};
	}
	
	public FireworkParticle(int[] pos, int angle){
		this();
		this.pos = new double[]{pos[0], pos[1]};
		startPos = pos;
		this.angle = angle;
		calculateVelocity();
	}
	
	public FireworkParticle(int[] pos, int radius, int angle){
		this();
		this.pos = new double[]{pos[0], pos[1]};
		startPos = pos;
		this.radius = radius;
		this.angle = angle;
		calculateVelocity();
	}
	
	public FireworkParticle(int[] pos, int velocity, int radius, int angle){
		this();
		this.pos = new double[]{pos[0], pos[1]};
		startPos = pos;
		this.absoluteVelocity = velocity;
		//Random rand = new Random();
		this.radius = radius;// + rand.nextInt(5);
		this.angle = angle;
		calculateVelocity();
	}

	/*public FireworkParticle(int[] pos, int velocity, int radius, int historyLength, int angle){
		this();
		this.pos = new double[]{pos[0], pos[1]};
		startPos = pos;
		this.absoluteVelocity = velocity;
		//Random rand = new Random();
		this.radius = radius;// + rand.nextInt(5);
		this.historyLength = historyLength;
		history = new int[historyLength][2];
		this.angle = angle;
		calculateVelocity();
	}*/
	
	public FireworkParticle(int[] pos, int velocity, int burnTime, int historyLength, int angle){
		this();
		this.pos = new double[]{pos[0], pos[1]};
		startPos = pos;
		this.absoluteVelocity = velocity;
		//Random rand = new Random();
		this.burnTime = burnTime;
		this.historyLength = historyLength;
		history = new int[historyLength][2];
		for(int i = 0; i < historyLength; i++)
			history[i] = pos;
		this.angle = angle;
		calculateVelocity();
	}
	
	public int[] getIntPos(){
		return new int[]{(int)pos[0], (int)pos[1]};
	}
	
	public double[] getPos(){
		return pos;
	}
	
	public boolean shouldDarken(){
		return darknessaFactor++ % 15 == 0;
	}
	
	public int getDistanceFromStart(){
		return (int)Math.sqrt(Math.pow(Math.abs(pos[0] - (double)startPos[0]), 2.0) + Math.pow(Math.abs(pos[1] - (double)startPos[1]), 2.0));
	}
	
	public boolean checkFinished(){
		return (System.currentTimeMillis() - creationTime) > burnTime;
		//return getDistanceFromStart() > radius;
	}
	
	public void setAbsoluteVelocity(int absoluteVelocity){
		this.absoluteVelocity = absoluteVelocity;
	}
	
	public void calculateVelocity(){		
		velocity[0] = Math.cos(Math.toRadians(angle)) * absoluteVelocity;
		velocity[1] = Math.sin(Math.toRadians(angle)) * absoluteVelocity;
	}
	
	public void addHistory(){
		for(int i = 0; i < historyLength - 1; i++){
			history[i] = history[i + 1];
		}
		history[historyLength-1] = getIntPos();
	}

	public void update(){
		
		accelerationFactor();
		
		/*while(radius - getDistanceFromStart() < historyLength && historyLength > 1){
			historyLength--;
			int[][] oldHistory = history;
			history = new int[historyLength][2];
			for(int i = 0; i < historyLength; i++){
				history[i] = oldHistory[i + 1];
			}
		}*/
				
		
		addHistory();
		
		//if(!checkFinished()){
			pos[0] += velocity[0];
			pos[1] += velocity[1];
		//}
	}

	public void accelerationFactor(){
		velocity[0] *= 0.998;
		velocity[1] *= 0.998;
	}
	
	public int[][] getHistory(){
		return history;
	}
	
	public String toString(){
		return pos[0] + ", " + pos[1];
	}
	
}
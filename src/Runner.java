import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


public class Runner extends JPanel implements ActionListener{

static int[] frameSize = new int[] {800, 600};
	
	Timer timer = new Timer(0, this);
	static JFrame jf = new JFrame();
	
	int frameTime;
	long intialTime;
	long lastFrameTime = System.currentTimeMillis();
	long lastFireworkTime = System.currentTimeMillis();
	
	int millisecondGoal = 1000 / 30;
	
	boolean limitFireworks = false;
	int maxFireworks = 50;
	
	int fireworkFrequency = 0;
	
	boolean benchmark = false;
	Firework benchmarkFirework = new Firework(400, 600, 200, 5, 25, 5 * 1000, 750, 1, Color.BLUE);
	
	boolean display = false;
	static boolean createFirework = false;
	
	static ArrayList<Firework> fireworks = new ArrayList<Firework>();
	
	int currentFirework = 0;
	Firework[] fireworksDisplay = new Firework[]{
			createRandomDisplayFirework(Color.RED, 73, 11),
			createRandomDisplayFirework(Color.BLUE, 98, 8),
			createRandomDisplayFirework(Color.GREEN, 113, 20),
			createRandomDisplayFirework(Color.ORANGE, 131, 5),
			createRandomDisplayFirework(Color.YELLOW, 140, 9),
			createRandomDisplayFirework(new Color(192,192,192), 156, 6),  //silver
			createRandomDisplayFirework(Color.pink, 163, 7),  		       //sparks
			createRandomDisplayFirework(new Color(255,218,185), 178, 10), //peach
			createRandomDisplayFirework(new Color(227,11,93), 181, 19),   //raspberry
			createRandomDisplayFirework(new Color(64,224,208), 199, 15)}; //Turquoise

	
	public static void main(String[] args) {
		//Dsun.java2d.opengl=true
		System.setProperty("sun.java2d.opengl", "true");
		setupWindow();
	}
	
	public static void setupWindow(){
			
		jf.setTitle("Fireworks");
		jf.setSize(frameSize[0], frameSize[1]);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		jf.setLocation(dim.width / 2 - frameSize[0] / 2, dim.height/2 - frameSize[1] / 2);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		Runner r = new Runner();
							
		r.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "newFirework");
		r.getActionMap().put("newFirework", newFirework);
									
		jf.add(r);
		
		jf.repaint();
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		frameSize[0] = (int) getBounds().getWidth();
		frameSize[1] = (int) getBounds().getHeight();
		
		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, frameSize[0], frameSize[1]);
		
		delay();
		
		frameTime = (int) (System.currentTimeMillis() - lastFrameTime);
		lastFrameTime = System.currentTimeMillis();
				
		checkIfFireworkNeeded();
	
		removeOldFireworks();
	
		
		for(int i = 0; i < fireworks.size(); i++){
				
				g.setColor(fireworks.get(i).getColor());
				
				fireworks.get(i).update(); //update locations for new frame
				
				if(!fireworks.get(i).getIsDetonated()) //don't draw rocket if detonated
					g.fillRect(fireworks.get(i).getCenter()[0] - 3, fireworks.get(i).getCenter()[1] - 3, 6, 6); //draw rocket
				else{ //draw particles if detonated
					for(FireworkParticle fp : fireworks.get(i).getParticles()){ //draw each particle
						fp.update(); //update particle
						g.fillRect( fp.getIntPos()[0] - 2, fp.getIntPos()[1] - 2, 4, 4); //draw particle
					for(int[] pos : fp.getHistory())//draw particle history
						g.fillRect(pos[0] - 1, pos[1] - 1, 2, 2);
					}
				}
		}
				
		g.setColor(Color.WHITE);
		g.drawString("fps:" + calcFps(), 10, 15);
		
		timer.start();
	}
	
	public int calcFps(){
		if(frameTime != 0)
			return 1000 / frameTime;
		return -1;
	}
	
	public void updateDynamicClock(){//old
		
		if(limitFireworks){
			if(timer.getDelay() == 0)
				maxFireworks--;
			else if(((double)fireworks.size()/maxFireworks) > 0.9 &&timer.getDelay() > 25)
				maxFireworks++;
		}			
			
		if(frameTime > millisecondGoal && timer.getDelay() > 0)//to slow
			timer.setDelay(timer.getDelay() - 1);
		else if(frameTime < millisecondGoal)//to fast
			timer.setDelay(timer.getDelay() + 1);
	
	}
	
	public void delay(){
		while(System.currentTimeMillis() - lastFrameTime < millisecondGoal){
			try{
				Thread.currentThread().sleep(1);
			}catch (InterruptedException e) {}
		}
	}
	
	public void checkIfFireworkNeeded(){
		
		if(createFirework){ //create firework because of input
			fireworks.add(addRandomFirework());
			lastFireworkTime = System.currentTimeMillis();
			createFirework = false;
	}
	
	if((System.currentTimeMillis() - lastFireworkTime) > fireworkFrequency && (!limitFireworks || fireworks.size() < maxFireworks)){ //create scheduled firework
			if(benchmark){
				//benchmarkFirework.updateCreationTime();
				fireworks.add(new Firework(100, 600, 200, 5, 25, 5 * 1000, 750, 1, Color.BLUE));
				fireworks.add(new Firework(200, 600, 200, 5, 25, 5 * 1000, 750, 1, Color.BLUE));
				fireworks.add(new Firework(300, 600, 200, 5, 25, 5 * 1000, 750, 1, Color.BLUE));
				fireworks.add(new Firework(400, 600, 200, 5, 25, 5 * 1000, 750, 1, Color.BLUE));
				fireworks.add(new Firework(500, 600, 200, 5, 25, 5 * 1000, 750, 1, Color.BLUE));
				fireworks.add(new Firework(600, 600, 200, 5, 25, 5 * 1000, 750, 1, Color.BLUE));
				fireworks.add(new Firework(700, 600, 200, 5, 25, 5 * 1000, 750, 1, Color.BLUE));
			}else if(display && currentFirework < 10){
				fireworks.add(fireworksDisplay[currentFirework]);
				currentFirework++;
			}else if(!display){
				fireworks.add(addRandomFirework());
			}
			lastFireworkTime = System.currentTimeMillis();
		}
		
	}
	
	public void removeOldFireworks(){
			
		for(Firework f : fireworks.toArray(new Firework[fireworks.size()]))
			if(f.checkCompleted())
				for(int i = 0; i < fireworks.size(); i++)
					if(fireworks.get(i).equals(f)){
						fireworks.remove(i);
						i = 999;
					}			
		
	}
	
	public Firework addRandomFirework(){
		
		Random rand = new Random();
		
		Firework f;
		
		
		if(rand.nextBoolean())
			f = new Firework(true, frameSize);
		else
			f = new WillowFirework(true, frameSize);
		
		
		/*int entryPoint = rand.nextInt(frameSize[0]);
	int detonationHight = rand.nextInt(frameSize[1]);
	
	int velocity = 1 + rand.nextInt(5);
	
	int numParticles = 20 + rand.nextInt(100);
	
	int burnTime = rand.nextInt(1 * 1000) + 1000;
	
	int radius = velocity * burnTime / fps;
	
	int historyLength = 10 + rand.nextInt(radius/2 - 20);
	//System.out.println(radius);
	Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
	
	Firework f;
	
	if(rand.nextBoolean()){
		f = new Firework(entryPoint, frameSize[1], detonationHight, velocity, numParticles, burnTime, radius, historyLength, color);
	}else{
		f = new WillowFirework(entryPoint, frameSize[1], detonationHight, velocity * 2, numParticles / 5, burnTime * 5, radius, historyLength, color);
	}*/
		return f;
	}
	
	public Firework createRandomDisplayFirework(Color color, int hight, int time){
		
		Random rand = new Random();
		
		//int entryPoint = rand.nextInt(frameSize[0]);
		int entryPoint = rand.nextInt(frameSize[0]);
		int detonationHight = frameSize[1] - 100 - hight;
	
		//int velocity = 1 + rand.nextInt(5);
		int velocity = 4;
	
		int numParticles = 20 + rand.nextInt(100);
	
		int burnTime = time * 1000;
	
		int radius = velocity * burnTime / millisecondGoal;
	
		int historyLength = 10 + rand.nextInt(radius - 20);
	
		//Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
		
		Firework f = new Firework(entryPoint, frameSize[1], detonationHight, velocity, numParticles, burnTime, radius, historyLength, color);
		return f;
	}
	
	public void actionPerformed(ActionEvent arg0){
		repaint();
	}
	
	static Action newFirework = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
	       createFirework = true;
	    }
	};
	
	}
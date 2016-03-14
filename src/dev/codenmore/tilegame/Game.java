package dev.codenmore.tilegame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.display.Display;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.gfx.ImageLoader;
import dev.codenmore.tilegame.gfx.SpriteSheet;

public class Game implements Runnable {
	
	private Display display;
	public int width, height;
	public String title;
	
	private boolean running = false;
	
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	

	
	
	public Game(String title, int width, int height){
		
		this.width = width;
		this.height = height;
		this.title = title;
		
	}
	
	private void init(){
		display = new Display(title, width, height);
		Assets.init();
	}
	
	int x = 0;
	
	private void tick(){
		x += 1;
	}
	
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g= bs.getDrawGraphics();
		// Clear screen
		g.clearRect(0, 0, width, height);
		//Draw here!
		
		g.drawImage(Assets.grass, x, 10, null);
		
		
		
		
		
		
		//End Drawing!
		bs.show();
		g.dispose();
	}
	
	public void run(){
		init();
		
		while(running){
			tick();
			render();
		}
		stop();
		
		
	}
	
	public synchronized void start(){
		if (running){
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if (!running){
			return;
		}
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}

}


public class ThreadFramework implements Runnable {

	private Thread thread ;
	private boolean running = false ;
	
	public ThreadFramework() {
		this.start() ;
	}
	
	private void tick() {
		System.out.print("One\n") ;
	}

	public void run() {
		while(running){
				tick() ;
			}
		stop();
	}
	
	public void delete() {
		this.stop() ;
	}
	
	public synchronized void start(){
		if(running) { return; }
		running = true ;
		thread = new Thread(this) ;
		thread.start() ;
	}
	
	private synchronized void stop(){
		if(!running) return ;
		running = false ;
		try{ thread.join() ;
		} catch(InterruptedException e){ e.printStackTrace() ; }
	}
}
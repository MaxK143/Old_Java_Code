import java.util.ArrayList;

public class ManagedThreadFramework implements Runnable {

	private Thread thread ;
	private boolean running = false ;
	
	private int ThreadId ;
	
	private ThreadManager Manager;
	
	private ArrayList<EncapObject> SData = new ArrayList<EncapObject> () ;
	
	private int Key1 = 22460000 ;
	
	private EncapObject EncapKey1 =  new EncapObject( Key1 , "Key1" ) ;
	
	private ArrayList<EncapObject> UpdateSData(){
		
		EncapKey1.UpdateObject( Key1 ) ;
		
		return SData ;
	}
	
	public ManagedThreadFramework( int Id  , ThreadManager manager ) {
		
		ThreadId = Id ;
		
		Manager = manager ;
		
		this.start() ;
	}
	
	private void tick() {
		System.out.print("One - "+Manager.getDataObject( "Key10" , 1 ).GetObject()+"\n" ) ;
		Key1 += 1 ;
	}

	public void run() {
		
		SData.add( EncapKey1 ) ;
		
		Manager.initData( ThreadId , SData ) ;
		
		while(running){
			tick() ;
			Manager.syncUpdate( ThreadId , UpdateSData() ) ;
		}
		stop();
	}
	
	public void delete() {
		this.stop() ;
	}
	
	private synchronized void start(){
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
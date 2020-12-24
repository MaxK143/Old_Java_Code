import java.util.ArrayList;

public class ManagedThreadTwo implements Runnable {

	private Thread thread ;
	private boolean running = false ;
	
	private int ThreadId ;
	
	private ThreadManager Manager;
	
	private ArrayList<EncapObject> SData = new ArrayList<EncapObject> () ;
	
	private int Key1 = 20000000 ;
	private float Key2 = 7 ;
	private String Key3 = "646" ;
	
	private EncapObject EncapKey1 =  new EncapObject( Key1 , "Key10" ) ;
	private EncapObject EncapKey2 =  new EncapObject( Key2 , "Key20" ) ;
	private EncapObject EncapKey3 =  new EncapObject( Key3 , "Key30" ) ;
	
	private ArrayList<EncapObject> UpdateSData(){
		
		EncapKey1.UpdateObject( Key1 ) ;
		EncapKey2.UpdateObject( Key2 ) ;
		EncapKey3.UpdateObject( Key3 ) ;
		
		return SData ;
	}
	
	public ManagedThreadTwo( int Id  , ThreadManager manager ) {
		
		ThreadId = Id ;
		
		Manager = manager ;
		
		this.start() ;
	}
	
	private void tick() {
		System.out.print("Two - "+Manager.getDataObject( "Key1" , 0 ).GetObject()+"\n" ) ;
		Key1 += 1 ;
	}

	public void run() {
		
		SData.add( EncapKey1 ) ;
		SData.add( EncapKey2 ) ;
		SData.add( EncapKey3 ) ;
		
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
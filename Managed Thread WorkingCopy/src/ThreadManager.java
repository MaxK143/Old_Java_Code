import java.util.ArrayList;

public class ThreadManager{
	
	private final static int ThreadsManaged = 2 ;
	
	private static int WaitingThreads = ThreadsManaged ;
	
	private static int ThreadsSynced = 0 ;
	
	private ArrayList<ArrayList< EncapObject >> Data = new ArrayList<ArrayList< EncapObject >>() ;
	
	public void initData( int ThreadId , ArrayList< EncapObject > Objs ) {
		Data.set( ThreadId , Objs ) ;
	}
	
	private final long WhileDelay = 50 ;
	@SuppressWarnings("unchecked")
	public void syncUpdate( int ThreadId , ArrayList< EncapObject > Objs ) {
		WaitingThreads-- ;
		while( WaitingThreads > 0 ) { try { Thread.sleep( WhileDelay ) ; } catch (InterruptedException e) { e.printStackTrace(); } }
		
		Data.set( ThreadId , (ArrayList<EncapObject>) Objs.clone() ) ;
		
		ThreadsSynced++ ;
		while( ThreadsSynced < ThreadsManaged ) { try { Thread.sleep( WhileDelay ) ; } catch (InterruptedException e) { e.printStackTrace(); } }
		WaitingThreads++ ;
		while( WaitingThreads < ThreadsManaged ) { try { Thread.sleep( WhileDelay ) ; } catch (InterruptedException e) { e.printStackTrace(); } }
		ThreadsSynced-- ;
		while( ThreadsSynced > 0 ) { try { Thread.sleep( WhileDelay ) ; } catch (InterruptedException e) { e.printStackTrace(); } }
	}

	public void syncUpdate() {
		WaitingThreads-- ;
		while( WaitingThreads > 0 ) { try { Thread.sleep( WhileDelay ) ; } catch (InterruptedException e) { e.printStackTrace(); } }
		ThreadsSynced++ ;
		while( ThreadsSynced < ThreadsManaged ) { try { Thread.sleep( WhileDelay ) ; } catch (InterruptedException e) { e.printStackTrace(); } }
		WaitingThreads++ ;
		while( WaitingThreads < ThreadsManaged ) { try { Thread.sleep( WhileDelay ) ; } catch (InterruptedException e) { e.printStackTrace(); } }
		ThreadsSynced-- ;
		while( ThreadsSynced > 0 ) { try { Thread.sleep( WhileDelay ) ; } catch (InterruptedException e) { e.printStackTrace(); } }
	}
	
	public EncapObject getDataObject( String id , int ThreadId ) {
		
		try {
			ArrayList< EncapObject > temp = Data.get( ThreadId ) ;
			for( int i = 0 ; i < temp.size() ; i++ ) {
				if( temp.get(i).GetId() == id ) {
					return temp.get(i) ;
				}
			}
		} catch( ArrayIndexOutOfBoundsException e ) { }

		this.syncUpdate() ;
		return this.getDataObject( id , ThreadId ) ;
		
	}
	
	public EncapObject getDataObject( String id ) {

		for( int in = 0 ; in < Data.size() ; in++ ) {
			ArrayList< EncapObject > temp = Data.get( in ) ;
			for( int i = 0 ; i < temp.size() ; i++ ) {
				if( temp.get(i).GetId() == id ) {
					return temp.get(i) ;
				}
			}
		}
		
		this.syncUpdate();
		
		return this.getDataObject( id );
		
	}
	
	public ArrayList<EncapObject> getDataArray( int ThreadId ){
		try {
			return Data.get( ThreadId ) ;
		} catch ( ArrayIndexOutOfBoundsException e ) {
			return null ;
		}
	}
	
	public ThreadManager() {
		
		if( Data.size() == 0 ) {
			int Ind = 0 ;
			while( Ind < ThreadsManaged ) {
				Data.add( new ArrayList< EncapObject >() ) ;
				Ind++ ;
			}
		}
		
		new ManagedThreadOne( 0 , this ) ;
		new ManagedThreadTwo( 1 , this ) ;
	}
	
	public static void main(String[] args) {
		new ThreadManager() ;
		/*try {
			new ThreadManager() ;
			Thread.sleep(200000) ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("\n\nSystem Exit") ;
			System.exit(0) ;
		}*/
	}
}
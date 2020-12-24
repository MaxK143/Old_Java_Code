
import java.io.File ;

public class StatGenerator implements Runnable {

	private Thread thread;
	private boolean running = false;
	
	private long EntriesMade = 0 ;
	
	private ArrayFile StatAverages ;
	private ArrayFile StatValues ;
	private ArrayFile StatMins ;
	private ArrayFile StatMaxs ;
	private ArrayFile StatSamples ;
	
	//createDiceSet vars
	final static private int DiceSize = 6 ;
	final static private int DiceCount = 4 ;
	
	//createStatSet vars
	static private int SetSize = 7 ;
	
	public StatGenerator( ArrayFile statAverages , ArrayFile statValues , ArrayFile statMins , ArrayFile statMaxs , ArrayFile statSamples  ) {
		StatAverages = (ArrayFile) ( statAverages.clone() ) ;
		StatValues = (ArrayFile) ( statValues.clone() ) ;
		StatMins = (ArrayFile) ( statMins.clone() ) ;
		StatMaxs = (ArrayFile) ( statMaxs.clone() ) ;
		StatSamples = (ArrayFile) ( statSamples.clone() ) ;
		
		this.start();
	}
	
	private void tick() {
		double[] temp = createStatSet() ;
		StatAverages.add( getAverage( temp ) + "" ) ;
		StatValues.add( getEffectiveValue( temp ) + "" ) ;
		StatMins.add( getMin( temp ) + "" ) ;
		StatMaxs.add( getMax( temp ) + "" ) ;
		StatSamples.add( temp[1] + "" ) ;
		EntriesMade++ ;
	}
	
	public Long getFileEntriesMade() {
			return EntriesMade ;
	}

	public void run() {
		
		while(running){
			tick();
		}
		stop();
	}
	
	public void delete() {
		
		StatAverages.delete() ;
		StatValues.delete() ;
		StatMins.delete() ;
		StatMaxs.delete() ;
		StatSamples.delete() ;
		
		running = false ;
		
	}

	private synchronized void start(){
		if(running) { return; }
		running = true;
		thread = new Thread(this);
		//thread.start calls the cores run() method
		thread.start();
	}
	
	private synchronized void stop(){
		if(!running) return;
		running = false;
		try{ thread.join();
		} catch(InterruptedException e){ e.printStackTrace(); }
	}
	
	public double[] createStatSet(){ // this creates a set of numbers through the totals of created dice sets.
		double[] Set = new double [SetSize] ;
		for (int i = 0 ; i <= Set.length - 1 ; i++){
			int tot = 0 ;
			double[] arr = createDiceSet() ;
			for (int in = 0 ; in <= arr.length - 1 ; in++){
				tot += arr[in] ; }
			Set[i] = tot ;}
		double[] FinSet = new double [ SetSize - 1 ] ;
		FinSet = removeLowest( Set , 1 ) ;
		return FinSet ;
	}
	
	private double[] createDiceSet() { // creates set of dice with final vars: DiceSize, DiceCount, RemoveLowest, RemveCount.
		double[] Dice = new double [DiceCount] ;
		for (int i = 0 ; i <= Dice.length - 1 ; i++){ Dice[i] = (int)( ( Math.random() * DiceSize ) + 1 ) ; }
		double[] FinDice = new double [ DiceCount - 1 ] ;
		FinDice = removeLowest( Dice , 1 ) ;
		return FinDice ;
	}
	
	private double[] removeLowest( double[] arr , int count ) {
		if( count > 0 ) {
			double min = Integer.MAX_VALUE ;
			int ind = 0 ;
			for (int i = 0 ; i <= arr.length - 1 ; i++){
				if( arr[i] < min ) {
					min = arr[i] ;
					ind = i ;
				}
			}
			double[] LowGone = new double [arr.length - 1] ;
			for (int i = 0 ; i <= arr.length - 1 ; i++){
				if( i < ind ){
					LowGone[i] = arr[i] ;
				}else if( i > ind ){
					LowGone[i-1] = arr[i] ;
				}
			}
			return removeLowest( LowGone , count-1 ) ;
		}else {
			return arr ;
		}
	}
	
	private static double getEffectiveValue( double[] temp ) {
		double[] arr = temp.clone() ;
		double EffectiveValue = 0 ;
		for (int i = 0 ; i <= arr.length - 1 ; i++) {
			EffectiveValue += getMax( arr ) * ( ( arr.length - i ) / ( arr.length ) ) ;
			arr = removeMax( arr ) ;
		}
		return EffectiveValue ;
	}
	
	private static double[] removeMax( double[] arr ) {
		double max = -1 ;
		int MaxIndex = 0 ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			if( arr[i] > max ) {
				max = arr[i] ;
				MaxIndex = i ;
			}
		}
		arr[MaxIndex] = 0 ;
		return arr ;
	}
	
	private static double getAverage( double[] arr ) {
		double total = 0 ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			total += arr[i] ;
		}
		return total / arr.length ;
	}
	
	private static double getMin( double[] arr ) {
		double min = Integer.MAX_VALUE ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			if( arr[i] < min ) {
				min = arr[i] ;
			}
		}
		return min ;
	}
	
	private static double getMax( double[] arr ) {
		double max = 0 ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			if( arr[i] > max ) {
				max = arr[i] ;
			}
		}
		return max ;
	}

}

import java.io.FileNotFoundException;

/*
 * This code is built for analyzing the statistics on DnD dice rolling, and what happens to the distribution curve as you drop the lowest value in a randomized set.
 * */
public class DnDStatsEncoder {
	
	//createDiceSet vars
	final static private int DiceSize = 6 ;
	final static private int DiceCount = 4 ;
	final static private int RemoveCount = 1 ;
	
	//createStatSet vars
	static private int SetSize = 7 ;
	static private int RemoveCountSet = 1 ;
	
	//Analyzing Methods vars
	final static private int SampleSize = 10000000 ;
	
	public static void main(String[] args) {
		analyzeStatSets() ;
	}
	
	private static void analyzeStatSets() {
		double[] SampleAverages = new double[ SampleSize ] ;
		double[] SampleValues = new double[ SampleSize ] ;
		int[] SampleMaxs = new int[ SampleSize ] ;
		int[] SampleMins = new int[ SampleSize ] ;
		int[] Sample = new int [ SampleSize ] ;
		
		for (int i = 0 ; i <= SampleSize - 1 ; i++){
			int[] temp = createStatSet() ;
			SampleAverages[i] = getAverage( temp ) ;
			SampleValues[i] = getEffectiveValue( temp ) ;
			SampleMaxs[i] = getMax( temp ) ;
			SampleMins[i] = getMin( temp ) ;
			Sample[i] = temp[ 1 ] ;
		}
		
		try {
			EncodeToText( to2DArray( getDistributionCurve( Sample ) ) ) ;
			EncodeToText( to2DArray( getDistributionCurve( SampleAverages ) ) ) ;
			EncodeToText( to2DArray( getDistributionCurve( SampleValues ) ) ) ;
			EncodeToText( to2DArray( getDistributionCurve( SampleMins ) ) ) ;
			EncodeToText( to2DArray( getDistributionCurve( SampleMaxs ) ) ) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println( "Analyze Dice Set with Sample Size: " + SampleSize ) ;
		System.out.println( "Avg Average: " + getAverage( SampleAverages ) ) ;
		System.out.println( "Avg Value: " + getAverage( SampleValues ) ) ;
		System.out.println( "Avg Maxs: " + getAverage( SampleMaxs ) );
		System.out.println( "Avg Mins: " + getAverage( SampleMins ) );
	}
	
	private static void EncodeToText( double[][] arr ) throws FileNotFoundException {

		int count = 0 ;
		String FileName = "C:\\Users\\Robert Kellinghaus\\Desktop\\My Java\\EncodedTexts\\EncodedFile" ;
		java.io.File file = new java.io.File( FileName ) ;
		
		do {
			FileName = "C:\\Users\\Robert Kellinghaus\\Desktop\\My Java\\EncodedTexts\\EncodedFile" + count + ".txt" ;
			file = new java.io.File( FileName );
			count++ ;
		}while( file.exists() ) ;
		
	    java.io.PrintWriter output = new java.io.PrintWriter(file);
	    
	    output.print( "\n\n\n" ) ;
	    
	    for(int index = 0; index < arr[0].length; index++) {
	    	
	    	String temp = "" + arr[0][index] + " " + arr[1][index];
	    	
	    	output.println(temp);
	    }
	    
	    output.close() ;
	}
	
	private static double[] getDistributionCurve( int[] arr ) {
		double[] DistCurve = new double[ getMax( arr ) + 1 ] ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			DistCurve[ arr[i] ]++ ;
		}
			for (int i = 0 ; i <= DistCurve.length - 1 ; i++){
				DistCurve[i] = ( DistCurve[i] / arr.length ) * 100 ;
			}
		
		return DistCurve ;
	}
	
	private static double[] getDistributionCurve( double[] arr ) {
		double[] DistCurve = new double[ (int)( getMax( arr ) + 2 ) ] ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			DistCurve[ (int)( arr[i] + 0.5 ) ]++ ;
		}
			for (int i = 0 ; i <= DistCurve.length - 1 ; i++){
				DistCurve[i] = ( DistCurve[i] / arr.length ) * 100 ;
			}
		
		return DistCurve ;
	}
	
	private static double[][] to2DArray( double[] arr ) {
		double[][] Array = new double[ 2 ][ arr.length ] ;
		for ( int i = 0 ; i <= arr.length - 1 ; i++ ){
			Array[ 0 ][ i ] = i ;
			Array[ 1 ][ i ] = arr[i] ;
		}
		return Array ;
	}
	
	private static void analyzeDiceSets() {
		double[] SampleAverages = new double[ SampleSize ] ;
		int[] SampleTotals = new int[ SampleSize ] ;
		int[] SampleMaxs = new int[ SampleSize ] ;
		int[] SampleMins = new int[ SampleSize ] ;
		int[][] Sample = new int [ SampleSize ] [ DiceCount - RemoveCount ] ;
		
		for (int i = 0 ; i <= SampleSize - 1 ; i++){
			int[] temp = createDiceSet() ;
			SampleAverages[i] = getAverage( temp ) ;
			SampleTotals[i] = getTotal( temp ) ;
			SampleMaxs[i] = getMax( temp ) ;
			SampleMins[i] = getMin( temp ) ;
			Sample[i] = temp ;
		}

		System.out.println( "Analyze Dice Set with Sample Size: " + SampleSize ) ;
		System.out.println( "Avg Average: " + getAverage( SampleAverages ) ) ;
		System.out.println( "Avg Totals: " + getAverage( SampleTotals ) );
		System.out.println( "Avg Maxs: " + getAverage( SampleMaxs ) );
		System.out.println( "Avg Mins: " + getAverage( SampleMins ) );
		System.out.println( "Random Sample: " + toString( Sample[ (int)( Math.random()*SampleSize ) ] ) );
	}
	
	private static int[] createStatSet(){ // this creates a set of numbers through the totals of created dice sets.
		int[] Set = new int [SetSize] ;

		for (int i = 0 ; i <= Set.length - 1 ; i++){
			Set[i] = getTotal( createDiceSet() ) ;
		}
		
		int[] FinSet = new int [ SetSize - RemoveCountSet ] ;
		
		FinSet = removeLowest( Set , RemoveCountSet ) ;
		
		return FinSet ;
		
	}
	
	private static int[] createDiceSet() { // creates set of dice with final vars: DiceSize, DiceCount, RemoveLowest, RemveCount.
		int[] Dice = new int [DiceCount] ;
		for (int i = 0 ; i <= Dice.length - 1 ; i++){ Dice[i] = (int)( ( Math.random() * DiceSize ) + 1 ) ; }

		int[] FinDice = new int [DiceCount-RemoveCount] ;
		
		FinDice = removeLowest( Dice , RemoveCount ) ;
		
		return FinDice ;
	}
	
	private static int[] removeLowest( int[] arr , int count ) {
		if( count > 0 ) {
			int min = Integer.MAX_VALUE ;
			int ind = 0 ;
			for (int i = 0 ; i <= arr.length - 1 ; i++){
				if( arr[i] < min ) {
					min = arr[i] ;
					ind = i ;
				}
			}
			int[] LowGone = new int [arr.length - 1] ;
			
			for (int i = 0 ; i <= arr.length - 1 ; i++){
				if( i < ind ) {
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
	
	private static double getEffectiveValue( int[] Array ) {
		
		int[] arr = Array.clone() ;
		
		int[] Mods = new int[ arr.length ] ;
		
		double EffectiveValue ;
		
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			if( arr[i] % 2 == 1 ) {
				arr[i] -= 1 ;
			}
			Mods[i] = (int) ( ( arr[i] - 10 ) / 2 ) ;
		}
		
		EffectiveValue = 0 ;
		
		for (int i = 0 ; i <= Mods.length - 1 ; i++) {
			EffectiveValue += ( getMax( Mods ) ) * ( ( ( Mods.length - 1 - i ) / ( Mods.length - 1 ) ) ) ;
			Mods = removeMax( Mods ) ;
		}
		
		return EffectiveValue ;
		
	}
	
	private static int[] removeMax( int[] arr ) {
		int max = -1 ;
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
	
	private static int getTotal( int[] arr) {
		int tot = 0 ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			tot += arr[i] ;
		}
		return tot ;
	}
	
	private static double getAverage( int[] arr ) {
		double total = 0 ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			total += arr[i] ;
		}
		return total / arr.length ;
	}
	
	private static double getAverage( double[] arr ) {
		double total = 0 ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			total += arr[i] ;
		}
		return total / arr.length ;
	}
	
	private static int getMin( int[] arr ) {
		int min = Integer.MAX_VALUE ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			if( arr[i] < min ) {
				min = arr[i] ;
			}
		}
		return min ;
	}
	
	private static int getMax( int[] arr ) {
		int max = 0 ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			if( arr[i] > max ) {
				max = arr[i] ;
			}
		}
		return max ;
	}
	
	private static int getMax( double[] arr ) {
		double max = 0 ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			if( arr[i] > max ) {
				max = arr[i] ;
			}
		}
		return (int)max ;
	}
	
	private static String toString( int[] arr ) {
		String ret = "" ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){ ret += arr[i] + " " ; }
		return ret ;
	}
}
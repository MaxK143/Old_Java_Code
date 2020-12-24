import java.util.Scanner;

/*
 * This code is built for analyzing the statistics on DnD dice rolling, and what happens to the distribution curve as you drop the lowest value in a randomized set.
 * */
public class DnDStats {
	
	//Approximate DnD Stat Recordings
	final static private double PerfectAverage = 12.91 ;
	final static private double PerfectValue = 31.71 ;
	final static private double PerfectMin = 10.05 ;
	final static private double PerfectMax = 15.85 ;
	
	//createSampleSet vars
	final static private double AverageDeviation = 0.5 ;
	final static private double ValueDeviation = 1.0 ;
	final static private double MinDeviation = 1.5 ;
	final static private double MaxDeviation = 1.5 ;
	final static private double SampleSizeLimit = -1 ;
	
	//createDiceSet vars
	final static private int DiceSize = 6 ;
	final static private int DiceCount = 4 ;
	final static private int RemoveCount = 1 ;
	
	//createStatSet vars
	static private int SetSize = 7 ;
	static private int RemoveCountSet = 1 ;
	
	//Analyzing Methods vars
	final static private int SampleSize = 1000000 ;
	
	//Analyze Derivation vars
	final static private int SampleSize2 = 1000 ;
	final static private int RemoveLimit = 1000 ;
	
	//Desired Conditions for Sample?
	final static private boolean DesireCondition = false ;
	
	public static void main(String[] args) {
		printSampleStats() ;
		System.out.print( "\nProgram Ended" );
	}
	
	private static void testSampleStats() {
	   	Scanner stdInp = new Scanner(System.in);
	   	System.out.println( "Enter the Stat Set" );
	   	int[] arr = createStatSet() ;

		for (int i = 0 ; i <= arr.length - 1 ; i++){
			arr[i] = stdInp.nextInt() ;
		}
		
		System.out.println( "Set Average: " + getAverage( arr ) + "\tPerfect Average: " + PerfectAverage ) ;
		System.out.println( "Set Value: " + getEffectiveValue( arr ) + "\t\tPerfect Value: " + PerfectValue ) ;
		System.out.println( "Set Min: " + getMin( arr ) + "\t\tPerfect Min: " + PerfectMin ) ;
		System.out.println( "Set Max: " + getMax( arr ) + "\t\tPerfect Max: " + PerfectMax ) ;
		
		stdInp.close();
	}
	
	private static void printSampleStats() {
		
		int[] Sample = createStatSet() ;
		
		int cnt = 0 ;
		
		if( DesireCondition ) {
			
			boolean cond1 = false ;
			boolean cond2 = false ;
			boolean cond3 = false ;
			boolean cond4 = false ;
			
			boolean flag = true ;
			while( flag ) {
				
				Sample = createStatSet() ;
				
				cond1 = ( getAverage( Sample ) <= PerfectAverage+AverageDeviation && getAverage( Sample ) >= PerfectAverage-AverageDeviation ) ;
				
				cond2 = ( getEffectiveValue( Sample ) <= PerfectValue+ValueDeviation && getEffectiveValue( Sample ) >= PerfectValue-ValueDeviation ) ;
				
				cond3 = ( getMax( Sample ) <= PerfectMax+MaxDeviation && getMax( Sample ) >= PerfectMax-MaxDeviation ) ;
				
				cond4 = ( getMin( Sample ) <= PerfectMin+MinDeviation && getMin( Sample ) >= PerfectMin-MinDeviation ) ;
				
				if( cnt >= 0 ) { cnt++ ; }
				
				if( ( cond1 && cond2 && cond3 && cond4 ) || ( cnt == SampleSizeLimit ) ) { flag = false ; }
			}
		}
		
		System.out.println( "Average: " + getAverage( Sample ) ) ;
		System.out.println( "Effective Value: " + getEffectiveValue( Sample ) ) ;
		System.out.print( "Min: " + getMin( Sample ) ) ;
		System.out.println( "\tMax: " + getMax( Sample ) ) ;
		System.out.println( "Sample Created: " + cnt);
		System.out.println( "Set: " + toString( Sample ) ) ;
	}
	
	private static void analyzeDeviation() {
		double[] SampleAverages = new double[ SampleSize ] ;
		int[] SampleMaxs = new int[ SampleSize ] ;
		int[] SampleMins = new int[ SampleSize ] ;
		double[] SampleDeviation = new double[ SampleSize2 ] ;

		double[] Deriv0 = new double[ RemoveLimit ] ;
		
		for (int index = 0 ; index <= RemoveLimit - 1 ; index++){
			for (int ind = 0 ; ind <= SampleSize2 - 1 ; ind++){
				for (int i = 0 ; i <= SampleSize - 1 ; i++){
					int[] temp = createStatSet() ;
					SampleAverages[i] = getAverage( temp ) ;
					SampleMaxs[i] = getMax( temp ) ;
					SampleMins[i] = getMin( temp ) ;
				}
			SampleDeviation[ind] = getMinMaxAvgDev( getAverage( SampleMins ) , getAverage( SampleMaxs ) , getAverage( SampleAverages ) ) ;
			}
			Deriv0[index] = getAverage( SampleDeviation ) ;
			SetSize++ ;
			RemoveCountSet++ ;
		}
		System.out.println( "Deriv0: " + getAverage( Deriv0 ) ) ;
		System.out.println( "Deriv1: " + getAverage( getDerivedArray( Deriv0 ) ) ) ;
		System.out.println( "Deriv2: " + getAverage( getDerivedArray( getDerivedArray( Deriv0 ) ) ) ) ;
		System.out.println( "Deriv3: " + getAverage( getDerivedArray( getDerivedArray( getDerivedArray( Deriv0 ) ) ) ) ) ;
		
	}
	
	private static void analyzeStatSets() {
		double[] SampleAverages = new double[ SampleSize ] ;
		double[] SampleValues = new double[ SampleSize ] ;
		int[] SampleTotals = new int[ SampleSize ] ;
		int[] SampleMaxs = new int[ SampleSize ] ;
		int[] SampleMins = new int[ SampleSize ] ;
		int[][] Sample = new int [ SampleSize ] [ SetSize - RemoveCountSet ] ;
		
		for (int i = 0 ; i <= SampleSize - 1 ; i++){
			int[] temp = createStatSet() ;
			SampleAverages[i] = getAverage( temp ) ;
			SampleValues[i] = getEffectiveValue( temp ) ;
			SampleTotals[i] = getTotal( temp ) ;
			SampleMaxs[i] = getMax( temp ) ;
			SampleMins[i] = getMin( temp ) ;
			Sample[i] = temp ;
		}

		System.out.println( "Analyze Stats Set with Sample Size: " + SampleSize ) ;
		System.out.println( "Avg Average: " + getAverage( SampleAverages ) ) ;
		System.out.println( "Avg Effective Value: " + getAverage( SampleValues ) );
		System.out.println( "Avg Totals: " + getAverage( SampleTotals ) );
		System.out.println( "Avg Maxs: " + getAverage( SampleMaxs ) );
		System.out.println( "Avg Mins: " + getAverage( SampleMins ) );
		System.out.println( "Random Sample: " + toString( Sample[ (int)( Math.random()*SampleSize ) ] ) );
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
	
	private static double[] getDerivedArray( double[] arr ) {
		
		double[] Deriv = new double[ arr.length - 1 ] ;
		
		for (int i = 0 ; i <= arr.length - 2 ; i++){
			Deriv[i] = ( arr[i+1] - arr[i] ) ;
		}
		return Deriv ;
	}
	private static double getMinMaxAvgDev( double min , double max , double average ) {
		return ( ( min + max ) / 2 ) - average ;
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
		
		double EffectiveValue = 0 ;
		
		for (int i = 0 ; i <= arr.length - 1 ; i++){
			Mods[i] = arr[i] ;
		}
		
		for (int i = 0 ; i <= Mods.length - 1 ; i++) {
			EffectiveValue += ( getMax( Mods ) ) * ( ( ( Mods.length - 1 - i ) / ( Mods.length - 1 ) ) * 2.0 ) ;
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
	
	private static String toString( int[] arr ) {
		String ret = "" ;
		for (int i = 0 ; i <= arr.length - 1 ; i++){ ret += arr[i] + " " ; }
		return ret ;
	}
}
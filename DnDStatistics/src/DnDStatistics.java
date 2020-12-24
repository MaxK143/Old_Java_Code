import java.io.FileNotFoundException;
import java.math.BigInteger;

import sun.applet.Main;

public class DnDStatistics {
	
	private static int RunningThreadCount = 0 ;
	
	private final static BigInteger SampleSize = BigInteger.valueOf( 2 ).pow( 40 ) ;

	public static void main(String[] args) {
		
		/*DiceRolls DiceRoller = new DiceRolls() ;
		
		BigInteger Count = BigInteger.ZERO ;
		BigInteger Total = BigInteger.ZERO ;
		
		long LoggedTime = System.currentTimeMillis() ;
		
		while( Count.compareTo( BigInteger.valueOf( Integer.MAX_VALUE ).multiply( BigInteger.valueOf( 10 ) ) ) < 0 ) {
			Total = Total.add( BigInteger.valueOf( (long)( 1000 * DiceRoller.CreateScore() ) ) ) ;
			Count = Count.add( BigInteger.ONE ) ;
		}
		
		double AvgScore = ( Total.divide(Count) ).doubleValue() / 1000 ;
		
		System.out.println( "Average Score: " + AvgScore + " - Over Sample of " + Count.toString() + " in " + (double)( System.currentTimeMillis() - LoggedTime ) / 1000 + " seconds." ) ;
		*/
		
		try {
			StatThreadSamples SampleThread = new StatThreadSamples( "Samples" , SampleSize  ) ;
			StatThreadAverages AverageThread = new StatThreadAverages( "Averages" , SampleSize ) ;
			StatThreadScores ScoreThread = new StatThreadScores( "Scores" , SampleSize  ) ;
			StatThreadMaxs MaxThread = new StatThreadMaxs( "Minimums" , SampleSize  ) ;
			StatThreadMins MinThread = new StatThreadMins( "Maximums" , SampleSize  ) ;
			
			RunningThreadCount = 5 ;
			
			System.out.println( "Threads Started: " + RunningThreadCount ) ;
			
			while( true ) { }
			
		} catch ( Throwable e ) {
			e.printStackTrace() ;
			System.exit( 1 ) ; 
		} finally {
			System.out.println( " System.exit(0) Finally Executed. " ) ;
			System.exit( 0 ) ;
		}
	}
	
	public static void ThreadFinished() {
		RunningThreadCount-- ;
		System.out.println( "Threads Running: " + RunningThreadCount ) ;
		if( RunningThreadCount == 0 ) {
			System.exit( 0 ) ;
		}
	}
}

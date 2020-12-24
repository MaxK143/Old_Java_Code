import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class StatThreadSamples implements Runnable {

	
	// Changeable Variables
	private final String CreatedStat = "Samples" ;
	
	private final double OccuranceIteration = 1 ;
	
	private final double MaxValue = 18 ;
	
	private double TrailingNum = 3 - 0.0001 ;
	
	private double LeadingNum = TrailingNum + 0.0001 + OccuranceIteration ;
	
	
	
	private Thread thread ;
	
	private boolean running = false ;
	
	private BigArrayFile ArrayFile ;
	
	private BigArrayFile FinalArrayFile ;
	
	private DiceRolls DiceRoller = new DiceRolls() ; ;
	
	private boolean StatCompletionFlag = true ;
	
	private boolean StatTranslationFlag = true ;
	
	private BigInteger IndexLimit ;
	
	private BigInteger Index = BigInteger.ZERO ;
	
	private ArrayList<BigInteger> SampleOccurances ;
	
	private ArrayList<Double> OccuranceRanges ;
	
	private BigInteger Occurances ;
	
	public StatThreadSamples( String FileName , BigInteger SampleSize ) throws FileNotFoundException {
		
		ArrayFile = new BigArrayFile( FileName ) ;

		FinalArrayFile = new BigArrayFile( FileName + "Occurances" ) ;
		
		SampleOccurances = new ArrayList<BigInteger>() ;
		
		OccuranceRanges = new ArrayList<Double>() ;
		
		IndexLimit = SampleSize ;
		
		this.start() ;
	}
	
	private void StatCreationTick() throws IOException {
		if( Index.compareTo( IndexLimit ) >= 0 ) {
			Index = BigInteger.ZERO ;
			StatCompletionFlag = false ;
		}

		Index = Index.add( BigInteger.ONE ) ;
		//System.out.println( Index.toString() );
		
		ArrayFile.Access( BigInteger.valueOf(-1) , DiceRoller.CreateSample()+"" ) ;
	
	}
	
	private void StatTranslationTick() throws NumberFormatException, IOException {
		Occurances = BigInteger.ZERO ;
		BigInteger Index = BigInteger.ZERO ;
		double temp = 0 ;
		while( LeadingNum <= MaxValue ) {
			while( Index.compareTo( IndexLimit ) < 0  ) {
				temp = Double.parseDouble( ArrayFile.Access( Index , null ) ) ;
				if( temp > TrailingNum && temp <= LeadingNum ) {
					Occurances = Occurances.add( BigInteger.ONE ) ;
				}
				Index = Index.add( BigInteger.ONE ) ;
			}
			SampleOccurances.add( Occurances ) ;
			OccuranceRanges.add( TrailingNum + ( ( LeadingNum - TrailingNum ) / 2 ) ) ;
			Index = BigInteger.ZERO ;
			Occurances = BigInteger.ZERO ;
			TrailingNum = LeadingNum ;
			LeadingNum += OccuranceIteration ;
		}
		
		for( int ind = 0 ; ind < SampleOccurances.size() ; ind++ ) {
			FinalArrayFile.Access( BigInteger.valueOf( -1 ) , SampleOccurances.get(ind).toString() + " " + OccuranceRanges.get(ind).toString() ) ;
		}
		
		System.out.print( CreatedStat + " Occuranaces: " ) ;
		for( int ind = 0 ; ind < SampleOccurances.size() ; ind++ ) {
			System.out.print( SampleOccurances.get(ind) + " " + OccuranceRanges.get(ind) + ", " ) ;
		}
		System.out.println() ;
		
		StatTranslationFlag = false ;
	}

	public void run() {
		while( running && StatCompletionFlag ){
			try {
				StatCreationTick() ;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while( running && StatTranslationFlag ) {
			try {
				StatTranslationTick() ;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		DnDStatistics.ThreadFinished() ;
		this.stop() ;
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
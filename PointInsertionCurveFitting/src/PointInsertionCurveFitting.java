import java.io.File;
import java.math.BigInteger;

public class PointInsertionCurveFitting {
	
	
	private final static String FileName = "AveragesOccurances.txt" ;

	public static void main(String[] args) {
		try {
			System.out.print( "PointInsertionCurveFitting Main Started\n" ) ;
			
			File TextFile = new File( FileName ) ;
			
			BigArrayFile Source = new BigArrayFile( TextFile ) ;
			
			new PointInsertion().PointInsertion( Source , 3 ) ;
			
			
		} catch ( Throwable e ) {
			System.out.print( "PointInsertionCurveFitting Main Ended With Exception:\n" ) ;
			e.printStackTrace() ;
			System.exit( 1 ) ;
		} finally {
			System.out.print( "PointInsertionCurveFitting Main Ended in finally {} \n" ) ;
			System.exit( 0 ) ;
		}
	}

}

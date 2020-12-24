import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

public class BigArrayFileTest {

	public static void main(String[] args) throws IOException {
		BigArrayFile test = new BigArrayFile( "test.txt" ) ;
		
		System.out.println( test.getFileName() ) ;
		System.out.println( test.getLength() + "\n" ) ;
		
		for( int i = 0 ; i < 200 ; i++ ) {
			System.out.println( test.Access( BigInteger.valueOf( -1 ) , ""+(i-500) ) ) ;
		}
		System.out.println() ;
		System.out.println( test.getLength() + "\n" ) ;
		
		for( int i = 0 ; i < 200 ; i+=10 ) {
			System.out.println( test.Access( BigInteger.valueOf( i ) , null ) ) ;
		}
	}
}

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

public class BigArrayFileTest {

	public static void main(String[] args) throws IOException {
		BigArrayFile test = new BigArrayFile( "Test" ) ;
		
		for( int i = 0 ; i < 200 ; i++ ) {
			test.Access( null , "" + ( i ), "add" ) ;
		}
		
		test.Access( BigInteger.valueOf( 11 ) , "debug" , "insert"  ) ;
		
		test.Access( BigInteger.valueOf( 11 ) , "debug2" , "insert"  ) ;

		test.Access( BigInteger.valueOf( 12 ) , null , "remove"  ) ;

		test.Access( BigInteger.valueOf( 0 ) , "debug3" , "set"  ) ;

		test.Access( null , null , "close"  ) ;
		
	}
}

import java.math.BigInteger;

public class ArrayFileTest {

	public static void main(String[] args) {
		ArrayFile test1 = new ArrayFile("C:\\Users\\Robert Kellinghaus\\Desktop\\My Java\\EncodedTexts\\Test.txt") ;
	
		for ( double i = 0 ; i <= 100 ; i++ ){
			test1.add( ( i / 2 ) + "" ) ;
			System.out.print( ( i/2 ) + " " ) ;
		}
		System.out.print( "\nLine 12 Reached\n" ) ;
		
		
		for ( double i = 5 ; i <= 100 ; i++ ){
			System.out.print( "Value at index: " + (int)i + " Intended val: " + i/2 + " Actual val: " ) ;
			System.out.println(  test1.get( BigInteger.valueOf( (long) i ) )  ) ;
		}
		
	}
}

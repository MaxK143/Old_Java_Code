import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;
import java.io.File;

public class ArrayFile {

	private String FileName ;

	private BigInteger Length ;

	private File file ;
	private PrintWriter Output ;
	private Scanner Input ;

	public ArrayFile( String fileName )  {
		
		FileName = fileName ;
		file = new File( FileName )  ;
		try {
			file.createNewFile() ;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		file.setWritable(true) ;
		file.setReadable(true) ;
		Length = BigInteger.ZERO ;
		
		try {
			System.out.println( file.getCanonicalPath() );
			Output = new PrintWriter( file ) ;
			Input = new Scanner( file ) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ArrayFile( String name , BigInteger length , File fil , PrintWriter out , Scanner inp ) {
		FileName = name ;
		Length = length ;
		file = fil ;
		Output = out ;
		Input = inp ;
	}

	public ArrayFile clone() {
		return new ArrayFile( FileName , Length , file , Output , Input ) ;
	}

	public void add( String in ) {
		Output.println( in ) ;
		Length = Length.add( BigInteger.ONE ) ;
	}

	public String get( BigInteger index ) {
		BigInteger i = BigInteger.ZERO ;
		if( index.compareTo( BigInteger.ZERO ) >= 0) {
			while( true ) {
				try {
					if( i.compareTo( index ) == 0 ) {
						return Input.nextLine();
					}
					Input.nextLine() ;
					i.add( BigInteger.ONE ) ;
				}catch( Exception ex ) {
					return ex.toString() ;
				}
			}
		}
		return "0" ;
	}

	public void delete() {
		Input.close() ;
		Output.close() ;
		file.delete() ;
	}
}
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class BigArrayFile {

	private String FileName ;

	private BigInteger Length = BigInteger.ZERO ;

	private File file ;
	
	private PrintWriter OutputWriter ;
	
	private FileOutputStream OutputStream ;
	
	private Scanner InputScanner ;
	
	public BigArrayFile( File txt ) throws FileNotFoundException {
		file = txt ;
		FileName = file.getAbsolutePath() ;// possible fuck up
		FileOutputStream OutputStream = new FileOutputStream(file);
		OutputWriter = new PrintWriter( OutputStream , true );
		InputScanner = new Scanner(file) ;
	}
	
	public BigArrayFile( String Name ) throws FileNotFoundException {
		new File( Name + ".txt" ).delete() ;
		file = new File( Name + ".txt" ) ;
		FileName = Name + ".txt" ;
		FileOutputStream OutputStream = new FileOutputStream(file) ;
		OutputWriter = new PrintWriter( OutputStream , true ) ;
		InputScanner = new Scanner(file) ;
	}
	
	public BigInteger getLength() {
		return Length ;
	}
	
	public String getFileName() {
		return FileName;
	}
	
	public String Access( BigInteger Index , String Value ) throws IOException {
		
		if( Index.compareTo( BigInteger.ZERO ) >= 0 ) {
			return this.getPoint( Index ) ;
		} else {
			return this.setPoint( Index , Value ) ;
		}
	}
	
	private String getPoint( BigInteger Index ) throws FileNotFoundException {
		
		
		BigInteger ind = BigInteger.ZERO ;
		String temp = "" ;
		while( ind.compareTo( Index ) <= 0 ) {
			ind = ind.add( BigInteger.ONE ) ;
			temp = InputScanner.nextLine() ;
		}
		
		InputScanner.close() ;
		InputScanner = new Scanner(file) ;
		
		return temp ;
	}
	
	private String setPoint( BigInteger Index , String Value ) throws IOException {
		
		Length = Length.add( BigInteger.ONE ) ;
		OutputWriter.print( Value + "\n" ) ;
		OutputWriter.flush() ;
		return Value ;
		
	}
}
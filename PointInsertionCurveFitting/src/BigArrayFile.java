import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class BigArrayFile {
	
	private boolean Closed = false ;

	private boolean Deleted = false ;
	
	private String FileName ;

	private BigInteger Length = BigInteger.ZERO ;

	private File file ;
	
	private PrintWriter OutputWriter ;
	
	private FileOutputStream OutputStream ;
	
	private BufferedReader InputScanner ;
	
	private InputStreamReader InputStream ;
	
	private FileInputStream FileStream ;
	
	public BigArrayFile( File txt ) throws FileNotFoundException {
		file = txt ;
		FileName = file.getAbsolutePath() ;// possible fuck up
		OutputStream = new FileOutputStream(file);
		OutputWriter = new PrintWriter( OutputStream , true );
		FileStream = new FileInputStream( file ) ;
		InputStream = new InputStreamReader( FileStream ) ;
		InputScanner = new BufferedReader( InputStream ) ;
	}
	
	public BigArrayFile( String Name ) throws FileNotFoundException {
		new File( Name + ".txt" ).delete() ;
		file = new File( Name + ".txt" ) ;
		FileName = Name + ".txt" ;
		OutputStream = new FileOutputStream(file) ;
		OutputWriter = new PrintWriter( OutputStream , true ) ;
		FileStream = new FileInputStream( file ) ;
		InputStream = new InputStreamReader( FileStream ) ;
		InputScanner = new BufferedReader( InputStream ) ;
	}
	
	public String Access( BigInteger Index , String Value , String Purpose ) throws IOException {
		if( Purpose == "get" ) {
			return this.getPoint( Index ) ;
		} else if ( Purpose == "add" ) {
			return this.addPoint( Value ) ;
		} else if ( Purpose == "set" ) {
			return this.setPoint( Index, Value ) ;
		} else if ( Purpose == "insert" ) {
			return this.insertPoint( Index, Value ) ;
		} else if ( Purpose == "remove" ) {
			return this.removePoint( Index ) ;
		} else if ( Purpose == "length" ) {
			return Length + "" ;
		} else if ( Purpose == "filename" ) {
			return FileName ;
		}else if( Closed || Deleted ) {
			return null ;
		} else if ( Purpose == "close" ) {
			
			Closed = true ;
			
			OutputStream.close() ;
			OutputWriter.close() ;
			FileStream.close() ;
			InputStream.close() ;
			InputScanner.close() ;
			
			return null ;
		} else if ( Purpose == "delete" ) {
			
			Deleted = true ;
			
			OutputStream.close() ;
			OutputWriter.close() ;
			FileStream.close() ;
			InputStream.close() ;
			InputScanner.close() ;
			
			file.delete() ;
			
			return null ;
		} else {
			throw new IllegalArgumentException() ;
		}
	}
	
	private String getPoint( BigInteger Index ) throws IOException {
		
		
		BigInteger ind = BigInteger.ZERO ;
		String temp = "" ;
		while( ind.compareTo( Index ) <= 0 ) {
			ind = ind.add( BigInteger.ONE ) ;
			temp = InputScanner.readLine() ;
		}

		InputScanner.close() ;
		FileStream = new FileInputStream( file ) ;
		InputStream = new InputStreamReader( FileStream ) ;
		InputScanner = new BufferedReader( InputStream ) ;
		
		return temp ;
	}
	
	private String addPoint( String Value ) throws IOException {
		
		Length = Length.add( BigInteger.ONE ) ;
		OutputWriter.print( Value + "\n" ) ;
		OutputWriter.flush() ;
		return Value ;
		
	}
	
	private String setPoint( BigInteger Index , String Value ) throws IOException {
		
		if ( Index.compareTo( BigInteger.ZERO ) < 0 || Index.compareTo( Length ) > 0 ) {
			throw new IndexOutOfBoundsException() ;
		} else {
			
			@SuppressWarnings("static-access")
			File tempFile = file.createTempFile( "Temp"+FileName , ".txt" ) ;
			
			FileOutputStream TempOutputStream = new FileOutputStream( tempFile );
			PrintWriter TempOutputWriter = new PrintWriter( TempOutputStream , true );

			FileInputStream TempFileStream = new FileInputStream( tempFile ) ;
			InputStreamReader TempInputStream = new InputStreamReader( TempFileStream ) ;
			BufferedReader TempInputScanner = new BufferedReader( TempInputStream ) ;
			
			for ( BigInteger ind = BigInteger.ZERO ; ind.compareTo( Length ) < 0 ; ind = ind.add( BigInteger.ONE ) ) {
				if( ind.compareTo( Index ) != 0 ) {
					TempOutputWriter.print( this.getPoint(ind) + "\n" ) ;
				} else {
					TempOutputWriter.print( Value + "\n" ) ;
				}
			}
			
			TempOutputWriter.flush() ;
			
			file = new File( FileName ) ;

			TempOutputStream = new FileOutputStream( file );
			TempOutputWriter = new PrintWriter( TempOutputStream , true );
			
			for ( BigInteger ind = BigInteger.ZERO ; ind.compareTo( Length ) < 0 ; ind = ind.add( BigInteger.ONE ) ) {
				TempOutputWriter.print( TempInputScanner.readLine() + "\n" ) ;
			}
			
			TempOutputWriter.flush() ;
			
			TempOutputStream.close() ;
			TempOutputWriter.close() ;
			TempFileStream.close() ;
			TempInputStream.close() ;
			TempInputScanner.close() ;
			
			return Value ;
		}
	}
	
	private String insertPoint( BigInteger Index , String Value ) throws IOException {
		
		if ( Index.compareTo( BigInteger.ZERO ) < 0 || Index.compareTo( Length ) > 0 ) {
			throw new IndexOutOfBoundsException() ;
		} else {
			
			@SuppressWarnings("static-access")
			File tempFile = file.createTempFile( "Temp"+FileName , ".txt" ) ;
			
			FileOutputStream TempOutputStream = new FileOutputStream( tempFile );
			PrintWriter TempOutputWriter = new PrintWriter( TempOutputStream , true );

			FileInputStream TempFileStream = new FileInputStream( tempFile ) ;
			InputStreamReader TempInputStream = new InputStreamReader( TempFileStream ) ;
			BufferedReader TempInputScanner = new BufferedReader( TempInputStream ) ;
			
			for ( BigInteger ind = BigInteger.ZERO ; ind.compareTo( Length ) <= 0 ; ind = ind.add( BigInteger.ONE ) ) {
				if( ind.compareTo( Index ) < 0 ) {
					TempOutputWriter.print( this.getPoint( ind ) + "\n" ) ;
				} else if( ind.compareTo( Index ) > 0 ) {
					TempOutputWriter.print( this.getPoint( ind.subtract( BigInteger.ONE ) ) + "\n" ) ;
				} else {
					TempOutputWriter.print( Value + "\n" ) ;
					Length = Length.add( BigInteger.ONE ) ;
				}
			}
			
			TempOutputWriter.flush() ;
			
			file = new File( FileName ) ;

			TempOutputStream = new FileOutputStream( file );
			TempOutputWriter = new PrintWriter( TempOutputStream , true );
			
			for ( BigInteger ind = BigInteger.ZERO ; ind.compareTo( Length ) < 0 ; ind = ind.add( BigInteger.ONE ) ) {
				TempOutputWriter.print( TempInputScanner.readLine() + "\n" ) ;
			}
			
			TempOutputWriter.flush() ;
			
			TempOutputStream.close() ;
			TempOutputWriter.close() ;
			TempFileStream.close() ;
			TempInputStream.close() ;
			TempInputScanner.close() ;
			
			return Value ;
		}
	}

	private String removePoint( BigInteger Index ) throws IOException {
		
		if ( Index.compareTo( BigInteger.ZERO ) < 0 || Index.compareTo( Length ) > 0 ) {
			throw new IndexOutOfBoundsException() ;
		} else {
			
			String Removed = this.getPoint(Index) ;
			
			@SuppressWarnings("static-access")
			File tempFile = file.createTempFile( "Temp"+FileName , ".txt" ) ;
			
			FileOutputStream TempOutputStream = new FileOutputStream( tempFile );
			PrintWriter TempOutputWriter = new PrintWriter( TempOutputStream , true );

			FileInputStream TempFileStream = new FileInputStream( tempFile ) ;
			InputStreamReader TempInputStream = new InputStreamReader( TempFileStream ) ;
			BufferedReader TempInputScanner = new BufferedReader( TempInputStream ) ;
			
			for ( BigInteger ind = BigInteger.ZERO ; ind.compareTo( Length ) < 0 ; ind = ind.add( BigInteger.ONE ) ) {
				if( ind.compareTo( Index ) != 0 ) {
					TempOutputWriter.print( this.getPoint(ind) + "\n" ) ;
				}
			}
			
			Length = Length.subtract( BigInteger.ONE ) ;
			
			TempOutputWriter.flush() ;
			
			file = new File( FileName ) ;

			TempOutputStream = new FileOutputStream( file );
			TempOutputWriter = new PrintWriter( TempOutputStream , true );
			
			for ( BigInteger ind = BigInteger.ZERO ; ind.compareTo( Length ) < 0 ; ind = ind.add( BigInteger.ONE ) ) {
				TempOutputWriter.print( TempInputScanner.readLine() + "\n" ) ;
			}
			
			TempOutputWriter.flush() ;
			
			TempOutputStream.close() ;
			TempOutputWriter.close() ;
			TempFileStream.close() ;
			TempInputStream.close() ;
			TempInputScanner.close() ;
			
			return Removed ;
		}
	}
}
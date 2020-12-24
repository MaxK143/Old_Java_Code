import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DnDStatistics {
	
	final private int SampleSize = 10000 ;
	
	//createDiceSet vars
	final private int DiceSize = 6 ;
	final private int DiceCount = 4 ;
	final private int RemoveCount = 1 ;
	
	//createStatSet vars
	final static private int SetSize = 7 ;
	final static private int RemoveCountSet = 1 ;
	
	//ArrayFiles
	private static ArrayFile TextAverages ;
	private static ArrayFile TextValues ;
	private static ArrayFile TextMins ;
	private static ArrayFile TextMaxs ;
	private static ArrayFile TextSamples ;
	
	//My child
	private static StatGenerator Generator ;

	public static void main(String[] args) {
		try {
		new DnDStatistics() ;
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e ;
		} finally {
			try {
			Generator.delete() ;
			} finally {
				System.exit(0) ;
			}
		}
	}
	
	public DnDStatistics() {
		
		ArrayFile textAverages = new ArrayFile( "C:\\Users\\Robert Kellinghaus\\Desktop\\My Java\\EncodedTexts\\EncodedFileAverages.txt" ) ;
		ArrayFile textValues = new ArrayFile( "C:\\Users\\Robert Kellinghaus\\Desktop\\My Java\\EncodedTexts\\EncodedFileValues.txt" ) ;
		ArrayFile textMins = new ArrayFile( "C:\\Users\\Robert Kellinghaus\\Desktop\\My Java\\EncodedTexts\\EncodedFileMins.txt" ) ;
		ArrayFile textMaxs = new ArrayFile( "C:\\Users\\Robert Kellinghaus\\Desktop\\My Java\\EncodedTexts\\EncodedFileMaxs.txt" ) ;
		ArrayFile textSamples = new ArrayFile( "C:\\Users\\Robert Kellinghaus\\Desktop\\My Java\\EncodedTexts\\EncodedFileSample.txt" ) ;
		
		StatGenerator Generator = new StatGenerator( textAverages , textValues , textMins , textMaxs, textSamples ) ;
		
		try { Thread.sleep(100);
		} catch (InterruptedException e) { e.printStackTrace(); }

		ArrayFile TextAverages = textAverages.clone() ;
		ArrayFile TextValues = textValues.clone() ;
		ArrayFile TextMins = textMins.clone() ;
		ArrayFile TextMaxs = textMaxs.clone() ;
		ArrayFile TextSamples = textSamples.clone() ;

		double Max = 100 ;//( DiceSize * ( DiceCount-RemoveCount ) ) + 1 ;
		double MaxValue = 100 ;
		System.out.println( Max ) ;
		System.out.println( MaxValue ) ;
		
		DistCurve AverageCurve = new DistCurve( Max ) ;
		DistCurve ValuesCurve = new DistCurve( MaxValue ) ;
		DistCurve MinsCurve = new DistCurve( Max ) ;
		DistCurve MaxsCurve = new DistCurve( Max ) ;
		DistCurve SampleCurve = new DistCurve( Max ) ;

		double[] SampleAverages = new double[ SampleSize ] ;
		double[] SampleValues = new double[ SampleSize ] ;
		double[] SampleMins = new double[ SampleSize ] ;
		double[] SampleMaxs = new double[ SampleSize ] ;
		double[] Sample = new double[ SampleSize ] ;
		
		long EntriesMade = 0 ;
		int Reference = 0 ;
		
		while( EntriesMade < Long.MAX_VALUE ) {
			while( Reference < SampleSize && EntriesMade < Long.MAX_VALUE && EntriesMade <=  Generator.getFileEntriesMade()-20 ) {
				SampleAverages[ Reference ] = Double.parseDouble( TextAverages.get( BigInteger.valueOf( EntriesMade ) ) ) ;
				if( Double.parseDouble( TextAverages.get( BigInteger.valueOf( EntriesMade ) ) ) < 0 ) { System.out.println( "Avg: " + TextAverages.get( BigInteger.valueOf( EntriesMade ) ) ) ; }
				SampleValues[ Reference ] = Double.parseDouble( TextValues.get( BigInteger.valueOf( EntriesMade ) ) ) ;
				if( Double.parseDouble( TextValues.get( BigInteger.valueOf( EntriesMade ) ) ) < 0 ) { System.out.println( "Val: " + TextValues.get( BigInteger.valueOf( EntriesMade ) ) ) ; }
				SampleMins[ Reference ] = Double.parseDouble( TextMins.get( BigInteger.valueOf( EntriesMade ) ) ) ;
				SampleMaxs[ Reference ] = Double.parseDouble( TextMaxs.get( BigInteger.valueOf( EntriesMade ) ) ) ;
				Sample[ Reference ] = Double.parseDouble( TextSamples.get( BigInteger.valueOf( EntriesMade ) ) ) ;
				EntriesMade++ ;
				Reference++ ;
			}
			
			/*AverageCurve.addToDistrubution( SampleAverages , Reference ) ;
			ValuesCurve.addToDistrubution( SampleValues , Reference ) ;
			MinsCurve.addToDistrubution( SampleMins , Reference ) ;
			MaxsCurve.addToDistrubution( SampleMaxs , Reference ) ;
			SampleCurve.addToDistrubution( Sample , Reference ) ;*/
			
			Reference  = 0 ;
			
			SampleAverages = new double[ SampleSize ] ;
			SampleValues = new double[ SampleSize ] ;
			SampleMins = new double[ SampleSize ] ;
			SampleMaxs = new double[ SampleSize ] ;
			Sample = new double[ SampleSize ] ;
		}
		
		Generator.delete() ;
		
		TextAverages.delete() ;
		TextValues.delete() ;
		TextMins.delete() ;
		TextMaxs.delete() ;
		TextSamples.delete() ;
		
		EncodeToFile( "AverageCurve" , AverageCurve.getPercentArrayPoints() ) ;
		EncodeToFile( "ValuesCurve" , ValuesCurve.getPercentArrayPoints() ) ;
		EncodeToFile( "MinsCurve" , MinsCurve.getPercentArrayPoints() ) ;
		EncodeToFile( "MaxsCurve" , MaxsCurve.getPercentArrayPoints() ) ;
		EncodeToFile( "SampleCurve" , SampleCurve.getPercentArrayPoints() ) ;
		
	}
	
	private void EncodeToFile( String fileName , BigDecimal[][] bigDecimals ) {
		
		int count = 0 ;
		String FileName = "C:\\Users\\Robert Kellinghaus\\Desktop\\My Java\\EncodedTexts\\"+fileName ;
		java.io.File file = new java.io.File( FileName ) ;
		do {
			FileName = "C:\\Users\\Robert Kellinghaus\\Desktop\\My Java\\EncodedTexts\\" + fileName + "(" + count + ").txt" ;
			file = new java.io.File( FileName );
			count++ ;
		}while( file.exists() ) ;
		
		try { java.io.PrintWriter Output = new java.io.PrintWriter( file ) ;
		    for(int index = 0; index < bigDecimals[0].length; index++) {
		    	String temp = bigDecimals[0][index] + " " + bigDecimals[1][index];
		    	Output.println(temp); }
		    Output.close() ;
		} catch (FileNotFoundException e) { e.printStackTrace(); }
	}
}

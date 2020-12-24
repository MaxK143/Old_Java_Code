import java.io.IOException;
import java.math.BigInteger;

public class PointInsertion {
	
	public PointInsertion() { }
	
	public BigArrayFile PointInsertion( BigArrayFile ArrayFile , Integer Recursions ) throws IOException {
		
		return InsertPoints( ArrayFile , BigInteger.valueOf( Recursions ) ) ;
		
	}
	
	private BigArrayFile InsertPoints(BigArrayFile ArrayFile, BigInteger Index ) throws IOException {
		
		double[] point1 = new double[2] ;
		
		point1[0] = Double.parseDouble( ArrayFile.Access( Index , null , "get" ).substring(0, ArrayFile.Access( Index , null , "get" ).indexOf(" ") ) ) ;
		point1[1] = Double.parseDouble( ArrayFile.Access( Index , null , "get" ).substring( ArrayFile.Access( Index , null , "get" ).indexOf(" ") + 1) ) ;
		
		double[] point2 = new double[2] ;
		
		point2[0] = Double.parseDouble( ArrayFile.Access( Index , null , "get" ).substring(0, ArrayFile.Access( Index.add( BigInteger.ONE ) , null , "get" ).indexOf(" ") ) ) ;
		point2[1] = Double.parseDouble( ArrayFile.Access( Index , null , "get" ).substring( ArrayFile.Access( Index.add( BigInteger.ONE ) , null , "get" ).indexOf(" ") + 1) ) ;
		
		double[] point3 = new double[2] ;
		
		point3[0] = Double.parseDouble( ArrayFile.Access( Index , null , "get" ).substring(0, ArrayFile.Access( Index.add( BigInteger.valueOf( 2 ) ) , null , "get" ).indexOf(" ") ) ) ;
		point3[1] = Double.parseDouble( ArrayFile.Access( Index , null , "get" ).substring( ArrayFile.Access( Index.add( BigInteger.valueOf( 2 ) ) , null , "get" ).indexOf(" ") + 1) ) ;
		
		double[] point4 = new double[2] ;
		
		point4[0] = Double.parseDouble( ArrayFile.Access( Index , null , "get" ).substring(0, ArrayFile.Access( Index.add( BigInteger.valueOf( 3 ) ) , null , "get" ).indexOf(" ") ) ) ;
		point4[1] = Double.parseDouble( ArrayFile.Access( Index , null , "get" ).substring( ArrayFile.Access( Index.add( BigInteger.valueOf( 3 ) ) , null , "get" ).indexOf(" ") + 1) ) ;
		
		double DeltaX = ( point3[1] - point2[1] ) / 2 ;
		
		double newY = point2[0] + ( DeltaX * ( point3[0] - point2[0] ) ) ; // deriv1
		
		System.out.println( "points:" + point2[0] + " " + newY + " " + point3[0] ) ;
		
		double deriv2  = ( ( ( ( point4[0] - point3[0] ) - ( point3[0] - point2[0] ) ) + ( ( point3[0] - point2[0] ) - ( point2[0] - point1[0] ) ) ) / 2 ) ;
		
		newY = ( deriv2 + point3[0] -point2[0] ) / -2 ;

		System.out.println( "points:" + point2[0] + " " + newY + " " + point3[0] ) ;
		
		if( Index.subtract( BigInteger.valueOf( 3 ) ).compareTo( BigInteger.valueOf( (long) Double.parseDouble( ArrayFile.Access( null , null , "length" ) ) ) ) < 0 ) {
			return InsertPoints( ArrayFile , Index.add( BigInteger.valueOf( 2 ) ) ) ;
		} else {
			return ArrayFile ;
		}
	}
}

import java.math.BigDecimal; 

public class DistCurve {
	
	private final double Iteration = 0.5 ;
	
	private final int Iterations = 2 ;
		
	private int[] Array ;
	
	private BigDecimal[] PercentArray ;
	
	private BigDecimal total = BigDecimal.ZERO ;
	
	private double Max ;

	public DistCurve( double max ) {
		Max = max ;
		Array = new int[ (int)( Max * Iterations ) ] ;
		PercentArray = new BigDecimal[ (int)( Max * Iterations ) ] ;
		total = BigDecimal.ZERO ;
	}
	
	public void addToDistrubution( double[] arr ) {
		for( int i = 0 ; i < arr.length ; i++ ) {
			System.out.print( arr[i] + " " ) ;
			Array[ (int)( arr[i] * Iterations ) ]++ ;
		}
		System.out.println() ;
		total = total.add( BigDecimal.valueOf( arr.length ) ) ;
	}
	
	public int[] getDistCurve() {
		return Array ;
	}
	
	public void refreshPercentCurve() {
		for( int i = 0 ; i < PercentArray.length ; i++ ) {
			PercentArray[i] = ( BigDecimal.valueOf( Array[i] ).divide( total )).multiply( BigDecimal.valueOf( 100 ) ) ;
		}
	}
	
	public BigDecimal[][] getPercentArrayPoints(){
		
		this.refreshPercentCurve() ;
		
		BigDecimal[][] arr = new BigDecimal[ PercentArray.length ][ 2 ] ;

		BigDecimal RealValue = BigDecimal.ZERO ;
		for( int in = 0 ; in < arr.length ; in++ ) {
			arr[in][0] = RealValue ;
			arr[in][1] = PercentArray[in] ;
			RealValue = RealValue.add( BigDecimal.valueOf( Iteration ) ) ;
		}
		return arr ;
	}
	
	public BigDecimal[] getPercentArray() {
		this.refreshPercentCurve() ;
		return PercentArray ;
	}
	
	public String toString() {
		String ret = "" ;
		for( int i = 0 ; i < PercentArray.length ; i++ ) {
			ret += PercentArray[i] + ", " ;
		}
		return ret ;
	}
}

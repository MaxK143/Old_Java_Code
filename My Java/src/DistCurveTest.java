import java.math.BigDecimal;

public class DistCurveTest {

	public static void main(String[] args) {
		//test: public DistCurve( double max )
		
		DistCurve test1 = new DistCurve( 10 ) ;
		
		//test: public DistCurve( double Iter , double max )

		DistCurve test2 = new DistCurve( 0.01 ) ;
		
		//test: public void addToDistrubution( double[] arr)
		
		double[] test3 = new double[20] ;
		for( double i = 0 ; i < test3.length ; i++ ) {
			test3[(int)i] = i / 2 ;
		}
		test1.addToDistrubution( test3 ) ;
		test1.addToDistrubution( test3 ) ;
		
		double[] test7 = new double[10] ;
		for( double i = 0 ; i < test7.length ; i++ ) {
			test7[(int)i] = i / 2 ;
		}
		test1.addToDistrubution( test7 ) ;
		
		//test: public int[] getDistCurve()
		
		int[] test4 = test1.getDistCurve() ;
		
		//test: public void refreshPercentCurve()
		
		test1.refreshPercentCurve() ;
		
		//test: public BigDecimal[][] getPercentArrayPoints()
		
		BigDecimal[][] test5 = test1.getPercentArrayPoints() ;
		
		//test: public BigDecimal[] getPercentArray()
		
		BigDecimal[] test6 = test1.getPercentArray() ;

		System.out.println( ) ;
		
		System.out.println( test1.toString() ) ;

		System.out.println( ) ;

		for( int i = 0 ; i < test6.length ; i++ ) {
			System.out.print( test6[i] + " " ) ;
		}

		System.out.println( ) ;
		System.out.println( ) ;

		for( int i = 0 ; i < test5[1].length ; i++ ) {
			System.out.print( test5[0][i] + "," + test5[1][i] + " , " ) ;
		}
		
	}

}

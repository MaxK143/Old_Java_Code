import java.io.IOException;

public class DiceRolls {
	
	/*public static void main(String[] args) {
		
		DiceRolls TestingDice = new DiceRolls() ;
		
		System.out.println( "CreateDiceRoll(): " + TestingDice.CreateDiceRoll() ) ;

		System.out.println() ;
		
		double[] test1 = CreateDiceRolls() ;
		for( int i = 0 ; i < test1.length ; i++ ) {
			System.out.println( "CreateDiceRolls() " + i +" : " + test1[i] ) ;
		}
		
		System.out.println() ;
		
		double[] test2 = TestingDice.CreateRollStats() ;
		for( int i = 0 ; i < test2.length ; i++ ) {
			System.out.println( "CreateRollStats() " + i +" : " + test2[i] ) ;
		}
		
	}*/
	
	public DiceRolls() {
	}
	
	public int CreateDiceRoll() {
		
		int[] Dice = new int[4] ;

		for( int i = 0 ; i < Dice.length ; i++ ) {
		Dice[i] = (int)( Math.random()*7 ) ; }
		
		int min = 6 ;
		for( int i = 0 ; i < Dice.length ; i++ ) {
		if( Dice[i] < min ) { min = Dice[i] ; } }
		
		int sum = -min ;
		for( int i = 0 ; i < Dice.length ; i++ ) { sum += Dice[i] ; }
		return sum ;
	}
	
	public double[] CreateDiceRolls() {
		
		int[] Dice = new int[7] ;

		for( int i = 0 ; i < Dice.length ; i++ ) {
			Dice[i] = CreateDiceRoll() ;
		}
		
		int min = 18 ;
		int minIndex = 0 ;
		for( int i = 0 ; i < Dice.length ; i++ ) {
			if( Dice[i] < min ) { min = Dice[i] ; minIndex = i ;
			}
		}

		double[] Rolls = new double[6] ;
		
		for( int i = 0 ; i < Dice.length ; i++ ) {
			if( i < minIndex ) {
				Rolls[i] = Dice[i] ;
			} else if ( i > minIndex ) {
				Rolls[i-1] = Dice[i] ;
			}
		}
		return Rolls ;
	}
	
	public double CreateSample() {
		return CreateDiceRoll() ;
	}
	
	public double CreateAverage() {
		double[] Rolls = CreateDiceRolls() ;
		double Average = 0 ;
		for( int i = 0 ; i < Rolls.length ; i++ ) {
			Average += Rolls[i] / Rolls.length ;
		}
		return Average ;
	}
	
	public double CreateScore() {
		double[] Rolls = CreateDiceRolls() ;
		
		double[] ScoreGradient = { 4 , 4 , 3 , 3 , 2 , 2 } ;
		
		double Score = 0 ;
		for( int i = 0 ; i < Rolls.length ; i++ ) {
			double max = 0 ;
			int maxIndex = 0 ;
			for( int in = 0 ; in < Rolls.length ; in++ ) {
				if( Rolls[in] > max ) {
					max = Rolls[in] ;
					maxIndex = in ;
				}
			}
			Score += max * ScoreGradient[i] ;
			Rolls[maxIndex] = -1 ;
		}
		return Score ;
	}
	
	public double[] CreateSortedDiceRolls() {
		
		double[] Rolls = CreateDiceRolls() ;
		
		double[] SortedRolls = CreateDiceRolls() ;
		
		double max = 0 ;
		
		int maxIndex = 0 ;
		
		for( int index = 0 ; index < SortedRolls.length ; index++ ) {
			for( int ind = 0 ; ind < Rolls.length ; ind++ ) {
				if( Rolls[ind] > max ) {
					max = Rolls[ind] ;
					maxIndex = ind ;
				}
			}
			SortedRolls[index] = max ;
			max = 0 ;
			Rolls[maxIndex] = -100 ;
		}
		return SortedRolls ;
	}
	
	
	public double CreateMaximum() {
		double[] Rolls = CreateDiceRolls() ;
		double max = 0 ;
		for( int in = 0 ; in < Rolls.length ; in++ ) {
			if( Rolls[in] > max ) {
				max = Rolls[in] ;
			}
		}
		return max ;
	}
	
	public double CreateMinimum() {
		double[] Rolls = CreateDiceRolls() ;
		double min = 18 ;
		for( int i = 0 ; i < Rolls.length ; i++ ) {
			if( Rolls[i] < min ) {
				min = Rolls[i] ;
			}
		}
		return min ;
	}
	
	public double[] CreateRollStats(){ // Returns an Array of the: 1. Example, 2. Average, 3. Worth, 4. Maximum, 5. Minimum
		
		double[] Stats = new double[5] ;
		
		Stats[0] = CreateDiceRoll() ;
		
		double[] Rolls = CreateDiceRolls() ;
		
		double Average = 0 ;
		for( int i = 0 ; i < Rolls.length ; i++ ) {
			Average += Rolls[i] / Rolls.length ;
		}
		Stats[1] = Average ;

		double min = 18 ;
		for( int i = 0 ; i < Rolls.length ; i++ ) {
			if( Rolls[i] < min ) {
				min = Rolls[i] ;
			}
		}
		
		Stats[4] = min ;
		
		
		double Score = 0 ;
		for( int i = 0 ; i < Rolls.length ; i++ ) {
			double max = 0 ;
			int maxIndex = 0 ;
			for( int in = 0 ; in < Rolls.length ; in++ ) {
				if( Rolls[in] > max ) { max = Rolls[in] ; maxIndex = in ;
				}
			}
			if( i == 0 ) { Stats[3] = max ; }
			
			Score += max * ( 6 * ( ( ( Rolls.length-1 ) - i ) / ( Rolls.length-1 ) ) ) ;
			Rolls[maxIndex] = -1 ;
		}
		Score = Score / Rolls.length ;
		
		Stats[2] = Score ;
		
		return Stats ;
	}
}
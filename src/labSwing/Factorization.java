package labSwing;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

/**
* Library of number factorization methods 
* @author Tyufyakov Maxim
* @version 1.0
*/
public class Factorization {
	/**
	* Prime factorization
	* @since 1.0
	* @param num target number
	* @return Array of prime divisors
	* @throws UncorrectedDataException If the argument is less
	than two
	*/
	public static BigInteger[] simple(BigInteger num) throws UncorrectedDataException {
		// exception
		if (num.compareTo(BigInteger.valueOf(2))==-1) 
			throw new UncorrectedDataException("Число должно быть не меньше 2.", num);
		// answer
		BigInteger[] factors = {};
		// i - divider
		BigInteger divider = BigInteger.valueOf(2);
		// while square is less or equal to number
		while (divider.multiply(divider).compareTo(num)!=1) {	
		// while we can share
			while (num.mod(divider).compareTo(BigInteger.valueOf(0))==0) {
				num = num.divide(divider);
				// add to answer		
				factors = Arrays.copyOf(factors, factors.length + 1);
				factors[factors.length - 1] = divider;
			}
			divider = divider.add(BigInteger.valueOf(1));
		}
		// if number
		if(num.compareTo(BigInteger.valueOf(1))==1) {
			// add to answer	
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = num;
		}
		return factors;
	}
	
	/**
	* Ferma's method
	* @since 1.0
	* @param num target number
	* @return Array of prime divisors
	* @throws UncorrectedDataException If the argument is less
	than two or odd
	*/
	public static BigInteger[] ferma(BigInteger num) throws UncorrectedDataException {
		// exceptions
		if (num.compareTo(BigInteger.valueOf(2))==-1) 
			throw new UncorrectedDataException("Число должно быть не меньше 2.", num);
		if (num.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(1))==0) 
			throw new UncorrectedDataException("Для данного метода число должно быть чётным.", num);
		// answer
		BigInteger[] factors = {};
		// dividers and intermediate value
		BigInteger divider1, divider2, y;
		
		// simplicity check
		if(simpleNumber(num)) {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = num;
			return factors;
		}
		// if it is already a square
		if (idealSqrt(num)) {
			divider1 = divider2 = num.sqrt();
		}
		else {
			// the whole root
			BigInteger sqrt = num.sqrt();
			BigInteger iterCount = BigInteger.valueOf(1);
			// main cycle
			while(!idealSqrt((sqrt.add(iterCount)).pow(2).subtract(num))) {
				iterCount = iterCount.add(BigInteger.valueOf(1));
			}
			y = (sqrt.add(iterCount)).pow(2).subtract(num).sqrt();
			// first divider
			divider1 = sqrt.add(iterCount).add(y);
			// second divider
			divider2 = sqrt.add(iterCount).subtract(y);
		}
		
		// if the first is simple
		if (simpleNumber(divider1)) {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = divider1;
		}
		// else repeat
		else {factors = concate(factors, ferma(divider1));}
		
		// if the second is simple
		if (simpleNumber(divider2)) {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = divider2;
		}
		// else repeat
		else {factors = concate(factors, ferma(divider2));}
		return factors;
	}
	
	/**
	* Polard's p-0 method
	* @since 1.0
	* @param num target number
	* @return Array of prime divisors
	* @throws UncorrectedDataException If the argument is less
	than two
	*/
	public static BigInteger[] pollard_p0(BigInteger num) throws UncorrectedDataException {
		// exceptions
		if (num.compareTo(BigInteger.valueOf(2))==-1) 
			throw new UncorrectedDataException("Число должно быть не меньше 2.", num);
		// answer
		BigInteger[] factors = {};
		// dividers
		BigInteger divider1 = BigInteger.valueOf(1);
		BigInteger divider2;
		
		// if it is already a simple
		if(simpleNumber(num)) {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = num;
			return factors;
		}
		// divided by 2
		if (num.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0))==0) {
			divider1 = BigInteger.valueOf(2);
		}
		else {
			// parameters			
			BigInteger param1 = new BigInteger(16, new Random()).mod(num);
			BigInteger param2 = new BigInteger(16, new Random()).mod(num);
			
			// iterations
			int totalIter = 100;
			int countIter = 1;
			// main cycle
			while (divider1.compareTo(BigInteger.valueOf(1))==0 || divider1.compareTo(num)==0) {
				// if looping
				if (countIter == totalIter) {
					// changing the parameters
					param1 = new BigInteger(16, new Random()).mod(num);
					param2 = new BigInteger(16, new Random()).mod(num);
					// reset
					divider1 = BigInteger.valueOf(1);
					countIter = 1;
					factors = Arrays.copyOf(factors, 0);
				}
				else {countIter++;}
				// first parameter -> step
				param2 = pollard_p0_func(param2).mod(num);
				// second parameter -> two step
				param1 = pollard_p0_func(pollard_p0_func(param1).mod(num)).mod(num);
				// find the common divisor
				BigInteger absXY = param1.subtract(param2).abs();
				divider1 = absXY.gcd(num);
			}
		}
		divider2 = num.divide(divider1);
		
		// if the first isn't simple
		if(!simpleNumber(divider1)) {
			factors = concate(factors, pollard_p0(divider1));
		}
		else {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = divider1;
		}
		
		// if the second isn't simple
		if(!simpleNumber(divider2)) {
			factors = concate(factors, pollard_p0(divider2));
		}
		else {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = divider2;
		}
	    return factors;
	}
	
	/**
	* Polard's p-1 method
	* @since 1.0
	* @param num target number
	* @return Array of prime divisors
	* @throws UncorrectedDataException If the argument is less
	than two
	*/
	public static BigInteger[] pollard_p1(BigInteger num) throws UncorrectedDataException {
		// exceptions
		if (num.compareTo(BigInteger.valueOf(2))==-1) 
			throw new UncorrectedDataException("Число должно быть не меньше 2.", num);
		// answers
		BigInteger[] factors = {};
		
		// if it is already a simple
		if(simpleNumber(num)) {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = num;
			return factors;
		}
		
		BigInteger param1 = BigInteger.valueOf(2);
		int iter = 2;
		BigInteger divider = BigInteger.valueOf(1);
		
		// divided by 2
		if (num.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0))==0) {
			divider = BigInteger.valueOf(2);
		}
		else {
			// the main cycle
			do {
				param1 = param1.pow(iter).mod(num);
				divider = num.gcd(param1.subtract(BigInteger.valueOf(1)));
				iter++;
			}while(divider.compareTo(BigInteger.valueOf(1))==0);
		}
		// add to answer (first)
		factors = Arrays.copyOf(factors, factors.length + 1);
    	factors[factors.length - 1] = divider;
    	// second
    	divider = num.divide(divider);
		// if the second isn't simple
		if(!simpleNumber(divider)) {
			factors = concate(factors, pollard_p1(divider));
		}
		else {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = divider;
		}
    	
    	return factors;
	}
	
	/**
	* Bent's method
	* @since 1.0
	* @param num target number
	* @return Array of prime divisors
	* @throws UncorrectedDataException If the argument is less
	than two
	*/
	public static BigInteger[] benta(BigInteger num) throws UncorrectedDataException {
		// exceptions
		if (num.compareTo(BigInteger.valueOf(2))==-1) 
			throw new UncorrectedDataException("Число должно быть не меньше 2.", num);
		// answer
		BigInteger[] factors = {};
				
		// if it is already a simple
		if(simpleNumber(num)) {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = num;
			return factors;
		}
		// parameters
		BigInteger param1 = new BigInteger(16, new Random()).mod(num);
		BigInteger param2 = (param1.multiply(param1).add(BigInteger.valueOf(2))).mod(num);
		// the first divider
		BigInteger divider = BigInteger.valueOf(1);
		// the iterations
		int totalIter = 100;
		int countIter = 1;
		// the main cycle
		do {
			// the looping
			if (countIter == totalIter) {
				// changing the parameters
				param1 = new BigInteger(16, new Random()).mod(num);
				param2 = (param1.multiply(param1).add(BigInteger.valueOf(2))).mod(num);
				// reset
				divider = BigInteger.valueOf(1);
				countIter = 1;
				factors = Arrays.copyOf(factors, 0);
			}
			else {countIter++;}
			// find the first divisor
			param2 = (param2.multiply(param2).add(BigInteger.valueOf(2))).mod(num);
			divider = num.gcd(param2.subtract(param1).abs());
		}while(divider.compareTo(BigInteger.valueOf(1))==0);
			
		// if the first isn't simple
		if(!simpleNumber(divider)) {
			factors = concate(factors, pollard_p1(divider));
		}
		else {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = divider;
		}
	    
		// second divisor
	    divider = num.divide(divider);
	    // if the second isn't simple
		if(!simpleNumber(divider)) {
			factors = concate(factors, pollard_p1(divider));
		}
		else {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = divider;
		}
	    	
	    return factors;
	}
	
	/**
	* Pollard's monte-carlo's method
	* @since 1.0
	* @param num target number
	* @return Array of prime divisors
	* @throws UncorrectedDataException If the argument is less
	than two
	*/
	public static BigInteger[] pollarda_monte_carlo(BigInteger num) throws UncorrectedDataException {
		// exceptions
		if (num.compareTo(BigInteger.valueOf(2))==-1) 
			throw new UncorrectedDataException("Число должно быть не меньше 2.", num);
		// answer
		BigInteger[] factors = {};
		// dividers
		BigInteger divider1 = BigInteger.valueOf(1);
		BigInteger divider2;
				
		// if it is already a simple
		if(simpleNumber(num)) {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = num;
			return factors;
		}
		// divided by 2
		if (num.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0))==0) {
			divider1 = BigInteger.valueOf(2);
		}
		else {
			// parameters		
			BigInteger param1 = new BigInteger(16, new Random()).mod(num);
			BigInteger param2 = new BigInteger(16, new Random()).mod(num);
					
			// iterations
			int totalIter = 100;
			int countIter = 1;
			// main cycle
			while (divider1.compareTo(BigInteger.valueOf(1))==0 || divider1.compareTo(num)==0) {
				// the looping
				if (countIter == totalIter) {
					// changing the parameters
					param1 = new BigInteger(16, new Random()).mod(num);
					param2 = new BigInteger(16, new Random()).mod(num);
					// reset
					divider1 = BigInteger.valueOf(1);
					countIter = 1;
					factors = Arrays.copyOf(factors, 0);
				}
				else {countIter++;}
				// changing the parameters
				param2 = pollarda_monte_carlo_func(param2).mod(num);
				param1 = pollarda_monte_carlo_func(pollarda_monte_carlo_func(param1).mod(num)).mod(num);
				// find the common divisor
				BigInteger absXY = param1.subtract(param2).abs();
				divider1 = absXY.gcd(num);
			}
		}
		divider2 = num.divide(divider1);
				
		// if the first isn't simple
		if(!simpleNumber(divider1)) {
			factors = concate(factors, pollard_p0(divider1));
		}
		else {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = divider1;
		}
				
		// if the second isn't simple
		if(!simpleNumber(divider2)) {
			factors = concate(factors, pollard_p0(divider2));
		}
		else {
			factors = Arrays.copyOf(factors, factors.length + 1);
			factors[factors.length - 1] = divider2;
		}
		return factors;
	}
	
	/**
	* Function for pollard's p-0
	* @since 1.0
	* @param num target number
	* @return number after conversion
	*/
	private static BigInteger pollard_p0_func (BigInteger num) {
		return (num.add(BigInteger.valueOf(1))).pow(2);
	}
	
	/**
	* Function for pollard's monte-carlo's
	* @author Tyufyakov Maxim
	* @since 1.0
	* @param num target number
	* @return number after conversion
	*/
	private static BigInteger pollarda_monte_carlo_func (BigInteger num) {
		return (num.add(BigInteger.valueOf(2))).pow(2);
	}
	
	/**
	* Simplicity check
	* @since 1.0
	* @param num target number
	* @return true if the target number is prime, else false
	*/
	private static boolean simpleNumber(BigInteger num) {
		if (num.compareTo(BigInteger.valueOf(1))==0){
			return false;
		}
		BigInteger i = BigInteger.valueOf(2);
		// while square less number
		while (i.multiply(i).compareTo(num) != 1) {
			if (num.mod(i).compareTo(BigInteger.valueOf(0))==0) {
				return false;
			}
			i = i.add(BigInteger.valueOf(1));
		}
		return true;
	}
	
	/**
	* The whole root check
	* @since 1.0
	* @param num target number
	* @return true if the target number is the whole root, else false
	*/
	private static boolean idealSqrt(BigInteger num) {
		BigInteger a = num.sqrt();
		BigInteger b = a.pow(2);
		if(b.compareTo(num) == 0) {return true;}
		else {return false;}
	}
	
	/**
	* Concatenation of two arrays
	* @since 1.0
	* @param a first array
	* @param b second array
	* @return new array (a.b)
	*/
	private static BigInteger[] concate(BigInteger[] a, BigInteger[] b) {
		BigInteger[] res = new BigInteger[a.length + b.length];
		System.arraycopy(a, 0, res, 0, a.length);
		System.arraycopy(b, 0, res, a.length, b.length);
		return res;
	}

}

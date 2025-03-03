package labSwing;

import java.math.BigInteger;

/**
* Exception incorrect input data
* @version 1.0
* @author Tyufyakov Maxim
*/
public class UncorrectedDataException extends Exception{
	private static final long serialVersionUID = 1L;
	/**
	* wrong number
	*/
	private BigInteger number;
	/**
	* number getter
	* @since 1.0
	* @return wrong number
	*/
    public BigInteger getNumber(){return number;}
    /**
	* constructor
	* @since 1.0
	* @param message error message
	* @param num wrong number
	*/
    public UncorrectedDataException(String message, BigInteger num){
        super(message);
        number=num;
    }
}
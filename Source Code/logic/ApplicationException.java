package ch06.logic;

public class ApplicationException extends Exception {

	private int intError;

	ApplicationException(int intErrNo){
		intError = intErrNo;
	}

	ApplicationException(String strMessage){
		super(strMessage);
	}

	public String toString(){
		return "ApplicationException["+intError+"]";
	}  
}
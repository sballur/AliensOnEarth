/**
 * 
 */
package com.shiva.aliensOnEarth.exception;

/**
 * While exporting if the user specifies invalid path then this exception will be thrown
 * @author Shiva
 *
 */
@SuppressWarnings("serial")
public class InvalidPathException extends RuntimeException {

	private String message;
	
	public InvalidPathException(String message) {
		this.message = message;		
	}

	public InvalidPathException() {
		
	}
	
	
	@Override
	public String getMessage() {
		if(message == null){
			return super.getMessage();
		}else{
			return message;
		}
	}
}

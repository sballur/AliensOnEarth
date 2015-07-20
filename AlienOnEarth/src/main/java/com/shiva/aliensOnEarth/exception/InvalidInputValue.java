/**
 * 
 */
package com.shiva.aliensOnEarth.exception;

/**
 * If the user provides invalid details then this error will be thrown.
 * @author Shiva
 *
 */
@SuppressWarnings("serial")
public class InvalidInputValue extends RuntimeException {

	private String message;
	
	public InvalidInputValue(String message) {
		this.message = message;		
	}

	public InvalidInputValue() {
		
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

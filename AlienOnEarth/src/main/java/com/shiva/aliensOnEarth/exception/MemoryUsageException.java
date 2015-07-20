/**
 * 
 */
package com.shiva.aliensOnEarth.exception;

/**
 * While exporting if the there is no enough space in the specified path then below this exception will be thrown.
 * @author Shiva
 */
@SuppressWarnings("serial")
public class MemoryUsageException extends RuntimeException {

	private String message;
	
	public MemoryUsageException(String message) {
		this.message = message;		
	}

	public MemoryUsageException() {
		
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

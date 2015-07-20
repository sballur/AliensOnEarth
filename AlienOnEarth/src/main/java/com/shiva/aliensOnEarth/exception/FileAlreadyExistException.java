/**
 * 
 */
package com.shiva.aliensOnEarth.exception;

/**
 * While exporting if the user specifies existing file name then this exception will be thrown
 * @author Shiva
 *
 */
@SuppressWarnings("serial")
public class FileAlreadyExistException extends RuntimeException {

	private String message;
	
	public FileAlreadyExistException(String message) {
		this.message = message;		
	}

	public FileAlreadyExistException() {
		
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

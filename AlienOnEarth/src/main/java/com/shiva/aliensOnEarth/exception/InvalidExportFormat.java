/**
 * 
 */
package com.shiva.aliensOnEarth.exception;

/**
 * @author ashiva
 *
 */
@SuppressWarnings("serial")
public class InvalidExportFormat extends RuntimeException {

	private String message;
	
	public InvalidExportFormat(String message) {
		this.message = message;		
	}

	public InvalidExportFormat() {
		
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

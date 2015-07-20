/**
 * 
 */
package com.shiva.aliensOnEarth.enums;

import com.shiva.aliensOnEarth.service.Export;
import com.shiva.aliensOnEarth.service.PDFExport;
import com.shiva.aliensOnEarth.service.SimpleExport;

/**
 * It holds all the format of export.
 * 
 * Note: In future if you want implement the any new formats, please create the respective formatter class by extending Export class
 * and also create the enum object in the below class.
 * 
 * @author ashiva
 *
 */
public enum ExportType {
	
	TEXT(SimpleExport.class),PDF(PDFExport.class);
	
	private Class<? extends Export> clazz;
	
	ExportType(final Class<? extends Export> clazz){
		this.clazz = clazz;
	}
	
	public Class<? extends Export> getClazz() {
		return clazz;
	}	
	
}

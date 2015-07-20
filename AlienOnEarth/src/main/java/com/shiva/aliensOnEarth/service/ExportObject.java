/**
 * 
 */
package com.shiva.aliensOnEarth.service;

import com.shiva.aliensOnEarth.enums.ExportType;
import com.shiva.aliensOnEarth.exception.InvalidExportFormat;

/**
 * Class which retuns the Respective Formatter object
 * 
 * @author ashiva
 *
 */
public class ExportObject {

	/**
	 * Returns the respective formatter object.
	 * @param exportOption
	 * @return
	 */
	public static Export getExportType(int exportOption){
		try{
			Class<? extends Export> exportClass = null;
			for(ExportType exportType : ExportType.values()){
				if(exportType.ordinal() == exportOption-1){
					exportClass = exportType.getClazz();
					break;
				}
			}
			if(exportClass == null){
				throw new InvalidExportFormat("Please provide the values from the available format");
			}
			/**
			 * We are creating the respective formatter object dynamically.
			 */
			return (Export) exportClass.newInstance();
			
		} catch (InstantiationException | IllegalAccessException e) {
			throw new InvalidExportFormat("Please provide the values from the available format");
		}
	}
}

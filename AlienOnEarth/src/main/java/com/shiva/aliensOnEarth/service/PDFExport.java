/**
 * 
 */
package com.shiva.aliensOnEarth.service;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileStore;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.shiva.aliensOnEarth.entity.Alien;
import com.shiva.aliensOnEarth.enums.AliensHeader;
import com.shiva.aliensOnEarth.exception.InvalidPathException;
import com.shiva.aliensOnEarth.exception.MemoryUsageException;

/**
 * Exports the data into PDFFormat.
 * Note : It internally uses itextpdf creator classes, so you need to add external jar 'itextpdf-5.4.3.jar' to export in this format.
 * @author Shiva
 *
 */
public class PDFExport extends Export{

	@Override
	protected boolean writeAfterValidation(File file, List<Alien> aliens) {
		
		Document documento = null;		
		try{		    

			PdfPTable headerTable = getAlienHeadingAsPDFHeaderRow();  

			PdfPTable dataTable = getAlienDetailsAsPDFTable(aliens);

			File file1;
			if(file.getAbsolutePath().endsWith(".pdf")){
				file1 = new File(file.getAbsolutePath());
			}else{
				file1 = new File(file.getAbsolutePath() + ".pdf");
			}
			FileStore fileStore = getStorageStats(file.getAbsolutePath());
			if(fileStore ==  null){
				throw new InvalidPathException("Path provided by you not available in the current system. Please provide other.");
			}			
			if(fileStore.getUsableSpace() <= fileStore.getTotalSpace() && fileStore.getUsableSpace() >= 1024){
				synchronized (file1) {
					if(fileStore.getUsableSpace() <= fileStore.getUsableSpace() && fileStore.getUsableSpace() >= 1024){
						documento = new Document();	     
						PdfWriter.getInstance(documento, new FileOutputStream(file1));    
						documento.open(); 
						documento.add(headerTable);
						documento.add(dataTable);
					}else{
						throw new MemoryUsageException("There is no enough space in the specifed path.Please delete some files or provide alternate path");
					}
				}
			}else{
				throw new MemoryUsageException("There is no enough space in the specifed path.Please delete some files or provide alternate path");
			}


		}catch(IOException e){
			
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}finally{
			if(documento != null){
				documento.close();	
			}
		}
		return false;
	}

	/**	 
	 * Creates the header row of the Alien Details
	 * @return
	 */
	private PdfPTable getAlienHeadingAsPDFHeaderRow() {
		
		Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);		
	    PdfPTable headerTable = new PdfPTable(AliensHeader.values().length);	
	    
	    for (AliensHeader header: AliensHeader.values()) {
	        Phrase frase = new Phrase(header.toString(), fontHeader);
	        PdfPCell cell = new PdfPCell(frase);
	        cell.setBackgroundColor(new BaseColor(Color.lightGray.getRGB()));
	        headerTable.addCell(cell);
	    }
	    
		return headerTable;
	}
	
	/**
	 * Creates the Pdf Body details.
	 * @param aliens
	 * @return
	 */
	private PdfPTable getAlienDetailsAsPDFTable(List<Alien> aliens) {
		
		Font fontBody = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);		
	    PdfPTable dataTable = new PdfPTable(AliensHeader.values().length);	  
	    
	    for(Alien alien : aliens){
	    	dataTable.addCell(new Phrase(alien.getCodeName(), fontBody));
	    	dataTable.addCell(new Phrase(alien.getBloodColor().toString(), fontBody));
	    	dataTable.addCell(new Phrase(alien.getNumberOfAntennas().toString(), fontBody) );
	    	dataTable.addCell(new Phrase(alien.getNumberOfLegs().toString() , fontBody));
	    	dataTable.addCell(new Phrase(alien.getHomePlanet().toString(), fontBody) );			
		}
	    
		return dataTable;
	}

}

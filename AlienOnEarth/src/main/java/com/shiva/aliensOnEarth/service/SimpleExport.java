package com.shiva.aliensOnEarth.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.shiva.aliensOnEarth.entity.Alien;
import com.shiva.aliensOnEarth.enums.AliensHeader;
import com.shiva.aliensOnEarth.exception.InvalidPathException;
import com.shiva.aliensOnEarth.exception.MemoryUsageException;

/**
 * Exports the data into simple text format.
 * @author Shiva
 *
 */
public class SimpleExport extends Export{

	/**
	 * Adds the space at the end of the string.
	 */
	private Function<Integer , String> getSpaces = length -> {
		StringBuilder build= new StringBuilder(); 
		for(int i = 0 ; i < length + 5 ;i++){build.append(" "); } 
		return build.toString();
	};
	
	/**
	 * Returns the length of the number.
	 */
	private UnaryOperator<Integer> getNumberLenght = number -> {
		int count = 0; 
		while(number > 0){ number = number /10;count++;} 
		return count;
	};

	/**
	 * Writes the data into Text file
	 */
	@Override
	protected boolean writeAfterValidation(File file, List<Alien> aliens) {
		BufferedWriter writer = null;
		try {	
			
			String dataTobeWritten = getData(aliens);
			
			File file1;
			if(file.getAbsolutePath().endsWith(".txt")){
				file1 = new File(file.getAbsolutePath());
			}else{
				file1 = new File(file.getAbsolutePath() + ".txt");
			}			
			
			FileStore fileStore = getStorageStats(file.getAbsolutePath());
			if(fileStore ==  null){
				throw new InvalidPathException("Path provided by you not available in the current system. Please provide other.");
			}			
			if(fileStore.getUsableSpace() <= fileStore.getTotalSpace() && fileStore.getUsableSpace() >= 1024){
				synchronized (file1) {
					if(fileStore.getUsableSpace() <= fileStore.getUsableSpace() && fileStore.getUsableSpace() >= 1024){
						writer = new BufferedWriter(new FileWriter(file1));	
						writer.write(dataTobeWritten);
					}else{
						throw new MemoryUsageException("There is no enough space in the specifed path.Please delete some files or provide alternate path");
					}
				}
			}else{
				throw new MemoryUsageException("There is no enough space in the specifed path.Please delete some files or provide alternate path");
			}

		} catch (IOException e) {			
			throw new InvalidPathException("Path provided by you not available in the current system. Please provide other.");
		}finally{
			if(writer != null)
				try {
					writer.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
		}
		return false;
	}

	/**
	 * Returns the formated string containing details of Alien, which can be directly written into output file.
	 * @param aliens
	 * @return
	 */
	public String getData(List<Alien> aliens){

		List<Integer> maxLengths = getMaxLenghtOfEachField(aliens);

		StringBuilder buildWriter = new StringBuilder();

		int maxLenthIndex =0;
		for(AliensHeader header : AliensHeader.values()){			
			buildWriter.append(header.toString()
					+ getSpaces.apply(maxLengths.get(maxLenthIndex++) - header.toString().length()));
		}	
		buildWriter.append(System.lineSeparator());
		
		int headerLenght = buildWriter.length();
		for(int i=0 ; i < headerLenght; i++){
			buildWriter.append("_");
		}
		buildWriter.append(System.lineSeparator());		

		for (Alien alien : aliens) {
			
			buildWriter.append(alien.getCodeName()
					+ getSpaces.apply(maxLengths.get(0)- alien.getCodeName().length()));
			
			buildWriter.append(alien.getBloodColor()
					+ getSpaces.apply(maxLengths.get(1)- alien.getBloodColor().length()));
			
			buildWriter.append(alien.getNumberOfAntennas()
					+ getSpaces.apply(maxLengths.get(2) - getNumberLenght.apply(alien.getNumberOfAntennas())));
			
			buildWriter.append(alien.getNumberOfLegs()
					+ getSpaces.apply(maxLengths.get(3) - getNumberLenght.apply(alien.getNumberOfLegs())));
			
			buildWriter.append(alien.getHomePlanet().toString()
					+ getSpaces.apply(maxLengths.get(4)- alien.getHomePlanet().toString().length()));
			
			buildWriter.append(System.lineSeparator());

		}

		return buildWriter.toString();
	}

	/**
	 * Calculates the max length of each field and return the list containing max length of each field.
	 * @param aliens
	 * @return
	 */
	private List<Integer> getMaxLenghtOfEachField(List<Alien> aliens){

		List<Integer> maxLenghts = new ArrayList<Integer>(AliensHeader.values().length);

		for(AliensHeader header : AliensHeader.values()){
			maxLenghts.add(header.toString().length());
		}

		for(int i=0 ; i < aliens.size() ; i++){
			if(aliens.get(i).getCodeName().length() > maxLenghts.get(0)){
				maxLenghts.add(0, aliens.get(i).getCodeName().length());
			}
			if(aliens.get(i).getBloodColor().length() > maxLenghts.get(1)){
				maxLenghts.add(1, aliens.get(i).getBloodColor().length());
			}
			if(getNumberLenght.apply(aliens.get(i).getNumberOfAntennas()) > maxLenghts.get(2)){
				maxLenghts.add(0, aliens.get(i).getCodeName().length());
			}
			if(getNumberLenght.apply(aliens.get(i).getNumberOfLegs()) > maxLenghts.get(3)){
				maxLenghts.add(1, aliens.get(i).getBloodColor().length());
			}
			if(aliens.get(i).getHomePlanet().toString().length() > maxLenghts.get(4)){
				maxLenghts.add(1, aliens.get(i).getBloodColor().length());
			}
			
		}
		return maxLenghts;
	}
}

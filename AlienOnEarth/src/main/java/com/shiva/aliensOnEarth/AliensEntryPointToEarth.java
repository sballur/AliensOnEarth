/**
 * 
 */
package com.shiva.aliensOnEarth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.shiva.aliensOnEarth.entity.Alien;
import com.shiva.aliensOnEarth.enums.ExportType;
import com.shiva.aliensOnEarth.exception.FileAlreadyExistException;
import com.shiva.aliensOnEarth.exception.InvalidExportFormat;
import com.shiva.aliensOnEarth.exception.InvalidInputValue;
import com.shiva.aliensOnEarth.exception.InvalidPathException;
import com.shiva.aliensOnEarth.exception.MemoryUsageException;
import com.shiva.aliensOnEarth.service.Export;
import com.shiva.aliensOnEarth.service.ExportObject;
import com.shiva.aliensOnEarth.validator.AlienValidator;

/**
 * This is main class from where application should will start.
 * @author Shiva
 *
 */
public class AliensEntryPointToEarth {

	static String filePath = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to AliensOnEarth Application");			
		try {
			BufferedReader readConsoleInput = new BufferedReader(new InputStreamReader(System.in));
			int userChoice = 0;
			List<Alien> aliens = new ArrayList<Alien>();
			while(true){
				
				System.out.println("Kinldy Select the below options ");
				System.out.println("1 : For insertion of new Alien details.");
				System.out.println("2 : To export the Alien details");
				System.out.println("3 or Anything : To Exit from the application");
				
				try{
					System.out.print("Your Option : "); 
					userChoice = Integer.parseInt(readConsoleInput.readLine());					
				}catch(NumberFormatException e){
					System.out.print("Are you sure you want to exit Y/N:[N] : ");					
					if("Y".equalsIgnoreCase(readConsoleInput.readLine())){
						break;
					}
				}
				if(userChoice == 1){			
					
					aliens.add(provideAlienDetails(readConsoleInput));
				
				}else if(userChoice == 2){	
					
					if(aliens == null || aliens.size() == 0){
						System.out.println("You don't have anything to export");
					}else{
						exportAlienDetails(readConsoleInput, aliens);
						
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Takes the input from the user and creates the Alien Object
	 * @param readConsoleInput
	 * @return
	 * @throws IOException
	 */

	private static Alien provideAlienDetails(BufferedReader readConsoleInput)
			throws IOException {		 
		System.out.println("Please Enter the details below");
		Alien newalien = new Alien();
		System.out.print("Code Name: "); 
		newalien.setCodeName(readConsoleInput.readLine());		
		while(true){
			try{
				System.out.print("Blood Color: "); 
				newalien.setBloodColor(AlienValidator.validateBloodColor(readConsoleInput.readLine()));
				break;
			}catch(InvalidInputValue e){
				System.out.println("Please provide valid value"); 
			}
		}
		
		while(true){
			try{
				System.out.print("No Of Antennas: "); 
				newalien.setNumberOfAntennas(Integer.parseInt(readConsoleInput.readLine()));
				break;
			}catch(NumberFormatException e){
				System.out.println("Please provide valid value"); 
			}
		}
		while(true){
			try{
				System.out.print("No Of Legs: "); 
				newalien.setNumberOfLegs(Integer.parseInt(readConsoleInput.readLine()));
				break;
			}catch(NumberFormatException e){
				System.out.println("Please provide valid value"); 
			}
		}			
		while(true){
			try{
				System.out.print("Home Planet: "); 
				newalien.setHomePlanet(AlienValidator.validateHomePlanet(readConsoleInput.readLine()));
				break;
			}catch(InvalidInputValue e){
				System.out.println("Please provide valid value"); 
			}
		}		
		return newalien;
	}
	
	/**
	 * Exports the details into specified format.
	 * @param readConsoleInput
	 * @param aliens
	 * @throws IOException
	 */
	private static void exportAlienDetails(BufferedReader readConsoleInput,
			List<Alien> aliens) throws IOException {		
		int exportChoice = 0;
		Export export = null;
		String fileName = null;
		String overWriteChoice = null;
		boolean overWriteFile = false;
		
		while(true){
			try{
				System.out.println("Kinldy Select the below format of Export");
				int exportOption = 0;	
				for(ExportType exportType : ExportType.values()){
					System.out.println(++exportOption +" : To export into " + exportType +" format");
				}
				System.out.print("Your Option : "); 
				exportChoice = Integer.parseInt(readConsoleInput.readLine());		
				export = ExportObject.getExportType(exportChoice);
				break;
			}catch(NumberFormatException | InvalidExportFormat e){
				System.out.println("Sorry! application doesnot support the spefied option format.");

			}			
		}

		while(true){
			try {
				if(filePath == null){
					System.out.print("Please provide the absolute path : ");
					filePath = readConsoleInput.readLine();
				}
				if(fileName == null){
					System.out.print("Please provide the file name : ");
					fileName = readConsoleInput.readLine();
				}				
				export.write(filePath+"\\"+fileName,overWriteFile, aliens);
				System.out.println("***************************************");
				System.out.println("Exported the details successfully.");
				System.out.println("***************************************");
				aliens.clear();
				break;
				
			} catch (FileAlreadyExistException e) {
				
				System.out.println(e.getMessage());
				System.out.print("Do you want to overwrite the file?Y/N :{N] ");
				overWriteChoice = readConsoleInput.readLine();
				
				if("Y".equalsIgnoreCase(overWriteChoice)){
					overWriteFile =  true;					
				}else{
					System.out.print("Please provide other file name : ");
					fileName = readConsoleInput.readLine();
				}				
			} catch (InvalidPathException e) {
				
				System.out.println(e.getMessage());
				System.out.print("Please provide the valid absolute path : ");
				filePath = readConsoleInput.readLine();
				
			} catch (MemoryUsageException e) {
				
				System.out.println(e.getMessage());
				System.out.print("Please provide the absolute path where there is memory : ");
				filePath = readConsoleInput.readLine();
				
			}catch (IOException e) {
				
				System.out.println(e.getMessage());
			}
		}

	}

}

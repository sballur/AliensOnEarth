package com.shiva.aliensOnEarth.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.shiva.aliensOnEarth.entity.Alien;
import com.shiva.aliensOnEarth.exception.FileAlreadyExistException;
import com.shiva.aliensOnEarth.exception.InvalidPathException;
import com.shiva.aliensOnEarth.exception.MemoryUsageException;

/**
 * Abstract class which is exposed to the application developer to export the Alien details.
 * @author Shiva
 *
 */
public abstract class Export {
	
	/**
	 * Call this method to write the data into specified file format, if the file already exists then this method throws
	 * FileAlreadyExistException exception
	 * @param path
	 * @param aliens
	 * @return
	 * @throws FileAlreadyExistException
	 * @throws InvalidPathException
	 * @throws IOException
	 */
	final public boolean  write(String path,List<Alien> aliens) 
			throws FileAlreadyExistException, InvalidPathException, IOException{
		
		return write(path,false,aliens);
		 
	}
	
	/**
	 * * Call this method to write the data into specified file format, if the file already exists then this method throws
	 * FileAlreadyExistException exception based the {@code overWriteFile} flag
	 * @param path
	 * @param overWriteFile
	 * @param aliens
	 * @return
	 * @throws FileAlreadyExistException
	 * @throws InvalidPathException
	 * @throws IOException
	 * @throws MemoryUsageException
	 */
	final public boolean  write(String path,boolean overWriteFile,List<Alien> aliens) 
			throws FileAlreadyExistException, InvalidPathException, IOException,MemoryUsageException{
		
		File file = validatePathAndCreateDirectories(path,overWriteFile);
		
		return writeAfterValidation(file,aliens);
	}
	
	/**
	 * This method validates the path, file,permission, required spaces is available or not.
	 * @param path
	 * @param overWriteFile
	 * @return
	 * @throws FileAlreadyExistException
	 * @throws InvalidPathException
	 * @throws IOException
	 */
	private File validatePathAndCreateDirectories(String path,boolean overWriteFile) 
			throws FileAlreadyExistException,InvalidPathException,IOException{

		File file = null;
		
		try{
			file = new File(path.substring(0, path.lastIndexOf("/")));

			file.setWritable(true);
			
			if(!file.isAbsolute()){
				//throw new InvalidPathException("Invalid path please specify the valid path");
			}
			
			if(!file.exists()){				
				file.mkdirs();	
			}
			file.createNewFile();			
		}catch(IOException e){
			throw new InvalidPathException("Invalid path please specify the valid path");
		}catch(SecurityException s){
			throw new SecurityException("For the specfied path access is denied.Please provide the permission or change the path");
		}catch(StringIndexOutOfBoundsException e){
			throw new InvalidPathException("Invalid path please specify the valid path");
		}
		
		if(!file.canWrite()){
			throw new SecurityException("For the specfied path access is denied.Please provide the permission or change the path");
		}
		
		FileStore fileStore = getStorageStats(file.getAbsolutePath());
		if(fileStore ==  null){
			throw new InvalidPathException("Path provided by you not available in the current system. Please provide other.");
		}
		if(fileStore.getUsableSpace() > fileStore.getTotalSpace() || fileStore.getUsableSpace() < 1024){
			throw new MemoryUsageException("There is no enough space in the specifed path.Please delete some files or provide alternate path");
		}
		
		file = new File(path);
		file.setWritable(true);
		if(file.exists() && !overWriteFile){
			throw new FileAlreadyExistException("Specified file alreday exist.");
		}
		
		return file;
	}	
	
	/**
	 * Return the FileStore object of the specified path if present else return null.
	 * @param absolutepath
	 * @return
	 */
	protected FileStore getStorageStats(String absolutepath){
		FileStore fileStore = null;
		try {
			String rootDirectory = absolutepath.split(":")[0];
			for (Path root : FileSystems.getDefault().getRootDirectories())
			{
				if(root.getRoot().toString().contains(rootDirectory)){
					fileStore = Files.getFileStore(root);
					break;
				} 
			}

		}catch (IOException e) {
			throw new InvalidPathException("Please provide the valid path");
		}
		return fileStore;
	}
	/**
	 * Based on the Child class implementation this method extracts the data into required format. 
	 * @param file
	 * @param aliens
	 * @return
	 */
	abstract protected boolean writeAfterValidation(File file,List<Alien> aliens);
}



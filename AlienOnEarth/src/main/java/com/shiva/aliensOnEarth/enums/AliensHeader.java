/**
 * 
 */
package com.shiva.aliensOnEarth.enums;

/**
 * Header Columns to be displayed while exporting the data.
 * @author Shiva
 * Note : Need to Synch up with Alien Class.
 */
public enum AliensHeader {
	
	CodeName("Code Name"),
	BloodColor("Blood Color"),
	NumberOfAntennas("No. of Antennas"),
	NumberOfLegs("No. of Legs"),
	HomePlanet("Home Planet");
	
	private String displayName;
		
	AliensHeader(String displayName){
		this.displayName = displayName;
	}
	
	@Override
	public String toString() {		
		return this.displayName;
	}

}

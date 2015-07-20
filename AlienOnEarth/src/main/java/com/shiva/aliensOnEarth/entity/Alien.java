/**
 * 
 */
package com.shiva.aliensOnEarth.entity;

import com.shiva.aliensOnEarth.enums.Planet;

/**
 * @author Shiva
 *
 */
public class Alien {

	private String codeName;
	
	private String bloodColor;
	
	private Integer numberOfAntennas;
	
	private Integer numberOfLegs;
	
	private Planet homePlanet;

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getBloodColor() {
		return bloodColor;
	}

	public void setBloodColor(String bloodColor) {
		this.bloodColor = bloodColor;
	}

	public Integer getNumberOfAntennas() {
		return numberOfAntennas;
	}

	public void setNumberOfAntennas(int numberOfAntennas) {
		this.numberOfAntennas = numberOfAntennas;
	}

	public Integer getNumberOfLegs() {
		return numberOfLegs;
	}

	public void setNumberOfLegs(int numberOfLegs) {
		this.numberOfLegs = numberOfLegs;
	}

	public Planet getHomePlanet() {
		return homePlanet;
	}

	public void setHomePlanet(Planet homePlanet) {
		this.homePlanet = homePlanet;
	}
	
	
}

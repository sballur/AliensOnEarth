/**
 * 
 */
package com.shiva.aliensOnEarth.validator;

import java.lang.reflect.Field;

import com.shiva.aliensOnEarth.enums.Planet;
import com.shiva.aliensOnEarth.exception.InvalidInputValue;

/**
 * Validates the Alien details such as Color and Planet.
 * @author Shiva
 *
 */
public class AlienValidator {
	
	/**
	 * validates the color, throws InvalidInputValue if the color is invalid.
	 * @param bloodColor
	 * @return
	 */
	public static String validateBloodColor(String bloodColor){
		try {
			Field field = Class.forName("java.awt.Color").getField(bloodColor.toUpperCase());
			if(field == null){
				throw new InvalidInputValue();
			}
		} catch (NoSuchFieldException e) {
			throw new InvalidInputValue();			
		} catch (SecurityException e) {			
		} catch (ClassNotFoundException e) {
			throw new InvalidInputValue();	
		}		
		return bloodColor;		
	}
	
	/**
	 * validates the Planet, throws InvalidInputValue if the planet is invalid.
	 * @param planet
	 * @return
	 */
	public static Planet validateHomePlanet(String planet){
		Planet homePlanet = null;
		try{
			homePlanet = Planet.valueOf(planet.toUpperCase());
			if(homePlanet == null){
				throw new InvalidInputValue();
			}
		}catch(IllegalArgumentException e){
			throw new InvalidInputValue();
		}
		return homePlanet;
		
	}

}

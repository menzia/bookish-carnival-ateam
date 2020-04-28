/*
 * FarmLandADT.java
 * 
 * Author: Alex Menzia,Linyi Lyu, Ethan Huang
 * Date: 4/20/2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001/001/002
 *
 * Other Credits: n/a
 *
 * Known Bugs: n/a
 */
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * FarmLandADT - Stores and allows access to the weight data
 * for a collection of farms. Also has methods for generating tables
 * displaying certain parts of the data.
 * 
 * @author menzia (2020)
 *
 */
public interface FarmLandADT {

	/**
	 * Return the FarmADT object with the given farm ID.
	 * If no such object exists, returns null
	 * 
	 * @param farmID ID to look for within data structure
	 * @return FarmADT object with given farmID
	 */
	public FarmADT getFarm(String farmID);
	
	/**
	 * Returns true if the data structure contains a farm
	 * with the given ID, and false otherwise
	 * 
	 * @param farmID to look for in structure
	 * @return true if a farm has the ID, false otherwise
	 */
	public boolean contains(String farmID);
	
	/**
	 * Return the total number of farms in this data structure
	 * 
	 * @return total number of farms
	 */
	public int size();
	
	/**
	 * Add a farm to the data structure with the given ID
	 * 
	 * @param farmID of farm to add to structure
	 */
	public void addFarm(String farmID);
	
	/**
	 * Set the daily weight for the given farm and the given date to the given weight.
	 * If the given ID is not in the structure, adds it to structure first.
	 * @param farmID of farm to set weight of
	 * @param weight to set the daily weight to
	 * @param yearNum of date to reset
	 * @param monthNum of date to reset
	 * @param dayNum of date to reset
	 */
	public void setDailyWeight(String farmID, int weight, int yearNum, int monthNum, int dayNum);	
	
	public void processDirectory(File dir) throws FileNotFoundException, DataFormatException;
	
	public void exportToDirectory(File dir) throws FileNotFoundException, IOException;
	
	
}

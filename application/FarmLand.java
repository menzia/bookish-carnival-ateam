/**
 * FarmLand.java created by alexm on Dell Inspiron in ATeam.
 *
 * Author: Alex Menzia
 * Date: @date
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001
 *
 * IDE: Eclipse IDE for Java Developers
 * Version: 2019-12 (4.14.0)
 * Build id: 20191212-1212
 *
 * Device: MENZIA-DELLINSPIRON
 * OS: Windows 10 Home
 * Version: 1903
 * OS Build: 18362.592
 *
 * List Collaborators: Name, email@wisc.edu, lecture number
 *
 * Other Credits: describe other sources(websites or people)
 *
 * Known Bugs: describe known unsolved bugs
 */
package application;

import java.io.FileNotFoundException;
import java.util.TreeMap;
import java.util.zip.DataFormatException;

import javafx.scene.control.TableView;

/**
 * FarmLand - Stores and allows access to the weight data
 * for a collection of farms. Also has methods for generating tables
 * displaying certain parts of the data.
 * 
 * @author menzia (2020)
 *
 */
public class FarmLand implements FarmLandADT {
	private TreeMap<String, Farm> farms;//where data on individual farms is stored
	private Farm allFarms;//where data on the combined weight of all farms is stored

	/**
	 * Constructs a new, empty,  FarmLand object
	 */
	public FarmLand() {
		farms = new TreeMap<String,Farm>();
		allFarms = new Farm("Stores Weight of All Farms in System");
	}
	
	@Override
	/**
	 * Return the FarmADT object with the given farm ID.
	 * If no such object exists, returns null
	 * 
	 * @param farmID ID to look for within data structure
	 * @return FarmADT object with given farmID
	 */
	public Farm getFarm(String farmID) {
		return farms.get(farmID);
	}

	@Override
	/**
	 * Returns true if the data structure contains a farm
	 * with the given ID, and false otherwise
	 * 
	 * @param farmID to look for in structure
	 * @return true if a farm has the ID, false otherwise
	 */
	public boolean contains(String farmID) {
		return farms.containsKey(farmID);
	}

	@Override
	/**
	 * Return the total number of farms in this data structure
	 * 
	 * @return total number of farms
	 */
	public int size() {
		return farms.size();
	}

	@Override
	/**
	 * Add a farm to the data structure with the given ID. If the
	 * given ID is already in the structure, then does nothing.
	 * 
	 * @param farmID of farm to add to structure
	 */
	public void addFarm(String farmID) {
		if (!this.contains(farmID)) {
			
			farms.put(farmID, new Farm(farmID));
			
			
		}

	}
	
	@Override
	/**
	 * Set the daily weight for the given farm and the given date to the given weight.
	 * If the given ID is not in the structure, adds it to structure first.
	 * @param farmID of farm to set weight of
	 * @param weight to set the daily weight to
	 * @param yearNum of date to reset
	 * @param monthNum of date to reset
	 * @param dayNum of date to reset
	 */
	public void setDailyWeight(String farmID, int weight, int yearNum, int monthNum, int dayNum) {
		addFarm(farmID);//make sure farmID is in dataStructure
		
		//set daily weight to the desired weight for that farm
		int prevWeight = farms.get(farmID).setDailyWeight(weight, yearNum, monthNum, dayNum);
		
		//calculate amount that the weight has changed
		int weightChange = weight - prevWeight;
		
		//account for weight change in allFarms
		allFarms.addToDailyWeight(weightChange,yearNum,monthNum,dayNum);
	}

	@Override
	/**
	 * Returns a Farm Report in the form of the table for the given farm
	 * and the given year.
	 * 
	 * TODO:May change TableView to something else depending on what 
	 * is desired for GUI
	 * 
	 * @param farmID of farm to get report on
	 * @param yearNum to get report on
	 * @return table of the desired report
	 */
	public TableView getFarmReport(String farmID, int yearNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Returns an Annual Report on this collection of farms for the
	 * given year
	 * 
	 * @param yearNum of year to return report on
	 * @return table of the desired report
	 */
	public TableView getAnnualReport(int yearNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Returns a Monthly Report on this collection of farms for the
	 * given month
	 * 
	 * @param yearNum of month to get report on
	 * @param monthNum of month to get report on
	 * @return table of the desired report
	 */
	public TableView getMonthlyReport(int yearNum, int monthNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Returns a Range Report on this collection of farms for the
	 * given range, inclusive on both ends
	 * @param yearNum of both dates in range
	 * @param startMonthNum month number of start date
	 * @param startDayNum day number of start date
	 * @param endMonthNum month number of end date
	 * @param endDayNum month number of start date
	 * @return table of desired report
	 */
	public TableView getRangeReport(int yearNum, int startMonthNum, int startDayNum, int endMonthNum, int endDayNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processFile(String fileName) throws FileNotFoundException, DataFormatException {
		// TODO

	}

	

}

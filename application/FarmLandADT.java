/**
 * FarmLandADT.java created by alexm on Dell Inspiron in ATeam.
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
import java.util.zip.DataFormatException;
import javafx.scene.control.TableView;

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
	public TableView getFarmReport(String farmID, int yearNum);
	
	/**
	 * Returns an Annual Report on this collection of farms for the
	 * given year
	 * 
	 * @param yearNum of year to return report on
	 * @return table of the desired report
	 */
	public TableView getAnnualReport(int yearNum);
	
	/**
	 * Returns a Monthly Report on this collection of farms for the
	 * given month
	 * 
	 * @param yearNum of month to get report on
	 * @param monthNum of month to get report on
	 * @return table of the desired report
	 */
	public TableView getMonthlyReport(int yearNum,int monthNum);
	
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
	public TableView getRangeReport(int yearNum, int startMonthNum, int startDayNum,
										int endMonthNum, int endDayNum);
	
	
	public void processFile(String fileName) throws FileNotFoundException, DataFormatException;
	
	
}

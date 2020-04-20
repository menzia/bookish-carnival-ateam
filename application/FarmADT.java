/**
 * FarmADT.java
 * 
 * Author: Alex Menzia, Linyi Lyu, Ethan Huang
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
import javafx.scene.control.TableView;

/**
 * FarmADT - Stores milk data for a given farm across an arbitrary number of years and
 * allows access to it. Also allows access to an ID string which identifies this farm
 * among a collection of other farms. Uses MonthADT and YearADT.
 * 
 * @author menzia (2020)
 *
 */
public interface FarmADT {
	/**
	 * Returns the Id of this farm
	 * @return a String which is the ID of this farm
	 */
	public String getId();
	
	/**
	 * Return the YearADT object corresponding to the given year number at this farm.
	 * If no data is stored for that year, returns null
	 * 
	 * @return YearADT object corresponding to the given year number.
	 */
	public YearADT getYear(int yearNum);
	
	/** Returns the MonthADT object corresponding to the given year number and month
	 * number at this farm. If no data is stored for this month, returns null
	 * @param yearNum of month object to return
	 * @param monthNum of month object to return
	 * @return MonthADT object corresponding to the desired month
	 */
	public MonthADT getMonth(int yearNum, int monthNum);
	
	/**
	 * Returns the total weight associated to this farm in the given year
	 *  
	 * @param yearNum of year to return total weight of
	 * @return total weight stored in the given year
	 */
	public int getYearTotal(int yearNum);
	
	/**
	 * Returns the total weight associated to this farm in the given month
	 * 
	 * @param yearNum of month to return total weight of
	 * @param monthNum of month to return total weight of
	 * @return total weight stored in the given month
	 */
	public int getMonthTotal(int yearNum, int monthNum);
	
	/**
	 * Returns the total weight associated to this farm in the range
	 * starting at the YYYY/MM/DD given by the first three parameters
	 * to the MM/DD with the same year given by the last two parameters.
	 * This range is inclusive at both ends.
	 * 
	 * @param startYear number of year
	 * @param startMonth month number for start date
	 * @param startDay day number for start date
	 * @param endMonth month number for end date
	 * @param endDay day number for end date
	 * @return total weight stored in this range
	 */
	public int getRange(int startYear,int startMonth,int startDay,
			int endMonth, int endDay);
	
	/**
	 * Set the daily weight for this farm and the given date to the given weight
	 * @param weight to set the daily weight to
	 * @param yearNum of date to reset
	 * @param monthNum of date to reset
	 * @param dayNum of date to reset
	 * @return the previous weight stored at that date
	 */
	public int setDailyWeight(int weight, int yearNum, int monthNum, int dayNum);
	
	/**
	 * Set the daily weight for this farm and the given weight to zero
	 * @param yearNum of date to reset
	 * @param monthNum of date to reset
	 * @param dayNum of date to reset
	 * @return the previous weight stored at that date
	 */
	public int clearDailyWeight(int yearNum, int monthNum, int dayNum);
	
	/**
	 * Returns a Farm Report in the form of the table for this farm
	 * and the given year.
	 * 
	 * TODO:May change TableView to something else depending on what 
	 * is desired for GUI
	 * 
	 * @param yearNum to get report on
	 * @return table of the desired report
	 */
	public TableView getFarmReport(int yearNum);
}

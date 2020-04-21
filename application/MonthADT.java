/**
 * MonthADT.java
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

/**
 * MonthADT - Stores the weight of milk obtained on each day of
 * a given month.
 * 
 * TODO:May want to add access to month number and year number
 * 
 * @author menzia (2020)
 *
 */
public interface MonthADT {
	
	/**
	 * Returns the number of days in the month
	 * @return number of days
	 */
	public int size();
	
	/**
	 * Returns the sum of all the daily weights in this month
	 * @return sum of all weights
	 */
	public int totalWeight();
	
	/**
	 * Sets the weight for the given day of the month to the given weight,
	 * then returns the previous weight.
	 * 
	 * @param weight to set daily weight to
	 * @param dayNum to set daily weight of
	 * @return the previous weight of that day
	 */
	public int set(int weight, int dayNum);
	
	/**
	 * Returns the weight for the given day of this month
	 * @param dayNum to return weight of
	 * @return weight of given day
	 */
	public int get(int dayNum);
	
	/**
	 * Returns the total amount of weight from the start day given
	 * to the end day given, inclusive on both sides.
	 * @param dayNumStart day to start count of milk weight
	 * @param dayNumEnd day to end count of milk weight
	 * @return total amount of milk from start date to end date
	 */
	public int getRange(int dayNumStart, int dayNumEnd);
	
	/**
	 * Returns the total amount of weight from the start day given
	 * to the end of the month
	 * @param dayNumStart day to start count of milk weight
	 * @return total amount of milk from start date to end of the month
	 */
	public int getUpperRange(int dayNumStart);
	
	/**
	 * Returns the total amount of weight from the beginning of the month
	 * to the end day given, inclusive on both sides.
	 * @param dayNumEnd day to end summation on
	 * @return total amount of milk from start of month to the end date
	 */
	public int getLowerRange(int dayNumEnd);
	
	/**
	 * Sets the weight for the given day to 0 and returns
	 * the previous weight.
	 * 
	 * @param dayNum to set daily weight of to 0
	 * @return the previous weight in the given day
	 */
	public int clear(int dayNum);

}
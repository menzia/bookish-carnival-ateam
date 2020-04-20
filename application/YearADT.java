/*
 * Author: Alex Menzia, Linyi Lyu, Ethan Huang
 * Date: 4/20/2020
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
 * List Collaborators: n/a
 *
 * Other Credits: n/a
 *
 * Known Bugs: n/a
 */
package application;

/**
 * YearADT - Stores and allows access to milk weight data for every
 * day in a year. Uses the MonthADT.java interface
 * 
 * TODO: May want to add access to the year number if needed
 * 
 * @author menzia (2020)
 *
 */
public interface YearADT {
	
	/**
	 * Returns the total weight of milk across the entire year
	 * @return total milk weight
	 */
	public int totalWeight();
	
	/**
	 * Returns the MonthADT object with the given month number
	 * @param monthNum month number to return month of
	 * @return MonthADT object of corresponding month number
	 */
	public MonthADT getMonth(int monthNum);
	
	/**
	 * Returns the maximum weight of a month within this year
	 * @return max total weight of a month in this year
	 */
	public int maxMonthlyWeight();
	
	/**
	 * Returns the minimum weight of a month within this year
	 * @return max total weight of a month in this year
	 */
	public int minMonthlyWeight();
	
	/**
	 * Returns the average monthly weight for this year
	 * @return average monthly weight in this year
	 */
	public int avgMonthWeight();
	
	/**
	 * Returns the total amount of weight stored from the given starting date
	 * to the given ending date, inclusive on both sides.
	 * @param startMonth month number of start date
	 * @param startDay day number of start date
	 * @param endMonth month number of end date
	 * @param endDay day number of end date
	 * @return total milk weight within the given range
	 */
	public int getRange(int startMonth,int startDay, int endMonth, int endDay);
	
	/**
	 * Set the daily weight for this year and the given date to the given weight
	 * @param weight to set the daily weight to
	 * @param monthNum of date to reset
	 * @param dayNum of date to reset
	 * @return the previous weight stored at that date
	 */
	public int setDailyWeight(int weight, int monthNum, int dayNum);
	
	/**
	 * Set the daily weight for this year and the given weight to zero
	 * @param monthNum of date to reset
	 * @param dayNum of date to reset
	 * @return the previous weight stored at that date
	 */
	public int clearDailyWeight(int monthNum, int dayNum);

}

/**
 * Farm.java created by alexm on Dell Inspiron in ATeam.
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

import java.util.TreeMap;

import javafx.scene.control.TableView;

/**
 * Farm - TODO Describe the purpose of this user-defined type
 * 
 * @author menzia (2020)
 *
 */
public class Farm implements FarmADT {
	
	private String farmId;//String uniquely representing this farm
	private TreeMap<Integer, Year> years;//Tree map storing the years associated to this farm

	/**
	 * Construct a new Farm with the given Id and an empty TreeMap of years.
	 * 
	 * @param farmId to associate with Farm object
	 */
	public Farm(String farmId) {
		this.farmId = farmId;
		this.years = new TreeMap<Integer, Year>();
	}

	@Override
	/**
	 * Get the Id associated to this farm
	 * 
	 * @return farmId of this farm
	 */
	public String getId() {
		return farmId;
	}

	@Override
	/**
	 * Return the YearADT object corresponding to the given year number at this farm.
	 * If no data is stored for that year, returns null
	 * 
	 * @return YearADT object corresponding to the given year number.
	 */
	public YearADT getYear(int yearNum) {
		if (years.containsKey(yearNum)) {
			return years.get(yearNum);
		} else {
			// may want to change to return null
			// if this construction is too problematic
			return null;
		}
	}

	@Override
	/** Returns the MonthADT object corresponding to the given year number and month
	 * number at this farm. If no data is stored for this month, returns null
	 * @param yearNum of month object to return
	 * @param monthNum of month object to return
	 * @return MonthADT object corresponding to the desired month
	 */
	public MonthADT getMonth(int yearNum, int monthNum) {
		if (years.containsKey(yearNum)) {
			return years.get(yearNum).getMonth(monthNum);
		} else {
			return null;
		}
	}

	@Override
	/**
	 * Returns the total weight associated to this farm in the given year
	 *  
	 * @param yearNum of year to return total weight of
	 * @return total weight stored in the given year
	 */
	public int getYearTotal(int yearNum) {
		if (years.containsKey(yearNum)) {
			return years.get(yearNum).totalWeight();
		} else {
			return 0;
		}
	}

	@Override
	/**
	 * Returns the total weight associated to this farm in the given month
	 * 
	 * @param yearNum of month to return total weight of
	 * @param monthNum of month to return total weight of
	 * @return total weight stored in the given month
	 */
	public int getMonthTotal(int yearNum, int monthNum) {
		if (years.containsKey(yearNum)) {
			return years.get(yearNum).getMonth(monthNum).totalWeight();

		} else {
			return 0;
		}
	}

	@Override
	/**
	 * Returns the total weight associated to this farm in the range
	 * starting at the YYYY/MM/DD given by the first three parameters
	 * to the YYYY/MM/DD given by the last three.
	 * This range is inclusive at both ends.
	 * 
	 * @param startYear number of year
	 * @param startMonth month number for start date
	 * @param startDay day number for start date
	 * @param endMonth month number for end date
	 * @param endDay day number for end date
	 * @return total weight stored in this range
	 */
	public int getRange(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
		//if the start year is after the end year, return 0
		if (startYear > endYear) {
			return 0;

			//if the start year and end year are the same, use the range method in Year class
		} else if (startYear == endYear) {

			if (years.containsKey(startYear)) {
				return years.get(startYear).getRange(startMonth, startDay, endMonth, endDay);
			} else {
				return 0;
			}

			//otherwise, add up the weight in the first year, the weight between the two years,
			//and the weight in the last year
		} else {
			int startWeight = getWeightAfter(startYear,startMonth,startDay);
			int betweenWeight = getWeightBetween(startYear, endYear);
			int endWeight = getWeightBefore(endYear,endMonth,endDay);
			
			return (startWeight + betweenWeight + endWeight);			
			
		}
	}

	/**
	 * Returns the total amount of weight between the starting year and the ending year,
	 * not including either of the endpoints.
	 * 
	 * @param startYear year at left endpoint of interval
	 * @param endYear year at right endpoint of interval
	 * @return total weight between the start year and end year, non-inclusive
	 */
	private int getWeightBetween(int startYear,int endYear) {
		int rangeWeight = 0;
		for (int i = startYear+1; i < endYear; ++i) {
			rangeWeight += getYearTotal(i);
		}
		
		return rangeWeight;
	}

	/**
	 * Returns the total weight from the date given to the end of that year,
	 * including the given date.
	 * 
	 * @param yearNum of date to start at
	 * @param monthNum of date to start at
	 * @param dayNum of date to start at
	 * @return total weight from starting date to end of year
	 */
	private int getWeightAfter(int yearNum, int monthNum, int dayNum) {
		if (years.containsKey(yearNum)) {
			// TODO:Replace with method inside of Year class?
			return years.get(yearNum).getRange(monthNum, dayNum, 12, 31);

		} else {
			return 0;
		}
	}

	/**
	 * Returns the total weight from the beginning of the year to the date given,
	 * including the given date.
	 * 
	 * @param yearNum of date to end at
	 * @param monthNum of date to end at
	 * @param dayNum of date to end at
	 * @return total weight from beginning of year to end date
	 */
	private int getWeightBefore(int yearNum, int monthNum, int dayNum) {
		if (years.containsKey(yearNum)) {
			// TODO:Replace with method in Year class?
			return years.get(yearNum).getRange(1, 1, monthNum, dayNum);
		} else {
			return 0;
		}
	}
	
	@Override
	public int getDailyWeight(int yearNum, int monthNum, int dayNum) {
		if (!years.containsKey(yearNum)) {
			return 0;
		} else {
			return years.get(yearNum).getMonth(monthNum).get(dayNum);
		}
	}

	@Override
	/**
	 * Set the daily weight for this farm and the given date to the given weight
	 * @param weight to set the daily weight to
	 * @param yearNum of date to reset
	 * @param monthNum of date to reset
	 * @param dayNum of date to reset
	 * @return the previous weight stored at that date
	 */
	public int setDailyWeight(int weight, int yearNum, int monthNum, int dayNum) {
		if (weight < 0) {
			throw new IllegalArgumentException("Weight cannot be negative");
		}
		
		if (!years.containsKey(yearNum)) {
			years.put(yearNum, new Year(yearNum));
		}
		
		int prevWeight = years.get(yearNum).setDailyWeight(weight, monthNum, dayNum);
		
		return prevWeight;
	}

	@Override
	/**
	 * Set the daily weight for this farm and the given weight to zero
	 * @param yearNum of date to reset
	 * @param monthNum of date to reset
	 * @param dayNum of date to reset
	 * @return the previous weight stored at that date
	 */
	public int clearDailyWeight(int yearNum, int monthNum, int dayNum) {
		return setDailyWeight(0,yearNum,monthNum,dayNum);
	}
	
	@Override
	public void addToDailyWeight(int weight, int yearNum, int monthNum, int dayNum) {
		int prevWeight = getDailyWeight(yearNum,monthNum,dayNum);
		int newWeight = prevWeight + weight;
		
		if (newWeight < 0) {
			throw new IllegalArgumentException("Cannot have a negative final weight");
		}
		
		setDailyWeight(newWeight,yearNum,monthNum,dayNum);
		
		
		
	}

	@Override
	/**
	 * Returns a Farm Report in the form of the table for this farm
	 * and the given year.
	 * 
	 * TODO:May change TableView to something else depending on what 
	 * is desired for GUI.
	 * 
	 * @param yearNum to get report on
	 * @return table of the desired report
	 */
	public TableView getFarmReport(int yearNum) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}

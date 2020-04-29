/**
 * Farm.java
 * Author: Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
 * Date: 4/29/2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001/002/001
 *
 * List Collaborators: 
 *
 * Other Credits: 
 *
 * Known Bugs: 
 */
package application;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;

/**
 * Farm - Stores the data on the amount of milk weight that a farm has sold on
 * by day as well as an id associated to that farm. Also has various methods for
 * updating data, saving data to files, and calculating results
 * 
 * @author menzia (2020)
 *
 */
public class Farm implements FarmADT {

	private String farmId;// String uniquely representing this farm
	private TreeMap<Integer, Year> years;// Tree map storing the years associated to this farm

	/**
	 * Construct a new Farm with the given Id and an empty TreeMap of years.
	 * 
	 * @param farmId to associate with Farm object
	 * @throws IllegalArgumentException if farmId is null
	 */
	public Farm(String farmId) {
		if (farmId == null) {
			throw new IllegalArgumentException("Farm Id cannot be null");
		}
		
		this.farmId = farmId;
		this.years = new TreeMap<Integer, Year>();
	}

	/**
	 * Return a set consisting of of the year numbers that this farm has data
	 * stored in.
	 * 
	 * @return set of all year numbers with data
	 */
	public Set<Integer> getYears() {
		return years.keySet();
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
	 * Return the YearADT object corresponding to the given year number at this
	 * farm. If no data is stored for that year, returns null
	 * 
	 * @return YearADT object corresponding to the given year number.
	 */
	public Year getYear(int yearNum) {
		if (years.containsKey(yearNum)) {
			return years.get(yearNum);
		} else {
			// may want to change to return null
			// if this construction is too problematic
			return null;
		}
	}

	@Override
	/**
	 * Returns the MonthADT object corresponding to the given year number and month
	 * number at this farm. If no data is stored for this month, returns null
	 * 
	 * @param yearNum  of month object to return
	 * @param monthNum of month object to return
	 * @return MonthADT object corresponding to the desired month
	 * @throws IllegalArgumentException if monthNum not valid
	 */
	public Month getMonth(int yearNum, int monthNum) throws IllegalArgumentException {
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
	public long getYearTotal(int yearNum) {
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
	 * @param yearNum  of month to return total weight of
	 * @param monthNum of month to return total weight of
	 * @return total weight stored in the given month
	 * @throws IllegalArgumentException if monthNum is not valid
	 */
	public long getMonthTotal(int yearNum, int monthNum) throws IllegalArgumentException {
		if (years.containsKey(yearNum)) {
			return years.get(yearNum).getMonth(monthNum).totalWeight();

		} else {
			return 0;
		}
	}

	@Override
	/**
	 * Returns the total weight associated to this farm in the range starting at the
	 * YYYY/MM/DD given by the first three parameters to the YYYY/MM/DD given by the
	 * last three. This range is inclusive at both ends.
	 * 
	 * @param startYear  number of year
	 * @param startMonth month number for start date
	 * @param startDay   day number for start date
	 * @param endMonth   month number for end date
	 * @param endDay     day number for end date
	 * @return total weight stored in this range
	 * @throws IllegalArgumentException if dates are not valid
	 */
	public long getRange(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
		// if the start year is after the end year, return 0
		if (startYear > endYear) {
			return 0;

			// if the start year and end year are the same, use the range method in Year
			// class
		} else if (startYear == endYear) {

			if (years.containsKey(startYear)) {
				return years.get(startYear).getRange(startMonth, startDay, endMonth, endDay);
			} else {
				return 0;
			}

			// otherwise, add up the weight in the first year, the weight between the two
			// years,
			// and the weight in the last year
		} else {
			long startWeight = getWeightAfter(startYear, startMonth, startDay);
			long betweenWeight = getWeightBetween(startYear, endYear);
			long endWeight = getWeightBefore(endYear, endMonth, endDay);

			return (startWeight + betweenWeight + endWeight);

		}
	}

	/**
	 * Returns the total amount of weight between the starting year and the ending
	 * year, not including either of the endpoints.
	 * 
	 * @param startYear year at left endpoint of interval
	 * @param endYear   year at right endpoint of interval
	 * @return total weight between the start year and end year, non-inclusive
	 */
	private long getWeightBetween(int startYear, int endYear) {
		int rangeWeight = 0;
		for (int i = startYear + 1; i < endYear; ++i) {
			rangeWeight += getYearTotal(i);
		}

		return rangeWeight;
	}

	/**
	 * Returns the total weight from the date given to the end of that year,
	 * including the given date.
	 * 
	 * @param yearNum  of date to start at
	 * @param monthNum of date to start at
	 * @param dayNum   of date to start at
	 * @return total weight from starting date to end of year
	 */
	private long getWeightAfter(int yearNum, int monthNum, int dayNum) {
		if (years.containsKey(yearNum)) {
			
			return years.get(yearNum).getRange(monthNum, dayNum, 12, 31);

		} else {
			return 0;
		}
	}

	/**
	 * Returns the total weight from the beginning of the year to the date given,
	 * including the given date.
	 * 
	 * @param yearNum  of date to end at
	 * @param monthNum of date to end at
	 * @param dayNum   of date to end at
	 * @return total weight from beginning of year to end date
	 */
	private long getWeightBefore(int yearNum, int monthNum, int dayNum) {
		if (years.containsKey(yearNum)) {
			
			return years.get(yearNum).getRange(1, 1, monthNum, dayNum);
		} else {
			return 0;
		}
	}

	@Override
	/**
	 * Returns the total weight stored at the given date
	 * 
	 * @param yearNum of date
	 * @param monthNum of date
	 * @param dayNum of date
	 * @return total weight at the date
	 * @throws IllegalArgumentException if date is invalid
	 */
	public int getDailyWeight(int yearNum, int monthNum, int dayNum) throws IllegalArgumentException {
		if (!years.containsKey(yearNum)) {
			return 0;
		} else {
			return years.get(yearNum).getMonth(monthNum).get(dayNum);
		}
	}

	@Override
	/**
	 * Set the daily weight for this farm and the given date to the given weight
	 * 
	 * @param weight   to set the daily weight to
	 * @param yearNum  of date to reset
	 * @param monthNum of date to reset
	 * @param dayNum   of date to reset
	 * @return the previous weight stored at that date
	 * @throws IllegalArgumentException if weight is negative or date is invalid
	 */
	public int setDailyWeight(int weight, int yearNum, int monthNum, int dayNum) throws IllegalArgumentException {
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
	 * 
	 * @param yearNum  of date to reset
	 * @param monthNum of date to reset
	 * @param dayNum   of date to reset
	 * @return the previous weight stored at that date
	 */
	public int clearDailyWeight(int yearNum, int monthNum, int dayNum) {
		return setDailyWeight(0, yearNum, monthNum, dayNum);
	}

	@Override
	/**
	 * Adds the given amount of weight to the weight already stored at the
	 * given date.
	 * 
	 * @param weight to add
	 * @param yearNum of date
	 * @param monthNum of date
	 * @param dayNum of date
	 * @throws IllegalArgumentException if weight given is negative
	 */
	public void addToDailyWeight(int weight, int yearNum, int monthNum, int dayNum) throws IllegalArgumentException {		
		int prevWeight = getDailyWeight(yearNum, monthNum, dayNum);
		int newWeight = prevWeight + weight;
		
		if (newWeight < 0) {
			throw new IllegalArgumentException("Cannot have negative final weight");
		}

		

		setDailyWeight(newWeight, yearNum, monthNum, dayNum);

	}

	/**
	 * Writes the data from this farm for the given year and month into the given
	 * file writer in csv format.
	 * 
	 * @param wrter to write onto file with
	 * @param yearNum of data to write
	 * @param monthNum of data to write
	 * @throws IOException if an I/O error occurs
	 */
	public void exportData(FileWriter wrter, int yearNum, int monthNum) throws IOException {
		if (years.get(yearNum) != null) {
			Year year = years.get(yearNum);
			Month month = year.getMonth(monthNum);

			for (int dayNum = 1; dayNum <= month.size(); ++dayNum) {
				int weight = month.get(dayNum);
				if (weight > 0) {
					wrter.write(dataLine(yearNum, monthNum, dayNum, weight));

				}
			}

		}
	}

	/**
	 * Helper method which returns a string which represents a line of the
	 * csv files used to import data.
	 * 
	 * @param yearNum to store data of
	 * @param monthNum to store data of
	 * @param dayNum to store data of
	 * @param weight to store data of
	 * @return String representing a line of a .csv file with the above data
	 */
	private String dataLine(int yearNum, int monthNum, int dayNum, int weight) {
		String date = yearNum + "-" + monthNum + "-" + dayNum;
		String id = getId();
		String weightString = Integer.toString(weight);

		String dataLine = date + "," + toCSVToken(id) + "," + weightString + "\n";

		return dataLine;

	}
	
	/**
	 * Encapsulates any commas in the string in a pair of double quotes so that
	 * it will be included verbatim in a csv file.
	 * 
	 * @param string to convert
	 * @return string with commas surrounded by double quotes
	 */
	private String toCSVToken(String string) {
		 return string.replace(",", "\",\"");
	}

}

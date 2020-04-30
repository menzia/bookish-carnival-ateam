/**
 * Month.java
 * 
 * Author: Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
 * Date: 4/20/2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001/002/001
 * 
 * Other Credits: 
 *
 * Known Bugs: 
 */
package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Month - Implementation of MonthADT. Stores the weight of milk obtained on each day of
 * a given month. Note that the day numbers are assumed to start at 1 as they would
 * in a normal calendar.
 * 
 * @author menzia (2020)
 *
 */
public class Month implements MonthADT {
	
	private long totalWeight;//sum of all weights from this month
	private int[] dailyWeights;//array of weights for each day of this month
	
	/**
	 * Constructs a month with the given year number and month
	 * number with 0 weight for each day.
	 * 
	 * @param yearNum of month to create
	 * @param monthNum of month to create
	 * @throws IllegalArgumentException is month number is out of valid range
	 */
	public Month(int yearNum, int monthNum) throws IllegalArgumentException {
		totalWeight = 0;
		
		if (monthNum < 1 || monthNum > 12) {
			throw new IllegalArgumentException("Month number must be between 1 and 12");
		}
		
		int size = getNumDays(yearNum,monthNum);
		dailyWeights = new int[size];
	}
	
	/**
	 * Returns the number of days in the given month.
	 * 
	 * @param yearNum of month to use
	 * @param monthNum of month to use
	 * @return number of days in given month
	 * @throws IllegalArgumentException if month number is invalid
	 */
	private static int getNumDays(int yearNum,int monthNum) throws IllegalArgumentException {
		if (monthNum < 1 || monthNum > 12) {
			throw new IllegalArgumentException("Month number must be between 1 and 12");
		}
		
		//April, June, September, and November have 30 days
		if (monthNum == 4 || monthNum == 6 || monthNum == 9 || monthNum == 11) {
			return 30;
			
		//Every other month except February has 31 days
		} else if (monthNum != 2) {
			return 31;
		
		//February has 29 days in a leap year and 28 otherwise
		} else if (isLeap(yearNum)) {
			return 29;
			
		} else {
			return 28;
		}
	}
	
	/**
	 * Returns true iff the given year is a leap year
	 */
	private static boolean isLeap(int yearNum) {
		if (yearNum % 4 == 0) {
			return false;
			
		} else if (yearNum % 400 == 0) {
			return true;
			
		} else if (yearNum % 100 == 0) {
			return false;
			
		} else {
			return true;
		}
	}
	
	@Override
	/**
	 * Returns the number of days in this month
	 * 
	 * @return number of days in this month
	 */
	public int size() {
		return dailyWeights.length;
	}

	@Override
	/**
	 * Returns the sum of all weights for this month
	 * 
	 * @return total weight across this month
	 */
	public long totalWeight() {
		return totalWeight;
	}

	@Override
	/**
	 * Sets the weight for the given day of the month to the given weight,
	 * then returns the previous weight. If weight is negative or the
	 * day number is out of range, will throw an IllegalArgumentException.
	 * 
	 * @param weight to set daily weight to
	 * @param dayNum to set daily weight of
	 * @return the previous weight of that day
	 * @throws IllegalArgumentException if weight is negative or day number invalid
	 */
	public int set(int weight, int dayNum) throws IllegalArgumentException {
		if (weight < 0) {
			throw new IllegalArgumentException("Weight must be non-negative");
		}
		
		int index = dayNum - 1;
		if (index < 0 || index >= dailyWeights.length) {
			throw new IllegalArgumentException("Day number out of range.");
			
		}else {
			int prevWeight = dailyWeights[index];
			dailyWeights[index] = weight;
			
			totalWeight += (weight - prevWeight);
			
			return prevWeight;
			
		}
		
	}

	@Override
	/**
	 * Returns the weight for the given day of this month. If the day is
	 * out of range, will throw IllegalArgumentException
	 * 
	 * @param dayNum to return weight of
	 * @return weight of given day
	 * @throws IllegalArgumentException if day number out of range
	 */
	public int get(int dayNum) throws IllegalArgumentException {
		int index = dayNum-1;
		
		if (0<= index && index < dailyWeights.length) {
			return dailyWeights[index];
			
		} else {
			throw new IllegalArgumentException("Day number out of range.");
			
		}
	}

	@Override
	/**
	 * Returns the total amount of weight from the start day given
	 * to the end day given, inclusive on both sides. If day numbers
	 * are out of range, will throw IllegalArgumentException.
	 * 
	 * @param dayNumStart day to start count of milk weight
	 * @param dayNumEnd day to end count of milk weight
	 * @return total amount of milk from start date to end date
	 * @throws IllegalArgumentException if days out of range
	 */
	public long getRange(int dayNumStart, int dayNumEnd) throws IllegalArgumentException {
		int index1 = dayNumStart - 1;
		int index2 = dayNumEnd - 1;
		
		if (index1 < 0 || index2 < 0 || index1 >= dailyWeights.length || index2 >= dailyWeights.length) {
			throw new IllegalArgumentException("Day numbers out of range.");
		}
		
		int sum = 0;
		for (int i = index1; i <= index2; ++i) {
			sum += dailyWeights[i];
		}
		
		return sum;
	}

	@Override
	/**
	 * Returns the total amount of weight from the start day given
	 * to the end of the month.If the day number is out of range,
	 * will throw exception
	 * 
	 * @param dayNumStart day to start count of milk weight
	 * @return total amount of milk from start date to end of the month
	 * @throws IllegalArgumentException if day number out of range
	 */
	public long getUpperRange(int dayNumStart) throws IllegalArgumentException {
		
		return getRange(dayNumStart, dailyWeights.length);
	}
	
	/**
	 * Returns the total amount of weight from the start of the
	 * month to the given day. If day is out of range,
	 * throws IllegalArgumentException.
	 * 
	 * @param dayNumStart day to end count of milk weight
	 * @return total amount of milk from start of month to day given
	 */
	public long getLowerRange(int dayNumEnd) throws IllegalArgumentException {
		
		return getRange(1, dayNumEnd);
	}

	@Override
	/**
	 * Sets the weight for the given day to 0 and returns
	 * the previous weight. If the day number is out of
	 * range, will throw IllegalArgumentException.
	 * 
	 * @param dayNum to set daily weight of to 0
	 * @return the previous weight in the given day
	 * @throws IllegalArgumentException if date out of range
	 */
	public int clear(int dayNum) throws IllegalArgumentException {
		
		return set(0, dayNum);
	}
	public Integer[] sortInReverse() {
	    Integer[] dailydata = new Integer[dailyWeights.length];
        int counter = 0;
        for(int i:dailyWeights) {
            dailydata[counter] = (Integer)i;
            counter++;
        }
        Arrays.sort(dailydata, Collections.reverseOrder());
        return dailydata;
	}
	public long getMin() {
	    Integer[] dailydata = sortInReverse();
	    return dailydata[dailydata.length-1];
	    
	}
	public long getMax() {
	    Integer[]dailydata = sortInReverse();
	    return dailydata[0];
	}
	public long getAverage() {
	    int numDays = size();
	    long totalW = totalWeight();

	    return totalW/(long)numDays;
	}
	
	public static void main(String args[]) {
	    
	}
	
}


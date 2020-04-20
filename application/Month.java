/**
 * Month.java
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

/**
 * Month - Implementation of MonthADT. Stores the weight of milk obtained on each day of
 * a given month. Note that the day numbers are assumed to start at 1 as they would
 * in a normal calendar.
 * 
 * @author menzia (2020)
 *
 */
public class Month implements MonthADT {
	
	int totalWeight;//sum of all weights from this month
	int[] dailyWeights;//array of weights for each day of this month
	
	/**
	 * Constructs a month with the given year number and month
	 * number with 0 weight for each day.
	 * 
	 * @param yearNum of month to create
	 * @param monthNum of month to create
	 * @throws IndexOutOfBoundsException is month number is out of valid range
	 */
	public Month(int yearNum, int monthNum) {
		totalWeight = 0;
		
		if (monthNum < 1 || monthNum > 12) {
			throw new IndexOutOfBoundsException("Month number must be between 1 and 12");
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
	 */
	private static int getNumDays(int yearNum,int monthNum) {
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
	public int totalWeight() {
		return totalWeight;
	}

	@Override
	/**
	 * Sets the weight for the given day of the month to the given weight,
	 * then returns the previous weight. If weight is negative or the
	 * day number is out of range, will return 0 and do nothing rather
	 * than throw an exception.
	 * 
	 * @param weight to set daily weight to
	 * @param dayNum to set daily weight of
	 * @return the previous weight of that day
	 */
	public int set(int weight, int dayNum) {
		
		int index = dayNum - 1;
		
		if (0<= index && index < dailyWeights.length && weight >= 0) {
			int prevWeight = dailyWeights[index];
			dailyWeights[index] = weight;
			
			totalWeight += (weight - prevWeight);
			
			return prevWeight;
			
		} else {
			
			return 0;
		}
		
	}

	@Override
	/**
	 * Returns the weight for the given day of this month. If the day is
	 * out of range, will simply return 0 rather than throw an exception.
	 * 
	 * @param dayNum to return weight of
	 * @return weight of given day
	 */
	public int get(int dayNum) {
		int index = dayNum-1;
		
		if (0<= index && index < dailyWeights.length) {
			return dailyWeights[index];
			
		} else {
			return 0;
			
		}
	}

	@Override
	/**
	 * Returns the total amount of weight from the start day given
	 * to the end day given, inclusive on both sides. If day numbers
	 * are out of range, will simply trim them to the closest in 
	 * range values.
	 * 
	 * @param dayNumStart day to start count of milk weight
	 * @param dayNumEnd day to end count of milk weight
	 * @return total amount of milk from start date to end date
	 */
	public int getRange(int dayNumStart, int dayNumEnd) {
		int index1 = dayNumStart - 1;
		index1 = Math.max(0, index1);
		
		int index2 = dayNumEnd - 1;
		index2 = Math.min(dayNumStart, dailyWeights.length - 1);
		
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
	 * will simply trim to the nearest in range value.
	 * 
	 * @param dayNumStart day to start count of milk weight
	 * @return total amount of milk from start date to end of the month
	 */
	public int getRange(int dayNumStart) {
		
		return getRange(dayNumStart, dailyWeights.length);
	}

	@Override
	/**
	 * Sets the weight for the given day to 0 and returns
	 * the previous weight. If the day number is out of
	 * range, will simply do nothing and return 0.
	 * 
	 * @param dayNum to set daily weight of to 0
	 * @return the previous weight in the given day
	 */
	public int clear(int dayNum) {
		
		return set(0, dayNum);
	}

}

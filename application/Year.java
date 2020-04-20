/**
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
 * Year - Implements YearADT. Stores and allows access to milk weight data for
 * every day in a year. Uses Month.java.
 * 
 * @author menzia (2020)
 *
 */
public class Year implements YearADT {
	Month[] months;// array which stores each month object
	int totalWeight;// stores total weight across entire year

	/**
	 * Construct a new year with zero weight for each day and zero total weight.
	 * 
	 * @param yearNum of year to create
	 */
	public Year(int yearNum) {
		totalWeight = 0;
		months = new Month[12];

		for (int i = 1; i <= 12; ++i) {

			months[i - 1] = new Month(yearNum, i);

		}
	}

	@Override

	public int totalWeight() {
		return totalWeight;
	}

	@Override
	/**
	 * Returns the MonthADT object with the given month number.
	 * 
	 * @param monthNum month number to return month of
	 * @return MonthADT object of corresponding month number
	 * @throws IllegalArgumentException if month number out of range
	 */
	public Month getMonth(int monthNum) throws IllegalArgumentException {
		if (monthNum < 1 || monthNum > 12) {

			throw new IllegalArgumentException("Month number must be between 1 and 12");
		} else {

			return months[monthNum - 1];
		}
	}

	@Override
	/**
	 * Returns the maximum weight of a month within this year
	 * 
	 * @return max total weight of a month in this year
	 */
	public int maxMonthlyWeight() {
		int max = getMonth(1).totalWeight();

		for (int i = 1; i <= 12; ++i) {
			if (getMonth(i).totalWeight() > max) {
				max = getMonth(i).totalWeight();
			}
		}

		return max;
	}

	@Override
	/**
	 * Returns the minimum weight of a month within this year
	 * 
	 * @return max total weight of a month in this year
	 */
	public int minMonthlyWeight() {
		int min = getMonth(1).totalWeight();

		for (int i = 1; i <= 12; ++i) {
			if (getMonth(i).totalWeight() < min) {
				min = getMonth(i).totalWeight();
			}
		}

		return min;
	}

	@Override
	/**
	 * Returns the average monthly weight for this year
	 * 
	 * @return average monthly weight in this year
	 */
	public float avgMonthWeight() {
		return (float) totalWeight / 12;
	}

	@Override
	/**
	 * Returns the total amount of weight stored from the given starting date to the
	 * given ending date, inclusive on both sides.
	 * 
	 * @param startMonth month number of start date
	 * @param startDay   day number of start date
	 * @param endMonth   month number of end date
	 * @param endDay     day number of end date
	 * @return total milk weight within the given range
	 * @throws IllegalArgumentException if dates are out of valid range
	 */
	public int getRange(int startMonth, int startDay, int endMonth, int endDay) throws IllegalArgumentException {

		Month start = getMonth(startMonth);
		Month end = getMonth(endMonth);

		if (startMonth < endMonth) {
			return 0;

		} else if (startMonth == endMonth) {
			return start.getRange(startDay, endDay);

		} else {
			int sum = start.getUpperRange(startDay);

			for (int i = startMonth + 1; i < endMonth; ++i) {
				sum += getMonth(i).totalWeight();
			}

			sum += end.getLowerRange(endDay);

			return sum;
		}
	}

	@Override
	/**
	 * Set the daily weight for this year and the given date to the given weight.
	 * 
	 * @param weight to set the daily weight to
	 * @param monthNum of date to reset
	 * @param dayNum of date to reset
	 * @return the previous weight stored at that date
	 * @throws IllegalArgumentException if weight is negative or date is out of range
	 */
	public int setDailyWeight(int weight, int monthNum, int dayNum) throws IllegalArgumentException {

		Month month = getMonth(monthNum);
		int prevWeight = month.set(weight, dayNum);

		totalWeight += (weight - prevWeight);

		return prevWeight;
	}

	@Override
	/**
	 * Set the daily weight for this year and the given weight to zero.
	 * 
	 * @param monthNum of date to reset
	 * @param dayNum of date to reset
	 * @return the previous weight stored at that date
	 * @throws IllegalArgumentException if date is out of range
	 */
	public int clearDailyWeight(int monthNum, int dayNum) {

		return setDailyWeight(0, monthNum, dayNum);
	}

}

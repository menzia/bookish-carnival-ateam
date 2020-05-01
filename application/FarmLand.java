/**
 * FarmLand.java
 * Author: Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
 * Date: 4/29/2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001/002/001
 *
 * List Collaborators: n/a
 *
 * Other Credits: n/a
 *
 * Known Bugs: n/a
 */
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.DataFormatException;

/**
 * FarmLand - Stores and allows access to the weight data for a collection of
 * farms. Also has methods for generating tables displaying certain parts of the
 * data.
 * 
 *
 */
public class FarmLand implements FarmLandADT {
	
	private TreeMap<String, Farm> farms;// where data on individual farms is stored
	private Farm allFarms;// where data on the combined weight of all farms is stored
	private int longestId;//length of longest id

	private static final String OPENING_LINE = "date,farm_id,weight\n";// first line for .csv files

	/**
	 * Constructs a new, empty, FarmLand object
	 */
	public FarmLand() {
		farms = new TreeMap<String, Farm>();
		allFarms = new Farm("Stores Weight of All Farms in System");
		longestId = 0;
	}
	
	/**
	 * Resets the FarmLand object to one with no farms and no data
	 */
	public void reset() {
		farms = new TreeMap<String,Farm>();
		allFarms = new Farm("Stores Weight of All Farms in System");
		longestId = 0;
	}

	/**
	 * Returns the set of all farm id's in this data structure
	 * 
	 * @return set of farmIds
	 */
	public Set<String> getFarms() {
		return farms.keySet();
	}

	/**
	 * Returns the set of all years numbers that have data
	 * stored on them.
	 * 
	 * @return set of all years with data
	 */
	public Set<Integer> getYears() {
		return allFarms.getYears();
	}

	@Override
	/**
	 * Return the FarmADT object with the given farm ID. If no such object exists,
	 * returns null
	 * 
	 * @param farmID ID to look for within data structure
	 * @return FarmADT object with given farmID
	 */
	public Farm getFarm(String farmID) {
		return farms.get(farmID);
	}

	/**
	 * Return the total weight across all farms in the given month
	 * 
	 * @param yearNum of month to get weight for
	 * @param monthNum of month to get weight for
	 * @return total weight of all farms in given month
	 * @throws IllegalArgumentException if monthNum is invalid
	 */
	public long getAllFarmMonthTotal(int yearNum, int monthNum) {
		return allFarms.getMonthTotal(yearNum, monthNum);
	}

	/**
	 * Return the total weight across all farms in the given year
	 * 
	 * @param yearNum of year to get total weight of
	 * @return total weight of all farms in this year
	 */
	public long getAllFarmsYearTotal(int yearNum) {
		return allFarms.getYearTotal(yearNum);
	}

	/**
	 * Return the total weight across all farms for the given range of dates.
	 * 
	 * @param startYear year number of start date
	 * @param startMonth month number of start date
	 * @param startDay day number of start date
	 * @param endYear year number of end date
	 * @param endMonth month number of end date
	 * @param endDay day number of end date
	 * @return total weight across all farms in the given range
	 * @throws IllegalArgumentException if the dates are invalid
	 */
	public long getAllFarmRange(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
		return allFarms.getRange(startYear, startMonth, startDay, endYear, endMonth, endDay);
	}

	@Override
	/**
	 * Returns true if the data structure contains a farm with the given ID, and
	 * false otherwise
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
	 * Add a farm to the data structure with the given ID. If the given ID is
	 * already in the structure, then does nothing.
	 * 
	 * @param farmID of farm to add to structure
	 */
	public void addFarm(String farmID) {
		if (!this.contains(farmID)) {

			farms.put(farmID, new Farm(farmID));
			if (farmID.length() > longestId) {
				longestId = farmID.length();
			}
		}

	}

	@Override
	/**
	 * Set the daily weight for the given farm and the given date to the given
	 * weight. If the given ID is not in the structure, adds it to structure first.
	 * 
	 * @param farmID   of farm to set weight of
	 * @param weight   to set the daily weight to
	 * @param yearNum  of date to reset
	 * @param monthNum of date to reset
	 * @param dayNum   of date to reset
	 */
	public void setDailyWeight(String farmID, int weight, int yearNum, int monthNum, int dayNum) {
		addFarm(farmID);// make sure farmID is in dataStructure

		// set daily weight to the desired weight for that farm
		int prevWeight = farms.get(farmID).getDailyWeight(yearNum, monthNum, dayNum);
		farms.get(farmID).setDailyWeight(weight, yearNum, monthNum, dayNum);

		// calculate amount that the weight has changed
		int weightChange = weight - prevWeight;

		// account for weight change in allFarms
		allFarms.addToDailyWeight(weightChange, yearNum, monthNum, dayNum);
	}
	
	/**
	 * Adds to the daily weight for the given farm and the given date by the given
	 * weight. If the given ID is not in the structure, adds it to structure first.
	 * 
	 * @param farmID   of farm to set weight of
	 * @param weight   to set the daily weight to
	 * @param yearNum  of date to reset
	 * @param monthNum of date to reset
	 * @param dayNum   of date to reset
	 */
	public void addToDailyWeight(String farmID, int weight, int yearNum, int monthNum, int dayNum) {
		addFarm(farmID);// make sure farmID is in dataStructure

		// adds the desired weight to the daily weight for the given farm and for all farms
		farms.get(farmID).addToDailyWeight(weight, yearNum, monthNum, dayNum);
		allFarms.addToDailyWeight(weight, yearNum, monthNum, dayNum);
	}

	@Override
	/**
	 * Attempts to read all the files in the given directory and import their data
	 * into the data structure. Throws a DataFormatException if one of the files
	 * does not have the data correctly written.
	 * 
	 * Will still import all data into the structure up until the point that an
	 * error in the data is found. Will set all weights to the given weights in the
	 * file rather than add the weights to any already existing weights.
	 */
	public void processDirectory(File dir) throws FileNotFoundException, DataFormatException {

		File[] files = dir.listFiles();
		if (files != null) {
			for (File csvFile : files) {
				processFile(csvFile);
			}

		} else {
			// display error message
			throw new FileNotFoundException("Directory not found.");
		}

	}

	/**
	 * Reads the given file if possible and adds the data within it to the data
	 * structure. Throws data format exception if the data is not properly written
	 * in the file.
	 * 
	 * @param file .csv file to parse
	 * @throws FileNotFoundException if file is not found
	 * @throws DataFormatException   if the format of the file is incorrect
	 */
	private void processFile(File file) throws FileNotFoundException, DataFormatException {

		Scanner sc = new Scanner(file);

		// first line does not have any data, so move past it to the second line
		if (sc.hasNextLine()) {
			sc.nextLine();
		}

		int lineNum = 2;

		while (sc.hasNextLine()) {

			String nextLine = sc.nextLine();

			// simply ignore a blank line
			if (nextLine.contentEquals("")) {
				lineNum += 1;

			} else {

				String[] tokens = nextLine.split(",");

				// each line should be three tokens separated by the regex ","
				if (tokens.length != 3) {
					sc.close();
					throw new DataFormatException(
							"Wrong number of tokens on line " + lineNum + " in the file " + file.getPath());
				}

				// first token contains date information separated by the regex "/"
				String[] date = tokens[0].split("-");

				try {

					// date have a month, day, and year component
					if (date.length != 3) {
						sc.close();
						throw new DataFormatException(
								"Cannot read date on line " + lineNum + "in the file " + file.getPath());
					}

					// try to parse integers for each part of date format
					int yearNum = Integer.parseInt(date[0]);
					int monthNum = Integer.parseInt(date[1]);
					int dayNum = Integer.parseInt(date[2]);

					// farmID is second token
					String farmID = tokens[1];

					// try to parse weight from last token
					int weight = Integer.parseInt(tokens[2]);

					// negative weights cause exception
					if (weight < 0) {
						sc.close();
						throw new DataFormatException(
								"Invalid negative weight on line " + lineNum + " in the file " + file.getPath());
					}

					// try to set corresponding daily weight
					// if date is invalid, will cause IllegalArgument exception which
					// is then caught below
					this.setDailyWeight(farmID, weight, yearNum, monthNum, dayNum);

					// increment line numbers and move on to next line
					lineNum += 1;

				} catch (NumberFormatException e) {
					sc.close();
					throw new DataFormatException(
							"Cannot parse numbers on line " + lineNum + " in the file " + file.getPath());

				} catch (IllegalArgumentException f) {
					sc.close();
					throw new DataFormatException("Error on Line " + lineNum + ": " + f.getMessage());
				}

			}
		}

		// after all lines have successfully been read, close the scanner
		sc.close();
	}

	@Override
	/**
	 * Writes all of the data currently contained within the structure to csv files
	 * in the directory given, in the same format that the usual import csv files
	 * are written.
	 * 
	 * @param File dir directory to write data into
	 * @throws FileNotFoundException if the director is not found
	 * @throws IOException           if something goes wrong trying to write the
	 *                               files
	 */
	public void exportToDirectory(File dir) throws FileNotFoundException, IOException {

		// Absolute path of directory to write into
		String origDirName = dir.getAbsolutePath();

		for (Integer yearNum : allFarms.getYears()) {

			// Make new directory inside the original directory for each year which
			// we have data on.
			File newDir = new File(origDirName + "\\FarmData-" + yearNum);
			newDir.mkdir();

			for (int monthNum = 1; monthNum <= 12; ++monthNum) {

				// For each month of this year, create a corresponding .csv file
				FileWriter wrter = new FileWriter(newDir.getAbsolutePath() + "\\" + yearNum + "-" + monthNum + ".csv");
				try {
					wrter.write(OPENING_LINE);
					for (String farmID : farms.keySet()) {
						farms.get(farmID).exportData(wrter, yearNum, monthNum);

					}

				} finally {
					// Finally, close the FileWriter
					wrter.close();
				}
			}

		}

	}
	
	/**
	 * Returns the length of the longest string used in an ID for this FarmLand
	 * 
	 * @return longest string used as an ID
	 */
	public int getLongestId() {
		return longestId;
	}
	

}

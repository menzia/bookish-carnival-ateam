/**
 * FarmLand.java created by alexm on Dell Inspiron in ATeam.
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.DataFormatException;

import javafx.scene.control.TableView;

/**
 * FarmLand - Stores and allows access to the weight data for a collection of
 * farms. Also has methods for generating tables displaying certain parts of the
 * data.
 * 
 * @author menzia (2020)
 *
 */
public class FarmLand implements FarmLandADT {
	private TreeMap<String, Farm> farms;// where data on individual farms is stored
	private Farm allFarms;// where data on the combined weight of all farms is stored
	private HashSet<Integer> years;

	private static final String OPENING_LINE = "date,farm_id,weight\n";// first line for .csv files

	/**
	 * Constructs a new, empty, FarmLand object
	 */
	public FarmLand() {
		farms = new TreeMap<String, Farm>();
		allFarms = new Farm("Stores Weight of All Farms in System");
		years = new HashSet<Integer>();
	}
	
	public Set<String> getFarms(){
		return farms.keySet();
	}
	
	public Set<Integer> getYears(){
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
	
	//TODO:Add comment
	public int getAllFarmMonthTotal(int yearNum, int monthNum) {
		return allFarms.getMonthTotal(yearNum, monthNum);
	}
	
	//TODO:Add comment
	public int getAllFarmsYearTotal(int yearNum) {
		return allFarms.getYearTotal(yearNum);
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
		int prevWeight = farms.get(farmID).setDailyWeight(weight, yearNum, monthNum, dayNum);

		// calculate amount that the weight has changed
		int weightChange = weight - prevWeight;

		// account for weight change in allFarms
		allFarms.addToDailyWeight(weightChange, yearNum, monthNum, dayNum);
		years.add(yearNum);
	}

	@Override
	/**
	 * Returns a Farm Report in the form of the table for the given farm and the
	 * given year.
	 * 
	 * TODO:May change TableView to something else depending on what is desired for
	 * GUI
	 * 
	 * @param farmID  of farm to get report on
	 * @param yearNum to get report on
	 * @return table of the desired report
	 */
	public TableView getFarmReport(String farmID, int yearNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Returns an Annual Report on this collection of farms for the given year
	 * 
	 * @param yearNum of year to return report on
	 * @return table of the desired report
	 */
	public TableView getAnnualReport(int yearNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Returns a Monthly Report on this collection of farms for the given month
	 * 
	 * @param yearNum  of month to get report on
	 * @param monthNum of month to get report on
	 * @return table of the desired report
	 */
	public TableView getMonthlyReport(int yearNum, int monthNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Returns a Range Report on this collection of farms for the given range,
	 * inclusive on both ends
	 * 
	 * @param yearNum       of both dates in range
	 * @param startMonthNum month number of start date
	 * @param startDayNum   day number of start date
	 * @param endMonthNum   month number of end date
	 * @param endDayNum     month number of start date
	 * @return table of desired report
	 */
	public TableView getRangeReport(int yearNum, int startMonthNum, int startDayNum, int endMonthNum, int endDayNum) {
		// TODO Auto-generated method stub
		return null;
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
					throw new DataFormatException("Wrong number of tokens on line " + lineNum);
				}

				// first token contains date information separated by the regex "/"
				String[] date = tokens[0].split("-");

				try {

					// date have a month, day, and year component
					if (date.length != 3) {
						sc.close();
						System.out.print(date);
						throw new DataFormatException("Cannot read date on line " + lineNum);
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
						throw new DataFormatException("Invalid negative weight on line " + lineNum);
					}

					// try to set corresponding daily weight
					// if date is invalid, will cause IllegalArgument exception which
					// is then caught below
					this.setDailyWeight(farmID, weight, yearNum, monthNum, dayNum);

					// increment line numbers and move on to next line
					lineNum += 1;

				} catch (NumberFormatException e) {
					sc.close();
					throw new DataFormatException("Cannot parse numbers on line " + lineNum);

				} catch (IllegalArgumentException f) {
					sc.close();
					throw new DataFormatException("Line " + lineNum + ": " + f.getMessage());
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

		for (Integer yearNum : years) {

			// Make new directory inside the original directory for each year which
			// we have data on.
			File newDir = new File(origDirName + "\\csvFarmData" + yearNum);
			newDir.mkdir();

			for (int monthNum = 1; monthNum <= 12; ++monthNum) {

				// For each month of this year, create a corresponding .csv file
				FileWriter wrter = new FileWriter(newDir.getAbsolutePath() + "\\" + yearNum + "-" + monthNum + ".csv");
				try {
					wrter.write(OPENING_LINE);
					for (String farmID : farms.keySet()) {
						farms.get(farmID).exportData(wrter, yearNum, monthNum);

					}

					// TODO:Add lines for all of the data in each farm to the file
				} finally {
					// Finally, close the FileWriter
					wrter.close();
				}
			}

		}

	}

}

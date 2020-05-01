/**
 * ReportMonthly.java 
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

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * ReportMonthly - Generates a Stage displaying a Monthly Report for the given
 * FarmLand object and given month.
 * 
 * If data is invalid, displays an error message.
 *
 */
public class ReportMonthly extends Stage {

	/**
	 * Displays a monthly report to the screen using the given FarmLand object and
	 * the given month.
	 * 
	 * @param farmLand to get data from
	 * @param yearNum  of month to make table of
	 * @param monthNum of month to make table of
	 */
	ReportMonthly(FarmLand farmLand, Integer yearNum, Integer monthNum) {
		// Table and its containers
		Scene scene;
		TableView<MonthRow> table;
		VBox vbox = new VBox();

		// Columns used in the report
		TableColumn<MonthRow, String> farmCol = new TableColumn<MonthRow, String>("Farm ID");
		TableColumn<MonthRow, String> weightCol = new TableColumn<MonthRow, String>("Total Milk Weight(lbs)");
		TableColumn<MonthRow, String> percentCol = new TableColumn<MonthRow, String>("Percentage of Total(%)");

		table = new TableView<MonthRow>();
		vbox = new VBox();

		scene = new Scene(new Group());
		table.setEditable(true);

		// farm column
		farmCol.setMinWidth(100);
		farmCol.setCellValueFactory(new PropertyValueFactory<MonthRow, String>("farm"));

		// weight column
		weightCol.setMinWidth(140);
		weightCol.setCellValueFactory(new PropertyValueFactory<MonthRow, String>("weight"));

		// percentage column
		percentCol.setMinWidth(140);
		percentCol.setCellValueFactory(new PropertyValueFactory<MonthRow, String>("percentage"));

		Label title = new Label();

		if (farmLand == null || yearNum == null || monthNum == null) {
			title.setText("Error: Must choose Year and Month");

		} else {
			// if data given is valid, construct a table
			table.getColumns().addAll(farmCol, weightCol, percentCol);
			table.setItems(getData(farmLand, yearNum, monthNum));
			title.setText("Monthly Report: " + yearNum + "/" + monthNum);

		}

		// Format table and display it
		title.setFont(new Font("Arial", 20));

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(title, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		table.setEditable(false);
		this.setTitle("Monthly Report");
		this.setWidth(450);
		this.setHeight(500);

		this.setScene(scene);
		this.show();
		// show scene

	}

	/**
	 * 
	 * MonthRow - Stores data for a single row of the montly report
	 * 
	 *
	 */
	public static class MonthRow {
		private SimpleStringProperty farm;// id of farm
		private SimpleLongProperty weight;// monthly weight of farm
		private SimpleStringProperty percentage;// percentage among all farms

		/**
		 * Constructs a row with the given data
		 * 
		 * @param farm       ID of farm to make row of
		 * @param weight     monthly weight of farm
		 * @param percentage of monthly weight among all farms
		 */
		private MonthRow(String farm, Long weight, String percentage) {
			this.farm = new SimpleStringProperty(farm);
			this.weight = new SimpleLongProperty(weight);
			this.percentage = new SimpleStringProperty(percentage);

		}

		public String getFarm() {
			return farm.get();
		}

		public void setFarm(String farm) {
			this.farm.set(farm);
		}

		public Long getWeight() {
			return weight.get();
		}

		public void setWeights(Integer w) {
			weight.set(w);
		}

		public String getPercentage() {
			return percentage.get();
		}

		public void setPercent(String percentage) {
			this.percentage.set(percentage);
		}

	}

	/**
	 * Creates an ObservableList of MonthRows with the data corresponding to the
	 * given year and returns it
	 * 
	 * @param farmLand to obtain data from
	 * @param yearNum  of year to make annual table on
	 * @return ObservableList with data for Annual Report
	 */
	private static ObservableList<MonthRow> getData(FarmLand farmLand, Integer yearNum, Integer monthNum) {
		
		try {
			// stores rows in order
			ArrayList<MonthRow> farmList = new ArrayList<MonthRow>();

			// weight across all farms for use in percentage calculation
			long totalWeight = farmLand.getAllFarmMonthTotal(yearNum, monthNum);

			for (String farm : farmLand.getFarms()) {

				// Calculate monthly weight and percentage of annual weight for this
				// month. The Double.MIN_NORMAL is added to the denominator to avoid
				// divide by zero errors.
				long weight = farmLand.getFarm(farm).getMonthTotal(yearNum, monthNum);
				double percentage = 100 * weight / (totalWeight + Double.MIN_NORMAL);

				// Convert the month, weight, and percentage to strings for display
				String farmString = farm;
				String percentageString = Double.toString(percentage);

				// Take four significant figures of percentage(plus the decimal point)
				int endIndex = Math.min(4, percentageString.length());
				percentageString = percentageString.substring(0, endIndex);
				
				// Some formating for special cases
				if (percentageString.equals("100.")) {
					percentageString = "100";
				} else if (percentageString.equals("0.0")) {
					percentageString = "0";
				}

				// Add row of table with data for this month
				MonthRow newMonthRow = new MonthRow(farmString, weight, percentageString);

				farmList.add(newMonthRow);

			}

			ObservableList<MonthRow> data = FXCollections.observableArrayList(farmList);

			return data;

		} catch (Exception e) {
			// In case unexpected exception occurs, return empty data set
			// This should never occur because the values inputed are checked
			// for validity before being entered here, but just in case
			ArrayList<MonthRow> farmList = new ArrayList<MonthRow>();
			MonthRow newMonthRow = new MonthRow("Error Making Table", 0L, e.getMessage());
			farmList.add(newMonthRow);
			
			return FXCollections.observableArrayList(farmList);
		}

	}

	/**
	 * Tries to print the monthly report for the given data to the given directory.
	 * 
	 * @param dir directory to save file to
	 * @param farmLand to get data from
	 * @param yearNum of year to print report on
	 */
	public static void printToDirectory(File dir, FarmLand farmLand, Integer yearNum, Integer monthNum) {
		
		File file = null;//file where the report will be written
		
		try {
			
			file = new File(dir, "/MonthlyReport-" + yearNum + ".txt");
			FileWriter wrter = new FileWriter(file);
			
			// for use in percent calculation
			Long totalWeight = farmLand.getAllFarmMonthTotal(yearNum, monthNum);
					
			// Explanatory top two lines
			wrter.write("Monthly Report: " + yearNum + "/" + monthNum + "\n\n");
			
			// make sure lengths of strings are all the same
			int longest = farmLand.getLongestId();
			String firstColumn = increaseToLength("Farm",longest);
			
			wrter.write(firstColumn + " | Percent | Total Weight \n");
			
			//Write each line of report
			for (String farm: farmLand.getFarms()) {				
				// Calculate monthly weight and percentage of annual weight for this
				// month. The Double.MIN_NORMAL is added to the denominator to avoid
				// divide by zero errors.
				long weight = farmLand.getFarm(farm).getMonthTotal(yearNum, monthNum);
				double percentage = 100 * weight / (totalWeight + Double.MIN_NORMAL);

				// Convert the month, weight, and percentage to strings for display
				
				String percentageString = Double.toString(percentage);

				// Take four significant figures of percentage(plus the decimal point)
				int endIndex = Math.min(4, percentageString.length());
				percentageString = percentageString.substring(0, endIndex);
				
				// Format special cases for alignment
				if (percentageString.equals("0.0") ){
					percentageString = "0.00";
				}
				
				// Add line to report for this month
				String line = increaseToLength(farm, longest) + " |   " + percentageString + "  | " + weight + "\n";
				wrter.write(line);
			}
			
			// Display success message and close wrter
			Stage responseStage = new Stage();
			responseStage.setTitle("Monthly Report");
			String response = "Report successfully written to " + dir.getPath();
			
			responseStage.setScene(new Scene(new Label(response), 400,300));
			responseStage.show();
			
			wrter.close();
			
		} catch (Exception e) {
			
			// if report cannot be made properly, delete the partial file made
			if (file != null) {
				file.delete();
			}
			
			// display failure response to user
			Stage responseStage = new Stage();
			responseStage.setTitle("Monthly Report");
			String response = " Error: Report not written\n " + e.getMessage();
			
			responseStage.setScene(new Scene(new Label(response), 400,300));
			responseStage.show();
			
		}
		
	}
	
	/**
	 * Returns the given string increased to the given length if it
	 * is not already longer than it.
	 * 
	 * @param string to increase length of
	 * @param length to increase string to
	 * @return string with increased length
	 */
	private static String increaseToLength(String string, int length) {
		String newString = string;
		if (string.length() < length) {
			int spaces = length - string.length();
			
			for (int i = 0; i < spaces; ++i) {
				newString = newString + " ";
			}
			
		}
		
		return newString;
	}
}

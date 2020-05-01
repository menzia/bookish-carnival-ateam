/**
 * ReportFarm.java 
 * Author:   Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
 * Date:     4/29/2020
 * 
 * Course:   CS400
 * Semester: Spring 2020 
 * Lecture:  001/002/001
 *
 * List Collaborators: 
 * 
 * Other Credits: 
 *
 * Know Bugs: 
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
 * 
 * ReportFarm - Generates a Stage displaying a Farm Report for the given
 * FarmLand object, given farm ID, and given year.
 * 
 * If data is invalid, displays an error message.
 *
 */
public class ReportFarm extends Stage {

	// Strings which will show up on left column for each month of year
	private static String[] monthStrings = { "01-Jan", " 02-Feb", " 03-Mar", " 04-Apr", " 05-May", " 06-Jun",
			" 07-Jul", " 08-Aug", " 09-Sep", " 10-Oct", " 11-Nov", " 12-Dec" };

	/**
	 * Displays a farm report to the screen using the given data
	 * 
	 * @param farmLand to get data from
	 * @param farmId   of farm to make report on
	 * @param yearNum  of year to make report on
	 * 
	 */
	ReportFarm(FarmLand farmLand, String farmId, Integer yearNum) {
		// Table it its containers
		Scene scene;
		TableView<FarmRow> table;
		VBox vbox = new VBox();

		// The columns used in the report
		TableColumn<FarmRow, String> monthCol = new TableColumn<FarmRow, String>("Month");
		TableColumn<FarmRow, String> weightCol = new TableColumn<FarmRow, String>("Total Milk Weight(lbs)");
		TableColumn<FarmRow, String> percentCol = new TableColumn<FarmRow, String>("Percentage of Total(%)");

		table = new TableView<FarmRow>();
		vbox = new VBox();

		scene = new Scene(new Group());
		table.setEditable(true);

		// month column
		monthCol.setMinWidth(100);
		monthCol.setCellValueFactory(new PropertyValueFactory<FarmRow, String>("month"));

		// weight column
		weightCol.setMinWidth(140);
		weightCol.setCellValueFactory(new PropertyValueFactory<FarmRow, String>("weight"));

		// percentage column
		percentCol.setMinWidth(140);
		percentCol.setCellValueFactory(new PropertyValueFactory<FarmRow, String>("percentage"));

		Label title = new Label();

		if (farmLand == null || farmId == null || yearNum == null) {
			title.setText("Error: Must choose Farm ID and Year");

		} else {
			// if inputs are valid, create table
			table.getColumns().addAll(monthCol, weightCol, percentCol);
			table.setItems(getData(farmLand, farmId, yearNum));
			title.setText("\"" + farmId + "\" Farm Report: " + yearNum);
		}

		// Format table and show it
		title.setFont(new Font("Arial", 20));

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(title, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		table.setEditable(true);
		this.setTitle("Farm Report");
		this.setWidth(450);
		this.setHeight(500);

		this.setScene(scene);
		this.show();
		// show scene

	}

	/**
	 * 
	 * FarmRow - Stores data for a single row of the farm report
	 *
	 */
	public static class FarmRow {
		private SimpleStringProperty month;// string rep of month
		private SimpleLongProperty weight;// weight in pounds
		private SimpleStringProperty percentage;// percentage of annual weight

		/**
		 * Constructs a row with the given data
		 * 
		 * @param month      String representation of month column
		 * @param weight     in pounds
		 * @param percentage of annual weight
		 */
		public FarmRow(String month, Long weight, String percentage) {
			this.month = new SimpleStringProperty(month);
			this.weight = new SimpleLongProperty(weight);
			this.percentage = new SimpleStringProperty(percentage);

		}

		public String getMonth() {
			return month.get();
		}

		public void setMonth(String month) {
			this.month.set(month);
		}

		public Long getWeight() {
			return weight.get();
		}

		public void setWeight(Integer w) {
			weight.set(w);
		}

		public String getPercentage() {
			return percentage.get();
		}

		public void setPercentage(String percentage) {
			this.percentage.set(percentage);
		}

	}

	/**
	 * Creates an ObservableList of FarmRows with the data corresponding to the
	 * given farmId in the given year, and returns it.
	 * 
	 * @param farmLand to obtain farm from
	 * @param farmId   of farm to make table on
	 * @param yearNum  of year to make table on
	 */
	private static ObservableList<FarmRow> getData(FarmLand farmLand, String farmId, Integer yearNum) {
		try {

			// stores rows in order
			ArrayList<FarmRow> monthList = new ArrayList<FarmRow>();

			if (farmLand.contains(farmId)) {
				// total weight across whole year for calculating percentage
				long totalWeight = farmLand.getFarm(farmId).getYearTotal(yearNum);

				for (int monthNum = 1; monthNum <= 12; ++monthNum) {

					// Calculate monthly weight and percentage of annual weight for this
					// month. The Double.MIN_NORMAL is added to the denominator to avoid
					// divide by zero errors.
					long weight = farmLand.getFarm(farmId).getMonthTotal(yearNum, monthNum);
					double percentage = 100 * weight / (totalWeight + Double.MIN_NORMAL);

					// Convert the month, weight, and percentage to strings for display
					String monthString = monthStrings[monthNum - 1];
					String percentageString = Double.toString(percentage);

					// Take four significant figures of percentage(plus the decimal point)
					int endIndex = Math.min(4, percentageString.length());
					percentageString = percentageString.substring(0, endIndex);

					// Some formating for special case of 100 and 0 percent
					if (percentageString.equals("100.")) {
						percentageString = "100";
					} else if (percentageString.equals("0.0")) {
						percentageString = "0";
					}

					// Add row of table with data for this month
					FarmRow newFarmRow = new FarmRow(monthString, weight, percentageString);

					monthList.add(newFarmRow);

				}
			}

			ObservableList<FarmRow> data = FXCollections.observableArrayList(monthList);

			return data;

		} catch (Exception e) {
			// In case unexpected exception occurs, return empty data set
			// This should never occur because the values inputed are checked
			// for validity before being entered here, but just in case
			ArrayList<FarmRow> monthList = new ArrayList<FarmRow>();
			FarmRow newFarmRow = new FarmRow("Error making table", 0L, e.getMessage());
			monthList.add(newFarmRow);

			return FXCollections.observableArrayList(monthList);
		}

	}

	/**
	 * Prints a farm report to the given directory in a .txt file
	 * 
	 * @param dirName directory to write report to
	 * @param farmLand to get data from
	 * @param farmId of farm to report on
	 * @param yearNum of year to report on
	 */
	public static void printToDirectory(String dirName,FarmLand farmLand, String farmId, int yearNum) {
		File file = new File(dirName + "/FarmReport-" + farmId + "-" + yearNum + ".txt");
		try {
		
			
			FileWriter wrter = new FileWriter(file);
			Farm farm = farmLand.getFarm(farmId);
			
			Long totalWeight = farm.getYearTotal(yearNum);
					
			wrter.write("Monthly Report: " + farmId + " : " + yearNum + "\n");
			wrter.write(" Month | Percent | Total Weight \n");
			
			for (int monthNum = 1; monthNum <= 12; ++monthNum) {				
				// Calculate monthly weight and percentage of annual weight for this
				// month. The Double.MIN_NORMAL is added to the denominator to avoid
				// divide by zero errors.
				long weight = farmLand.getFarm(farmId).getMonthTotal(yearNum, monthNum);
				double percentage = 100 * weight / (totalWeight + Double.MIN_NORMAL);

				// Convert the month, weight, and percentage to strings for display
				String monthString = monthStrings[monthNum - 1];
				String percentageString = Double.toString(percentage);

				// Take four significant figures of percentage(plus the decimal point)
				int endIndex = Math.min(4, percentageString.length());
				percentageString = percentageString.substring(0, endIndex);
				
				String line = monthString + " |   " + percentageString + "  | " + weight + "\n";
				wrter.write(line);
			}
			
			Stage responseStage = new Stage();
			responseStage.setTitle("Farm Report");
			String response = "Report successfully written to " + dirName;
			
			responseStage.setScene(new Scene(new Label(response), 400,300));
			responseStage.show();
			
			wrter.close();
			
		} catch (Exception e) {
			
			file.delete();
			
			Stage responseStage = new Stage();
			responseStage.setTitle("Farm Report");
			String response = " Error: Report not written\n " + e.getMessage();
			
			responseStage.setScene(new Scene(new Label(response), 400,300));
			responseStage.show();
			
		}
	}
}

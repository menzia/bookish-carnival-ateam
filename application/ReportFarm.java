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
 * ReportFarm - Generates a farm report for use in A2 GUI.
 *
 */
public class ReportFarm extends Stage {

	private Scene scene;
	private TableView<MonthRow> table;
	private VBox vbox = new VBox();

	// May want to move these into the constructor method instead of as
	// static fields, but not sure.
	private static TableColumn<MonthRow, String> monthCol = new TableColumn<MonthRow, String>("Month");
	private static TableColumn<MonthRow, String> totalWeights = new TableColumn<MonthRow, String>(
			"Total Milk Weight(lbs)");
	private static TableColumn<MonthRow, String> percent = new TableColumn<MonthRow, String>("Percentage of Total(%)");

	// Strings which will show up on left column for each month of year
	private static String[] monthStrings = { "01-Jan", "02-Feb", "03-Mar", "04-Apr", "05-May", "06-Jun", "07-Jul",
			"08-Aug", "09-Sep", "10-Oct", "11-Nov", "12-Dec" };

	/**
	 * Displays an example farm report to the screen
	 */
	ReportFarm(FarmLand farmLand, String farmId, Integer yearNum) {
		table = new TableView<MonthRow>();
		vbox = new VBox();

		scene = new Scene(new Group());
		table.setEditable(true);

		// month column
		monthCol.setMinWidth(100);
		monthCol.setCellValueFactory(new PropertyValueFactory<MonthRow, String>("month"));

		// total weights column
		totalWeights.setMinWidth(140);
		totalWeights.setCellValueFactory(new PropertyValueFactory<MonthRow, String>("weights"));

		// percentage column
		percent.setMinWidth(140);
		percent.setCellValueFactory(new PropertyValueFactory<MonthRow, String>("percentage"));

		Label title = new Label();

		if (farmLand == null || farmId == null || yearNum == null) {
			title.setText("Error: Must choose Farm ID and Year");

		} else {
			table.getColumns().addAll(monthCol, totalWeights, percent);
			table.setItems(getData(farmLand, farmId, yearNum));
			title.setText("\"" + farmId + "\" Farm Report: " + yearNum);
		}

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
	 * MonthRow - TODO Describe the purpose of this user-defined type
	 * 
	 * @author menzia (2020)
	 *
	 */
	public static class MonthRow {
		private SimpleStringProperty month;
		private SimpleLongProperty weights;
		private SimpleStringProperty percentage;

		private MonthRow(String month, Long weights, String percentage) {
			this.month = new SimpleStringProperty(month);
			this.weights = new SimpleLongProperty(weights);
			this.percentage = new SimpleStringProperty(percentage);

		}

		public String getMonth() {
			return month.get();
		}

		public void setMonth(String month) {
			this.month.set(month);
		}

		public Long getWeights() {
			return weights.get();
		}

		public void setWeights(Integer w) {
			weights.set(w);
		}

		public String getPercentage() {
			return percentage.get();
		}

		public void setPercentage(String percentage) {
			this.percentage.set(percentage);
		}

	}

	/**
	 * Creates an ObservableList of MonthRows with the data corresponding to the
	 * given farmId in the given year, and returns it.
	 * 
	 * @param farmLand to obtain farm from
	 * @param farmId   of farm to make table on
	 * @param yearNum  of year to make table on
	 * @return
	 */
	private static ObservableList<MonthRow> getData(FarmLand farmLand, String farmId, Integer yearNum) {
		ArrayList<MonthRow> monthList = new ArrayList<MonthRow>();

		if (farmLand.contains(farmId)) {
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

				// Row of table with data for this month
				MonthRow newMonthRow = new MonthRow(monthString, weight, percentageString);

				monthList.add(newMonthRow);

			}
		}

		ObservableList<MonthRow> data = FXCollections.observableArrayList(monthList);

		return data;

	}
}

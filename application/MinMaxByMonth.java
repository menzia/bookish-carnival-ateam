/**
 * MinMaxByMonth.java
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
 * MinMaxByMonth - Generates a Stage displaying a Min/Max/Avg report
 * by Month for the selected year and selected farm.
 * 
 */
public class MinMaxByMonth extends Stage {
	
	// The month strings which will appear on the left column
	private static String[] monthStr = { "01-Jan", "02-Feb", "03-Mar", "04-Apr", "05-May", "06-Jun", "07-Jul", "08-Aug",
			"09-Sep", "10-Oct", "11-Nov", "12-Dec" };

	/**
	 * Construct a Stage containing the table corresponding to the given data.
	 * If the data is invalid, will show an empty table. This should not
	 * occur as the choice of inputs should be restricted to valid inputs
	 * 
	 * @param farmLand to get data from
	 * @param farmId of farm to make table on
	 * @param yearNum of year to make table of
	 */
	public MinMaxByMonth(FarmLand farmLand, String farmId, Integer yearNum) {
		// The table and its containers
		Scene scene;
		TableView<MinMonthRow> table;
		VBox vbox = new VBox();
		
		// The rows of the table
		TableColumn<MinMonthRow, String> minCol;
		TableColumn<MinMonthRow, String> maxCol;
		TableColumn<MinMonthRow, String> avgCol;
		TableColumn<MinMonthRow, String> monthCol;
		
		minCol = new TableColumn<MinMonthRow, String>("min(lbs)");
		maxCol = new TableColumn<MinMonthRow, String>("max(lbs)");
		avgCol = new TableColumn<MinMonthRow, String>("Average(lbs)");
		monthCol = new TableColumn<MinMonthRow, String>("Month");

		table = new TableView<MinMonthRow>();
		vbox = new VBox();
		scene = new Scene(new Group());
		table.setEditable(true);

		// month column
		monthCol.setMinWidth(100);
		monthCol.setCellValueFactory(new PropertyValueFactory<MinMonthRow, String>("month"));
		// min column
		minCol.setMinWidth(140);
		minCol.setCellValueFactory(new PropertyValueFactory<MinMonthRow, String>("min"));
		// max column
		maxCol.setMinWidth(140);
		maxCol.setCellValueFactory(new PropertyValueFactory<MinMonthRow, String>("max"));
		// average column
		avgCol.setMinWidth(140);
		avgCol.setCellValueFactory(new PropertyValueFactory<MinMonthRow, String>("average"));

		Label title = new Label();

		if (farmLand == null || farmId == null || yearNum == null) {
			title.setText("Error: Must choose Farm ID and Year");

		} else {
			// if inputed data is valid, make table and display
			table.getColumns().addAll(monthCol, minCol, maxCol, avgCol);
			table.setItems(getData(farmLand, farmId, yearNum));
			title.setText("\"" + farmId + "\" Min/Max/Avg Daily Weight\n By Month For: " + yearNum);
		}

		// Format table and display
		title.setFont(new Font("Arial", 20));

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(title, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		table.setEditable(true);
		this.setTitle("Min/Max/Avg by Month Report");
		this.setWidth(600);
		this.setHeight(500);

		this.setScene(scene);
		this.show();
		// show scene

	}

	/**
	 * 
	 * MinMonthRow - contains data for a single row of the table
	 *
	 */
	public static class MinMonthRow {
		private SimpleStringProperty month;
		private SimpleLongProperty min;//minimum daily weight for this month
		private SimpleLongProperty max;//maximum daily weight for this month
		private SimpleLongProperty average;//average daily weight for this month

		/**
		 * Constructs a row with the given data
		 * 
		 * @param month string rep of month
		 * @param min minimum daily weight
		 * @param max maximum daily weight
		 * @param average average daily weight
		 */
		private MinMonthRow(String month, Long min, Long max, Long average) {
			this.month = new SimpleStringProperty(month);
			this.min = new SimpleLongProperty(min);
			this.max = new SimpleLongProperty(max);
			this.average = new SimpleLongProperty(average);

		}

		public String getMonth() {
			return month.get();
		}

		public void setMonth(String month) {
			this.month.set(month);
			;
		}

		public Long getMin() {
			return min.get();
		}

		public void setMin(Integer min) {
			this.min.set(min);
		}

		public Long getMax() {
			return max.get();
		}

		public void setMax(Integer max) {
			this.max.set(max);
		}

		public Long getAverage() {
			return average.get();
		}

		public void setAverage(Long average) {
			this.average.set(average);
		}

	}

	/**
	 * Returns a list containing the data for a table which uses the given farmId and the
	 * given year number. If farmId is not contained in farmLand, will return an empty list.
	 * 
	 * @param farmLand to get data from
	 * @param farmId of farm to make table on
	 * @param yearNum of year to make table on
	 * @return ObservableList containing the data in the table
	 */
	private static ObservableList<MinMonthRow> getData(FarmLand farmLand, String farmId, Integer yearNum) {
		try {
		// Contains the rows of the table
		ArrayList<MinMonthRow> monthList = new ArrayList<MinMonthRow>();
		
		if (farmLand.contains(farmId)) {
			
			//compute all values for each month and add the corresponding row
			for (int monthNum = 1; monthNum <= 12; ++monthNum) {
				
				long min = farmLand.getFarm(farmId).getMonth(yearNum, monthNum).getMin();
				long max = farmLand.getFarm(farmId).getMonth(yearNum, monthNum).getMax();
				long average = farmLand.getFarm(farmId).getMonth(yearNum, monthNum).getAverage();
				
				String monthString = monthStr[monthNum - 1];

				MinMonthRow newMinMonthRow = new MinMonthRow(monthString, min, max, average);

				monthList.add(newMinMonthRow);

			}

		}
		
		ObservableList<MinMonthRow> data = FXCollections.observableArrayList(monthList);

		return data;
		
		} catch (Exception e) {
			// If an unexpected exception is encountered, return empty list. In practice
			// this should never happen as the inputs are checked for correctness when
			// they are selected.
			ObservableList<MinMonthRow> data = FXCollections.observableArrayList(new ArrayList<MinMonthRow>());
			
			return data;
		}

	}

}

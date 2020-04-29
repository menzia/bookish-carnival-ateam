/**
 * RangeReport.java created by alexm on Dell Inspiron in ATeam.
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

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
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
 * RangeReport - TODO Describe the purpose of this user-defined type
 * 
 * @author menzia (2020)
 *
 */
public class RangeReport extends Stage {

	private Scene scene;
	private TableView<FarmRow> table;
	private VBox vbox = new VBox();

	// May want to move these into the constructor method instead of as
	// static fields, but not sure.
	private static TableColumn<FarmRow, String> farmCol = new TableColumn<FarmRow, String>("Farm ID");
	private static TableColumn<FarmRow, String> totalWeights = new TableColumn<FarmRow, String>(
			"Total Milk Weight(lbs)");
	private static TableColumn<FarmRow, String> percent = new TableColumn<FarmRow, String>("Percentage of Total(%)");

	/**
	 * Displays a Range Report to the screen TODO:Expand
	 */
	RangeReport(FarmLand farmLand, LocalDate start, LocalDate end) {
		table = new TableView<FarmRow>();
		vbox = new VBox();

		scene = new Scene(new Group());
		table.setEditable(true);

		// month column
		farmCol.setMinWidth(100);
		farmCol.setCellValueFactory(new PropertyValueFactory<FarmRow, String>("farm"));

		// total weights column
		totalWeights.setMinWidth(140);
		totalWeights.setCellValueFactory(new PropertyValueFactory<FarmRow, String>("weights"));

		// percentage column
		percent.setMinWidth(140);
		percent.setCellValueFactory(new PropertyValueFactory<FarmRow, String>("percentage"));

		Label title = new Label();

		if (farmLand == null || start == null || end == null) {
			title.setText("Error: Must choose start and end dates");

		} else {
			table.getColumns().addAll(farmCol, totalWeights, percent);
			table.setItems(getData(farmLand, start, end));
			title.setText("Range Report: " + start + " until " + end);

		}

		title.setFont(new Font("Arial", 20));

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(title, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		table.setEditable(false);
		this.setTitle("Range Report");
		this.setWidth(450);
		this.setHeight(500);

		this.setScene(scene);
		this.show();
		// show scene

	}

	/**
	 * 
	 * FarmRow - TODO Describe the purpose of this user-defined type
	 * 
	 * @author menzia (2020)
	 *
	 */
	public static class FarmRow {
		private SimpleStringProperty farm;
		private SimpleLongProperty weights;
		private SimpleStringProperty percentage;

		private FarmRow(String farm, Long weights, String percentage) {
			this.farm = new SimpleStringProperty(farm);
			this.weights = new SimpleLongProperty(weights);
			this.percentage = new SimpleStringProperty(percentage);

		}

		public String getFarm() {
			return farm.get();
		}

		public void setFarm(String farm) {
			this.farm.set(farm);
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

		public void setPercent(String percentage) {
			this.percentage.set(percentage);
		}

	}

	/**
	 * Creates an ObservableList of FarmRows with the data corresponding to the
	 * given range and returns it
	 * 
	 * @param FarmLand  farmLand to obtain data from
	 * @param LocalDate start of range
	 * @param LocalDate end of range
	 * @return an ObservableList with data for Annual Report
	 */
	private static ObservableList<FarmRow> getData(FarmLand farmLand, LocalDate start, LocalDate end) {
		ArrayList<FarmRow> farmList = new ArrayList<FarmRow>();

		int startYear = start.getYear();
		int startMonth = start.getMonthValue();
		int startDay = start.getDayOfMonth();

		int endYear = end.getYear();
		int endMonth = end.getMonthValue();
		int endDay = end.getDayOfMonth();

		long totalWeight = farmLand.getAllFarmRange(startYear, startMonth, startDay, endYear, endMonth, endDay);

		for (String farm : farmLand.getFarms()) {

			// Calculate weight for this farm and percentage of total weight for the range
			// The Double.MIN_NORMAL is added to the denominator to avoid
			// divide by zero errors.
			long weight = farmLand.getFarm(farm).getRange(startYear, startMonth, startDay, endYear, endMonth, endDay);
			double percentage = 100 * weight / (totalWeight + Double.MIN_NORMAL);

			// Convert the month, weight, and percentage to strings for display
			String farmString = farm;
			String percentageString = Double.toString(percentage);

			// Take four significant figures of percentage(plus the decimal point)
			int endIndex = Math.min(4, percentageString.length());
			percentageString = percentageString.substring(0, endIndex);

			// Row of table with data for this farm
			FarmRow newFarmRow = new FarmRow(farmString, weight, percentageString);

			farmList.add(newFarmRow);

		}

		ObservableList<FarmRow> data = FXCollections.observableArrayList(farmList);

		return data;

	}
}

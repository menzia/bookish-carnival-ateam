/**
 * MinMaxByFarm.java
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

public class MinMaxByFarm extends Stage {

	private Scene scene;
	private TableView<byFarmRow> table;
	private VBox vbox = new VBox();
	// for all farms feature
	// May want to move these into the constructor method instead of as
	// static fields, but not sure.
	private static TableColumn<byFarmRow, String> farmCol = new TableColumn<byFarmRow, String>("Farm ID");
	private static TableColumn<byFarmRow, String> Min = new TableColumn<byFarmRow, String>("Min");
	private static TableColumn<byFarmRow, String> Max = new TableColumn<byFarmRow, String>("Max");
	private static TableColumn<byFarmRow, String> Avg = new TableColumn<byFarmRow, String>("Avg");

	/**
	 * Creates the center dialogue box which allows the user to select the Min/Max
	 * report they would like to create. In actual implementation will need to pass
	 * in access to the FarmLand object so that a report can be created
	 * 
	 * @return VBox containg UI for Min/Max display
	 */
	/**
	 * Displays an example farm report to the screen
	 */
	MinMaxByFarm(FarmLand farmLand, Integer yearNum, Integer month) {
		table = new TableView<byFarmRow>();
		vbox = new VBox();

		scene = new Scene(new Group());
		table.setEditable(true);

		// month column
		farmCol.setMinWidth(100);
		farmCol.setCellValueFactory(new PropertyValueFactory<byFarmRow, String>("farm"));

		// Min column
		Min.setMinWidth(140);
		Min.setCellValueFactory(new PropertyValueFactory<byFarmRow, String>("Min"));

		// Max column
		Max.setMinWidth(140);
		Max.setCellValueFactory(new PropertyValueFactory<byFarmRow, String>("Max"));

		// Avg column
		Avg.setMinWidth(140);
		Avg.setCellValueFactory(new PropertyValueFactory<byFarmRow, String>("Avg"));

		Label title = new Label();

		if (farmLand == null || yearNum == null || month == null) {
			title.setText("Error: Must choose year and month");

		} else {
			table.getColumns().addAll(farmCol, Min, Max, Avg);
			table.setItems(getData(farmLand, yearNum, month));
			title.setText("Display Min/Max/Avg From All Farms " + '\n' + "For : " + yearNum + "/" + month);

		}

		title.setFont(new Font("Arial", 20));

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(title, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		table.setEditable(true);
		this.setTitle("Min/Max/Avg by all Farms Report");
		this.setWidth(549);
		this.setHeight(500);

		this.setScene(scene);
		this.show();
		// show scene

	}

	/**
	 * 
	 * byFarmRow - TODO Describe the purpose of this user-defined type
	 * 
	 * @author Ethan Huang (2020)
	 *
	 */
	public static class byFarmRow {
		private SimpleStringProperty farm;
		private SimpleLongProperty min;
		private SimpleLongProperty max;
		private SimpleLongProperty avg;

		private byFarmRow(String farm, Long min, Long max, Long avg) {
			this.farm = new SimpleStringProperty(farm);
			this.min = new SimpleLongProperty(min);
			this.max = new SimpleLongProperty(max);
			this.avg = new SimpleLongProperty(avg);
		}

		public String getFarm() {
			return farm.get();
		}

		public void setFarm(String farm) {
			this.farm.set(farm);
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

		public Long getAvg() {
			return avg.get();
		}

		public void setAvg(Long avg) {
			this.avg.set(avg);
		}

	}

	/**
	 * getData method
	 * 
	 * @param farmLand
	 * @param yearNum
	 * @param month
	 * @return
	 */
	private static ObservableList<byFarmRow> getData(FarmLand farmLand, Integer yearNum, Integer month) {
		ArrayList<byFarmRow> farmList = new ArrayList<byFarmRow>();

		long totalWeight = farmLand.getAllFarmsYearTotal(yearNum);

		for (String farm : farmLand.getFarms()) {

			String farmString = farm;

			long max = farmLand.getFarm(farm).getMonth(yearNum, month).getMax();
			long avg = farmLand.getFarm(farm).getMonth(yearNum, month).getAverage();
			long min = farmLand.getFarm(farm).getMonth(yearNum, month).getMin();

			// Row of table with data for this year / month
			byFarmRow newFarmRow = new byFarmRow(farmString, min, max, avg);

			farmList.add(newFarmRow);
		}

		ObservableList<byFarmRow> data = FXCollections.observableArrayList(farmList);

		return data;

	}

}

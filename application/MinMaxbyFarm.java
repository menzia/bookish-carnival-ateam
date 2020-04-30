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
 * MinMaxByFarm - Generates a Stage displaying a Min/Max/Avg report
 * by Farm for the selected month
 */
public class MinMaxByFarm extends Stage {

	/**
	 * Creates the center dialogue box which allows the user to select the Min/Max
	 * report they would like to create. In actual implementation will need to pass
	 * in access to the FarmLand object so that a report can be created
	 * 
	 * @return VBox containing UI for Min/Max display by farm
	 */
	MinMaxByFarm(FarmLand farmLand, Integer yearNum, Integer month) {
		// The table and its containers
		Scene scene;
		TableView<MinFarmRow> table;
		VBox vbox = new VBox();

		// Columns of the table
		TableColumn<MinFarmRow, String> farmCol = new TableColumn<MinFarmRow, String>("Farm ID");
		TableColumn<MinFarmRow, String> minCol = new TableColumn<MinFarmRow, String>("Min");
		TableColumn<MinFarmRow, String> maxCol = new TableColumn<MinFarmRow, String>("Max");
		TableColumn<MinFarmRow, String> avgCol = new TableColumn<MinFarmRow, String>("Avg");

		table = new TableView<MinFarmRow>();
		vbox = new VBox();

		scene = new Scene(new Group());
		table.setEditable(true);

		// month column
		farmCol.setMinWidth(100);
		farmCol.setCellValueFactory(new PropertyValueFactory<MinFarmRow, String>("farm"));

		// Min column
		minCol.setMinWidth(140);
		minCol.setCellValueFactory(new PropertyValueFactory<MinFarmRow, String>("Min"));

		// Max column
		maxCol.setMinWidth(140);
		maxCol.setCellValueFactory(new PropertyValueFactory<MinFarmRow, String>("Max"));

		// Avg column
		avgCol.setMinWidth(140);
		avgCol.setCellValueFactory(new PropertyValueFactory<MinFarmRow, String>("Avg"));

		Label title = new Label();

		if (farmLand == null || yearNum == null || month == null) {
			title.setText("Error: Must choose year and month");

		} else {
			// if inputs are valid, make talbe
			table.getColumns().addAll(farmCol, minCol, maxCol, avgCol);
			table.setItems(getData(farmLand, yearNum, month));
			title.setText("Display Min/Max/Avg From All Farms " + '\n' + "For The Month : " + yearNum + "/" + month);

		}

		// Format table and display
		title.setFont(new Font("Arial", 20));

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(title, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		table.setEditable(false);
		this.setTitle("Min/Max/Avg by all Farms Report");
		this.setWidth(600);
		this.setHeight(400);

		this.setScene(scene);
		this.show();
		// show scene

	}

	/**
	 * Stores the data for a single row of the min/max by farm table.
	 */
	public static class MinFarmRow {
		private SimpleStringProperty farm;
		private SimpleLongProperty min;// minimum daily weight for this farm
		private SimpleLongProperty max;// maximum daily weight for this farm
		private SimpleLongProperty avg;// average daily weight for this farm

		/**
		 * Constructs a new row with the given data
		 * 
		 * @param farm id of farm to make row on
		 * @param min  minimum daily weight for this farm
		 * @param max  maximum daily weight for this farm
		 * @param avg  average daily weight for this farm
		 */
		public MinFarmRow(String farm, Long min, Long max, Long avg) {
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
	 * Returns a list containing the data for a table using the given month If the
	 * inputs are invalid, will return an empty list.
	 * 
	 * @param farmLand to get data from
	 * @param yearNum  of month to make table on
	 * @param monthNum number of month to make table on
	 * @return
	 */
	private static ObservableList<MinFarmRow> getData(FarmLand farmLand, Integer yearNum, Integer monthNum) {
		try {
			// Stores rows of table in order
			ArrayList<MinFarmRow> farmList = new ArrayList<MinFarmRow>();

			for (String farm : farmLand.getFarms()) {

				String farmString = farm;

				// Calculate the entries in the row
				long max = farmLand.getFarm(farm).getMonth(yearNum, monthNum).getMax();
				long avg = farmLand.getFarm(farm).getMonth(yearNum, monthNum).getAverage();
				long min = farmLand.getFarm(farm).getMonth(yearNum, monthNum).getMin();

				// Row of table with data for this month
				MinFarmRow newFarmRow = new MinFarmRow(farmString, min, max, avg);

				farmList.add(newFarmRow);
			}

			ObservableList<MinFarmRow> data = FXCollections.observableArrayList(farmList);

			return data;
			
		} catch (Exception e) {
			// If an unexpected exception is encountered, return empty list. In practice
			// this should never happen as the inputs are checked for correctness when
			// they are selected.
			System.out.println(e.getMessage());
			ObservableList<MinFarmRow> data = FXCollections.observableArrayList(new ArrayList<MinFarmRow>());

			return data;

		}
	}

}

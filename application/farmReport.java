/**
 * farmReport.java 
 * Author:   Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu)
 * Date:     @date
 * 
 * Course:   CS400
 * Semester: Spring 2020 
 * Lecture:  001
 * 
 * IDE:      Eclipse IDE for Java Developers
 * Version:  2019-12
 * Build id:
 * 
 * Device:
 * OS:
 * Version:
 * OS Build:
 *
 * List Collaborators: name, wisc.edu, lecture number
 * 
 * Other Credits: Other source, website or people
 *
 * Know Bugs: known unresolved bugs
 */
package application;
import java.util.zip.Adler32;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 
 * farmReport - Generates an example farm report for use in A2 GUI.
 * Should not need to be used in the final project as the FarmLand section
 * should take care of it on it's own.
 * 
 *
 */
public class farmReport extends Stage {
	
	//Hardcoded parts below would be obtained from the FarmLand class
	Integer year = 2018;
	
	String farmId = "Farm 6";
	
	Scene scene;
	// ScrollPane sp = new ScrollPane();
	BorderPane pane = new BorderPane();
	private TableView<Month> table = new TableView<Month>();
	private final ObservableList<Month> data = FXCollections.observableArrayList(
			new Month("01 Jan", "100", "05.00"),
			new Month("02 Feb", "100", "05.00"),
			new Month("03 Mar", "200", "10.00"),
			new Month("04 Apr", "200", "10.00"),
			new Month("05 May", "400", "20.00" ),
			new Month("06 Jun", "200", "10.00"),
			new Month("07 Jul", "200", "10.00"),
			new Month("08 Aug", "400","20.00"),
			new Month("09 Sept", "200","10.00"),
			new Month("10 Oct", "0", "00.00"),
			new Month("11 Nov", "0","00.00"),
			new Month("12 Dec", "0","00.00") );

	TableColumn monthCol = new TableColumn("Month");
	TableColumn totalWeights = new TableColumn("Total Milk Weight(lbs)");
	TableColumn percent = new TableColumn("Percentage of Total(%)");
	VBox vbox = new VBox();

	/**
	 * Displays an example farm report to the screen
	 */
	farmReport() {
		scene = new Scene(new Group());
		table.setEditable(true);
		// month column
		monthCol.setMinWidth(100);
		monthCol.setCellValueFactory(new PropertyValueFactory<Month, String>("month"));

		// total weights column
		totalWeights.setMinWidth(140);
		totalWeights.setCellValueFactory(new PropertyValueFactory<Month, String>("weights"));
		// percentage column
		percent.setMinWidth(140);
		percent.setCellValueFactory(new PropertyValueFactory<Month, String>("percent"));

		//
		table.setItems(data);
		table.getColumns().addAll(monthCol, totalWeights, percent);

		final Label label = new Label(farmId + " Report " + year);
		label.setFont(new Font("Arial", 20));
		
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		table.setEditable(true);
		this.setTitle("Farm Report");
		this.setWidth(450);
		this.setHeight(500);

		this.setScene(scene);
		// stage.setScene(sp);
		this.show();
		// show scene

	}

	public static class Month {
		private final SimpleStringProperty month;
		private final SimpleStringProperty weights;
		private final SimpleStringProperty percentag;

		private Month(String month, String weights, String percentag) {
			this.month = new SimpleStringProperty(month);
			this.weights = new SimpleStringProperty(weights);
			this.percentag = new SimpleStringProperty(percentag);

		}

		public String getMonth() {
			return month.get();
		}

		public void setMon(String month) {
			this.month.set(month);
		}

		public String getWeights() {
			return weights.get();
		}

		public void setWeights(String w) {
			weights.set(w);
		}

		public String getPercent() {
			return percentag.get();
		}

		public void setPercent(String percentage) {
			this.percentag.set(percentage);
		}

	}
}

package application;
/**
 * Main.java created by {user} on Surface Pro 7 in ateam_MilkWeights
 * Author:   Linyi Lyu (llyu4@wisc.edu)
 * 			 Ethan Huang (ihuang22@wisc.edu)
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

public class farmReport extends Stage {
	Scene scene;
	// ScrollPane sp = new ScrollPane();
	BorderPane pane = new BorderPane();
	private TableView<month> table = new TableView<month>();
	private final ObservableList<month> data = FXCollections.observableArrayList(
			new month("Jan", "234", "12.34"),
			new month("Feb", "326", "13.24"),
			new month("March", "135", "7.362"),
			new month("April", "635", "40.54"));

	TableColumn monthCol = new TableColumn("month");
	TableColumn totalWeights = new TableColumn("Total Milk Weight(lbs)");
	TableColumn percent = new TableColumn("Percentage of All(%)");
	final VBox vbox = new VBox();

	farmReport() {
		scene = new Scene(new Group());
		table.setEditable(true);
		// month column
		monthCol.setMinWidth(100);
		monthCol.setCellValueFactory(new PropertyValueFactory<month, String>("month"));

		// total weights column
		totalWeights.setMinWidth(140);
		totalWeights.setCellValueFactory(new PropertyValueFactory<month, String>("weights"));
		// percentage column
		percent.setMinWidth(140);
		percent.setCellValueFactory(new PropertyValueFactory<month, String>("percent"));

		//
		table.setItems(data);
		table.getColumns().addAll(monthCol, totalWeights, percent);

		int id = 6;
		int year = 2018;
		final Label label = new Label("Farm " + id + " REPORT " + year);
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

	public static class month {
		private final SimpleStringProperty month;
		private final SimpleStringProperty weights;
		private final SimpleStringProperty percentag;

		private month(String month, String weights, String percentag) {
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

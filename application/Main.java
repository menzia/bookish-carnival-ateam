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
package application;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Main - TODO Describe purpose of this user defined type
 * 
 * @author Linyi Lyu (2020)
 * @author Ethan Huang (2020)
 *
 */
public class Main extends Application {
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 800;
	private static final String APP_TITLE = "Milk_Weight";

	@Override
	public void start(Stage primaryStage) throws Exception {

		// left panel
		BorderPane bd = new BorderPane();
		bd.setLeft(left());
		
		// Scene
		Scene scene_1 = new Scene(bd, WINDOW_WIDTH, WINDOW_HEIGHT);

		// set stage
		primaryStage.setScene(scene_1);
		primaryStage.show();

	}

	public VBox left() {
		VBox leftvb = new VBox();
		leftvb.setSpacing(10);
		leftvb.setStyle("-fx-background-color: #336699;");
		
		
		// Buttons on the left VBox		
		Button upcsv = new Button("Upload CSV");
		upcsv.setPrefSize(100, 20);
		
		Button edit = new Button("Add/Edit/Remove(day)");
		edit.setPrefSize(100, 20);
		
		Button minmax = new Button("Display Max/Min/Avg");
		minmax.setPrefSize(100, 20);
		
		Button report = new Button("Generate Report");
		report.setPrefSize(100, 20);
		
		
		leftvb.getChildren().addAll(upcsv, edit, minmax, report);
		return leftvb;
	}

	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}

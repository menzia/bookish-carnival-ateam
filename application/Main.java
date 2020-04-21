/**
 * Main.java created by {user} on Alienware M15 in ateam_MilkWeights
 * Author:   Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
 * @author Alex Menzia (2020)
 *
 */
public class Main extends Application {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	private static final String APP_TITLE = "Milk_Weight System";


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// left panel
		BorderPane bd = new BorderPane();
		bd.setLeft(menuButton(bd,primaryStage));

		// Scene
		Scene scene_1 = new Scene(bd, WINDOW_WIDTH, WINDOW_HEIGHT);

		// set stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(scene_1);
		primaryStage.show();

	}

	public VBox menuButton(BorderPane bd, Stage primaryStage) {
		
		// BorderPane mbp = new BorderPane();
		VBox leftvb = new VBox();
		leftvb.setSpacing(10);
		leftvb.setStyle("-fx-background-color: #6699FF;");

		// Buttons on the left VBox
		ToggleGroup group = new ToggleGroup();
		
		RadioButton upcsv = new RadioButton("Import/Export Data");
		upcsv.setOnAction(e ->bd.setCenter(ChooseFile.chooseFileDialogue(primaryStage)));
		upcsv.setPrefSize(150, 20);
		upcsv.setToggleGroup(group);
		upcsv.setSelected(true);
		
		HBox csvTemp = new HBox();
		csvTemp.getChildren().addAll(upcsv);
		

		RadioButton edit = new RadioButton("Add/Edit/Remove(day)");
		edit.setToggleGroup(group);
		edit.setPrefSize(150, 20);

		RadioButton minmax = new RadioButton("Display Max/Min/Avg");
		minmax.setToggleGroup(group);
		minmax.setPrefSize(150, 20);

		RadioButton report = new RadioButton("Generate Report");
		report.setToggleGroup(group);
		
	
		HBox reportTemp = new HBox();
		reportTemp.getChildren().addAll(report);
		report.setPrefSize(150, 20);
		report.setOnAction(e ->{
			bd.setCenter(TabPaneGenerateReport.tabPane());
		});
		
		leftvb.getChildren().addAll(csvTemp, reportTemp, edit, minmax);
		
		//Start off in the import/export dialogue box since the user
		//Will need to do this before generating reports
		bd.setCenter(ChooseFile.chooseFileDialogue(primaryStage));

		return leftvb;
	}


}

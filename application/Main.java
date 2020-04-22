/**
 * Main.java 
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
 * List Collaborators: n/a
 * 
 * Other Credits: n/a
 *
 * Know Bugs: n/a
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
import javafx.geometry.Pos;
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
 * Main - Displays a GUI which a user can interact with to load/edit/view
 * Data about the milk weight output of various farms.
 * 
 * @author Linyi Lyu (2020)
 * @author Ethan Huang (2020)
 * @author Alex Menzia (2020)
 *
 */
public class Main extends Application {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	private static final String APP_TITLE = "Milk Weight System";


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	/**
	 * Defines the main section of the GUI
	 */
	public void start(Stage primaryStage) throws Exception {

		// Border Pane which will contain main selections in the
		// left panel and specific selections in the center panel
		BorderPane bd = new BorderPane();
		
		// Add the main selections on left panel
		bd.setLeft(menuButton(bd,primaryStage));

		// Scene
		Scene scene_1 = new Scene(bd, WINDOW_WIDTH, WINDOW_HEIGHT);

		// set stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(scene_1);
		primaryStage.show();

	}

	/**
	 * Creates a vertical box containing the main menu options
	 * for the milk weight system: 1)Import/Export 2)Report
	 * 3)Edit Data 4)Min/Max/Avg.
	 * 
	 * @param bd border pane used in primaryStage
	 * @param primaryStage primary stage of the GUI
	 * @return vertical box containing buttons for the four menu options
	 */
	public VBox menuButton(BorderPane bd, Stage primaryStage) {
		
		// Box which will contain all menu options
		VBox leftvb = new VBox();
		leftvb.setSpacing(80);
		leftvb.setStyle("-fx-background-color: #6699FF;");
		leftvb.setAlignment(Pos.CENTER);

		
		ToggleGroup group = new ToggleGroup();
		
		// Button taking user to Import/Export screen
		// Will be taken here by default on start up
		RadioButton impExp = new RadioButton("Import/Export Data");
		impExp.setOnAction(e ->bd.setCenter(ChooseFile.chooseFileDialogue(primaryStage)));
		impExp.setPrefSize(150, 20);
		impExp.setToggleGroup(group);
		impExp.setSelected(true);
		
		// Button taking user to a Report screen
		// Once there, they can select what kind of
		// report they want to create
		RadioButton report = new RadioButton("Generate Report");
		report.setToggleGroup(group);
		report.setPrefSize(150, 20);
		report.setOnAction(e ->{
			bd.setCenter(TabPaneGenerateReport.tabPane());
		});
		
		// Button taking user to screen where they can
		// edit the milk weight data.
		RadioButton edit = new RadioButton("Edit Data");
		edit.setOnAction(e -> bd.setCenter(TabPaneEditData.tabPane()));
		edit.setToggleGroup(group);
		edit.setPrefSize(150, 20);

		// Button taking user to screen where they can
		// generate tables displaying minimum, maximum,
		// and average milk weights in several categories
		RadioButton minmax = new RadioButton("Display Max/Min/Avg");
		minmax.setOnAction(e -> bd.setCenter(TabPaneMinMax.tabPane()));
		minmax.setToggleGroup(group);
		minmax.setPrefSize(150, 20);

		//Add all four buttons to the vertical box
		leftvb.getChildren().addAll(impExp, report, edit, minmax);
		
		//Start off in the import/export dialogue box since the user
		//Will need to do this before generating reports
		bd.setCenter(ChooseFile.chooseFileDialogue(primaryStage));

		return leftvb;
	}


}

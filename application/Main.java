/**
 * Main.java 
 * Author:   Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
 * Date:     4/29/2020
 * 
 * Course:   CS400
 * Semester: Spring 2020 
 * Lecture:  001/002/001
 *
 * List Collaborators: n/a
 * 
 * Other Credits: n/a
 *
 * Know Bugs: n/a
 */
package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
	
	private FarmLand farmLand;


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

		farmLand = new FarmLand();
		
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
	private VBox menuButton(BorderPane bd, Stage primaryStage) {
		
		// Box which will contain all menu options
		VBox leftvb = new VBox();
		leftvb.setSpacing(60);
		leftvb.setStyle("-fx-background-color: #6699FF;");
		leftvb.setAlignment(Pos.CENTER);

		
		ToggleGroup group = new ToggleGroup();
		
		// Button taking user to Import/Export screen
		// Will be taken here by default on start up
		RadioButton impExp = new RadioButton("Import/Export Data");
		impExp.setOnAction(e ->bd.setCenter(ChooseFile.chooseFileDialogue(primaryStage,farmLand)));
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
			bd.setCenter(ChooseGenerateReport.tabPane(farmLand));
		});
		
		// Button taking user to screen where they can
		// edit the milk weight data.
		RadioButton edit = new RadioButton("Edit Data");
		edit.setOnAction(e -> {
			ChooseEditData editData = new ChooseEditData(farmLand);
			bd.setCenter(editData.tabPane());
		});
		edit.setToggleGroup(group);
		edit.setPrefSize(150, 20);

		// Button taking user to screen where they can enter
		// a farm id to add to their structure.
		RadioButton add = new RadioButton("Add Farm");
		add.setOnAction(e -> {
			ChooseAddFarm addFarm = new ChooseAddFarm(farmLand);
			bd.setCenter(addFarm.addFarmBox());
		});
		add.setToggleGroup(group);
		add.setPrefSize(150, 20);
		
		// Button taking user to screen where they can
		// generate tables displaying minimum, maximum,
		// and average milk weights in several categories
		RadioButton minmax = new RadioButton("Display Max/Min/Avg");
		minmax.setOnAction(e -> {
		    ChooseMinMax minmaxData = new ChooseMinMax(farmLand);
		    bd.setCenter(minmaxData.tabPane());
		    });
		minmax.setToggleGroup(group);
		minmax.setPrefSize(150, 20);

		//Add all four buttons to the vertical box
		leftvb.getChildren().addAll(impExp, report, edit, add, minmax);
		
		//Start off in the import/export dialogue box since the user
		//Will need to do this before generating reports
		bd.setCenter(ChooseFile.chooseFileDialogue(primaryStage,farmLand));

		return leftvb;
	}


}

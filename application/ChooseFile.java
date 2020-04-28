/**
 * ChooseFile.java 
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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.*;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.*;
import java.io.*;
import java.util.zip.DataFormatException;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * ChooseFile - Contains method chooseFileDialogue(), which returns a vertical
 * box containg the input structure for getting the user to choose a directory
 * to import data from or to export data to.
 * 
 *
 */
public class ChooseFile {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 200;

	/**
	 * Creates a stage in which the user can choose either to import data to their
	 * project or export the data in their project to a specified directory.
	 * 
	 * TODO:Once data structure section is finished, this will need to have some way
	 * to access it in order to modify it(ie by passing a FarmLand object as one of
	 * the arguments)
	 * 
	 */
	static public VBox chooseFileDialogue(Stage stage, FarmLand farmLand) {

		// Top most section will consist of two radio buttons
		// which user can use to choose between importing and
		// exporting
		HBox topSelection = new HBox();
		topSelection.setAlignment(Pos.CENTER);
		topSelection.setSpacing(20);

		ToggleGroup group = new ToggleGroup();
		RadioButton importButton = new RadioButton("Import Data");
		importButton.setToggleGroup(group);
		importButton.setSelected(true);

		RadioButton exportButton = new RadioButton("Export Data");
		exportButton.setToggleGroup(group);

		topSelection.getChildren().addAll(importButton, exportButton);

		// Next section will be a button which the user can
		// press to open a dialogue where they can choose the
		// directory they want to import or export.
		DirectoryChooser chooser = new DirectoryChooser();
		// display the user's chosen directory
		Label confirmLabel = new Label("No Import Directory Selected");
		// user presses this to confirm import/export
		Button confirmButton = new Button("Import");
		HBox confirmBar = new HBox(20, confirmLabel, confirmButton);
		confirmBar.setAlignment(Pos.CENTER);

		// User presses this to open a dialogue where they can choose a directory
		Button fileButton = new Button("Choose a Directory to Import From");

		// When import button is pressed, switch all to import mode
		importButton.setOnAction(e -> {
			fileButton.setText("Choose a Directory to Import From");
			confirmLabel.setText("No Import Directory Selected");
			confirmButton.setText("Import");

		});

		// When export button is pressed, switch all to export mode
		exportButton.setOnAction(e -> {
			fileButton.setText("Choose a Directory to Export To");
			confirmLabel.setText("No Export Directory Selected");
			confirmButton.setText("Export");
		});

		// When pressed, file button opens directory dialogue and attempts to import
		// from
		// or export to the given directory
		fileButton.setOnAction(e -> {
			if (importButton.isSelected()) {
				// try to import the directory selected
				try {
					File dir = chooser.showDialog(stage);
					farmLand.processDirectory(dir);

					Scene scene = new Scene(new Label(dir.getAbsolutePath() + " Successfully Read"), WINDOW_WIDTH,
							WINDOW_HEIGHT);

					Stage successStage = new Stage();

					successStage.setTitle("Success");
					successStage.setScene(scene);
					successStage.show();

					// Print success message
				} catch (Exception f) {

					Scene scene = new Scene(new Label("Directory not fully read: " + f.getMessage()));

					Stage failStage = new Stage();

					failStage.setTitle("Failure");
					failStage.setScene(scene);
					failStage.show();

					// print error message
				}

			} else {

				// try to export the directory selected
				try {
					File dir = chooser.showDialog(stage);
					farmLand.exportToDirectory(dir);

					Scene scene = new Scene(new Label("Successfully Exported to " + dir.getAbsolutePath()), WINDOW_WIDTH,
							WINDOW_HEIGHT);

					Stage successStage = new Stage();

					successStage.setTitle("Success");
					successStage.setScene(scene);
					successStage.show();

					// Print success message
				} catch (Exception f) {

					Scene scene = new Scene(new Label("Directory not fully exported: " + f.getMessage()));

					Stage failStage = new Stage();

					failStage.setTitle("Failure");
					failStage.setScene(scene);
					failStage.show();

					// print error message
				}
			}
		});

		// When pressed, will actually execute the users selected
		// import/export
		// TODO:Remove confirm button and simply automatically import whatever directory
		// is selected
		confirmButton.setOnAction(e -> {
			if (importButton.isSelected()) {
				// TODO: try to import from selected file

			} else {
				// TODO:try to export to selected file
			}
		});

		// create a VBox containing all above parts
		VBox vbox = new VBox(30, topSelection, confirmBar, fileButton);
		vbox.setAlignment(Pos.CENTER);

		return vbox;

	}
}

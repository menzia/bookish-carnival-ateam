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
 * List Collaborators: name, wisc.edu, lecture number
 * 
 * Other Credits: Other source, website or people
 *
 * Know Bugs: known unresolved bugs
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class ChooseFile {
	
	boolean importDirectory = true;

	/**
	 * Creates a stage in which the user can choose either to
	 * import data to their project or export the data in their
	 * project to a specified directory.
	 * 
	 * TODO:Once data structure section is finished,
	 * this will need to have some way to access it in order
	 * to modify it.
	 * 
	 */
	static public VBox chooseFileDialogue(Stage stage) {
		
		//Top most section will consist of two radio buttons
		//which user can use to choose between importing and
		//exporting
		HBox topSelection = new HBox();
		topSelection.setAlignment(Pos.CENTER);
		topSelection.setSpacing(20);
		
		ToggleGroup group = new ToggleGroup();
	    RadioButton importButton = new RadioButton("Import Data");
	    importButton.setToggleGroup(group);
	    importButton.setSelected(true);
	    RadioButton exportButton = new RadioButton("Export Data");
	    exportButton.setToggleGroup(group);
		
		topSelection.getChildren().addAll(importButton,exportButton);
		
		//Next section will be a button which the user can
		//press to open a dialogue where they can choose the
		//directory they want to import or export.
		DirectoryChooser chooser = new DirectoryChooser();
		Label confirmLabel = new Label("No Import Directory Selected");
		Button confirmButton = new Button("Import");
		HBox confirmBar = new HBox(20,confirmLabel,confirmButton);
		confirmBar.setAlignment(Pos.CENTER);
		
		Button fileButton = new Button("Choose a Directory to Import From");
		
		importButton.setOnAction(e -> {
			fileButton.setText("Choose a Directory to Import From");
			confirmLabel.setText("No Import Directory Selected");
			confirmButton.setText("Import");
		
		});
		
		exportButton.setOnAction(e -> {
			fileButton.setText("Choose a Directory to Export To");
			confirmLabel.setText("No Export Directory Selected");
			confirmButton.setText("Export");
		});

		// When pressed, button opens directory dialogue and attempts to import from
		// or export to the given directory 
		fileButton.setOnAction(e -> {if (importButton.isSelected()){
			// try to import the directory selected
			File file = chooser.showDialog(stage);

			if (file != null) {

				confirmLabel.setText(file.getAbsolutePath() + " Selected");
			}
		} else {
			//try to export to directory selected
			File file = chooser.showDialog(stage);
			
			if (file != null) {
				confirmLabel.setText(file.getAbsolutePath() + " Selected");
			}
		}});
		
		confirmButton.setOnAction(e ->{
			if (importButton.isSelected()) {
				//TODO: try to import from selected file
				
			} else {
				//TODO:try to export to selected file
			}
		});
		
		// create a VBox
		VBox vbox = new VBox(30, topSelection,confirmBar, fileButton);
		vbox.setAlignment(Pos.CENTER);

		return vbox;

	}
}

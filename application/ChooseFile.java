/**
 * ChooseFile.java 
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

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import java.io.*;
import javafx.stage.DirectoryChooser;

/**
 * ChooseFile - Contains method chooseFileDialogue(), which returns a vertical
 * box containing the input structure for getting the user to choose a directory
 * to import data from or to export data to.
 *
 */
public class ChooseFile {
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 200;

	private static final String IMPORT_PROMPT = " Choose a Directory to Import From";
	private static final String EXPORT_PROMPT = " Choose a Directory to Export To";
	private static final String DEFAULT_LABEL = " No Directory Selected";

	private static final String IMPORT_WARNING = " Note: Chosen directory must only contain .csv files\n\n"
			+ " Warning: Importing will overwrite data with matching Farm ID and date";

	private static final String EXPORT_WARNING = "Warning: Exporting will overwrite previously exported files in same directory.";

	/**
	 * Creates a stage in which the user can choose either to import data to their
	 * project or export the data in their project to a specified directory.
	 * 
	 * @param stage    Stage object of the main GUI
	 * @param farmLand FarmLand object which stores data for the main GUI
	 * @return a VBox object containing the UI interface needed for importing and
	 *         exporting csv files
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
		// directory they want to import to or export from
		DirectoryChooser chooser = new DirectoryChooser();
		// will display and save the user's chosen directory
		Label confirmLabel = new Label(DEFAULT_LABEL);

		// user presses this to confirm import/export
		Button confirmButton = new Button("Import");
		HBox confirmBar = new HBox(20, confirmLabel, confirmButton);
		confirmBar.setAlignment(Pos.CENTER);

		// User presses this to open a dialogue where they can choose a directory
		Button fileButton = new Button(IMPORT_PROMPT);

		// Label which contains warning for user
		// Start of as import warning, switch to export
		// when needed
		Label warning = new Label(IMPORT_WARNING);

		// When import button is pressed, switch all to import mode
		importButton.setOnAction(e -> {
			fileButton.setText(IMPORT_PROMPT);
			confirmLabel.setText(DEFAULT_LABEL);
			confirmButton.setText("Import");
			warning.setText(IMPORT_WARNING);

		});

		// When export button is pressed, switch all to export mode
		exportButton.setOnAction(e -> {
			fileButton.setText(EXPORT_PROMPT);
			confirmLabel.setText(DEFAULT_LABEL);
			confirmButton.setText("Export");
			warning.setText(EXPORT_WARNING);
		});

		// When pressed, file button opens directory dialogue and change the
		// label to the given directory name if one is selected
		fileButton.setOnAction(e -> {
			try {
				File dir = chooser.showDialog(stage);

				if (dir != null) {
					confirmLabel.setText(dir.getPath());
				}

			} catch (Exception f) {

				Scene scene = new Scene(new Label(" Problem choosing directory: " + f.getMessage()));

				Stage failStage = new Stage();

				failStage.setTitle("Failure");
				failStage.setScene(scene);
				failStage.show();

				// print error message
			}
		});

		// When confirm button pressed, will actually execute the users selected
		// import/export if possible.
		confirmButton.setOnAction(e -> {
			String directoryPath = confirmLabel.getText();// directory chosen by user

			if (directoryPath.equals(DEFAULT_LABEL)) {
				// If no directory selected, display corresponding error message
				Scene scene = new Scene(new Label(DEFAULT_LABEL), WINDOW_WIDTH, WINDOW_HEIGHT);

				Stage unchosenStage = new Stage();

				unchosenStage.setTitle(" Error: No Selection");
				unchosenStage.setScene(scene);
				unchosenStage.show();

			} else if (importButton.isSelected()) {
				// if import is selected
				try {
					// try to import the given file
					File dir = new File(directoryPath);
					farmLand.processDirectory(dir);

					// if above does not throw exception, display success message
					Scene scene = new Scene(new Label(directoryPath + " Successfully Read"), WINDOW_WIDTH,
							WINDOW_HEIGHT);

					Stage successStage = new Stage();

					successStage.setTitle(" Success");
					successStage.setScene(scene);
					successStage.show();

				} catch (Exception f) {
					// if exception occurs, display descriptive error message
					Scene scene = new Scene(
							new Label(" Directory not fully read: " + f.getMessage() + "\n Fix file and try again."));

					Stage failStage = new Stage();

					failStage.setTitle("Failure");
					failStage.setScene(scene);
					failStage.show();
				}

			} else {
				// if export is selected
				try {
					// try to export to the given directory
					File dir = new File(directoryPath);
					farmLand.exportToDirectory(dir);

					// if above does not throw exception, display success message
					Scene scene = new Scene(new Label(" Successfully Exported to " + dir.getAbsolutePath()),
							WINDOW_WIDTH, WINDOW_HEIGHT);

					Stage successStage = new Stage();

					successStage.setTitle("Success");
					successStage.setScene(scene);
					successStage.show();

				} catch (Exception f) {
					// if exception is thrown above, display descriptive error message
					Scene scene = new Scene(new Label(" Directory not fully exported: " + f.getMessage()));

					Stage failStage = new Stage();

					failStage.setTitle("Failure");
					failStage.setScene(scene);
					failStage.show();
				}
			}

			// either way, reset label to default
			confirmLabel.setText(DEFAULT_LABEL);
		});

		// Button which when pressed removes all data from the FarmLand
		// object. Is colored red as implicit warning to user.
		Button restartButton = new Button("RESET ALL DATA");
		restartButton.setStyle("-fx-background-color: #ff5555 ; ");

		restartButton.setOnAction(e -> {
			farmLand.reset();

			Scene successScene = new Scene(new Label(" All Data Successfully Reset"), WINDOW_WIDTH, WINDOW_HEIGHT);
			Stage successStage = new Stage();

			successStage.setTitle("All Data Reset");
			successStage.setScene(successScene);
			successStage.show();
		});

		// create a VBox containing all above parts
		VBox vbox = new VBox(30, topSelection, confirmBar, fileButton, warning, restartButton);
		vbox.setAlignment(Pos.CENTER);

		return vbox;

	}
}

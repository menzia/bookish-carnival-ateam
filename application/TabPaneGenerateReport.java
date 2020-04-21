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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TabPaneGenerateReport extends Application {

	private static final int WINDOW_WIDTH = 400;
	private static final int WINDOW_HEIGHT = 400;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {

		TabPane tabPane = new TabPane();

		Tab tab1 = new Tab("Farm Report", farmRep());
		Tab tab2 = new Tab("Annual Report", new Label("Show all cars available"));
		Tab tab3 = new Tab("Monthly Report", new Label("Show all boats available"));
		Tab tab4 = new Tab("Date Report", new Label("Show all boats available"));

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tabPane.getTabs().add(tab3);
		tabPane.getTabs().add(tab4);

		VBox vBox = new VBox(tabPane);
		Scene scene = new Scene(vBox, WINDOW_WIDTH, WINDOW_HEIGHT);

		primaryStage.setScene(scene);
		primaryStage.setTitle("JavaFX App");
		primaryStage.show();
	}

	public BorderPane farmRep() {
		BorderPane farmRep = new BorderPane();
		VBox farmid = new VBox();
		farmid.getChildren().addAll(new Label("Enter Farm ID:"), new TextField());

		VBox year = new VBox();
		year.getChildren().addAll(new Label("Enter Year:"), new TextField());

		HBox hb = new HBox();
		hb.getChildren().addAll(farmid, year);
		farmRep.setCenter(hb);
		Button gButton = new Button("Generate");
		gButton.setOnAction(e -> buttonAction());
		farmRep.setBottom(gButton);
		return farmRep;

	}

	// click on generate to generate a table view format farm report
	public void buttonAction() {
		farmReport fm = new farmReport();
		fm.centerOnScreen();
	}

}

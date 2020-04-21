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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
	private static final int WINDOW_WIDTH = 400;
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
		bd.setCenter(menuButton());

		// Scene
		Scene scene_1 = new Scene(bd, WINDOW_WIDTH, WINDOW_HEIGHT);

		// set stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(scene_1);
		primaryStage.show();

	}

	public VBox menuButton() {
		Label temp = new Label("(Available now)");
		Label temp2 = new Label("(Available now)");
		temp.setFont(new Font("Arial", 20));
		temp2.setFont(new Font("Arial", 20));
		// BorderPane mbp = new BorderPane();
		VBox leftvb = new VBox();
		leftvb.setSpacing(10);
		leftvb.setStyle("-fx-background-color: #336699;");

		// Buttons on the left VBox
		Button upcsv = new Button("Upload CSV");
		upcsv.setOnAction(e ->{
			ChooseFile cf = new ChooseFile();
			Stage stage = new Stage();
			try {
				cf.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		upcsv.setPrefSize(150, 20);
		HBox csvTemp = new HBox();
		csvTemp.getChildren().addAll(upcsv,temp2);
		

		Button edit = new Button("Add/Edit/Remove(day)");
		edit.setPrefSize(150, 20);

		Button minmax = new Button("Display Max/Min/Avg");
		minmax.setPrefSize(150, 20);

		Button report = new Button("Generate Report");
		
	
		HBox reportTemp = new HBox();
		reportTemp.getChildren().addAll(report,temp);
		report.setPrefSize(150, 20);
		report.setOnAction(e ->{
			TabPaneGenerateReport rp = new TabPaneGenerateReport();
			Stage st = new Stage();
			rp.start(st);;
		});
		
		
		
		leftvb.getChildren().addAll(csvTemp, edit, minmax, reportTemp);

		DropShadow shadow = new DropShadow();
		// Adding the shadow when the mouse cursor is on
		upcsv.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				
				upcsv.setEffect(shadow);

			}
		});
		
		//upcsv.setOnAction(this.handleupcsvButtonAction());

		edit.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				edit.setEffect(shadow);
			}
		});

		// Removing the shadow when the mouse cursor is off
		minmax.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
				minmax.setEffect(shadow);
			}
		});

		report.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
				report.setEffect(shadow);
			}
		});



		return leftvb;
	}


}

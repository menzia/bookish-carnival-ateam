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
import javafx.stage.FileChooser;


public class ChooseFile extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("FileChooser");
		FileChooser chooser = new FileChooser(); 
	
		Label label = new Label("no files selected"); 
	  
    // create a Button 
    Button button = new Button("Show open dialog");
    
    button.setOnAction(e ->{
   // get the file selected 
      File file = chooser.showOpenDialog(stage); 

      if (file != null) { 
            
          label.setText(file.getAbsolutePath()  
                              + "  selected"); 
      } 
    });
    
    // create a Button 
    Button button1 = new Button("Show save dialog"); 
    button1.setOnAction(e ->{
    	 // get the file selected 
      File file = chooser.showSaveDialog(stage); 

      if (file != null) { 
          label.setText(file.getAbsolutePath()  
                              + "  selected"); 
      } 
    }); 
    // create a VBox 
    VBox vbox = new VBox(30, label, button, button1); 

    // set Alignment 
    vbox.setAlignment(Pos.CENTER); 

    // create a scene 
    Scene scene = new Scene(vbox, 600, 400); 

    // set the scene 
    stage.setScene(scene); 

    stage.show(); 

}
}

package application;

import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

//window for farm report
public class stage2 extends Stage{
Button button = new Button("Generate");
    BorderPane bd = new BorderPane();
    HBox hb = new HBox(new Label("Farm Report"));
    
    VBox outer = new VBox();
    Scene sc;
    
    //hbox that contains farmid and year
    HBox hb1 = new HBox();
    //VBOX FOR FARMID
    VBox farmID = new VBox();
    Label lb = new Label("Enter Farm ID:");
    TextField idfield = new TextField();
    
    //VBOX for select year
    Label yrlb = new Label ("Enter Year:");
    TextField yearField =  new TextField();
    VBox yrCol = new VBox();
    
    //constructor
    stage2(int index){
       //set id vbox
        
        farmID.getChildren().addAll(lb,idfield);
        yrCol.getChildren().addAll(yrlb,yearField);
        hb1.getChildren().addAll(farmID,yrCol);
        bd.setCenter(hb1);
        bd.setBottom(button);
        sc = new Scene(bd,500,500);
        this.setScene(sc);
        this.show();
    }
    
    
    
    
}

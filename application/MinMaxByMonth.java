/**
 * MinMaxByMonth.java
 * Author:   Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
 * Date:     4/29/2020
 * 
 * Course:   CS400
 * Semester: Spring 2020 
 * Lecture:  001/002/001
 *
 * List Collaborators: 
 * 
 * Other Credits: 
 *
 * Know Bugs: 
 */
package application;

import java.util.ArrayList;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MinMaxByMonth extends Stage {

   private Scene scene;
   private TableView<MinMonthRow> table;
   private VBox vbox = new VBox();
   private  TableColumn<MinMonthRow, String> minCol ;
   private  TableColumn<MinMonthRow, String> maxCol;
   private  TableColumn<MinMonthRow, String> avgCol;
   private TableColumn<MinMonthRow,String> monthCol;
   private static String[] monthStr = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
   
   public MinMaxByMonth(FarmLand farmLand, String farmId, Integer yearNum){
       
       minCol =  new TableColumn<MinMonthRow, String>("min(lbs)");
       maxCol  = new TableColumn<MinMonthRow, String>(
           "max(lbs)");
       avgCol  = new TableColumn<MinMonthRow, String>("Average(lbs)");
       monthCol = new TableColumn<MinMonthRow, String>("Month");
       
       table = new TableView<MinMonthRow>();
       vbox = new VBox();
       scene = new Scene(new Group());
       table.setEditable(true);
      
       
       
       
       //month column
       monthCol.setMinWidth(100);
       monthCol.setCellValueFactory(new PropertyValueFactory<MinMonthRow, String>("month"));
       //min column
       minCol.setMinWidth(140);
       minCol.setCellValueFactory(new PropertyValueFactory<MinMonthRow, String>("min"));
       //max column
       maxCol.setMinWidth(140);
       maxCol.setCellValueFactory(new PropertyValueFactory<MinMonthRow, String>("max"));
       //average column
       avgCol.setMinWidth(140);
       avgCol.setCellValueFactory(new PropertyValueFactory<MinMonthRow, String>("average"));
       
       
       Label title = new Label();

       if (farmLand == null || farmId == null || yearNum == null) {
           title.setText("Error: Must choose Farm ID and Year");

       } else {
           table.getColumns().addAll(monthCol, minCol, maxCol,avgCol);
           table.setItems(getData(farmLand, farmId, yearNum));
           title.setText("\"" + farmId + "\" Min/Max/Avg Daily Weight by Month For: " + yearNum);
       }

       title.setFont(new Font("Arial", 20));

       vbox.setSpacing(5);
       vbox.setPadding(new Insets(10, 0, 0, 10));
       vbox.getChildren().addAll(title, table);

       ((Group) scene.getRoot()).getChildren().addAll(vbox);

       table.setEditable(true);
       this.setTitle("Min/Max/Avg by Month Report");
       this.setWidth(549);
       this.setHeight(500);

       this.setScene(scene);
       this.show();
       // show scene
   }
   
   
   
   
   
   
   public static class MinMonthRow {
       private SimpleStringProperty month;
       private SimpleLongProperty min;
       private SimpleLongProperty max;
       private SimpleLongProperty average;

       
       
       /*
        * 
        */
       private MinMonthRow(String month, Long min, Long max, Long average) {
           this.month = new SimpleStringProperty(month);
           this.min = new SimpleLongProperty(min);
           this.max = new SimpleLongProperty(max);
           this.average = new SimpleLongProperty(average);

       }
       public String getMonth() {
           return month.get();
       }
       public void setMonth(String month) {
           this.month.set(month);;
       }

       public Long getMin() {
           return min.get();
       }

       public void setMin(Integer min) {
           this.min.set(min);
       }

       public Long getMax() {
           return max.get();
       }

       public void setMax(Integer max) {
           this.max.set(max);
       }

       public Long getAverage() {
           return average.get();
       }

       public void setAverage(Long average) {
           this.average.set(average);
       }

   }
   private static ObservableList<MinMonthRow> getData(FarmLand farmLand, String farmId, Integer yearNum) {
       ArrayList<MinMonthRow> monthList = new ArrayList<MinMonthRow>();
       if (farmLand.contains(farmId)) {
           
           for (int monthNum = 1; monthNum <= 12; ++monthNum) {
               long min = farmLand.getFarm(farmId).getMonth(yearNum, monthNum).getMin();
               long max = farmLand.getFarm(farmId).getMonth(yearNum, monthNum).getMax();
               long average = farmLand.getFarm(farmId).getMonth(yearNum, monthNum).getAverage();
               String monthString = monthStr[monthNum - 1];

               MinMonthRow newMinMonthRow = new MinMonthRow(monthString,min, max,average);

               monthList.add(newMinMonthRow);

               
           }

           
       }
       ObservableList<MinMonthRow> data = FXCollections.observableArrayList(monthList);

    return data;
       
       
   }
    
    
    
}

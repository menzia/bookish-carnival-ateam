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
   private TableView<MonthRow> table;
   private VBox vbox = new VBox();
   private  TableColumn<MonthRow, String> minCol ;
   private  TableColumn<MonthRow, String> maxCol;
   private  TableColumn<MonthRow, String> avgCol;
   private TableColumn<MonthRow,String> monthCol;
   private static String[] monthStr = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
   
   public MinMaxByMonth(FarmLand farmLand, String farmId, Integer yearNum){
       
       minCol =  new TableColumn<MonthRow, String>("min(lbs)");
       maxCol  = new TableColumn<MonthRow, String>(
           "max(lbs)");
       avgCol  = new TableColumn<MonthRow, String>("Average(lbs)");
       monthCol = new TableColumn<MonthRow, String>("Month");
       
       table = new TableView<MonthRow>();
       vbox = new VBox();
       scene = new Scene(new Group());
       table.setEditable(true);
      
       
       
       
       //month column
       monthCol.setMinWidth(100);
       monthCol.setCellValueFactory(new PropertyValueFactory<MonthRow, String>("month"));
       //min column
       minCol.setMinWidth(140);
       minCol.setCellValueFactory(new PropertyValueFactory<MonthRow, String>("min"));
       //max column
       maxCol.setMinWidth(140);
       maxCol.setCellValueFactory(new PropertyValueFactory<MonthRow, String>("max"));
       //average column
       avgCol.setMinWidth(140);
       avgCol.setCellValueFactory(new PropertyValueFactory<MonthRow, String>("average"));
       
       
       Label title = new Label();

       if (farmLand == null || farmId == null || yearNum == null) {
           title.setText("Error: Must choose Farm ID and Year");

       } else {
           table.getColumns().addAll(monthCol, minCol, maxCol,avgCol);
           table.setItems(getData(farmLand, farmId, yearNum));
           title.setText("\"" + farmId + "\" Min/Max/Avg by Month Report: " + yearNum);
       }

       title.setFont(new Font("Arial", 20));

       vbox.setSpacing(5);
       vbox.setPadding(new Insets(10, 0, 0, 10));
       vbox.getChildren().addAll(title, table);

       ((Group) scene.getRoot()).getChildren().addAll(vbox);

       table.setEditable(true);
       this.setTitle("Min/Max/Avg by Month Report");
       this.setWidth(800);
       this.setHeight(500);

       this.setScene(scene);
       this.show();
       // show scene
 
       
   }
   
   
   
   
   
   
   public static class MonthRow {
       private SimpleStringProperty month;
       private SimpleLongProperty min;
       private SimpleLongProperty max;
       private SimpleLongProperty average;

       
       
       /*
        * 
        */
       private MonthRow(String month, Long min, Long max, Long average) {
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
   private static ObservableList<MonthRow> getData(FarmLand farmLand, String farmId, Integer yearNum) {
       ArrayList<MonthRow> monthList = new ArrayList<MonthRow>();
       if (farmLand.contains(farmId)) {
           
           for (int monthNum = 1; monthNum <= 12; ++monthNum) {
               long min = farmLand.getFarm(farmId).getMonth(yearNum, monthNum).getMin();
               long max = farmLand.getFarm(farmId).getMonth(yearNum, monthNum).getMax();
               long average = farmLand.getFarm(farmId).getMonth(yearNum, monthNum).getAverage();
               String monthString = monthStr[monthNum - 1];

               MonthRow newMonthRow = new MonthRow(monthString,min, max,average);

               monthList.add(newMonthRow);

               
           }

           
       }
       ObservableList<MonthRow> data = FXCollections.observableArrayList(monthList);

    return data;
       
       
   }
    
    
    
}

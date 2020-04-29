package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {
private String inputFile;//csv format
private String outputFile;

public FileManager(String inputFile) {
    this.inputFile=inputFile;

}

/*
 * Read data from file to the data structure
 */
public boolean readFile() {
    
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";
    
    try {

        br = new BufferedReader(new FileReader(inputFile));
        line = br.readLine();//skip the first line
        while ((line = br.readLine()) != null) {

            // use comma as separator
            String[] str = line.split(cvsSplitBy);
         //   System.out.println(str[0]+" "+str[1]+" "+str[2]);


        }

    } catch (FileNotFoundException e) {
        System.out.println("File not found.");
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
    
    
    return true;
}


public boolean writeToFile() {
    return false;
    
}
public String getFileContents() {
    return inputFile;
    
}
public static void main(String[] args) {
    // TODO Auto-generated method stub
    
    FileManager fm = new FileManager("/Users/lyulinyi/eclipse-workspace/ateam_MilkWeights/csv/large/2019-1.csv");
 fm.readFile();
}


}

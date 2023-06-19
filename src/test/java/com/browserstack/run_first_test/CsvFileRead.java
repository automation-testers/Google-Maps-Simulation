package com.browserstack.run_first_test;

//import CSVReader.Rows;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvFileRead {
    public static List<String[]> CsvFIleRead() {
            Path fileName = Paths.get("src/test/resources/Directions.csv");
//      Path fileName = Paths.get("C:\\Users\\Patil\\Downloads\\Directions.csv");

            try (Reader reader = Files.newBufferedReader(fileName)) {
                try (CSVReader csvReader = new CSVReader(reader)) {
                    return csvReader.readAll();
                } catch (IOException | CsvException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//        List<String> Rowss=null;
//        // Now calling Files.readString() method to
//        // read the file
//        try {
//            Rowss = Files.readAllLines(fileName);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        Rows rws=new Rows();
//        rws.ParseData(Rowss);
//        return ;
            return null;
        }




    public static void readCSVDynamic() {
        try(
                BufferedReader br = new BufferedReader(new FileReader("resources/data.txt"));
                CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(br);
        ) {
            for(CSVRecord record : parser) {
                System.out.println(record.get("First"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

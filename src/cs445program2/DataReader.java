/*************************************************************
 *     file: DataReader.java
 *     author: Shun Lu
 *     class: CS 445 - Computer Graphics
 * 
 *     assignment: program 2
 *     last modified: 4/30/2017
 * 
 *     purpose: This program reads coordinates.txt and draw polygon,
 *     filled with desired color, then transform through given information,
 *     for main algorithms, check DataReader.java, Shape.java, and 
 *     Matrix.java. 
 *     Line.java and Point.java are imported from first program.
 *     Edge.java and Vertice.java inherit these two classes.
 * 
 *************************************************************/
package cs445program2;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

/**
 * This class reads data from a txt file and stores primitive shapes
 * in fields.  
 * 
 * @author Shun Lu
 */
public class DataReader {

    private List<Polygon> list;
    
    /**
     * This constructor constructs data read from txt
     */
    public DataReader(String filePath) {
        list = new ArrayList<>(); 
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                // This read "P" header in a txt file
                // This line should be color information
                if (data[0].equals("P")) {
                    float r = 0.0f + Float.parseFloat(data[1]);
                    float g = 0.0f + Float.parseFloat(data[2]);
                    float b = 0.0f + Float.parseFloat(data[3]);
                    // This list is a temportary list that holds data from txt
                    List<Vertice> vertice = new ArrayList<>();
                    while ((line = reader.readLine()).charAt(0) != 'T') {
                        data = line.split(" ");
                        vertice.add(new Vertice(Float.parseFloat(data[0]), 
                                Float.parseFloat(data[1])));
                    }
                    // create an instance of Shape(Polygon)
                    list.add(new Shape(vertice, r, g, b));
                }
                else if (data[0].equals("t")) {
                    list.get(list.size() - 1).translate(Float.parseFloat(data[1]),
                                                        Float.parseFloat(data[2]));
                }
                else if (data[0].equals("r")) {
                    list.get(list.size() - 1).rotate(Float.parseFloat(data[1]),
                                                     Float.parseFloat(data[2]),
                                                     Float.parseFloat(data[3]));
                }
                else if (data[0].equals("s")) {
                    list.get(list.size() - 1).scale(Float.parseFloat(data[1]),
                                                    Float.parseFloat(data[2]),
                                                    Float.parseFloat(data[3]),
                                                    Float.parseFloat(data[4]));
                }
                else
                {
                    System.out.println("Provided shape is not provided. PASS.");
                }
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * METHOD: getData
     * PURPOSE: get list from DataReader object
     */
    public List<Polygon> getData() {
        return list;
    }
}

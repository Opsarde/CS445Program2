/*************************************************************
 *     file: DataReader.java
 *     author: Shun Lu
 *     class: CS 445 - Computer Graphics
 * 
 *     assignment: program 1
 *     last modified: 4/10/2017
 * 
 *     purpose: This program reads coordinates.txt and draw line,
 *     circle, and ellipse on a window using OpenGL
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

    //private ArrayList<Shape> list;
    private List<Polygon> list;

    // How about one polygon for each iteration of datareader
    // along with its transform data?
    
    /**
     * This constructor constructs data read from txt
     */
    public DataReader(String filePath) {
        list = new ArrayList<>(); 
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                // Color information abut a polygon

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

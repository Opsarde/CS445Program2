/*************************************************************
 *     file: Point.java
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

/**
 * This Point class represents a point in a coordinate system
 * Has x and y values as float
 * 
 * @author Shun Lu
 */
public class Point {
    public float x;
    public float y;

    /**
     * A Point constructor that initializes Point object with x and y.
     */
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * A Point constructor that initializes Point object with a String array of
     * x and y.
     */
    public Point(String[] points) {
        this.x = Float.parseFloat(points[0]);
        this.y = Float.parseFloat(points[1]);
    }

    /**
     * METHOD: toString
     * PURPOSE: print some useful information about a point
     */
    @Override
    public String toString() {
        return "x = " + x + ", y = " + y;
    }
}

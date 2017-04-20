/*************************************************************
 *     file: Point.java
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

/**
 * This Point class represents a point in a coordinate system
 * Has x and y values as float
 * 
 * @author shun7817
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

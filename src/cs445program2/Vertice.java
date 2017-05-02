/*************************************************************
 *     file: Vertice.java
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
 * CLASS: Vertice
 * PURPOSE: A vertice is a point in a polygon
 * 
 * @author Shun Lu
 */
public class Vertice extends Point {
    
    public Vertice(float x, float y) {
        super(x, y);
    }
    
}

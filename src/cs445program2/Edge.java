/*************************************************************
 *     file: Edge.java
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
 * CLASS: Edge
 * PURPOSE: Edge is a Line in polygon
 * 
 * @author Shun Lu
 */
public class Edge extends Line{

    public Edge(Vertice p1, Vertice p2) {
        super(p1, p2); 
    }
    
}

/*************************************************************
 *     file: Polygon.java
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
 * INTERFACE: Polygon
 * PURPOSE: Some basic operation for drawing and filling a polygon
 * 
 * @author Shun Lu
 */
public interface Polygon {

    /**
     * PURPOSE: draw this polygon on GL projection matrix
     */
    public void draw(); 

    /**
     * PURPOSE: fill this polygon with color information given
     */
    public void fill();

    /**
     * PURPOSE: transform this polygon
     */
    public void translate(float dx, float dy);

    /**
     * PURPOSE: rotate this polygon
     */
    public void rotate(float angle, float px, float py);

    /**
     * PURPOSE: scale this polygon
     */
    public void scale(float sacleX, float scaleY, float px, float py);

    /**
     * PURPOSE: return some information about this shape
     */
    public String toString();
}

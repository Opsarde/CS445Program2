/*************************************************************
 *     file: Shape.java
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
 * INTERFACE: Shape
 * PURPOSE: each shape should have a draw and toString method
 * 
 * @author Shun Lu
 */
public interface Polygon {

    /**
     * PURPOSE: draw this shape on GL projection matrix
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

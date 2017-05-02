/*************************************************************
 *     file: Line.java
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

import static org.lwjgl.opengl.GL11.*;


/**
 * This class represents a line in a coordinate system
 *
 * @author Shun Lu
 */
public class Line {
    public Point p1;
    public Point p2;
    
    /**
     * PURPOSE: Initialize Line object with given points
     */
    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    /**
     * METHOD: draw
     * PURPOSE: draw a line by using the line algorithm
     */

    public void draw() {
        //glColor3f(255.0f, 0.0f, 0.0f);
        float dx = Math.abs(p2.x - p1.x);
        float dy = Math.abs(p2.y - p1.y);
        float incrementRight = 2 * dy;
        float incrementUpRight = 2 * (dy - dx);
        float d = 2 * dy - dx;
        float x = p1.x; 
        float y = p1.y;
        glBegin(GL_POINTS);
        glVertex2f(x, y);
        if (dx == 0) {
            drawVertical();
        }
        else if (Math.abs(dy / dx) > 1) {
            dx = Math.abs(p2.y - p1.y);
            dy = Math.abs(p2.x - p1.x);
            incrementRight = 2 * dy;
            incrementUpRight = 2 * (dy - dx);
            d = 2 * dy - dx;
            while ((y < p2.y && p2.y - p1.y >= 0) ||
                    (y > p2.y && p2.y - p1.y < 0)) {
               if (d > 0) {
                   d += incrementUpRight;
                   if (p2.y - p1.y >= 0) ++y; else --y;
                   if (p2.x - p1.x >= 0) ++x; else --x;
               } 
               else {
                   d += incrementRight;
                   if (p2.y - p1.y >= 0) ++y; else --y;
               }
               glVertex2f(x, y);
            }
            glEnd();
        }
        else {
            while ((x < p2.x && p2.x - p1.x >= 0) ||
                    (x > p2.x && p2.x - p1.x < 0)) {
               if (d > 0) {
                   d += incrementUpRight;
                   if (p2.x - p1.x >= 0) ++x; else --x;
                   if (p2.y - p1.y >= 0) ++y; else --y;
               } 
               else {
                   d += incrementRight;
                   if (p2.x - p1.x >= 0) ++x; else --x;
               }
               glVertex2f(x, y);
            }
            glEnd();
        }
    }

    /**
     * METHOD: toString
     * PURPOSE: print some useful information about a line
     */
    public String toString() {
        return "p1: " + p1.toString() + "; p2: " + p2.toString();
    }

    /**
     * METHOD: drawVertical
     * PURPOSE: draw a vertical line
     */
    private void drawVertical() {
        float begin, end; 
        if (p1.y <= p2.y) {
            begin = p1.y;
            end = p2.y;
        }
        else {
            begin = p2.y;
            end = p1.y;
        }
        glBegin(GL_POINTS);
        glVertex2f(p1.x, begin);
        while (begin < end) {
            ++begin;
            glVertex2f(p1.x, begin);
        }
        glEnd();
    }
}

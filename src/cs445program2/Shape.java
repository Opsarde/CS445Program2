/*************************************************************
 *     file: Shape.java
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

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This Shape class contains a vertex and a edge table.
 * It has draw and fill methods.
 * 
 * @author Shun Lu
 */
public class Shape implements Polygon {

    private List<Vertice> verticeTable;
    private List<Edge> edges;
    
    private float r, g, b;
    /**
     * Constructor: Shape
     * Purpose: initialize two tables and rgb value for a shape.
     */
    public Shape(List<Vertice> vertice, float r, float g, float b) {
        verticeTable = new ArrayList<>();
        edges = new ArrayList<>();
        for (Vertice v : vertice) {
            verticeTable.add(new Vertice(v.x, v.y));
        }
        this.r = r;
        this.g = g;
        this.b = b;
        for (int i = 0; i != verticeTable.size(); ++i) {
            Edge e;
            if (i == verticeTable.size() - 1) {
                e = new Edge(verticeTable.get(i), verticeTable.get(0));
            } else {
                e = new Edge(verticeTable.get(i), verticeTable.get(i + 1));
            }
            edges.add(e);
        }
    }

    /**
     * Method: draw
     * Purpose: draw the outline of a shape
     */
    @Override
    public void draw() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glColor3f(r, g, b);
        for (Edge e : edges)
            e.draw();
    }

    /**
     * Method: fill
     * Purpose: When this method gets called, an object of Shape will
     * use the scanline fill algorithm to fill itself.  
     * Each entry in table has [0]: y-min, [1]: y-max, [2]: x-val, and
     * [3]: 1 / m
     */
    @Override
    public void fill() {
        List<float[]> globalTable = new ArrayList<>();
        List<float[]> activeTable = new ArrayList<>();
        // Initialize all edges in global_table
        for (Edge e : edges) {
            float[] array = new float[4];
            array[0] = yMin(e.p1, e.p2);
            array[1] = yMax(e.p1, e.p2);
            array[2] = xVal(e.p1, e.p2);
            array[3] = slope(e.p1, e.p2);
           // edgeTable.add(array);
            if (Float.isFinite(array[3])) {
                globalTable.add(array);
                sortGlobal(globalTable);
            }
        }

        float scanLine = globalTable.get(0)[0];
        // Loop until both Table are empty
        while (!globalTable.isEmpty() || !activeTable.isEmpty()) {
            // remove the top each time true
            // until top y-min is greater than scanline
            while (!globalTable.isEmpty() && globalTable.get(0)[0] <= scanLine) {
                activeTable.add(globalTable.remove(0));
                sortActive(activeTable);
            }
            drawScanline(scanLine, activeTable);
            // remove fnished entry and sort
            ++scanLine;
            sortActive(activeTable);
            for (int i = 0; i < activeTable.size(); ++i) {
                activeTable.get(i)[2] += activeTable.get(i)[3];
                if (scanLine == activeTable.get(i)[1]) {
                    // remove the entry here
                    activeTable.remove(i);
                    --i;
                }
            }
       }
    }
    
    /**
     * Method: translate
     * Purpose: translate the shape by dx and dy
     */
    @Override
    public void translate(float dx, float dy) {
        // Change verticeTable values
        float[][] array2 = {{1, 0, dx},
                            {0, 1, dy},
                            {0, 0, 1}};
        calculateTransformation(array2);
    }
    
    /**
     * Method: Rotate
     * Purpose: rotate the shape by angle, assume rotate by origin
     */
    @Override
    public void rotate(float angle, float px, float py) {
        // Assume all pivot points in this project are at origin
        angle = (float) Math.toRadians(angle);
        float[][] array2 = {{(float) Math.cos(angle), (float) -Math.sin(angle), 0},
                            {(float) Math.sin(angle), (float) Math.cos(angle), 0},
                            {0, 0, 1}};
        calculateTransformation(array2);
    }
    
    /**
     * Method: Scale
     * Purpose: scale the shape by Sx and Sy, assume scale by origin
     */
    @Override
    public void scale(float scaleX, float scaleY, float px, float py) {
        float[][] array2 = {{scaleX, 0, 0},
                            {0, scaleY, 0},
                            {0, 0, 1}};
        calculateTransformation(array2);
    }

    // Method: calculateTransformation
    // Purpose: Private method for calculating the transformation
    private void calculateTransformation(float[][] array) {
        for (Vertice v : verticeTable) {
            float[][] array1 = {{v.x}, {v.y}, {1}};
            Matrix s = new Matrix(array1, 3, 1);
            Matrix basis = new Matrix(array, 3, 3);
            Matrix derivS = basis.multiply(s);
            v.x = derivS.getValue(0, 0);
            v.y = derivS.getValue(1, 0);
        }
    }

    // Method: yMin
    // Purpose: private method to get yMin
    private float yMin(Point p1, Point p2) {
        return (p1.y < p2.y) ? p1.y : p2.y;
    }
    
    // Method: yMax
    // Purpose: private method to get yMax
    private float yMax(Point p1, Point p2) {
        return (p1.y > p2.y) ? p1.y : p2.y;
    }

    // Method: xVal
    // Purpose: private method to get xVal
    private float xVal(Point p1, Point p2) {
        return (p1.y <= p2.y) ? p1.x : p2.x;
    }

    // Method: slope
    // Purpose: private method to get slope
    private float slope(Point p1, Point p2) {
        return (p2.x - p1.x) / (p2.y - p1.y);
    }

    // Method: sortGlobal
    // Purpose: sort globaltable based on y-min and x-val
    private void sortGlobal(List<float[]> list) {
        Collections.sort(list, new Comparator<float[]>() {
            @Override
            public int compare(float[] o1, float[] o2) {
                int c = 0;
                if (o1[0] < o2[0])
                    c = -1;
                else if (o1[0] > o2[0])
                    c = 1;
                if (c == 0) {
                    if (o1[2] < o2[2])
                        c = -1;
                    else if (o1[2] < o2[2])
                        c = 1;
                }
                return c;
            }
        });
    }

    // Method: sortActive
    // Purpose: sort active_table based on x-val
    private void sortActive(List<float[]> list) {
        Collections.sort(list, new Comparator<float[]>() {
            @Override
            public int compare(float[] o1, float[] o2) {
                int c = 0;
                if (o1[2] < o2[2])
                    c = -1;
                else if (o1[2] > o2[2])
                    c = 1;
                return c;
            }
        });
    }

    // Method: drawScanline
    // Purpose: draw the line based on scanline algorithm
    private void drawScanline(float scanLine, List<float[]> table) {
        int parity = 0;
        float currentPixel = 0;
        float endPixel = 0;
        glColor3f(r, g, b);
        glBegin(GL_POINTS);
        for (int i = 0; i < table.size(); ++i) {
            if (++parity % 2 == 1) {
                //draw from table.get(i)[2] until next i
                currentPixel = (table.get(i)[2]);
                // round endpixel so some little pixel gaps can be filled
                endPixel = Math.round(table.get(i + 1)[2]);
                while (++currentPixel < endPixel) {
                    glVertex2f(currentPixel, scanLine);
                }
            }
        }
        glEnd();
    }
}
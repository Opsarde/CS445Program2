/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445program2;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author shun7817
 */
public class Shape implements Polygon {

    //private Vertice[] verticeTable;
    private List<Vertice> verticeTable;
    private List<Edge> edges;
    
    private float r, g, b;

    public Shape(List<Vertice> vertice, float r, float g, float b) {
        //verticeTable = vertice;
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
        //List<float[]> edgeTable = new ArrayList<>();
        List<float[]> globalTable = new ArrayList<>();
        List<float[]> activeTable = new ArrayList<>();
        // Initialize all edges in edge_table
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
       // if (!edgeTable.isEmpty() && Float.isFinite(edgeTable.get(0)[3]))
       //     globalTable.add(edgeTable.get(0));
       // for (float[] e : edgeTable) {
       //     if (Float.isInfinite(e[3]))
       //         continue;
       //     int index = 0;
       //     if (e[0] > globalTable.get(index)[0])
       //         ++index;
       // }

        float scanLine = globalTable.get(0)[0];
        // Loop until both Table are empty
        while (!globalTable.isEmpty() || !activeTable.isEmpty()) {
            // remove the top each time true
            // until top y-min is greater than scanline
            while (!globalTable.isEmpty() && globalTable.get(0)[0] <= scanLine) {
                activeTable.add(globalTable.remove(0));
                sortActive(activeTable);
            }
            // current active_table
            // what to do? 
            drawScanline(scanLine, activeTable);
            // remove fnished entry
            ++scanLine;
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

    @Override
    public void translate(float dx, float dy) {
        // Change verticeTable values
        float[][] array2 = {{1, 0, dx},
                            {0, 1, dy},
                            {0, 0, 1}};
        calculateTransformation(array2);
    }

    @Override
    public void rotate(float angle, float px, float py) {
        // Assume all pivot points in this project are at origin
        angle = (float) Math.toRadians(angle);
        float[][] array2 = {{(float) Math.cos(angle), (float) -Math.sin(angle), 0},
                            {(float) Math.sin(angle), (float) Math.cos(angle), 0},
                            {0, 0, 1}};
        calculateTransformation(array2);
    }

    @Override
    public void scale(float scaleX, float scaleY, float px, float py) {
        float[][] array2 = {{scaleX, 0, 0},
                            {0, scaleY, 0},
                            {0, 0, 1}};
        calculateTransformation(array2);
    }

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

    private float yMin(Point p1, Point p2) {
        return (p1.y < p2.y) ? p1.y : p2.y;
    }

    private float yMax(Point p1, Point p2) {
        return (p1.y > p2.y) ? p1.y : p2.y;
    }

    private float xVal(Point p1, Point p2) {
        return (p1.y <= p2.y) ? p1.x : p2.x;
    }

    private float slope(Point p1, Point p2) {
        return (p2.x - p1.x) / (p2.y - p1.y);
    }

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

    private void drawScanline(float scanLine, List<float[]> table) {
        int parity = 0;
        float currentPixel = 0;
        float endPixel = 0;
        glColor3f(r, g, b);
        glBegin(GL_POINTS);
        for (int i = 0; i < table.size(); ++i) {
            if (++parity % 2 == 1) {
                //draw from table.get(i)[2] until next i
                currentPixel = Math.round(table.get(i)[2]);
                endPixel = Math.round(table.get(i)[2]);
                if (i < table.size() - 1);
                    endPixel = Math.round(table.get(i + 1)[2]);
                while (currentPixel < endPixel) {
                    glVertex2f(currentPixel, scanLine);
                    currentPixel += 1;
                }
            }
        }
        glEnd();
    }
}
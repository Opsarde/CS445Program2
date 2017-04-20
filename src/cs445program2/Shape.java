/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445program2;

import java.util.List;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author shun7817
 */
public class Shape implements Polygon{

    //private Vertice[] verticeTable;
    private List<Vertice> verticeTable;
    private float r, g, b;
    
    public Shape(List<Vertice> vertice, float r, float g, float b) {
        //verticeTable = vertice;
        verticeTable = new ArrayList<>();
        for (Vertice v : vertice) {
            verticeTable.add(new Vertice(v.x, v.y));
        }
        this.r = r;
        this.g = g;
        this.b = b;
    }
    @Override
    public void draw() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glColor3f(r, g, b);
        for (int i = 0; i != verticeTable.size(); ++i) {
            Edge e;
            if (i == verticeTable.size() - 1)
                e = new Edge(verticeTable.get(i), verticeTable.get(0));
            else
                e = new Edge(verticeTable.get(i), verticeTable.get(i + 1)); 
            e.draw();
        }
    }

    @Override
    public void fill(float r, float g, float b, float a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void translate(float dx, float dy) {
        // Change verticeTable values
        for (Vertice v : verticeTable) {
            float[][] array1 = {{v.x},
                                {v.y},
                                {1}};
            Matrix s = new Matrix(array1, 3, 1);
            float[][] array2 = {{1, 0, dx},
                                {0, 1, dy},
                                {0, 0, 1}};
            Matrix basis = new Matrix(array2, 3, 3);
            Matrix derivS = basis.dotProduct(s);
            v.x = derivS.getValue(0, 0);
            v.y = derivS.getValue(1, 0);
            float num1 = s.getValue(2, 0);
            float num2 = basis.getValue(2, 0);
            float num3 = derivS.getValue(2, 0);
            float random = 1.0f;
        }
    }

    @Override
    public void rotate(float angle, float px, float py) {
        // Assume all pivot points in this project are at origin
        angle = (float) Math.toRadians(angle);
        for (Vertice v : verticeTable) {
            float[][] array1 = {{v.x}, {v.y}, {1}};
            Matrix s = new Matrix(array1, 3, 1);
            float[][] array2 = {{(float) Math.cos(angle), (float) -Math.sin(angle), 0},
                                {(float) Math.sin(angle), (float) Math.cos(angle), 0},
                                {0, 0, 1}};
            Matrix basis = new Matrix(array2, 3, 3);
            Matrix derivS = basis.dotProduct(s);
            v.x = derivS.getValue(0, 0);
            v.y = derivS.getValue(1, 0);
        }
    }

    @Override
    public void scale(float scaleX, float scaleY, float px, float py) {
        for (Vertice v : verticeTable) {
            float[][] array1 = {{v.x}, {v.y}, {1}};
            Matrix s = new Matrix(array1, 3, 1);
            float[][] array2 = {{scaleX, 0, 0},
                                {0, scaleY, 0},
                                {0, 0, 1}};
            Matrix basis = new Matrix(array2, 3, 3);
            Matrix derivS = basis.dotProduct(s);
            v.x = derivS.getValue(0, 0);
            v.y = derivS.getValue(1, 0);
        }
    }
    
    private void calculateTransformation(float[][] array) {
        for (Vertice v : verticeTable) {
            float[][] array1 = {{v.x}, {v.y}, {1}};
            Matrix s = new Matrix(array1, 3, 1);
            Matrix basis = new Matrix(array, 3, 3);
            Matrix derivS = basis.dotProduct(s);
            v.x = derivS.getValue(0, 0);
            v.y = derivS.getValue(1, 0);
        }
    }
}

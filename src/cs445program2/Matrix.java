/*************************************************************
 *     file: Matrix.java
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
 * This class Matrix stores a 2D array and perform simple multiplication
 * 
 * @author Shun Lu
 */
public class Matrix {
    private int row;
    private int col;
    private float[][] values;

    /**
     * Constructor: Matrix
     * Purpose: accept row and col number to initialize an empty matrix
     */
    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        values = new float[row][col];
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                values[i][j] = 0.0f;
            }
        }
    }
    
    /**
     * Constructor: Matrix
     * Purpose: a simple copy constructor for Matrix
     */
    public Matrix(float[][] array, int row, int col) {
        this.row = row;
        this.col = col;
        values = new float[row][col];
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                values[i][j] = array[i][j];
            }
        }

    }
    
    /**
     * Method:multiply
     * Purpose: perform a simple matrix multiplication
     */
    public Matrix multiply(Matrix other) {
        Matrix result = new Matrix(row, other.col);
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < other.col; ++j) {
                for (int k = 0; k < col; ++k) {
                    result.values[i][j] += values[i][k] * other.values[k][j];
                }
            }
        }
        result.values[0][0] = Math.round(result.values[0][0]);
        result.values[1][0] = Math.round(result.values[1][0]);
        return result;
    }

    /**
     * Method:getValue
     * Purpose: get the value located at row i and column j
     */
    public float getValue(int i, int j) {
        return values[i][j];
    }
}

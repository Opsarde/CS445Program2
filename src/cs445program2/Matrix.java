/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs445program2;

/**
 *
 * @author shun7817
 */
public class Matrix {
    private int row;
    private int col;
    private float[][] values;

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

    public float getValue(int i, int j) {
        return values[i][j];
    }
}

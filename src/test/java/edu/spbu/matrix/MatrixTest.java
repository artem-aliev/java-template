package edu.spbu.matrix;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class MatrixTest {
    @Test
    public void addDD() {
        Matrix m1 = new DenseMatrix(4, 2);
        Matrix m2 = new DenseMatrix(4, 2);
        Matrix result = new DenseMatrix(4, 2);
        try {
            m1.changeCell(0, 0, 3);
            m1.changeCell(0, 1, 9);
            m1.changeCell(1, 0, 2);
            m1.changeCell(1, 1, 1);
            m1.changeCell(2, 0, 0);
            m1.changeCell(2, 1, 5);
            m1.changeCell(3, 0, 7);
            m1.changeCell(3, 1, 3);
            m2.changeCell(0, 0, -6);
            m2.changeCell(0, 1, 5.6);
            m2.changeCell(1, 0, 44);
            m2.changeCell(1, 1, -3.8);
            m2.changeCell(2, 0, 1);
            m2.changeCell(2, 1, 2.6);
            m2.changeCell(3, 0, 1.1);
            m2.changeCell(3, 1, -89.3);
            result.changeCell(0, 0, -3);
            result.changeCell(0, 1, 14.6);
            result.changeCell(1, 0, 46);
            result.changeCell(1, 1, -2.8);
            result.changeCell(2, 0, 1);
            result.changeCell(2, 1, 7.6);
            result.changeCell(3, 0, 8.1);
            result.changeCell(3, 1, -86.3);
            assertEquals(result, m1.add(m2));
        } catch (WrongSizeException | WrongSizeMatrixException e) {
            e.printStackTrace();
        }


    }
    @Test
    public void transDD() {
        Matrix m1 = new DenseMatrix(3, 3);
        Matrix result = new DenseMatrix(3, 3);
        try {
            m1.changeCell(0, 0, 3);
            m1.changeCell(0, 1, 9);
            m1.changeCell(0, 2, 65);
            m1.changeCell(1, 0, 2);
            m1.changeCell(1, 1, 1);
            m1.changeCell(1, 2, 45);
            m1.changeCell(2, 0, 0);
            m1.changeCell(2, 1, 5);
            m1.changeCell(2, 2, -5.8);
            result.changeCell(0, 0, 3);
            result.changeCell(0, 1, 2);
            result.changeCell(0, 2, 0);
            result.changeCell(1, 0, 9);
            result.changeCell(1, 1, 1);
            result.changeCell(1, 2, 5);
            result.changeCell(2, 0, 65);
            result.changeCell(2, 1, 45);
            result.changeCell(2, 2, -5.8);
            assertEquals(result, m1.trans());
        } catch (WrongSizeException | WrongSizeMatrixException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void mulDD() {
        Matrix m1 = new DenseMatrix(4, 2);
        Matrix m2 = new DenseMatrix(2, 1);
        Matrix result = new DenseMatrix(4, 1);
        try {
            m1.changeCell(0, 0, 3);
            m1.changeCell(0, 1, 9);
            m1.changeCell(1, 0, 2);
            m1.changeCell(1, 1, 1);
            m1.changeCell(2, 0, 0);
            m1.changeCell(2, 1, 5);
            m1.changeCell(3, 0, 7);
            m1.changeCell(3, 1, 3);
            m2.changeCell(0, 0, 6);
            m2.changeCell(1, 0, 13);
            result.changeCell(0, 0, 135);
            result.changeCell(1, 0, 25);
            result.changeCell(2, 0, 65);
            result.changeCell(3, 0, 81);
            assertEquals(result, m1.mul(m2));
        } catch (WrongSizeException | WrongSizeMatrixException e) {
            e.printStackTrace();
        }


    }
    @Test
    public void mulDD2() {
        try {
            Matrix m1 = new DenseMatrix("m1.txt");
            Matrix m2 = new DenseMatrix("m2.txt");
            Matrix expected = new DenseMatrix("result.txt");
            assertEquals(expected, m1.mul(m2));
        } catch (FileNotFoundException | WrongSizeMatrixException e) {
            e.printStackTrace();
        }
    }
}

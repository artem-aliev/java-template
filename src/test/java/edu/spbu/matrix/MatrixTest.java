package edu.spbu.matrix;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
    public void transD() throws WrongSizeException {
        Matrix m1 = new DenseMatrix(3, 3);
        Matrix result = new DenseMatrix(3, 3);
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
        } catch (FileNotFoundException | WrongSizeMatrixException | WrongSizeException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void stringS() throws FileNotFoundException, WrongSizeException {
        Matrix m = new SparseMatrix("m1.txt");
        System.out.println(m.toString());
    }
    @Test
    public void  mulDS() throws FileNotFoundException, WrongSizeMatrixException, WrongSizeException {
        Matrix m3 = new DenseMatrix("m3.txt");
        Matrix m4 = new SparseMatrix("m4.txt");
        Matrix expected = new DenseMatrix("result2.txt");
        assertEquals(expected, m3.mul(m4));
    }

    @Test
    public void mulSS() throws FileNotFoundException, WrongSizeMatrixException, WrongSizeException {
        Matrix m5 = new SparseMatrix("m5.txt");
        Matrix m6 = new SparseMatrix("m6.txt");
        Matrix expected = new SparseMatrix("result3.txt");
        Matrix o = m5.mul(m6);
        assertEquals(expected, o);
    }
    @Test
    public void mulSD() throws FileNotFoundException, WrongSizeMatrixException, WrongSizeException {
        Matrix m7 = new SparseMatrix("m7.txt");
        Matrix m8 = new DenseMatrix("m8.txt");
        Matrix expected = new DenseMatrix("result4.txt");
        assertEquals(expected, m7.mul(m8));
    }
    @Test
    public void mulSD_Exception() throws FileNotFoundException, WrongSizeMatrixException, WrongSizeException {
        Matrix m7 = new SparseMatrix("m7.txt");
        Matrix m8 = new DenseMatrix("m8.txt");
        Matrix expected = new DenseMatrix("result5.txt");
        assertFalse(expected.equals(m7.mul(m8)));
    }
    @Test
    public void transS() throws FileNotFoundException{
        Matrix m6 = new SparseMatrix("m6.txt");
        System.out.println(m6.trans().toString());
    }
}

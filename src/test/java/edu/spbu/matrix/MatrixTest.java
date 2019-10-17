package edu.spbu.matrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MatrixTest
{
  /**
   * ожидается 4 таких теста
   */
  @Test
  public void mulDD() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("m2.txt");
    Matrix expected = new DenseMatrix("result.txt");
    assertEquals(expected, m1.mul(m2));
    System.out.println(m1.mul(m2));
  }
  @Test
  public void nullmul(){
      Matrix mNul1 = new DenseMatrix(0,0);
      Matrix mNul2 = new DenseMatrix(0,0);
      Matrix resNul = mNul2.mul(mNul1);
      assertEquals(0, resNul.getCols());
      assertEquals(0,resNul.getRows());
  }
}



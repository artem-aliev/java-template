package edu.spbu.matrix;

/**
 *
 */
public interface Matrix
{
  double getCell(int r, int c) throws WrongSizeException;
  int getNumbersOfRows();
  int getNumbersOfColumns();
  int[] getCol();
  int[] getPointer();
  double[] getValues();
  void changeCell(int r, int c, double value) throws WrongSizeException;
  Matrix add(Matrix o)throws WrongSizeException, WrongSizeMatrixException;
  Matrix trans();
  boolean equals(Object o);
  String toString();
  /**
   * однопоточное умнджение матриц
   * должно поддерживаться для всех 4-х вариантов
   * @param o
   * @return
   */
  Matrix mul(Matrix o) throws WrongSizeMatrixException, WrongSizeException;

  /**
   * многопоточное умножение матриц
   * @param o
   * @return
   */
  Matrix dmul(Matrix o);

}

package edu.spbu.matrix;

/**
 * Разряженная матрица
 */
public class SparseMatrix implements Matrix
{
  /**
   * загружает матрицу из файла
   * @param fileName
   */
  public SparseMatrix(String fileName) {

  }

  @Override
  public double getCell(int r, int c) throws WrongSizeException {
    return 0;
  }

  @Override
  public int getNumbersOfRows() {
    return 0;
  }

  @Override
  public int getNumbersOfColumns() {
    return 0;
  }

  @Override
  public void changeCell(int r, int c, double value) throws WrongSizeException {

  }

  @Override
  public Matrix add(Matrix o) throws WrongSizeException, WrongSizeMatrixException {
    return null;
  }

  @Override
  public Matrix trans() {
    return null;
  }

  /**
   * однопоточное умнджение матриц
   * должно поддерживаться для всех 4-х вариантов
   *
   * @param o
   * @return
   */
  @Override public Matrix mul(Matrix o)
  {
    return null;
  }

  /**
   * многопоточное умножение матриц
   *
   * @param o
   * @return
   */
  @Override public Matrix dmul(Matrix o)
  {
    return null;
  }

  /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */
  @Override public boolean equals(Object o) {
    return false;
  }
}

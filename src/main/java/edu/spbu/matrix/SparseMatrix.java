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

  @Override
  public int getCols() {
    return 0;
  }

  @Override
  public int getRows() {
    return 0;
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

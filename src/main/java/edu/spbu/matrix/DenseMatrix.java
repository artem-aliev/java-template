package edu.spbu.matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix
{
  private int M;             // number of rows
  private int N;             // number of columns
  private double[][] data;
  public DenseMatrix(int M, int N){
    this.M = M;
    this.N = N;
    this.data = new double[M][N];
  }
  public int getNumbersOfRows() {
      return M;
  }
  public int getNumbersOfColumns() {
      return N;
  }
  @Override public double getCell(int r, int c) throws WrongSizeException{
      if(r >= getNumbersOfRows() || c >= getNumbersOfColumns()){
          throw new WrongSizeException();
      }
      return data[r][c];
  }
  public void changeCell(int r, int c, double value)throws WrongSizeException{
      if(r >= getNumbersOfRows() || c >= getNumbersOfColumns()){
          throw new WrongSizeException();
      }
      data[r][c] = value;
  }
  @Override public  Matrix add(Matrix o)throws WrongSizeMatrixException{
      if(o.getNumbersOfRows() != getNumbersOfRows() || o.getNumbersOfColumns() != getNumbersOfColumns()){
          throw new WrongSizeMatrixException();
      }
      Matrix o2 = new DenseMatrix(M, N);
      for(int i = 0; i < M; i++){
          for(int j = 0; j < N; j++){
              try {
                  o2.changeCell(i,j,getCell(i, j) + o.getCell(i, j));
              } catch (WrongSizeException e) {
                  e.printStackTrace();
              }
          }
      }
      return o2;
  }
  @Override public Matrix trans() throws WrongSizeMatrixException{
      if(M != N){
          throw new WrongSizeMatrixException();
      }
      DenseMatrix o = new DenseMatrix(M, N);
      for(int i = 0; i < M; i++){
          for(int j = i; j < N; j++){
              try {
                  o.changeCell(j, i, getCell(i, j));
                  o.changeCell(i, j, getCell(j, i));
              } catch (WrongSizeException e) {
                  e.printStackTrace();
              }
          }
      }
      return o;
  }
  /**
   * загружает матрицу из файла
   * @param fileName
   */
  public DenseMatrix(String fileName) throws FileNotFoundException {
      Scanner in = new Scanner((new FileReader(fileName)));
      String data = "";
      while (in.hasNext()) {
          data += in.nextLine() + '\n';
      }
      in.close();
      String[] array = data.split("\n");
      M = array.length;
      N = array[0].split(" ").length;
      this.data = new double[M][N];
      for(int i = 0; i < M; i++){
          String[] array2 = array[i].split(" ");
          for(int j = 0; j< N; j++){
              try {
                  changeCell(i, j, Double.parseDouble(array2[j]));
              } catch (WrongSizeException e) {
                  e.printStackTrace();
              }
          }
      }
  }
  /**
   * однопоточное умнджение матриц
   * должно поддерживаться для всех 4-х вариантов
   *
   * @param o
   * @return
   */
  @Override public Matrix mul(Matrix o) throws WrongSizeMatrixException{
      if(o.getNumbersOfRows() != getNumbersOfColumns()){
          throw new WrongSizeMatrixException();
      }
      DenseMatrix o2 = new DenseMatrix(M, o.getNumbersOfColumns());
      for(int i = 0; i < M; i++){
          for(int j = 0; j < o.getNumbersOfColumns(); j++){
              for(int k = 0; k < N; k++) {
                  try {
                      o2.changeCell(i, j, o2.getCell(i, j) + (getCell(i, k) * o.getCell(k, j)));
                  } catch (WrongSizeException e) {
                      e.printStackTrace();
                  }
              }
          }
      }
      return o2;
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

  @Override public String toString() {
      String line = "[";
      for(int i = 0; i < M-1; i++){
          line += "[";
          for(int j = 0; j < N-1; j++){
              try {
                  line += Double.toString(getCell(i, j));
                  line += ", ";
              } catch (WrongSizeException e) {
                  e.printStackTrace();
              }
          }
          try {
              line += Double.toString(getCell(i, N-1));
          } catch (WrongSizeException e) {
              e.printStackTrace();
          }
          line += "], ";
      }
        line += "[";
        for(int j = 0; j < N-1; j++){
            try {
                line += Double.toString(getCell(M-1, j));
                line += ", ";
            } catch (WrongSizeException e) {
                e.printStackTrace();
            }
        }
        try {
            line += Double.toString(getCell(M-1, N-1));
        } catch (WrongSizeException e) {
            e.printStackTrace();
        }
        line += "]";
      line += "]";
      return line;
    }

    /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */
  @Override public boolean equals(Object o) {
      if(getNumbersOfColumns() != ((Matrix)o).getNumbersOfColumns()) {
          return false;
      }
      if(getNumbersOfRows() != ((Matrix)o).getNumbersOfRows()) {
          return false;
      }
      for(int i = 0; i < M; i++) {
          for (int j = 0; j < N; j++) {
              try {
                  if (getCell(i, j) != ((Matrix) o).getCell(i, j)) {
                      return false;
                  }
              } catch (WrongSizeException e) {
                  e.printStackTrace();
              }
          }
      }
      return true;
  }

}

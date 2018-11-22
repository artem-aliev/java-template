package edu.spbu.matrix;

import java.io.*;
import java.util.ArrayList;
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
  @Override public int getNumbersOfRows() {
      return M;
  }
  @Override public int getNumbersOfColumns() {
      return N;
  }

    @Override
    public int[] getCol() {
        return new int[0];
    }

    @Override
    public int[] getPointer() {
        return new int[0];
    }

    @Override
    public double[] getValues() {
        return new double[0];
    }

    @Override public double getCell(int r, int c) throws WrongSizeException{
      if(r >= getNumbersOfRows() || c >= getNumbersOfColumns()){
          throw new WrongSizeException();
      }
      return data[r][c];
  }
  @Override public void changeCell(int r, int c, double value)throws WrongSizeException{
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
  @Override public Matrix trans(){
      DenseMatrix o = new DenseMatrix(N, M);
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
   * однопоточное умнджение матриц
   * должно поддерживаться для всех 4-х вариантов
   *
   * @param o
   * @return
   */
  @Override public Matrix mul(Matrix o) throws WrongSizeMatrixException, WrongSizeException{
      if(o.getNumbersOfRows() != getNumbersOfColumns()){
          throw new WrongSizeMatrixException();
      }
      if(o instanceof DenseMatrix){
          return mulDD(o);
      }
      if(o instanceof SparseMatrix){
          return mulDS(o);
      }
      return null;
  }
  private Matrix mulDD(Matrix o) throws WrongSizeMatrixException, WrongSizeException{
      if(N != o.getNumbersOfRows()){
          throw new WrongSizeMatrixException();
      }
      Matrix o2 = new DenseMatrix(M, o.getNumbersOfColumns());
      for(int i = 0; i < M; i++){
          for(int j = 0; j < o.getNumbersOfColumns(); j++){
              for(int k = 0; k < N; k++) {
                  ((DenseMatrix) o2).data[i][j] +=  data[i][k] * ((DenseMatrix)o).data[k][j];
              }
          }
      }
      return o2;
  }
    private Matrix mulDS(Matrix o) throws WrongSizeException {
        o = o.trans();
        double sum = 0;
        int right_matr_number = 0;
        int[] pointer_o2 = new int [getNumbersOfRows()+1];
        ArrayList<Double> values_l = new ArrayList<>();
        ArrayList<Integer> col_l = new ArrayList<>();

        for(int i = 0; i < M; i++){
            pointer_o2[i+1] = pointer_o2[i];
            for(int j = 0; j < o.getPointer().length-1; j++) {
                if(o.getPointer()[j+1] - o.getPointer()[j] > 0) {
                    right_matr_number = o.getPointer()[j];
                    sum = 0;
                    while(right_matr_number < o.getPointer()[j+1]) {
                        sum += getCell(i, o.getCol()[right_matr_number]) * o.getValues()[right_matr_number];
                        right_matr_number++;
                    }
                    if(sum != 0){
                        pointer_o2[i+1]++;
                        col_l.add(j);
                        values_l.add(sum);
                    }

                }
            }
        }
        double[] values_o2 =  values_l.stream().mapToDouble(Double::doubleValue).toArray();
        int [] col_o2 = col_l.stream().mapToInt(Integer::intValue).toArray();
        Matrix o2 = new SparseMatrix(M, o.getNumbersOfRows(), pointer_o2, col_o2, values_o2);
        return o2;
    }


  /**
   * многопоточное умножение матриц
   *
   * @param o
   * @return
   */
  @Override public Matrix dmul(Matrix o) throws WrongSizeMatrixException {
      final int NUMBER_OF_THREADS = 4;
      if(N != o.getNumbersOfRows()){
          throw new WrongSizeMatrixException();
      }
      Matrix o2 = new DenseMatrix(M, o.getNumbersOfColumns());
      class MyRunnable implements Runnable {
          private int index;
          public MyRunnable(int index) {
              this.index = index;
          }

          public void run() {
              for (int i = (M * index)/NUMBER_OF_THREADS; i < (M * (index+1))/NUMBER_OF_THREADS; i++) {
                  for (int j = 0; j < o.getNumbersOfColumns(); j++) {
                      for (int k = 0; k < N; k++) {
                          ((DenseMatrix) o2).data[i][j] += data[i][k] * ((DenseMatrix) o).data[k][j];
                      }
                  }
              }
          }
      }
      Thread[] thread = new Thread[NUMBER_OF_THREADS];
      for (int thread_counter = 0; thread_counter < thread.length; thread_counter++) {
          thread[thread_counter] = new Thread(new MyRunnable(thread_counter));
          thread[thread_counter].start();
      }
      for (int thread_counter = 0; thread_counter < thread.length; thread_counter++) {
          try {
              thread[thread_counter].join();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
      return o2;
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

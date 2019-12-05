package edu.spbu.matrix;

import java.io.*;
import java.util.HashMap;

/**
 * Разряженная матрица
 */
public class SparseMatrix implements Matrix
{
  HashMap<Integer, HashMap<Integer, Double>> vals;
  int cols, rows;


  /**
   * загружает матрицу из файла
   * @param fileName
   */
  public SparseMatrix(String fileName) {
    HashMap<Integer, HashMap<Integer, Double>> res = new HashMap<>();
    cols = 0;
    rows = 0;
    try{
      BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
      String[] str;
      HashMap<Integer, Double> cur;
      String line = br.readLine();
      int len;
      str = line.split(" ");
      len = str.length;
      while(line!=null){
        str = line.split(" ");
        cur = new HashMap<>();
        for(int i = 0; i<len; i++)
            if (str[i] != "0") {
                cur.put(i, Double.parseDouble(str[i]));
            }
        if(cur.size()!=0){
            res.put(rows++, cur);
        }
        line = br.readLine();
      }
      cols = len;
      vals = res;
    }catch (FileNotFoundException e){
        e.printStackTrace();
    }catch (IOException e){
        e.printStackTrace();
    }
  }

  public SparseMatrix(int rows, int cols, HashMap<Integer,HashMap<Integer, Double>> vals){
      this.cols = cols;
      this.rows = rows;
      this.vals = vals;
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

  @Override
  public Matrix transpose(){
      HashMap<Integer,HashMap<Integer,Double>> res = new HashMap<>();
      for(HashMap.Entry<Integer, HashMap<Integer,Double>> r : vals.entrySet()){
          for (HashMap.Entry<Integer, Double> el : r.getValue().entrySet()){
              if(!res.containsKey(el.getKey())){
                  res.put(el.getKey(), new HashMap<>());
              }
              res.get(el.getKey()).put(r.getKey(), el.getValue());
          }
      }
      return new SparseMatrix(rows,cols,res);
  }

  /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */
  @Override public boolean equals(Object o) {
      if(this==o)
          return true;
      if(o instanceof SparseMatrix){
          SparseMatrix sp = (SparseMatrix) o;
          if(sp.rows!=rows || sp.cols!=cols)
              return false;

      }
    return false;
  }
}

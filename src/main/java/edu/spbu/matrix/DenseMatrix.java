package edu.spbu.matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix {
    int rows, cols;
    double[][] denseMatrix;

    public DenseMatrix(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        this.denseMatrix = new double [rows][cols];
    }

    /**
     * загружает матрицу из файла
     *
     * @param fileName
     */
    public DenseMatrix(String fileName) {
        ArrayList<double[]> tmp = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            String line = br.readLine();
            if(line == null){
                throw new NullPointerException("пустая матрица");}
            String[] values = line.split(" ");
            this.cols = values.length;
            double[] array = Arrays.stream(values).mapToDouble(Double::parseDouble).toArray();
            tmp.add(array);
            this.rows = 1;
            while ((line = br.readLine()) != null) {
                values = line.split(" ");
                array = Arrays.stream(values).mapToDouble(Double::parseDouble).toArray();
                tmp.add(array);
                this.rows++;

            }
            this.denseMatrix = new double[this.rows][this.cols];
            for (int i = 0; i < this.rows; i++) {
                denseMatrix[i] = tmp.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * однопоточное умнджение матриц
     * должно поддерживаться для всех 4-х вариантов
     *
     * @param o
     * @return
     */
    @Override
    public Matrix mul(Matrix o) {
        if (o instanceof DenseMatrix) {
            DenseMatrix m2 = (DenseMatrix) o;
            int resRows = this.getRows();
            int resCols = m2.getCols();
            DenseMatrix res = new DenseMatrix(resRows, resCols);
            for (int i = 0; i < resRows; i++) {
                for (int j = 0; j < resCols; j++) {
                    for (int k = 0; k < this.cols; k++) {
                        res.denseMatrix[i][j] += this.denseMatrix[i][k] * m2.denseMatrix[k][j];
                    }
                }
            }
            return res;
        }

        return null;
    }

    /**
     * многопоточное умножение матриц
     *
     * @param o
     * @return
     */
    @Override
    public Matrix dmul(Matrix o) {
        return null;
    }

    /**
     * спавнивает с обоими вариантами
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if(o instanceof DenseMatrix){
            DenseMatrix m = (DenseMatrix) o;
            if(this.rows != m.rows || this.cols != m.cols ){
                return false;
            }
            else {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (this.denseMatrix[i][j] != m.denseMatrix[i][j]) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i=0; i<rows;i++) {
            for (int j = 0; j < cols; j++) {
                str.append(denseMatrix[i][j]);
                str.append(" ");
            }
            str.append("\n");
        }
        return (str.toString());
    }

    public int getCols(){
        return this.cols;
    }

    public int getRows(){
        return this.rows;
    }

}

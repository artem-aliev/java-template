package edu.spbu.matrix;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Разреженная матрица
 */
public class SparseMatrix implements Matrix {
    private int M;             // number of rows
    private int N;             // number of columns
    private double[] values;
    private int[] col;
    private int[] pointer;

    public SparseMatrix(int M, int N, int[] pointer, int[] col, double[] values) {
        this.M = M;
        this.N = N;
        this.pointer = pointer;
        this.col = col;
        this.values = values;
    }
    /**
     * загружает матрицу из файла
     *
     * @param fileName
     */
    public SparseMatrix(String fileName) throws FileNotFoundException {
        ArrayList<Double> values_l = new ArrayList<>();
        ArrayList<Integer> col_l = new ArrayList<>();
        Scanner in = new Scanner((new FileReader(fileName)));
        String data = "";
        while (in.hasNext()) {
            data += in.nextLine() + '\n';
        }
        in.close();
        String[] array = data.split("\n");
        M = array.length;
        pointer = new int[M+1];
        N = array[0].split(" ").length;

        int values_number = 0;
        for (int i = 0; i < M; i++) {
            String[] array2 = array[i].split(" ");
            for (int j = 0; j < N; j++) {
                if(!array2[j].equals("0")){
                    col_l.add(j);
                    values_l.add(Double.parseDouble(array2[j]));
                    values_number++;
                }
            }
            pointer[i+1]=values_number;
        }
        values =  values_l.stream().mapToDouble(Double::doubleValue).toArray();
        col = col_l.stream().mapToInt(Integer::intValue).toArray();
    }
    @Override public double getCell(int r, int c) throws WrongSizeException {
        if(r >= getNumbersOfRows() || c >= getNumbersOfColumns()){
            throw new WrongSizeException();
        }
        int point = pointer[r+1] - 1 - pointer[r];
        if(point < 0){
            return 0;
        }
        System.out.println(Arrays.toString(col));
        int first_col = col[pointer[r]];
        int last_col = col[pointer[r+1]-1];
        if(last_col >= c && c >= first_col) {
            for (; point >= 0; point--) {
                if (c == col[pointer[r + 1] - 1 - point]) {
                    return values[pointer[r + 1] - 1 - point];
                }
            }
        }
        return 0;
    }

    @Override public int getNumbersOfRows(){
            return M;
    }
    @Override public int getNumbersOfColumns () {
        return N;
    }
    @Override public void changeCell(int r, int c, double value) throws WrongSizeException {
    }


    @Override public Matrix add(Matrix o) throws WrongSizeException, WrongSizeMatrixException {
        return null;
    }


    @Override public Matrix trans() {
        return null;
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
            return mulSD(o);
        }
        if(o instanceof SparseMatrix){
            return mulSS(o);
        }
        return null;
    }
    private Matrix mulSD(Matrix o) throws WrongSizeException {
        Matrix o2 = new DenseMatrix(M, o.getNumbersOfColumns());
        for(int i = 0; i < M; i++){
            for(int j = 0; j < o.getNumbersOfColumns(); j++){
                for(int k = 0; k < N; k++) {
                    o2.changeCell(i, j, o2.getCell(i, j) + (getCell(i, k) * o.getCell(k, j)));
                }
            }
        }
        return o2;
    }

    private Matrix mulSS(Matrix o) throws WrongSizeException {
        boolean the_first = true;
        int point = 0;
        int[] pointer = new int [getNumbersOfRows()+1];
        ArrayList<Double> values_l = new ArrayList<>();
        ArrayList<Integer> col_l = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < o.getNumbersOfColumns(); j++) {
                for (int k = 0; k < N; k++) {
                    if(getCell(i, k) != 0 && o.getCell(k, j) != 0) {
                        if(the_first) {
                            values_l.add(getCell(i, k) * o.getCell(k, j));
                            the_first = false;
                            col_l.add(j);
                            point++;
                        }
                        else{
                            values_l.set(values_l.size()-1, values_l.get(values_l.size()-1) + getCell(i, k) * o.getCell(k, j));
                            point++;
                        }
                    }
                }
                the_first = true;
                pointer[i+1] = point;
            }
        }
        values =  values_l.stream().mapToDouble(Double::doubleValue).toArray();
        col = col_l.stream().mapToInt(Integer::intValue).toArray();
        Matrix o2 = new SparseMatrix(M, o.getNumbersOfColumns(), pointer, col, values);
        return o2;
    }

    /**
     * многопоточное умножение матриц
     *
     * @param o
     * @return
     */
    @Override public Matrix dmul(Matrix o) {
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

    @Override
    public String toString() {
        String line = "[";
        int number_in_row = 0;
        int number_of_values  = 0;
        for(int i = 0; i <= M-1; i++){
            number_in_row = pointer [i+1] - pointer[i];
            line += "[";
            for(int j = 0; j <= N-1; j++){
                if(number_of_values != pointer[M]) {
                    if (col[number_of_values] == j && number_in_row != 0) {
                        line += Double.toString(values[number_of_values]);
                        number_in_row--;
                        number_of_values++;
                    } else {
                        line += "0";
                    }
                }
                else{
                    line += "0";
                }
                if(j != N-1){
                    line += ", ";
                }
            }
            line += "]";
            if(i != M-1){
                line += ", ";
            }
        }
        line += "]";
        return line;
    }
}

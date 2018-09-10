package edu.spbu.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by artemaliev on 07/09/15.
 */
public class IntSort {
  public static void sort (int array[]) {
    myquick(array,0, array.length-1);
  }

  public static void sort (List<Integer> list) {
    Collections.sort(list);
  }
  public  static void myquick(int array[], int l, int r){
    int i = l, j = r;
    int t;
    int king = array[((l + r) / 2)];
    while (i <= j) {
      while (array[i] < king) i++;
      while (array[j] > king) j--;
      if (i <= j) {
        t = array[i];
        array[i] = array[j];
        array[j] = t;
        i++; j--;
      }
    }
    if (l < j)
      myquick(array, l, j);
    if (i < r)
      myquick(array, i, r);
  }
}

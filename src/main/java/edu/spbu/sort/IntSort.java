package edu.spbu.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by artemaliev on 07/09/15.
 */
public class IntSort {
  public static void swap (int i, int j, int array[])
  {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  public static void sort (int array[])
  {
    int length = array.length;
    int start = 0;
    int end = length - 1;
    qsort(start, end, array);
  }
  public static void qsort(int start, int end, int array[])
  {
      if (start >= end) {
          return;
      }
      int i = start;
      int j = end;
      int mid = i - (i - j) / 2;
      while (i < j) {
          while (i < mid && (array[i] <= array[mid])) {
              i++;
          }
          while (j > mid && (array[mid] <= array[j])) {
              j--;
          }
          if (i < j) {
              swap(i,j,array);
              if (i == mid) {
                  mid = j;
              }
              else if (j == mid) {
                  mid = i;
              }
          }
      }
      qsort(start, mid, array);
      qsort(mid+1, end, array);
  }

  public static void sort (List<Integer> list) {
    Collections.sort(list);
  }
}

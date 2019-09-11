package edu.spbu.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by artemaliev on 07/09/15.
 */
public class IntSort {
  public static void swap (int x1, int x2)
  {
    int temp = x1;
    x1 = x2;
    x2 = temp;
  }

  public static void sort (int array[])
  {
    int length = array.length;
    int gap = length;
    boolean check = true;
    while(gap>1||check)
    {
      if(gap>1)
      {
        gap=(int)(gap/1.3);
      }
      check = false;
      for(int i=0; i<length-gap;i++)
      {
        if(array[i]>array[i+gap])
        {
          swap(array[i], array[i+gap]);
          check = true;
        }
      }
    }

  }

  public static void sort (List<Integer> list) {
    Collections.sort(list);
  }
}

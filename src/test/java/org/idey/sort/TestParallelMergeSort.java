package org.idey.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestParallelMergeSort {
    @Test
    public void testSuccessParallelSort(){
        List<Integer> list = Arrays.asList(5,4,1,3,100,9,11,17,2,6,77,8);
        AbstractSort<Integer> sort = new ParallelMergeSort<Integer>(list,3);
        sort.sort();
        Assert.assertArrayEquals(list.toArray(new Integer[list.size()]), new Integer[]{1,2,3,4,5,6,8,9,11,17,77,100});
    }


    @Test
    public void testSuccessRangeParallelSort(){
        List<Integer> list = Arrays.asList(5,4,1,3,100,9,11,17,2,6,77,8);
        AbstractSort<Integer> sort = new ParallelMergeSort<Integer>(list,2, list.size()-1, 3);
        sort.sort();
        Assert.assertArrayEquals(list.toArray(new Integer[list.size()]), new Integer[]{5,4,1,2,3,6,8,9,11,17,77,100});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailedParallelSort(){
        List<Integer> list = Arrays.asList(5,4,1,3,100,9,11,17,2,6,77,8);
        new ParallelMergeSort<Integer>(list,1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutOfRangeError(){
        List<Integer> list = Arrays.asList(5,4,1,3,100,9);
        new ParallelMergeSort<Integer>(list,5,3,3);
    }
}

package org.idey.sort;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestSerialMergeSort {
    @Test
    public void testSerialSort(){
        List<Integer> list = Arrays.asList(5,4,1,3,100,9);
        AbstractSort<Integer> sort = new SerialMergeSort<Integer>(list);
        sort.sort();
        Assert.assertArrayEquals(list.toArray(new Integer[list.size()]), new Integer[]{1,3,4,5,9,100});
    }


    @Test
    public void testSerialSortRange(){
        List<Integer> list = Arrays.asList(5,4,1,3,100,9);
        AbstractSort<Integer> sort = new SerialMergeSort<Integer>(list,2,5);
        sort.sort();
        Assert.assertArrayEquals(list.toArray(new Integer[list.size()]), new Integer[]{5,4,1,3,9,100});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutOfRangeError(){
        List<Integer> list = Arrays.asList(5,4,1,3,100,9);
        AbstractSort<Integer> sort = new SerialMergeSort<Integer>(list,5,3);
        sort.sort();
    }
}

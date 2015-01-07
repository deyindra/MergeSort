package org.idey.sort;

import java.util.List;

/**
 * A Serial Merge Sort which will perform single threaded merge sort
 * @param <T> Generic Object which implements Comparable Interface
 * @see org.idey.sort.AbstractSort
 */
public class SerialMergeSort<T extends Comparable<T>> extends  AbstractSort<T>{

    public SerialMergeSort(List<T> list, final int start, final int end) {
        super(list, start, end);
    }

    public SerialMergeSort(List<T> list) {
        super(list);
    }

    @Override
    public void sort() {
        if(list!=null && !list.isEmpty()){
            sort(start, end);
        }
    }

    private void sort(final int from, final int to){
        if(from != to){
            int mid = (from+to)/2;
            sort(from, mid);
            sort(mid+1, to);
            merge(from, mid, to);
        }
    }

}
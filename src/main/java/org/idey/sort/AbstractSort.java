package org.idey.sort;


import java.util.ArrayList;
import java.util.List;

/**
 * An Abstract Merge Sort class
 * @param <T> any Generic object which will be implementing Comparable interface
 * @see java.util.Comparator
 * @see org.idey.sort.ParallelMergeSort
 * @see org.idey.sort.SerialMergeSort
 */
public abstract class AbstractSort<T extends Comparable<T>> {
    protected List<T> list;
    protected int start;
    protected int end;

    /**
     *
     * @param list List needs to be sorted
     * @param start Start Index (start>=0 and start<=end)
     * @param end end Index (end>=0 and end<list.size())
     * @see java.util.List
     * @see java.lang.IllegalArgumentException
     */
    protected AbstractSort(List<T> list, final int start, final int end) {
        if(list!=null && !list.isEmpty()){
            if(start>=0 && start<=end){
                this.start = start;
            }else{
                throw new IllegalArgumentException("Invalid Start");
            }

            if(end>=0 && end<list.size()){
                this.end =end;
            }else{
                throw new IllegalArgumentException("Invalid end");
            }
            this.list = list;
        }
    }

    /**
     *
     * @param list List needs to be sorted
     *
     */
    protected AbstractSort(List<T> list) {
        this(list,0,list.size()-1);
    }

    /**
     * Abstract method which is implemented at ParallelMergeSort {@link ParallelMergeSort#sort()}
     * and SerialMergeSort {@link SerialMergeSort#sort()}
     * @see org.idey.sort.ParallelMergeSort
     * @see org.idey.sort.SerialMergeSort
     */
    public abstract void sort();


    /**
     * This is merge step of the merge sort which will take O(N) time with O(N) extra space
     * @param from from Index of list
     * @param mid mid Index of the list
     * @param to to index of the list
     */
    protected void merge(final int from, final int mid, final int to){
        int n = to - from + 1;
        List<T> values = new ArrayList<T>(n);

        int fromValue = from;
        int middleValue = mid + 1;
        int index = 0;

        while (fromValue <= mid && middleValue <= to) {
            if (list.get(fromValue).compareTo(list.get(middleValue)) < 0) {
                values.add(list.get(fromValue));
                fromValue++;
            } else {
                values.add(list.get(middleValue));
                middleValue++;
            }
            index++;
        }

        while (fromValue <= mid) {
            values.add(list.get(fromValue));
            fromValue++;
            index++;
        }
        while (middleValue <= to) {
            values.add(list.get(middleValue));
            middleValue++;
            index++;
        }

        for (index = 0; index < n; index++){
            list.set(from + index,values.get(index));
        }
    }

}
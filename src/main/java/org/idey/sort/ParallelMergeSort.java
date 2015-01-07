package org.idey.sort;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * A multi threaded Merge Sort which will perform Parallel Merge Sort,The idea is to do splitting in the list
 * in 2 different thread and then do the final merge in the parent thread. {@link java.util.concurrent.CyclicBarrier}
 * is used to handle this splitting and merging
 * @param <T> A generic Object which implements Comparable interface
 * @see org.idey.sort.AbstractSort
 * @see java.util.concurrent.Executors
 * @see java.util.concurrent.ExecutorService
 */

public class ParallelMergeSort<T extends  Comparable<T>> extends AbstractSort<T> {
    private final int thresholds;
    private final ExecutorService executor;

    /**
     *
     * @param list List which will be sorted using Multi threading
     * @param start start index of the list
     * @param end end index of the list
     * @param thresholds threshold beyond of which it will be executed as {@link org.idey.sort.SerialMergeSort}
     * @see org.idey.sort.SerialMergeSort
     */
    public ParallelMergeSort(List<T> list, int start, int end, final int thresholds) {
        super(list, start, end);
        if(thresholds >1){
            this.thresholds = thresholds;
        }else{
            throw new IllegalArgumentException("Invalid threshold value");
        }
        executor = Executors.newCachedThreadPool();
    }

    public ParallelMergeSort(List<T> list, final int thresholds) {
        this(list,0,list.size()-1, thresholds);
    }

    @Override
    public void sort() {
        try{
            if(list!=null && !list.isEmpty()){
                Future<?> sort = executor.submit(new ParallelMerge(start,end));
                sort.get();
            }
        }catch (ExecutionException e){
            throw new RuntimeException(e);
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }finally {
            executor.shutdown();
        }
    }


    private class ParallelMerge implements Runnable{
        private CyclicBarrier barrier;
        private final int fromIndex;
        private final int toIndex;

        private ParallelMerge(CyclicBarrier barrier, final int fromIndex, final int toIndex) {
            this.barrier = barrier;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        private ParallelMerge(final int fromIndex, final int toIndex) {
            this(null,fromIndex, toIndex);
        }

        @Override
        public void run() {
            try{
                if(fromIndex != toIndex){
                    final int size = toIndex - fromIndex + 1;
                    //If the size is less than or equal to threshold it will execute as SerialMergeSort
                    if(size<= thresholds){
                        AbstractSort<T> sortObject = new SerialMergeSort<T>(list, fromIndex, toIndex);
                        sortObject.sort();
                    }else{
                        final int mid = (fromIndex+toIndex)/2;
                        CyclicBarrier c = new CyclicBarrier(2, new MergeRunnable(fromIndex, mid, toIndex));
                        ParallelMerge leftParallelMerge = new ParallelMerge(c,fromIndex,mid);
                        ParallelMerge rightParallelMerge = new ParallelMerge(c,mid+1,toIndex);

                        Future<?> leftFuture = executor.submit(leftParallelMerge);
                        Future<?> rightFuture = executor.submit(rightParallelMerge);

                        leftFuture.get();
                        rightFuture.get();
                    }
                }
                if(barrier !=null){
                    barrier.await();
                }
            }catch (ExecutionException e){
                throw new RuntimeException(e);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }catch(BrokenBarrierException e){
                throw new RuntimeException(e);
            }
        }
    }



    private class MergeRunnable implements Runnable{
        private final int from;
        private final int mid;
        private final int to;

        private MergeRunnable(final int from, final int mid, final int to) {
            this.from = from;
            this.mid = mid;
            this.to = to;
        }

        @Override
        public void run() {
            merge(from, mid, to);
        }
    }

}
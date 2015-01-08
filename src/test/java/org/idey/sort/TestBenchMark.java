package org.idey.sort;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart(filePrefix = "benchmark-lists")
public class TestBenchMark extends AbstractBenchmark{
    private List<Integer> list;
    private Random rand = new Random();
    @Before
    public void setup(){
        System.out.println("Setup started...");
        list = new ArrayList<Integer>();
        for(int i=0;i<3000000;i++){
            int random = rand.nextInt((30000000 - i) + 1) + i;
            list.add(random);
        }
        System.out.println("Setup FINISHED...");
    }

    @BenchmarkOptions(benchmarkRounds = 20, warmupRounds = 4, callgc = true)
    @Test
    public void testSerialSort(){
        System.out.println("SERIAL TEST STARTED...");
        AbstractSort<Integer> sort = new SerialMergeSort<Integer>(list);
        sort.sort();
        System.out.println("SERIAL TEST FINISHED...");
    }


    @BenchmarkOptions(benchmarkRounds = 20, warmupRounds =4, callgc = true)
    @Test
    public void testParallelSort(){
        System.out.println("PARALLEL TEST STARTED...");
        AbstractSort<Integer> sort = new ParallelMergeSort<Integer>(list,60000);
        sort.sort();
        System.out.println("PARALLEL TEST FINISHED...");
    }

    @After
    public void cleanup(){
        list.clear();
        list=null;
    }
}


package heaps;

import java.util.Arrays;
import java.util.PriorityQueue;

public class PriorityQueueSample {
    static int [] input=new int[] {3,1,6,5,2,4,1,10,7};
    public static void main(String args[]){
        PriorityQueue minHeap= new PriorityQueue<>(Arrays.asList(3,1,6,5,2,4,1,10,7));
        System.out.println( Arrays.toString(input));
        System.out.println( Arrays.toString(minHeap.toArray()));

    }
}

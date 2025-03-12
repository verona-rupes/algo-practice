package heaps;

import java.util.Arrays;

/**
 * Hello world!
 */
public class MinHeap {
    static int[] input = new int[]{3, 1, 6, 5, 2, 4, 1, 10, 7};
    static int[] minHeap = new int[input.length];

    public static void main(String[] args) {
        System.out.println(Arrays.toString(input));
        for (int k = 0; k < input.length; k++) {
            insert(k);
        }
        System.out.println(Arrays.toString(minHeap));
    }


    private static void insert(int index) {
        minHeap[index] = input[index];
        minHeapify(index);
    }

    private static void minHeapify(int cIndex) {
        int parent = getParent(cIndex);
        if (cIndex == parent) return;
        if (minHeap[cIndex] < minHeap[parent]) {
            swap(cIndex,parent);
        }
        minHeapify(parent);
    }

    private static void swap(int cIndex,int pIndex){
        int c = minHeap[cIndex];
        minHeap[cIndex] = minHeap[pIndex];
        minHeap[pIndex] = c;
    }

    private static int getParent(int index) {
        return index == 0 ? 0 : ((index % 2 == 0) ? (index - 2) / 2 : (index - 1) / 2);
    }

    private static int getLeft(int index){
        return   2*index +1;
    }

    private static int getRight(int index){
        return   2*index +2;
    }



    private static int deleteSmallest(){
        int parent=0;
        minHeap[parent]=minHeap[minHeap.length-1];

        if(minHeap[getLeft(parent)]<minHeap[parent]){
                swap(getLeft(parent),parent);
                parent=getLeft(parent);
        }else if(minHeap[getRight(parent)]<minHeap[parent]){
            swap(getRight(parent),parent);
        }

        return 0;
    }

}

package com.example.thread.example;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Created with IntelliJ IDEA.
 * @author: weijie
 * @Date: 2019/9/30
 * @Time: 17:24
 * @Description: No Description
 */
public class TopKTest {

    Integer[] data;
    Integer numSize = 10000;

    public TopKTest() {
        this.data = initData();
    }

    private Integer[] initData() {
        return getRandomArray(numSize);

    }

    private Integer[] getRandomArray(Integer numSize) {
        Integer[] result = new Integer[numSize];
        for (int i = 0; i < numSize; i++) {
            result[i] = (int) Math.random() * 100;
        }
        return result;
    }


    public static void main(String[] args) {
        TopKTest topKTest = new TopKTest();
        //堆排序


    }

    /**
     * 堆排序
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minQueue = new PriorityQueue<>(k);
        for (int num : nums) {
            if (minQueue.size() < k || num > minQueue.peek()) {
                minQueue.offer(num);
            }
            if (minQueue.size() > k) {
                minQueue.poll();
            }
        }
        return minQueue.peek();
    }
}

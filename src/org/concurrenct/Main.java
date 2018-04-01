package org.concurrenct;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author zjb
 * @date 2018/4/1.
 */
public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        int m = 4;
        int n = 16;
        int k = 3;
        Storage storage = new Storage(n, m, linkedList);
        Producer producer = new Producer();
        producer.setStorage(storage);
        producer.start();

        List<Consumer> consumerList = new ArrayList<>(k);
        for(int i=1; i<=k; i++){
            Consumer consumer = new Consumer();
            consumer.setMethodNum(i);
            consumer.setStorage(storage);
            consumerList.add(consumer);
        }

        consumerList.forEach(c->c.start());
    }
}

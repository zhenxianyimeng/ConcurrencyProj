package org.concurrenct;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zjb
 * @date 2018/4/1.
 */
public class Storage {
    private int n;

    private int m;

    private int count;

    private LinkedList<Integer> list = new LinkedList<>();

    public Storage() {
    }

    public Storage(int n, int m, LinkedList<Integer> list) {
        this.n = n;
        this.m = m;
        this.list = list;
        count = 0;
    }

    public void produce() {
        while (count < n) {
            Random random = new Random();
            synchronized (list) {
                // 如果仓库已满
                while (list.size() == m) {
                    System.out.println("仓库已满，【producer】： 暂时不能执行生产任务!");
                    try {
                        // 由于条件不满足，生产阻塞
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 生产产品
                list.add(++count);
//                list.add(Math.abs(random.nextInt()));
                System.out.println("【producer】：生产了一个产品\t【现仓储量为】:" + list.size());

                list.notifyAll();
            }
        }
    }

    // 消费产品
    public void consumeEven() {
        while (!(count >= n && isAllnotEven())) {
            synchronized (list) {
                //如果仓库存储量不足
                while (list.size() == 0) {
                    System.out.println("仓库已空，【consumerEven】： 暂时不能执行消费任务!");
                    try {
                        // 由于条件不满足，消费阻塞
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i=0; i<list.size(); i++){
                    if(list.get(i)%2==0){
                        System.out.println("【consumerEven】：消费了一个产品\t【现仓储量为】:" + list.size());
                        System.out.println("*********************Even+:" + list.get(i));
                        list.remove(i);
                    }
                }
//                int num = list.peek();
//                System.out.println("【consumerEven】：消费了一个产品\t【现仓储量为】:" + list.size());
//                if (num % 2 == 0) {
//                    System.out.println("*********************Even+:" + num);
//                    list.pop();
//                }
                list.notifyAll();
            }
        }
    }

    // 消费产品
    public void consumeOdd() {
        while (!(count >= n && isAllnotOdd())) {
            synchronized (list) {
                //如果仓库存储量不足
                while (list.size() == 0) {
                    System.out.println("仓库已空，【consumerOdd】： 暂时不能执行消费任务!");
                    try {
                        // 由于条件不满足，消费阻塞
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int i=0; i<list.size(); i++){
                    if(list.get(i)%2==1){
                        System.out.println("【consumerOdd】：消费了一个产品\t【现仓储量为】:" + list.size());
                        System.out.println("*********************Odd+:" + list.get(i));
                        list.remove(i);
                    }
                }
//                int num = list.peek();
//            if (num % 2 == 1 || true) {
//                System.out.println("*********************Odd+:" + num);
//                list.pop();
//            }
                list.notifyAll();
            }
        }
    }

    // 消费产品
    public void consumeNum(int methodNum) {
        synchronized (list) {
            //如果仓库存储量不足
            while (list.size() == 0) {
                System.out.println("仓库已空，【consumeNum】： 暂时不能执行消费任务!");
                try {
                    // 由于条件不满足，消费阻塞
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i=0; i<list.size(); i++){
                if(list.get(i)%methodNum==0){
                    System.out.println("【consumerOdd】：消费了一个产品\t【现仓储量为】:" + list.size());
                    System.out.println("*********************Odd+:" + list.get(i));
                    list.remove(i);
                }
            }
            list.notifyAll();
        }
    }

    private boolean isAllnotEven(){
        for (int i=0; i<list.size(); i++){
            if(list.get(i)%2==0){
                return false;
            }
        }
        return true;
    }

    private boolean isAllnotOdd(){
        for (int i=0; i<list.size(); i++){
            if(list.get(i)%2==1){
                return false;
            }
        }
        return true;
    }

    private boolean isAllnotNumberDiv(int methodNum){

        for (int i=0; i<list.size(); i++){
            if(list.get(i)%methodNum==0){
                return false;
            }
        }
        return true;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public LinkedList<Integer> getList() {
        return list;
    }

    public void setList(LinkedList<Integer> list) {
        this.list = list;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }
}

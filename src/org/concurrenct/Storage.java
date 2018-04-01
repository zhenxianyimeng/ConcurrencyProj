package org.concurrenct;

import java.io.FileWriter;
import java.io.IOException;
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

    private AtomicInteger count;

    private LinkedList<Integer> list = new LinkedList<>();

    public Storage() {
    }

    public Storage(int n, int m, LinkedList<Integer> list) {
        this.n = n;
        this.m = m;
        this.list = list;
        count = new AtomicInteger(0);
    }

    public void produce() {
        while (count.intValue() < n) {
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
                count.incrementAndGet();
                list.add(count.intValue());
//                list.add(Math.abs(random.nextInt()));
                System.out.println("【producer】：生产了一个产品\t【现仓储量为】:" + list.size());

                list.notifyAll();
            }
        }
    }

    // 消费产品
    public void consumeEven() {
        FileWriter fw = null;
        try {
            fw = new FileWriter("even-numbers.txt");
            while (!(count.intValue() >= n && isAllnotEven())) {
                synchronized (list) {
                    //如果仓库存储量不足
                    while (list.size() == 0) {
                        if(count.intValue() >= n && isAllnotEven()){
                            break;
                        }
                        System.out.println("仓库已空，【consumerEven】： 暂时不能执行消费任务!");
                        try {
                            // 由于条件不满足，消费阻塞
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) % 2 == 0) {
                            System.out.println("【consumerEven】：消费了一个产品\t【现仓储量为】:" + list.size());
                            System.out.println("*********************Even+:" + list.get(i));
                            fw.write(list.get(i).toString() + "\n");
                            list.remove(i);
                        }
                    }

                    list.notifyAll();
                }
            }
            fw.flush();
        } catch (Exception e) {

        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // 消费产品
    public void consumeOdd() {
        FileWriter fw = null;
        try {
            fw = new FileWriter("odd-numbers.txt");
            while (!(count.intValue() >= n && isAllnotOdd())) {
                synchronized (list) {
                    //如果仓库存储量不足
                    while (list.size() == 0) {
                        if(count.intValue() >= n && isAllnotOdd()){
                            break;
                        }
                        System.out.println("仓库已空，【consumerOdd】： 暂时不能执行消费任务!");
                        try {
                            // 由于条件不满足，消费阻塞
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) % 2 == 1) {
                            System.out.println("【consumerOdd】：消费了一个产品\t【现仓储量为】:" + list.size());
                            System.out.println("*********************Odd+:" + list.get(i));
                            fw.write(list.get(i).toString() + "\n");
                            list.remove(i);
                        }
                    }
                    list.notifyAll();
                }
            }
            fw.flush();
        } catch (Exception e) {

        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 消费产品
    public void consumeNum(int methodNum) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("div" + methodNum + "-numbers.txt");
            while (!(count.intValue() >= n && isAllnotNumberDiv(methodNum))) {
                synchronized (list) {
                    //如果仓库存储量不足
                    while (list.size() == 0) {
                        if(count.intValue() >= n && isAllnotNumberDiv(methodNum)){
                            break;
                        }
                        System.out.println("仓库已空，【consumeNum】" + methodNum + "： 暂时不能执行消费任务!");
                        try {
                            // 由于条件不满足，消费阻塞
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) % methodNum == 0) {
                            System.out.println("【consumeNum】" + methodNum + "：消费了一个产品\t【现仓储量为】:" + list.size());
                            System.out.println("*********************" + methodNum + ":" + list.get(i));
                            fw.write(list.get(i).toString() + "\n");
                            list.remove(i);
                        }
                    }
                    list.notifyAll();
                }
            }
            fw.flush();
        } catch (Exception e) {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private boolean isAllnotEven() {
        synchronized (list) {
            if (list.size() == 0) {
                return true;
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) % 2 == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean isAllnotOdd() {
        synchronized (list) {
            if (list.size() == 0) {
                return true;
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) % 2 == 1) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean isAllnotNumberDiv(int methodNum) {
        synchronized (list) {
            if (list.size() == 0) {
                return true;
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) % methodNum == 0) {
                    return false;
                }
            }

            return true;
        }
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

package org.concurrenct;

import java.util.Stack;

/**
 * @author zjb
 * @date 2018/4/1.
 */
public class NumberHandler implements Runnable{

    private Integer methodNum;

    private Stack<Integer> stack;

    private Integer m;

    @Override
    public void run() {

    }

    private void methodGenerateNum(){

    }

    private void methodA(){

    }

    private void methodB(){

    }

    public void setMethodNum(Integer methodNum) {
        this.methodNum = methodNum;
    }

    public void setStack(Stack<Integer> stack) {
        this.stack = stack;
    }

    public void setM(Integer m) {
        this.m = m;
    }
}

package org.concurrenct;

/**
 * @author zjb
 * @date 2018/4/1.
 */
public class Producer extends Thread{

    private String producer;
    private Storage storage;

    @Override
    public void run() {
        produce(producer);
    }

    public void produce(String producer)
    {
        storage.produce();
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}

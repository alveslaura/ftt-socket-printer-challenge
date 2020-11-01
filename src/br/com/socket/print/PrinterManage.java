package br.com.socket.print;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PrinterManage
{
    ConcurrentLinkedQueue<String> printQueue;

    private static PrinterManage instance;

    private PrinterManage(){
        printQueue = new ConcurrentLinkedQueue<>();
    }

    public static PrinterManage getInstance(){
        if(instance == null){
            instance = new PrinterManage();
        }
        return instance;
    }

    List<ThreadPrinterManage> threads;

    public void addMsgPrint(String msgAudit){
        printQueue.add(msgAudit);
    }

    String removeMsgPrint(){
        return printQueue.poll();
    }

    public void activate(){
        if(threads == null){
            threads = new LinkedList<>();
            for (int i = 0; i < 5; i++) {
                ThreadPrinterManage thread = new ThreadPrinterManage();
                thread.setName("Thead " + (i + 1));
                thread.start();
                threads.add(thread);
            }
        }

    }

    public void deactivate(){
        if(threads != null){
            for (ThreadPrinterManage thread:
                    threads) {
                thread.setStatus(false);
                try{
                    thread.join(3000);
                }
                catch (InterruptedException ex){
                    Logger.getLogger(PrinterManage.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(thread.isAlive()){
                    thread.interrupt();
                }
            }
        }
    }
}


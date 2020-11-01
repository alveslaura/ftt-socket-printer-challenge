package br.com.socket.print;

import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

 public class ThreadPrinterManage  extends Thread {
    private boolean status;

    public void run() {
        setStatus(true);
        while (status) {
            try {
                String msg = PrinterManage.getInstance().removeMsgPrint();
                if (msg != null) {
                    sendMsgPrint(msg);
                }
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadPrinterManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setStatus(boolean value) {
        status = value;
    }

    private void sendMsgPrint(String msg) throws InterruptedException {
        System.out.printf("%s - Impressão pela %s = %s\n", Instant.now().toString(), getName(), msg);
        Thread.sleep(100);
    }
}

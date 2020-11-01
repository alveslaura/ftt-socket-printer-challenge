package br.com.socket.customers;

import br.com.socket.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

    public class CustomersThread extends Thread {

    public Socket getCustumersSocket() {
        return custumersSocket;
    }

    public void setCustumersSocket(Socket custumersSocket) {
        this.custumersSocket = custumersSocket;
    }

    private Socket custumersSocket;

    private StateMachineMessage stateMachine = new StateMachineMessage();

    @Override
    public void run() {
        try{
            stateMachine.start();
            System.out.println(custumersSocket.hashCode() +
                    ": conexão estabelecida");
            InputStream stream = custumersSocket.getInputStream();
            try{
                int readBytes = 0;
                do{
                    byte[] data = new byte[1024];
                    readBytes = stream.read(data);
                    if(readBytes>0){
                        List<Message> msgs = stateMachine.normalizeData(data, readBytes);
                        resolveMessages(msgs);
                    }
                } while (readBytes!=-1);
            }
            finally {
                if(stream !=null){
                    stream.close();
                }
                if(custumersSocket.isConnected()){
                    custumersSocket.close();
                }
            }
        }
        catch (IOException ex){
            if(ex.getMessage().equals("Socket Closed")){
                System.out.println(custumersSocket.hashCode() + ": Conexão cliente encerrada");
            }
            else{
                Logger.getLogger(CustomersThread.class.getName()).log(Level.SEVERE, null, ex );
            }
        }
    }

    private void resolveMessages(List<Message> messages){
        for (Message m:
                messages) {
            switch (m.getOpCode()){
                case "P":
                    //PrinterManager
                    break;
                case "C":

                    break;
                case "S":

                    break;
                case "T":
                    System.out.println(custumersSocket.hashCode() + ": Status:");
                    break;
            }
        }
    }

    public void finish() throws IOException {
        if(custumersSocket.isConnected()){
            custumersSocket.close();
        }
    }

    public void list(){
        if(custumersSocket.isConnected() && !custumersSocket.isClosed()){
            System.out.println(custumersSocket.hashCode() + ": Conexão estabelecida");
        }
        else{
            System.out.println(custumersSocket.hashCode() + ": Conexão encerrada");
        }
    }
}

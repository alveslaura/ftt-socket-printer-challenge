package br.com.socket.customers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ManageCustomers  extends Thread {

    List<CustomersThread> customersThreadList;

    public ManageCustomers(){
        customersThreadList = new ArrayList<>();
    }

    public ServerSocket server;

    private Boolean ativo;

    @Override
    public void run() {
        try{
            ativo = true;
            server = new ServerSocket(123);

            while (ativo){
                Socket socket = server.accept();
                addThreadCustomers(socket);
                Thread.sleep(10);
            }

            if(!server.isClosed()){
                server.close();
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addThreadCustomers(Socket s){
        CustomersThread customers = new CustomersThread();
        customers.setCustumersSocket(s);
        customersThreadList.add(customers);
        customers.start();
    }

    public void finishThreadCustomers() throws IOException {
        for (CustomersThread customersThread:
                customersThreadList) {
            try{
                customersThread.finish();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        ativo = false;
        server.close();
    }

    public void listCustomers(){
        for (CustomersThread customersThread:
                customersThreadList) {
            customersThread.list();
        }
    }
}


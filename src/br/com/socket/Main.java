package br.com.socket;

import br.com.socket.customers.ManageCustomers;
import br.com.socket.print.PrinterManage;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        ManageCustomers server = new ManageCustomers();
        server.start();

        PrinterManage.getInstance().activate();

        Scanner scan = new Scanner(System.in);
        try {
            boolean exit = false;
            do{
                System.out.println("Bem vindo(a) ao sistema de gerenciamento de impressão!");
                System.out.println("1 - Listar customers");
                System.out.println("2 - Sair");
                int opc = scan.nextInt();
                switch (opc){
                    case 1:
                        server.listCustomers();
                    case 2:
                        exit = true;
                        break;
                }
            }while (!exit);
        } finally {
            scan.close();
            server.finishThreadCustomers();
            PrinterManage.getInstance().deactivate();
        }
    }
}

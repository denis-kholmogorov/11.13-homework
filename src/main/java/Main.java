import Bank.Bank;

import java.util.ArrayList;

public class Main
{
    public static ArrayList<Client> listClients = new ArrayList<Client>();
    public static void main(String[] args)
    {

        int clients = 7;

        Bank bank = new Bank();

        for(int i = 0; i < clients; i++){
            bank.setNewAccounts(String.valueOf(i),  100000);
            listClients.add(new Client(i, bank, clients));
        }
        System.out.println(bank.accountCounts() - 1);

        listClients.forEach(client -> {
            new Thread(client).start();

        });
    }
}

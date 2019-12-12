import Bank.Bank;

import java.util.ArrayList;

public class Main
{
    public static ArrayList<Client> listClients = new ArrayList<Client>();
    public static void main(String[] args)
    {

        int clients = 50;

        Bank bank = new Bank();

        for(int i = 0; i < clients; i++){
            bank.setNewAccounts(String.valueOf(i), (long) ((Math.random() * 1000000)));
            listClients.add(new Client(i, bank, clients));
        }
        System.out.println(bank.accountCounts());

        listClients.forEach(client -> {
            client.run();
        });


    }
}

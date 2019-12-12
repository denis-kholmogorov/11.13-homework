import Bank.Bank;

public class Client implements Runnable {


    private Bank bank;
    private int nameNumber;
    private int maxCountClients;

    public Client(int nameNumber, Bank bank, int maxCountClients){
        this.bank = bank;
        this.nameNumber = nameNumber;
        this.maxCountClients = maxCountClients;
    }

    @Override
    public void run() {

        int numberAnotherClient = anotherAccountNumber(maxCountClients);

        while (numberAnotherClient == (nameNumber)) {
            numberAnotherClient = anotherAccountNumber(maxCountClients);
        }

        int money = (int)(Math.random() * 100000);
        bank.transfer(String.valueOf(nameNumber), String.valueOf(numberAnotherClient), money);
        System.out.println("Я " + nameNumber + " перевожу деньги слиенту " + numberAnotherClient + " сумму = " + money);
        System.out.println("Мой " + nameNumber + " баланс " + bank.getBalance(String.valueOf(nameNumber)));
    }

    private int anotherAccountNumber(int maxCountClients){
        int numberAnotherClient = (int)(Math.random() * maxCountClients);
        return numberAnotherClient;
    }
}

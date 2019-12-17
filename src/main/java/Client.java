import Bank.Bank;

public class Client implements Runnable {


    private Bank bank;
    private int nameNumber;
    private int maxCountClients;

    public Client(int nameNumber, Bank bank, int maxCountClients) {
        this.bank = bank;
        this.nameNumber = nameNumber;
        this.maxCountClients = maxCountClients;
    }

    @Override
    public void run() {

        switch (nameNumber) {
            case 0:
                bank.transfer(String.valueOf(nameNumber), String.valueOf(6), 60000);
                break;
            case 1:
                bank.transfer(String.valueOf(nameNumber), String.valueOf(5), 20000);
                break;
            case 2:
                bank.transfer(String.valueOf(nameNumber), String.valueOf(1), 10000);
                break;
            case 3:
                bank.transfer(String.valueOf(nameNumber), String.valueOf(0), 30000);
                break;
            case 4:
                bank.transfer(String.valueOf(nameNumber), String.valueOf(2), 400000);
                break;
            case 5:
                bank.transfer(String.valueOf(nameNumber), String.valueOf(1), 49999);
                break;
            case 6:
                bank.transfer(String.valueOf(nameNumber), String.valueOf(0), 10000);
                break;
        }
    }
}

        /*int numberAnotherClient = anotherAccountNumber(maxCountClients);

        while (numberAnotherClient == (nameNumber)) {
            numberAnotherClient = anotherAccountNumber(maxCountClients);
        }

        int money = (int)(Math.random() * 53000);

        bank.transfer(String.valueOf(nameNumber), String.valueOf(numberAnotherClient), money);

    }

    private int anotherAccountNumber(int maxCountClients){
        int numberAnotherClient = (int)(Math.random() * maxCountClients);
        return numberAnotherClient;
    }*/


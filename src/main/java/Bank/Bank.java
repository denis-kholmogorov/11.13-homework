package Bank;

import java.util.HashMap;
import java.util.Random;

public class Bank {

    private HashMap<String, Account> accounts = new HashMap<>();

    private final Random random = new Random();

    public Account getAccount(String accNumber) {
        return accounts.get(accNumber);
    }

    public void setNewAccounts(String accNumber, long money) {
        accounts.put(accNumber, new Account(money, accNumber));
    }

    public int accountCounts() {
        return accounts.size();
    }

    public boolean isFraud(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        if (amount < 50000) {
            realize(fromAccountNum, toAccountNum, amount);
        } else {
            realizeWithCompare(fromAccountNum, toAccountNum, amount);
        }
    }


    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        {
            synchronized (accounts) {
                return accounts.get(accountNum).getMoney();
            }
        }
    }

    public void realize(String fromAccountNum, String toAccountNum, long amount) {

        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);
        System.out.println("Клиент " + fromAccount.getAccNumber() + " с балансом "
                + getBalance(fromAccountNum) + " пытается перевести деньги клиенту "
                + toAccount.getAccNumber() + " с балансом " + getBalance(toAccountNum) + " сумму - " + amount);
        try {
            if (!fromAccount.isBlock() && !toAccount.isBlock()) {
                fromAccount.reduceMoney(amount);
                toAccount.addMoney(amount);
                System.out.println("УСПЕХ! Перевод успешно завершен от клиента " + fromAccount.getAccNumber()
                        + " с балансом " + getBalance(fromAccountNum) + " клиенту "
                        + toAccount.getAccNumber() + " с балансом " + getBalance(toAccountNum));
            } else if (fromAccount.isBlock()) {
                System.err.println("СТОП! На данный момент клиент " + fromAccount.getAccNumber()
                        + " заблокирован и не может перевести деньги " + toAccount.getAccNumber());
            } else if (toAccount.isBlock()) {
                System.err.println("СТОП! На данный момент " + fromAccount.getAccNumber()
                        + " не может перевести деньги т.к счет клиента " + toAccount.getAccNumber() + " заблокрован");
            }
        } catch (UnavailableQuantityException e) {
            System.out.println(e.getMessage());
        }

    }

    public void realizeWithCompare(String fromAccountNum, String toAccountNum, long amount){
        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);
        System.out.println("ПРОВЕРКА! Клиент " + fromAccount.getAccNumber() + " с балансом "
                + getBalance(fromAccountNum) + " пытается перевести деньги клиенту "
                + toAccount.getAccNumber() + " с балансом " + getBalance(toAccountNum)
                + " сумму - " + amount +" необходима проверка");
        try {
            if (!fromAccount.isBlock() && !toAccount.isBlock()) {
                fromAccount.reduceMoney(amount);
                toAccount.addMoney(amount);
                fromAccount.setBlock(true);
                toAccount.setBlock(true);
                System.out.println("Началась проверка счета клиента " + fromAccount.getAccNumber() +
                        " и клиента " + toAccount.getAccNumber());
                boolean block = isFraud(fromAccount.getAccNumber(), toAccount.getAccNumber(), amount);
                if (!block) {
                    System.out.println("Проверка прошла успешно счета клиента " + fromAccount.getAccNumber()
                            + " с балансом " + getBalance(fromAccountNum) + " и клиента " + toAccount.getAccNumber()
                            + " с балансом " + getBalance(toAccountNum) + " разблокированы!");
                    fromAccount.setBlock(false);
                    toAccount.setBlock(false);
                } else {
                    System.err.println("Счета клиента " + fromAccount.getAccNumber() + " и клиента "
                            + toAccount.getAccNumber() + " Заблокированы!!!");
                }

            } else if (fromAccount.isBlock()) {
                System.out.println("На данный момент счет клиента " + fromAccount.getAccNumber() + " заблокирован");
            } else if (toAccount.isBlock()) {
                System.out.println("На данный момент счет клиента " + toAccount.getAccNumber() + " заблокрован");
            }
        } catch (UnavailableQuantityException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}



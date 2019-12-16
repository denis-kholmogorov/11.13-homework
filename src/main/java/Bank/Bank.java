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
        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);

        System.out.println("Клиент " + fromAccount.getAccNumber() + " с балансом "
                + getBalance(fromAccountNum) + " пытается перевести деньги клиенту "
                + toAccount.getAccNumber() + " с балансом " + getBalance(toAccountNum) + " сумму - " + amount);
            try {
                if (!fromAccount.isBlock() && !toAccount.isBlock()) {   // Проверка блокировки счетов
                    synchronized (this) {
                        fromAccount.reduceMoney(amount);                    // Проводим снятие денег
                        toAccount.addMoney(amount);                         // Проводим пополнение денег
                        }
                    if (amount > 50000) {                               // Проверка суммы
                        synchronized (this) {
                            fromAccount.setBlock(true);                     // Блокировка отправителя на время проверки
                            toAccount.setBlock(true);                       // Блокировка принимающего
                            }
                        System.out.println("Началась проверка счета клиента " + fromAccount.getAccNumber() +
                                " и клиента " + toAccount.getAccNumber());
                        boolean block = isFraud(fromAccount.getAccNumber(), toAccount.getAccNumber(), amount); // Проверка перевода
                        if (!block) {                                                                          // Проверка результата проверки
                            System.out.println("Проверка прошла успешно счета клиента " + fromAccount.getAccNumber()
                                    + " с балансом " + getBalance(fromAccountNum) + " и клиента " + toAccount.getAccNumber()
                                    + " с балансом " + getBalance(toAccountNum) + " разблокированы!");
                            synchronized (this) {
                                fromAccount.setBlock(false);        // Разблокировка счета отправителя
                                toAccount.setBlock(false);          // Разблокировка счета принимающего
                                }
                        } else {
                            System.err.println("Счет клиента " + fromAccount.getAccNumber() + " с балансом "
                                    + getBalance(fromAccountNum) + " и счет клиента  "
                                    + toAccount.getAccNumber() + " с балансом " + getBalance(toAccountNum)
                                    + " Заблокированы");
                        }
                    } else {
                        System.out.println("УСПЕХ! Перевод успешно завершен от клиента " + fromAccount.getAccNumber()
                                + " с балансом " + getBalance(fromAccountNum) + " клиенту "
                                + toAccount.getAccNumber() + " с балансом " + getBalance(toAccountNum));
                    }
                }
                else if (fromAccount.isBlock()) // проверка заблокированного счета
                {
                    System.err.println("СТОП! На данный момент клиент " + fromAccount.getAccNumber()
                            + " заблокирован и не может перевести деньги " + toAccount.getAccNumber());
                }
                else if (toAccount.isBlock()) // проверка заблокированного счета
                {
                    System.err.println("СТОП! На данный момент клиент " + fromAccount.getAccNumber()
                            + " не может перевести деньги т.к счет клиента " + toAccount.getAccNumber() + " заблокрован");
                }

            } catch (UnavailableQuantityException e) {
                System.out.println(e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        {
            synchronized (this) {
                return accounts.get(accountNum).getMoney();
            }
        }
    }
}





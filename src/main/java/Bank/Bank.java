package Bank;

import java.util.HashMap;
import java.util.Random;

public class Bank
{

    private HashMap<String, Account> accounts = new HashMap<>();

    private final Random random = new Random();

    public Account getAccount (String accNumber) {
        return accounts.get(accNumber);
    }

    public void setNewAccounts(String accNumber, long money) {
        accounts.put(accNumber, new Account(money, accNumber));
    }

    public int accountCounts(){
        return accounts.size();
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException
    {
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
    public synchronized void transfer(String fromAccountNum, String toAccountNum, long amount)
    {
        try {
            accounts.get(fromAccountNum).reduceMoney(amount);
            accounts.get(toAccountNum).addMoney(amount);
        } catch (ArithmeticException e){
            System.out.println("На счету нехватает средств или счет заблокирован");
        } catch (Exception e){
            System.out.println("Уважаемый " + e.getMessage() + "! Ваш счет заблокирован");
        }

        if(amount > 50000) {
            try
            {
                boolean block = isFraud(fromAccountNum, toAccountNum, amount);

                if(block){
                    accounts.get(fromAccountNum).setBlock(true);
                }
            } catch (InterruptedException e)
            {
                    e.printStackTrace();
            }
        }

    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum)
    {
        synchronized (this) {
            return accounts.get(accountNum).getMoney();
        }
    }

}

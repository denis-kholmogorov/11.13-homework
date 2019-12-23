package Bank;

import java.util.HashMap;
import java.util.Random;

public class Bank {

    private HashMap<Integer, Account> accounts = new HashMap<>();

    private final Random random = new Random();

    public void setNewAccounts(int accountId, long money)
    {
        accounts.put(accountId, new Account(money, accountId));
    }

    public int accountCounts()
    {
        return accounts.size();
    }

    public boolean isFraud() throws InterruptedException
    {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transferMoney (final int fromAcc, final int toAcc, final long amount)
    {
        Account fromAccount = accounts.get(fromAcc);
        Account toAccount = accounts.get(toAcc);

        if(fromAccount.getAccId()< toAccount.getAccId())
        {
            synchronized (fromAccount)
            {
                synchronized (toAccount)
                {
                    transfer(fromAccount, toAccount, amount);
                }
            }
        }
        else
        {
            synchronized (toAccount)
            {
                synchronized (fromAccount)
                {
                    transfer(fromAccount, toAccount, amount);
                }
            }
        }
    }
    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    private void transfer(Account fromAccount, Account toAccount, final long amount)
    {
        try
        {
            System.out.println(Thread.currentThread().getName() + " ПОТОК Выполняет трансфер от клиента "
                    + fromAccount.getAccId() +  " -> клиенту  " + toAccount.getAccId());

            if (!fromAccount.isBlock() && !toAccount.isBlock())
            {
                fromAccount.reduceMoney(amount);
                toAccount.addMoney(amount);

                if (amount > 50000)
                {
                    fromAccount.setBlock(true);
                    toAccount.setBlock(true);

                    System.out.println("--- " + Thread.currentThread().getName() + " поток ждет ответа в Банке при переводе от клиента "
                            + fromAccount.getAccId() +  " -> клиенту  " + toAccount.getAccId());

                    boolean block = isFraud();

                    if (!block)
                    {
                        fromAccount.setBlock(false);
                        toAccount.setBlock(false);

                        System.out.println("--- СЧЕТА разблокированы " + Thread.currentThread().getName()
                                +  " клиента " + fromAccount.getAccId() +  " -> клиенту  "
                                + toAccount.getAccId());

                    }
                    else{
                        System.err.println("Счет клиента " + fromAccount.getAccId() + " с балансом "
                                + getBalance(fromAccount) + " и счет клиента  "
                                + toAccount.getAccId() + " с балансом " + getBalance(toAccount)
                                + " Заблокированы");
                    }
                }
                else {
                    System.out.println("УСПЕХ! Перевод завершен от клиента " + fromAccount.getAccId()
                            + " с суммой " + amount + " клиенту "
                            + toAccount.getAccId() + " с балансом " + getBalance(toAccount));
                }
            }
            else if (fromAccount.isBlock())
            {
                System.err.println("СТОП! На данный момент клиент " + fromAccount.getAccId()
                        + " заблокирован и не может перевести деньги " + toAccount.getAccId());
            }
            else if (toAccount.isBlock())
            {
                System.err.println("СТОП! На данный момент клиент " + fromAccount.getAccId()
                        + " не может перевести деньги т.к счет клиента " + toAccount.getAccId() + " заблокрован");
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
    public long getBalance(Account account) {
        {
            synchronized (account) {
                return account.getMoney();
            }
        }
    }
}




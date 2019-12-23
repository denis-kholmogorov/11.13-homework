import Bank.Bank;

public class Main
{

    public static Double BIG_TRANSFERS_PERCENT = 0.05;
    public static Integer TRANSFERS_QUANTITY = 50;
    public static Integer THREADS_QUANTITY = 8;
    public static Integer ACCOUNTS_QUANTITY = 10;
    public static int maxBigCount = (int)((50 * 8) * BIG_TRANSFERS_PERCENT);

    public static void main(String[] args)
    {
        Bank bank = new Bank();
        long money = 100000 + (long) (Math.random() * 500000);
        System.out.println(money);
        for (int i = 0; i < ACCOUNTS_QUANTITY; i++) {
            bank.setNewAccounts(i, money);
        }

        System.out.println("Аккаунты сгенерированны в количестве | " + ACCOUNTS_QUANTITY);

        for (int i = 0; i < THREADS_QUANTITY; i++)
        {
            new Thread(() ->
            {
                int maxCount = 0;
                System.out.println("Начинает работу поток " + Thread.currentThread().getName());
                for (int j = 0; j < TRANSFERS_QUANTITY; j++)
                {
                    long amount =(int)((Math.random() * 50000) + (50000 * BIG_TRANSFERS_PERCENT));

                    if(amount > 50000 && maxBigCount > maxCount) // проверка для контроля моксимального количества операций с большими деьгами
                    {
                        maxCount++;
                    }
                    else {
                        amount =(int)((Math.random() * 50000));
                    }
                    int fromAccount = (int)(Math.random() * bank.accountCounts());
                    int toAccount = (int)(Math.random() * bank.accountCounts());

                    if(fromAccount == toAccount)
                    {
                        continue;
                    }

                    bank.transferMoney(fromAccount, toAccount, amount);
                }
            }).start();

        }
    }
}

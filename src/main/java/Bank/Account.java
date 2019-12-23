package Bank;

class Account
{
    private long money;

    private int accNumber;

    private boolean block;

    public Account(long money, int accountId)
    {
        this.money = money;
        this.accNumber = accountId;
        block = false;
    }

    public long getMoney()
    {
        return this.money;
    }

    public void addMoney(long money)
    {
        this.money = this.money + money;
    }

    public void reduceMoney(long money) throws UnavailableQuantityException
    {
        if (this.money >= money)
        {
            this.money = this.money - money;
        }
        else {
            throw new UnavailableQuantityException("ВАЖНО! У клиента номер " + accNumber + " нехватает средств для перевода");
        }
    }

    public int getAccId() {
        return accNumber;
    }

    public void setAccId(int accNumber)
    {
        this.accNumber = accNumber;
    }

    public boolean isBlock()
    {
        return block;
    }

    public void setBlock(boolean block)
    {
        this.block = block;
    }
}

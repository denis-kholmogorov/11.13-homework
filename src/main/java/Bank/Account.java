package Bank;

class Account
{
    private long money;

    private String accNumber;

    boolean block;

    public Account(long money, String accNumber)
    {
        this.money = money;
        this.accNumber = accNumber;
        block = false;
    }

    public long getMoney() {
        return this.money;
    }

    public void addMoney(long money) {
            this.money = this.money + money;
    }

    public void reduceMoney(long money) throws UnavailableQuantityException {
        if (this.money >= money) {
            this.money = this.money - money;
        } else {
            throw new UnavailableQuantityException("ВАЖНО! У клиента номер " + accNumber + " нехватает средств для перевода");
        }
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }
}

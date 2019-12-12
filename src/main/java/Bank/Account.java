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

    public void addMoney(long money) throws NullPointerException, ArithmeticException {

        if(block){
            throw new NullPointerException(accNumber);
        }
        else if (this.money >= money ) {
            this.money = this.money + money;
        } else {
            throw new ArithmeticException(accNumber);
        }
    }

    public void reduceMoney(long money) throws Exception {
        if (this.money >= money && !block) {
            this.money = this.money - money;
        } else {
            throw new Exception(accNumber);
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

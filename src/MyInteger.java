public class MyInteger {
    private int num;

    public MyInteger(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void addOne(){
        num++;
    }

    @Override
    public String toString() {
        return num + "";
    }
}

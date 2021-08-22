public class Raspberry extends Fruit{
    public Raspberry(){
        super("/raspberry.png", 30.0f);
    }

    @Override
    public void slice(){

    }

    @Override
    public int getScore(){
        return 11;
    }
}
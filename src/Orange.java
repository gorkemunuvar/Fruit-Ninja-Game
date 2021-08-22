public class Orange extends Fruit{
    public Orange(){
        super("/orange.png",50.0f);
    }

    @Override
    public void slice(){
    }

    @Override
    public int getScore(){
        return 7;
    }
}

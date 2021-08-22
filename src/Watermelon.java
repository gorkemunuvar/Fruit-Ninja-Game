public class Watermelon extends Fruit{

    public Watermelon() {
        super("/watermelon.png", 70.0f);
    }

    @Override
    public void slice(){
    }

    @Override
    public int getScore(){
        return 5;
    }
}

public class Pomegranate extends Fruit{
    public Pomegranate(){
        super("/pomeg.png", 40.0f);
    }

    @Override
    public void slice(){

    }

    @Override
    public int getScore(){
        return 9;
    }
}

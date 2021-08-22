import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public abstract class Bomb extends Circle implements Sliceable {
    private TranslateTransition jumping;
    private TranslateTransition falling;

    public Bomb(String imgUrl){
        Image image = new Image(imgUrl);
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);

        double randX = Math.random() * 1325 + 1;
        double randY = Math.random() * 100 + 850;

        this.setCenterX(randX);
        this.setCenterY(randY);
        this.setRadius(75);

        jumping = new TranslateTransition(Duration.millis(2000), this);
        falling = new TranslateTransition(Duration.millis(20000), this);

        //jumping.setInterpolator(Interpolator.LINEAR);
        falling.setByY(20000);
    }

    public void jump(){
        jumping.setByY(-850);
        jumping.setCycleCount(1);
        //falling.stop();
        //jumping.stop();
        jumping.play();
        jumping.setOnFinished((finishedEvent) -> {
            falling.play();
        });
    }

    public void explode(){
        Image image = new Image("/explode.png");
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
        this.setRadius(300);

        jumping.stop();
    }
}
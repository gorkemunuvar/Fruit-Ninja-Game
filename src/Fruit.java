import javafx.animation.Interpolator;
import javafx.animation.KeyValue;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import javafx.util.Duration;

public abstract class Fruit extends Circle implements Sliceable {
    private TranslateTransition jumping;
    private TranslateTransition falling;

    public Fruit(String imgUrl, double size){
        Image image = new Image(imgUrl);
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);

        double randX = Math.random() * 1325 + 1;
        //double randY = Math.random() * 100 + 850;
        double randY = 900;

        this.setCenterX(randX);
        this.setCenterY(randY);
        this.setRadius(size);

        jumping = new TranslateTransition(Duration.millis(2000), this);
        falling = new TranslateTransition(Duration.millis(20000), this);

        jumping.setInterpolator(Interpolator.LINEAR);
        falling.setByY(20000);
    }

    public void jump(){
        jumping.setByY(-850);
        jumping.setCycleCount(1);
        falling.stop();
        jumping.stop();
        jumping.play();
        jumping.setOnFinished((finishedEvent) -> {
            falling.play();
        });
    }

    public abstract int getScore();
}
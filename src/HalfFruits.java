import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class HalfFruits extends Circle {
    private TranslateTransition falling;

    public HalfFruits(String imgUrl, double size){
        Image image = new Image(imgUrl);
        ImagePattern imagePattern = new ImagePattern(image);

        this.setFill(imagePattern);
        this.setRadius(size);
        this.setVisible(false);


        falling = new TranslateTransition(Duration.millis(5000), this);
        falling.setByY(5000);
    }

    public void fall() {
        this.setVisible(true);
        falling.stop();
        falling.play();
    }

}

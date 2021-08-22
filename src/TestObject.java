import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class TestObject extends Circle {
    public TestObject(){
        this.setRadius(75);
        this.setCenterX(100);
        this.setCenterY(300);
    }

    public void jumpObject(){
        Timeline t = new Timeline();
        t.getKeyFrames().add(new KeyFrame(
                Duration.seconds(3), new KeyValue(this.centerXProperty(),1200)
        ));

        t.play();

    }
}

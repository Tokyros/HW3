import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by SBK on 6/29/2018.
 */
public class AnimatedAnnouncement extends Label {
    private final String ANNOUNCMENT_TEXT_TEMPLATE = "%s Afeka Instrument Music Store $$$ ON SALE!!! $$$ Guitars, Basses, Flutes, Saxophones, and more!";
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Scene scene;

    private Timeline clockUpdateTimeline;
    private Timeline animationTimeline;

    public AnimatedAnnouncement(Scene parentScene){
        this.scene = parentScene;
        setTextFill(Color.RED);
        updateAnnouncementLabel();
        createClockAnimation();
        createTextAnimation();
    }

    private void updateAnnouncementLabel() {
        setText(String.format(ANNOUNCMENT_TEXT_TEMPLATE, LocalDateTime.now().format(dateTimeFormatter)));
    }

    private void createClockAnimation() {
        clockUpdateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateAnnouncementLabel()));
        clockUpdateTimeline.setCycleCount(Animation.INDEFINITE);
        clockUpdateTimeline.play();
    }

    private void createTextAnimation() {
        animationTimeline = new Timeline();
        resetTextAnimation();
        animationTimeline.setCycleCount(Timeline.INDEFINITE);
        animationTimeline.setAutoReverse(true);
        animationTimeline.play();
        setOnMouseEntered(event -> animationTimeline.pause());
        setOnMouseExited(event -> animationTimeline.play());
        scene.widthProperty().addListener(e -> resetTextAnimation());
    }

    private void resetTextAnimation() {
        double sceneWidth = scene.getWidth();
        double textWidth = getLayoutBounds().getWidth();

        Duration startDuration = Duration.ZERO;
        KeyValue startKeyValue = new KeyValue(translateXProperty(), -textWidth);
        KeyFrame startKeyFrame = new KeyFrame(startDuration, startKeyValue);
        Duration endDuration = Duration.seconds(10);
        KeyValue endKeyValue = new KeyValue(translateXProperty(), sceneWidth);
        KeyFrame endKeyFrame = new KeyFrame(endDuration, endKeyValue);
        animationTimeline.stop();
        animationTimeline.getKeyFrames().clear();
        animationTimeline.getKeyFrames().addAll(startKeyFrame, endKeyFrame);
        animationTimeline.play();
    }

    public void stop() {
        animationTimeline.stop();
        clockUpdateTimeline.stop();
    }
}

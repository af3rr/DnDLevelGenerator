package gui;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class MonsterAnimation extends Transition {
    private ImageView image;
    private Duration duration;
    private int frames;
    private int width;
    private int height;
    private int last;

    public MonsterAnimation(String monster) {
        image = new ImageView();
        last = 245;

        switch(monster) {
            case "skeleton":
                setSkeleton();
                break;

            case "spider":
                setSpider();
                break;

            case "shrieker":
                setShrieker();
                break;
        }

        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
    }

    public ImageView getImage() {
        return image;
    }

    protected void interpolate(double i) {
        int index = Math.min((int) Math.floor(i * frames), frames - 1);

        if (index != last) {
            int x = (index % frames) * width;
            int y = (index / frames) * height;
            image.setViewport(new Rectangle2D(x, y, width, height));
            last = index;
        }
    }

    public void setSkeleton() {
        image.setImage(new Image("file:res/skeleton.png"));
        duration = Duration.millis(365);
        frames = 4;
        width = 22*2;
        height = 30*2;
    }

    public void setSpider() {
        image.setImage(new Image("file:res/spider.png"));
        duration = Duration.millis(365);
        frames = 4;
        width = 24*2;
        height = 23*2;
    }

    public void setShrieker() {
        image.setImage(new Image("file:res/shrieker.png"));
        duration = Duration.millis(725);
        frames = 8;
        width = 32*2;
        height = 32*2;
    }

}
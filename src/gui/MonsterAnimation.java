package gui;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class MonsterAnimation extends Transition {
    private final int RATIO = 3;
    
    private ImageView image;
    private Duration duration;
    private int frames;
    private int width;
    private int height;
    private int last;

    public MonsterAnimation(String monster) {
        image = new ImageView();
        last = 0;

        switch(monster) {
            case "M1":
                setSkeleton();
                break;

            case "M2":
                setSpider();
                break;

            case "M3":
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
        width = 22*RATIO;
        height = 30*RATIO;
    }

    public void setSpider() {
        image.setImage(new Image("file:res/spider.png"));
        duration = Duration.millis(365);
        frames = 4;
        width = 24*RATIO;
        height = 23*RATIO;
    }

    public void setShrieker() {
        image.setImage(new Image("file:res/shrieker.png"));
        duration = Duration.millis(725);
        frames = 8;
        width = 32*RATIO;
        height = 32*RATIO;
    }

}
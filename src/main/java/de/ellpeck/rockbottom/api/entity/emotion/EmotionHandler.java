package de.ellpeck.rockbottom.api.entity.emotion;

public class EmotionHandler {

    private int timer;
    private int max;
    private Emotion currentEmotion;

    public void update() {
        if (this.max <= 0) return;

        this.timer++;
        if (this.timer > this.max) {
            this.currentEmotion = null;
        }
    }

    public Emotion getEmotion() {
        return this.currentEmotion;
    }

    public void clearEmotion() {
        this.currentEmotion = null;
        this.max = 0;
        this.timer = 0;
    }

    public void setEmotion(Emotion emotion, int time) {
        this.currentEmotion = emotion;
        this.max = time;
        this.timer = 0;
    }

}

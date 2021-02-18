package de.ellpeck.rockbottom.api.entity.player;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.emotion.Emotion;
import de.ellpeck.rockbottom.api.util.Util;

public class CameraMode {

    public AbstractPlayerEntity player;
    private double x, y, lastTickX, lastTickY;
    private double motionX, motionY;
    private boolean active;
    private float oldWorldScale;
    private float zoomedWorldScale;

    public CameraMode(AbstractPlayerEntity player) {
        this.player = player;
    }

    public boolean start() {
        if (!player.getGameMode().isCreative()) {
            return false;
        }
        this.active = true;
        this.x = this.lastTickX = this.player.getX();
        this.y = this.lastTickY = this.player.getY();
        this.motionX = 0;
        this.motionY = 0;
        this.oldWorldScale = RockBottomAPI.getGame().getSettings().renderScale;
        this.zoomedWorldScale = oldWorldScale;
        this.player.getEmotionHandler().setEmotion(new Emotion(Emotion.EmotionType.THOUGHT, GameContent.Emotions.HAPPY), 0);
        return true;
    }

    public void update() {
        this.lastTickX = this.x;
        this.lastTickY = this.y;

        this.x += this.motionX;
        this.y += this.motionY;

        this.motionX = 0;
        this.motionY = 0;

        boolean changedScale = false;
        if (Settings.KEY_ZOOM_IN.isDown()) {
            this.zoomedWorldScale = Util.clampf(this.zoomedWorldScale + 0.01f, 0.05f, 1f);
            changedScale = true;
        }
        if (Settings.KEY_ZOOM_OUT.isDown()) {
            this.zoomedWorldScale = Util.clampf(this.zoomedWorldScale - 0.01f, 0.05f, 1f);
            changedScale = true;
        }
        if (changedScale) {
            RockBottomAPI.getGame().getSettings().renderScale = this.zoomedWorldScale;
            RockBottomAPI.getGame().getRenderer().calcScales();
        }
    }

    public void end() {
        this.active = false;
        this.player.getEmotionHandler().clearEmotion();
        RockBottomAPI.getGame().getSettings().renderScale = this.oldWorldScale;
        RockBottomAPI.getGame().getRenderer().calcScales();
    }

    public boolean move(MoveType type) {
        switch (type) {
            case LEFT: {
                this.motionX = -this.getCameraSpeed();
                break;
            }
            case RIGHT: {
                this.motionX = this.getCameraSpeed();
                break;
            }
            case UP: {
                this.motionY = this.getCameraSpeed();
                break;
            }
            case DOWN: {
                this.motionY = -this.getCameraSpeed();
                break;
            }
        }
        return false;
    }

    public double getCameraSpeed() {
        return 0.5f;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getLerpedX() {
        return Util.lerp(this.lastTickX, this.x, RockBottomAPI.getGame().getTickDelta());
    }

    public double getLerpedY() {
        return Util.lerp(this.lastTickY, this.y, RockBottomAPI.getGame().getTickDelta());
    }

    public boolean isActive() {
        return this.active;
    }
}

package de.ellpeck.rockbottom.api.entity.emotion;

import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class Emotion {
    private EmotionType type;
    private ResourceName texture;

    public Emotion(EmotionType type, ResourceName texture) {
        this.type = type;
        this.texture = texture;
    }

    public EmotionType getType() {
        return this.type;
    }

    public ResourceName getTexture() {
        return this.texture;
    }

    public enum EmotionType {
        SPEECH("speech"),
        THOUGHT("thought");

        public final ResourceName texture;

        EmotionType(String type) {
            this.texture = ResourceName.intern("emotions.bubbles." + type);
        }
    }

}

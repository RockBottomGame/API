package de.ellpeck.rockbottom.api.util.math;

public class Vector2 {

    public static final Vector2 ZERO = new Vector2(0);
    public static final Vector2 ONE = new Vector2(1);

    public final float x;
    public final float y;

    public Vector2(float value) {
        this(value, value);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 multiply(Vector2 scale) {
        return new Vector2(this.x * scale.x, this.y * scale.y);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vector2{");
        sb.append(x);
        sb.append(", ").append(y);
        sb.append('}');
        return sb.toString();
    }
}

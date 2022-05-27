package de.ellpeck.rockbottom.api.util.math;

public class Matrix3 {

    public static Matrix3 translation(float x, float y) {
        return new Matrix3(
                1, 0, x,
                0, 1, y,
                0, 0, 1
        );
    }

    public static Matrix3 rotation(float radians) {
        return new Matrix3(
                (float) Math.cos(radians), (float) Math.sin(radians), 0,
                -(float) Math.sin(radians), (float) Math.cos(radians), 0,
                0, 0, 1
        );
    }

    public static Matrix3 scale(float scaleX, float scaleY) {
        return new Matrix3(
                scaleX, 0, 0,
                0, scaleY, 0,
                0, 0, 1
        );
    }

    public static Matrix3 horizontalMirror() {
        return new Matrix3(
                -1, 0, 0,
                0, 1, 0,
                0, 0, 1
        );
    }

    public static Matrix3 verticalMirror() {
        return new Matrix3(
                1, 0, 0,
                0, -1, 0,
                0, 0, 1
        );
    }

    public static Matrix3 identity() {
        return new Matrix3();
    }

    private float m00, m01, m02;
    private float m10, m11, m12;
    private float m20, m21, m22;

    public Matrix3() {
        this.m00 = 1;
        this.m11 = 1;
        this.m22 = 1;
    }

    public Matrix3(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    public Matrix3(Matrix3 copy) {
        this(copy.m00, copy.m01, copy.m02, copy.m10, copy.m11, copy.m12, copy.m20, copy.m21, copy.m22);
    }

    public Matrix3 multiply(Matrix3 matrix) {
        float m00 = this.m00 * matrix.m00 + this.m01 * matrix.m10 + this.m02 * matrix.m20;
        float m01 = this.m00 * matrix.m01 + this.m01 * matrix.m11 + this.m02 * matrix.m21;
        float m02 = this.m00 * matrix.m02 + this.m01 * matrix.m12 + this.m02 * matrix.m22;

        float m10 = this.m10 * matrix.m00 + this.m11 * matrix.m10 + this.m12 * matrix.m20;
        float m11 = this.m10 * matrix.m01 + this.m11 * matrix.m11 + this.m12 * matrix.m21;
        float m12 = this.m10 * matrix.m02 + this.m11 * matrix.m12 + this.m12 * matrix.m22;

        float m20 = this.m20 * matrix.m00 + this.m21 * matrix.m10 + this.m22 * matrix.m20;
        float m21 = this.m20 * matrix.m01 + this.m21 * matrix.m11 + this.m22 * matrix.m21;
        float m22 = this.m20 * matrix.m02 + this.m21 * matrix.m12 + this.m22 * matrix.m22;

        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        return this;
    }

    public Vector2 apply(Vector2 point) {
        return new Vector2(
                this.m00 * point.x + this.m01 * point.y + this.m02,
                this.m10 * point.x + this.m11 * point.y + this.m12
        );
    }

    public Matrix3 copy() {
        return new Matrix3(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Matrix3{");
        sb.append('\n').append(m00).append(" ").append(m01).append(" ").append(m02);
        sb.append('\n').append(m10).append(" ").append(m11).append(" ").append(m12);
        sb.append('\n').append(m20).append(" ").append(m21).append(" ").append(m22);
        sb.append("\n}");
        return sb.toString();
    }
}

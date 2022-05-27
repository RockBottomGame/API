package de.ellpeck.rockbottom.api.util.math;

import com.google.common.collect.Queues;
import de.ellpeck.rockbottom.api.RockBottomAPI;

import java.util.Deque;
import java.util.logging.Level;

public class MatrixStack {

    private final Deque<Matrix3> stack = Queues.newArrayDeque();
    private Matrix3 matrix = Matrix3.identity();

    public void push() {
        this.stack.push(this.matrix);
        this.matrix = this.matrix.copy();
    }

    public void pop() {
        if (this.stack.isEmpty()) {
            RockBottomAPI.logger().log(Level.WARNING, "Attempted to pop the matrix stack while empty. Make sure the number of pops and pushes are paired correctly.");
            return;
        }
        this.matrix = this.stack.pop();
    }

    public Matrix3 getLast() {
        return this.matrix;
    }

    public void translate(float x, float y) {
        this.matrix.multiply(Matrix3.translation(x, y));
    }

    public void rotate(float degrees) {
        this.rotateRad((float)Math.toRadians(degrees));
    }

    public void rotateRad(float radians) {
        this.matrix.multiply(Matrix3.rotation(radians));
    }

    public void scale(float scale) {
        this.scale(scale, scale);
    }

    public void scale(float scaleX, float scaleY) {
        this.matrix.multiply(Matrix3.scale(scaleX, scaleY));
    }

    public void clear() {
        this.stack.clear();
        this.matrix = Matrix3.identity();
    }
}

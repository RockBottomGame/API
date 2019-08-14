package de.ellpeck.rockbottom.api.util;

public class Pair<A, B> {

    private A left;
    private B right;

    private Pair(A left, B right){
        this.left = left;
        this.right = right;
    }

    public static <A, B> Pair<A, B> of(A left, B right){
        return new Pair<>(left, right);
    }

    public A getLeft() {
        return left;
    }

    public void setLeft(A left) {
        this.left = left;
    }

    public B getRight() {
        return right;
    }

    public void setRight(B right) {
        this.right = right;
    }
}

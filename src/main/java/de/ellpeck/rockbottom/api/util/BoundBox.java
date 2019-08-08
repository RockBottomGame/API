/*
 * This file ("BoundBox.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame/>.
 * View information on the project at <https://rockbottom.ellpeck.de/>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 *
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.util;

public final class BoundBox {

    private double minX;
    private double minY;

    private double maxX;
    private double maxY;

    public BoundBox() {
        this(0, 0, 0, 0);
    }

    public BoundBox(double minX, double minY, double maxX, double maxY) {
        this.set(minX, minY, maxX, maxY);
    }

    public BoundBox set(BoundBox box) {
        return this.set(box.minX, box.minY, box.maxX, box.maxY);
    }

    public BoundBox set(double minX, double minY, double maxX, double maxY) {
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);

        this.maxX = Math.max(maxX, minX);
        this.maxY = Math.max(maxY, minY);

        return this;
    }

    public BoundBox add(double x, double y) {
        this.minX += x;
        this.minY += y;

        this.maxX += x;
        this.maxY += y;

        return this;
    }

    public BoundBox expand(double x, double y) {
        this.minX -= x;
        this.maxX += x;

        this.minY -= y;
        this.maxY += y;

        return this;
    }

    public BoundBox expand(double amount) {
        return this.expand(amount, amount);
    }

    public boolean intersects(BoundBox other) {
        return this.intersects(other.minX, other.minY, other.maxX, other.maxY);
    }

    public boolean intersects(double minX, double minY, double maxX, double maxY) {
        return this.minX < maxX && this.maxX > minX && this.minY < maxY && this.maxY > minY;
    }

    public boolean contains(double x, double y) {
        return this.minX <= x && this.minY <= y && this.maxX >= x && this.maxY >= y;
    }

    public BoundBox getIntersection(BoundBox other) {
        return new BoundBox(
                Math.max(this.getMinX(), other.getMinX()),
                Math.max(this.getMinY(), other.getMinY()),
                Math.min(this.getMaxX(), other.getMaxX()),
                Math.min(this.getMaxY(), other.getMaxY()));
    }

    public boolean isEmpty() {
        return this.getWidth() <= 0 || this.getHeight() <= 0;
    }

	/**
	 * Gets the position of an edge based on the direction.
	 * For example:
	 * box = BoundBox(-2.8, 0.2, 10.2, 11.5)
	 * box.getBoundEdge(Direction.UP) is 11.5.
	 * box.getBoundEdge(Direction.LEFT) is -2.8.
	 * @param direction The (CARDINAL) direction towards the edge
	 * @return The position value at that edge if direction is cardinal, 0 otherwise.
	 */
	public double getBoundEdge(Direction direction) {
    	switch (direction) {
			case LEFT: return minX;
			case RIGHT: return maxX;
			case DOWN: return minY;
			case UP: return maxY;
			default: return 0;
		}
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        BoundBox boundBox = (BoundBox) o;
        return Double.compare(boundBox.minX, this.minX) == 0 && Double.compare(boundBox.minY, this.minY) == 0 && Double.compare(boundBox.maxX, this.maxX) == 0 && Double.compare(boundBox.maxY, this.maxY) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(this.minX);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.minY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.maxX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.maxY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "{" + this.minX + ", " + this.minY + " -> " + this.maxX + ", " + this.maxY + '}';
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMinY() {
        return this.minY;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMaxY() {
        return this.maxY;
    }

    public double getWidth() {
        return this.maxX - this.minX;
    }

    public double getHeight() {
        return this.maxY - this.minY;
    }

    public double getCenterX() {
        return this.minX + this.getWidth() / 2D;
    }

    public double getCenterY() {
        return this.minY + this.getHeight() / 2D;
    }

    public BoundBox copy() {
        return new BoundBox(this.minX, this.minY, this.maxX, this.maxY);
    }
}

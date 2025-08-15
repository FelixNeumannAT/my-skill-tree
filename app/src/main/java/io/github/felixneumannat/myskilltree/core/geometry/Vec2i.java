package io.github.felixneumannat.myskilltree.core.geometry;

import androidx.annotation.NonNull;

/**
 * Note: Uses 32-bit wraparound for add/subtract/scale; metrics use 64-bit intermediates and may
 * overflow at extreme values.
 */
public final class Vec2i {

    private static final int HASHING_CONSTANT = 0x9e3779b9;
    private final int x;
    private final int y;

    public static final Vec2i ZERO = new Vec2i(0, 0);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vec2i() {
        this(0, 0);
    }

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2i add(Vec2i v) {
        return new Vec2i(x + v.x, y + v.y);
    }

    public Vec2i add(int x, int y) {
        return new Vec2i(this.x + x, this.y + y);
    }

    public Vec2i subtract(Vec2i v) {
        return new Vec2i(x - v.x, y - v.y);
    }

    public Vec2i subtract(int x, int y) {
        return new Vec2i(this.x - x, this.y - y);
    }

    public Vec2i scale(int s) {
        return new Vec2i(this.x * s, this.y * s);
    }

    public long dot(Vec2i v) {
        return (long) x * v.x + (long) y * v.y;
    }

    public long lengthSquared() {
        return (long) x * x + (long) y * y;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public long distanceSquared(Vec2i v) {
        long dx = (long) x - v.x;
        long dy = (long) y - v.y;
        return dx * dx + dy * dy;
    }

    public double distance(Vec2i v) {
        return Math.sqrt(distanceSquared(v));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec2i)) return false;
        Vec2i v = (Vec2i) o;
        return x == v.x && y == v.y;
    }

    @Override
    public int hashCode() {
        return x * HASHING_CONSTANT + y;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

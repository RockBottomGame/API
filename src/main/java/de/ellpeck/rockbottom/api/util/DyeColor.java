package de.ellpeck.rockbottom.api.util;

public enum DyeColor {
    GRAY(0xFFD3D3D3),
    ORANGE(0xFFED6A00),
    PINK(0xFFFF3399),
    RED(0xFFD6000B),
    WHITE(0xFFFFFFFF),
    YELLOW(0xFFEDE100),
    BLUE(0xFF1A53FF),
    PURPLE(0xFF7200E6);

    public final int color;

    DyeColor(int color) {
        this.color = color;
    }

    /**
     * Obtains the {@link DyeColor} given the flower based on the flowers.
     * @param meta the meta value.
     * @return the color corresponding to the flowers meta.
     */
    public static DyeColor fromMeta(int meta) {
        if (meta >= 0 && meta < values().length) {
            return values()[meta];
        }
        return WHITE;
    }
}

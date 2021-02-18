package de.ellpeck.rockbottom.api.entity.player;

/**
 * The MoveType Enum is used to determine how the player should move.
 * It replaces the old integer based implementation, but the ordinals are equal to them.
 *
 * @see AbstractPlayerEntity#move(MoveType)
 */
public enum MoveType {

    LEFT,
    RIGHT,
    JUMP,
    UP,
    DOWN

}

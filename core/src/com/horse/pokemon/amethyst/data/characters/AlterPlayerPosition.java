package com.horse.pokemon.amethyst.data.characters;

@FunctionalInterface
public interface AlterPlayerPosition {
    void alterPosition(PrimitiveConsumer setPositionMethod, PrimitiveSupplier getPositionMethod, int alterValue);
}

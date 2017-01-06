package com.horse.pokemon.ObjectData.Players;

@FunctionalInterface
public interface AlterPlayerPosition {
    void alterPosition(PrimitiveConsumer setPositionMethod, PrimitiveSupplier getPositionMethod, int alterValue);
}

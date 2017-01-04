package com.horse.pokemon.ObjectData.Players;

import java.util.function.Consumer;
import java.util.function.Supplier;

@FunctionalInterface
public interface AlterPlayerPosition {
    void alterPosition(Consumer<Float> setPositionMethod, Supplier<Float> getPositionMethod, float alterValue);
}

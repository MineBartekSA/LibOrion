package com.minebarteksa.orion.potion;

import net.minecraft.potion.PotionType;

public interface MixRegister
{
    /**
     * Method used by OrionRegistry system. <br>
     * Here you can set some Mixing recipes
     */
    default void registerPotionMix() {}

    /**
     * Method used by OrionRegistry system. <br>
     * Use <b>PotionType.REGISTRY.getObject()</b> to get a valid PotionType instance
     */
    default PotionType getPotionType() { return null; }

    /**
     * Method used by OrionRegistry system. <br>
     * Use <b>PotionType.REGISTRY.getObject()</b> to get a valid PotionType instance
     */
    default PotionType getLongPotionType() { return null; }
}

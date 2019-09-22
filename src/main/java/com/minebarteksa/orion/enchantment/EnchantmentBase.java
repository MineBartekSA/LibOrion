package com.minebarteksa.orion.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import java.util.ArrayList;
import java.util.function.Function;

public class EnchantmentBase extends Enchantment
{
    protected int maxLevel = 1;
    protected int minLevel = 1;
    protected boolean curse = false;
    protected boolean enchantable = true;
    protected boolean allonOnBook = true;
    protected ArrayList<Enchantment> uncompatible = new ArrayList<>();

    private Function<Integer, Integer> minEnch = (l) -> 1 + l * 10;
    private Function<Integer, Integer> maxEnch = (l) -> this.getMinEnchantability(l) + 5;

    public EnchantmentBase(String name) { this(name, Rarity.COMMON, EntityEquipmentSlot.values()); }

    public EnchantmentBase(String name, Rarity rarity) { this(name, rarity, EntityEquipmentSlot.values()); }

    public EnchantmentBase(String name, EntityEquipmentSlot[] slots) { this(name, Rarity.COMMON, slots); }

    public EnchantmentBase(String name, Rarity rarity, EntityEquipmentSlot[] slots)
    {
        super(rarity, EnumEnchantmentType.ALL, slots);
        setRegistryName(name);
        setName(name);
    }

    @Override
    public EnchantmentBase setName(String name)
    {
        this.name = name;
        return this;
    }

    public EnchantmentBase setMaxLevel(int level)
    {
        maxLevel = level;
        return this;
    }

    public EnchantmentBase setMinLevel(int level)
    {
        minLevel = level;
        return this;
    }

    public EnchantmentBase setType(EnumEnchantmentType type)
    {
        this.type = type;
        return this;
    }

    public EnchantmentBase setCurse()
    {
        curse = true;
        return this;
    }

    public EnchantmentBase setNotEnchantable()
    {
        enchantable = false;
        return this;
    }

    public EnchantmentBase disallowOnBooks()
    {
        allonOnBook = false;
        return this;
    }

    public EnchantmentBase uncompatibleWith(Enchantment enchantment)
    {
        uncompatible.add(enchantment);
        return this;
    }

    public EnchantmentBase setMinEnchantibility(Function<Integer, Integer> func)
    {
        minEnch = func;
        return this;
    }

    public EnchantmentBase setMaxEnchantibility(Function<Integer, Integer> func)
    {
        maxEnch = func;
        return this;
    }

    @Override
    public int getMaxLevel() { return maxLevel; }

    @Override
    public int getMinLevel() { return minLevel; }

    @Override
    public boolean isCurse() { return curse; }

    @Override
    public boolean isTreasureEnchantment() { return enchantable; }

    @Override
    public boolean isAllowedOnBooks() { return allonOnBook; }

    @Override
    protected boolean canApplyTogether(Enchantment enchantment) { return super.canApplyTogether(enchantment) && !uncompatible.contains(enchantment); }

    @Override
    public int getMinEnchantability(int enchantmentLevel) { return minEnch.apply(enchantmentLevel); }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) { return maxEnch.apply(enchantmentLevel); }
}

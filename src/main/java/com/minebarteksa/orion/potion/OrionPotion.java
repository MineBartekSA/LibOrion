package com.minebarteksa.orion.potion;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.events.IOrionListener;
import com.minebarteksa.orion.events.OrionEvent;
import com.minebarteksa.orion.events.OrionMouseEvents;
import com.minebarteksa.orion.network.OrionPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class OrionPotion extends Potion implements IOrionListener, MixRegister
{
    private int lastScrollValue;
    private List<Integer> updateScrollValue = new ArrayList<>();

    public OrionPotion()
    {
        super(true, 666666);
        this.setPotionName("effect.orion");
        this.setRegistryName(new ResourceLocation(Orion.ModID,"orion"));
        OrionMouseEvents.SE.addListener(this);
    }

    @Override
    public void registerPotionMix()
    {
        PotionHelper.addMix(PotionTypes.STRONG_POISON, Items.ENDER_PEARL, PotionType.REGISTRY.getObject(new ResourceLocation(Orion.ModID, "orion")));
        PotionHelper.addMix(PotionType.REGISTRY.getObject(new ResourceLocation(Orion.ModID, "orion")), Items.ENDER_EYE, PotionType.REGISTRY.getObject(new ResourceLocation(Orion.ModID, "long_orion")));
    }

    @Override
    public PotionType getPotionType() { return new PotionType("orion", new PotionEffect(this, 1200)).setRegistryName(this.getRegistryName()); }

    @Override
    public PotionType getLongPotionType() { return new PotionType("long_orion", new PotionEffect(this, 12000)).setRegistryName(new ResourceLocation(Orion.ModID, "long_orion")); }

    @Override
    public boolean isBeneficial() { return false; }

    @Override
    public boolean isInstant() { return false; }

    @Override
    public boolean isReady(int duration, int amplifier) { return true; }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) { run(entityLivingBaseIn); }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) { run(entityLivingBaseIn); }

    private void run(EntityLivingBase entity)
    {
        if(!entity.world.isRemote)
        {
            int eid = entity.getEntityId();
            if(updateScrollValue.contains(eid))
                entity.attackEntityFrom(DamageSource.MAGIC, 9.5f);
            updateScrollValue.clear();
        }
        else
        {
            if(lastScrollValue != 0)
                OrionPacketHandler.INSTANCE.sendToServer(new OrionPacketHandler.PotionPacket(entity.getEntityId()));
            lastScrollValue = 0;
        }
    }

    public void updateScrollValue(int entityID) { this.updateScrollValue.add(entityID); }

    @Override
    public void onOrionEvent(OrionEvent ev) { lastScrollValue = ((OrionMouseEvents.ScrollEvent)ev).value; }

    @Override
    public List<ItemStack> getCurativeItems() { return new ArrayList<>(); }
}

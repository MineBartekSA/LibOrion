package com.minebarteksa.orion.criteria;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public abstract class CriterionBase<T extends CriterionInstanceBase> implements ICriterionTrigger<T>
{
    private ResourceLocation id;
    private Map<PlayerAdvancements, Listeners<T>> listeners = Maps.newHashMap();

    public CriterionBase(String name) { this(new ResourceLocation(name)); }

    public CriterionBase(ResourceLocation name) { this.id = name; }

    @Override
    public ResourceLocation getId() { return this.id; }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<T> listener)
    {
        Listeners<T> lis = this.listeners.get(playerAdvancementsIn);
        if(lis == null)
        {
            lis = new Listeners<>(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, lis);
        }
        lis.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<T> listener)
    {
        Listeners<T> lis = this.listeners.get(playerAdvancementsIn);
        if(lis != null)
        {
            lis.remove(listener);
            if(lis.isEmpty())
                this.listeners.remove(playerAdvancementsIn);
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) { this.listeners.remove(playerAdvancementsIn); }

    public void trigger(EntityPlayerMP player, Predicate<T> test)
    {
        Listeners<T> lis = listeners.get(player.getAdvancements());
        if(lis != null)
            lis.trigger(test);
    }

    public static class Listeners<T extends CriterionInstanceBase>
    {
        private final PlayerAdvancements playerAdv;
        private final Set<Listener<T>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdv) { this.playerAdv = playerAdv; }

        public final boolean isEmpty() { return this.listeners.isEmpty(); }

        public final void add(Listener<T> listener) { this.listeners.add(listener); }

        public final void remove(Listener<T> listener) { this.listeners.remove(listener); }

        public final void trigger(Predicate<T> test)
        {
            List<Listener<T>> list = Lists.newArrayList();

            for(Listener<T> lis : this.listeners)
                if(test.test(lis.getCriterionInstance()))
                    list.add(lis);

            for(Listener<T> lis : list)
                lis.grantCriterion(this.playerAdv);
        }
    }
}

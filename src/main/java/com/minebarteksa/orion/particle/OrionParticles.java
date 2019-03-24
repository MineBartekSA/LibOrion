package com.minebarteksa.orion.particle;

import com.minebarteksa.orion.Orion;
import net.minecraft.client.particle.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.registries.RegistryManager;

@SideOnly(Side.CLIENT)
public class OrionParticles // ToDo - To rewrite!!
{
  public static RegistryNamespacedDefaultedByKey<ResourceLocation, IParticleFactory> REGISTRY = new RegistryNamespacedDefaultedByKey<>(new ResourceLocation("null", "null")); // This REGISTRY only is needed to get the ID's of the particles and severs no other purpose.
  private static int nextId = 0;
  public static ParticleManager partManager = Minecraft.getMinecraft().effectRenderer;

  public static void init()
  {
      REGISTRY.register(EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.EXPLOSION_NORMAL.getParticleName()), new ParticleExplosion.Factory());
      REGISTRY.register(EnumParticleTypes.SPIT.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SPIT.getParticleName()), new ParticleSpit.Factory());
      REGISTRY.register(EnumParticleTypes.WATER_BUBBLE.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.WATER_BUBBLE.getParticleName()), new ParticleBubble.Factory());
      REGISTRY.register(EnumParticleTypes.WATER_SPLASH.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.WATER_SPLASH.getParticleName()), new ParticleSplash.Factory());
      REGISTRY.register(EnumParticleTypes.WATER_WAKE.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.WATER_WAKE.getParticleName()), new ParticleWaterWake.Factory());
      REGISTRY.register(EnumParticleTypes.WATER_DROP.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.WATER_DROP.getParticleName()), new ParticleRain.Factory());
      REGISTRY.register(EnumParticleTypes.SUSPENDED.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SUSPENDED.getParticleName()), new ParticleSuspend.Factory());
      REGISTRY.register(EnumParticleTypes.SUSPENDED_DEPTH.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SUSPENDED_DEPTH.getParticleName()), new ParticleSuspendedTown.Factory());
      REGISTRY.register(EnumParticleTypes.CRIT.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.CRIT.getParticleName()), new ParticleCrit.Factory());
      REGISTRY.register(EnumParticleTypes.CRIT_MAGIC.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.CRIT_MAGIC.getParticleName()), new ParticleCrit.MagicFactory());
      REGISTRY.register(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SMOKE_NORMAL.getParticleName()), new ParticleSmokeNormal.Factory());
      REGISTRY.register(EnumParticleTypes.SMOKE_LARGE.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SMOKE_LARGE.getParticleName()), new ParticleSmokeLarge.Factory());
      REGISTRY.register(EnumParticleTypes.SPELL.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SPELL.getParticleName()), new ParticleSpell.Factory());
      REGISTRY.register(EnumParticleTypes.SPELL_INSTANT.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SPELL_INSTANT.getParticleName()), new ParticleSpell.InstantFactory());
      REGISTRY.register(EnumParticleTypes.SPELL_MOB.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SPELL_MOB.getParticleName()), new ParticleSpell.MobFactory());
      REGISTRY.register(EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleName()), new ParticleSpell.AmbientMobFactory());
      REGISTRY.register(EnumParticleTypes.SPELL_WITCH.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SPELL_WITCH.getParticleName()), new ParticleSpell.WitchFactory());
      REGISTRY.register(EnumParticleTypes.DRIP_WATER.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.DRIP_WATER.getParticleName()), new ParticleDrip.WaterFactory());
      REGISTRY.register(EnumParticleTypes.DRIP_LAVA.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.DRIP_LAVA.getParticleName()), new ParticleDrip.LavaFactory());
      REGISTRY.register(EnumParticleTypes.VILLAGER_ANGRY.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.VILLAGER_ANGRY.getParticleName()), new ParticleHeart.AngryVillagerFactory());
      REGISTRY.register(EnumParticleTypes.VILLAGER_HAPPY.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.VILLAGER_HAPPY.getParticleName()), new ParticleSuspendedTown.HappyVillagerFactory());
      REGISTRY.register(EnumParticleTypes.TOWN_AURA.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.TOWN_AURA.getParticleName()), new ParticleSuspendedTown.Factory());
      REGISTRY.register(EnumParticleTypes.NOTE.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.NOTE.getParticleName()), new ParticleNote.Factory());
      REGISTRY.register(EnumParticleTypes.PORTAL.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.PORTAL.getParticleName()), new ParticlePortal.Factory());
      REGISTRY.register(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.ENCHANTMENT_TABLE.getParticleName()), new ParticleEnchantmentTable.EnchantmentTable());
      REGISTRY.register(EnumParticleTypes.FLAME.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.FLAME.getParticleName()), new ParticleFlame.Factory());
      REGISTRY.register(EnumParticleTypes.LAVA.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.LAVA.getParticleName()), new ParticleLava.Factory());
      REGISTRY.register(EnumParticleTypes.FOOTSTEP.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.FOOTSTEP.getParticleName()), new ParticleFootStep.Factory());
      REGISTRY.register(EnumParticleTypes.CLOUD.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.CLOUD.getParticleName()), new ParticleCloud.Factory());
      REGISTRY.register(EnumParticleTypes.REDSTONE.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.REDSTONE.getParticleName()), new ParticleRedstone.Factory());
      REGISTRY.register(EnumParticleTypes.FALLING_DUST.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.FALLING_DUST.getParticleName()), new ParticleFallingDust.Factory());
      REGISTRY.register(EnumParticleTypes.SNOWBALL.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SNOWBALL.getParticleName()), new ParticleBreaking.SnowballFactory());
      REGISTRY.register(EnumParticleTypes.SNOW_SHOVEL.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SNOW_SHOVEL.getParticleName()), new ParticleSnowShovel.Factory());
      REGISTRY.register(EnumParticleTypes.SLIME.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SLIME.getParticleName()), new ParticleBreaking.SlimeFactory());
      REGISTRY.register(EnumParticleTypes.HEART.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.HEART.getParticleName()), new ParticleHeart.Factory());
      REGISTRY.register(EnumParticleTypes.BARRIER.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.BARRIER.getParticleName()), new Barrier.Factory());
      REGISTRY.register(EnumParticleTypes.ITEM_CRACK.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.ITEM_CRACK.getParticleName()), new ParticleBreaking.Factory());
      REGISTRY.register(EnumParticleTypes.BLOCK_CRACK.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.BLOCK_CRACK.getParticleName()), new ParticleDigging.Factory());
      REGISTRY.register(EnumParticleTypes.BLOCK_DUST.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.BLOCK_DUST.getParticleName()), new ParticleBlockDust.Factory());
      REGISTRY.register(EnumParticleTypes.EXPLOSION_HUGE.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.EXPLOSION_HUGE.getParticleName()), new ParticleExplosionHuge.Factory());
      REGISTRY.register(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.EXPLOSION_LARGE.getParticleName()), new ParticleExplosionLarge.Factory());
      REGISTRY.register(EnumParticleTypes.FIREWORKS_SPARK.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.FIREWORKS_SPARK.getParticleName()), new ParticleFirework.Factory());
      REGISTRY.register(EnumParticleTypes.MOB_APPEARANCE.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.MOB_APPEARANCE.getParticleName()), new ParticleMobAppearance.Factory());
      REGISTRY.register(EnumParticleTypes.DRAGON_BREATH.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.DRAGON_BREATH.getParticleName()), new ParticleDragonBreath.Factory());
      REGISTRY.register(EnumParticleTypes.END_ROD.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.END_ROD.getParticleName()), new ParticleEndRod.Factory());
      REGISTRY.register(EnumParticleTypes.DAMAGE_INDICATOR.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.DAMAGE_INDICATOR.getParticleName()), new ParticleCrit.DamageIndicatorFactory());
      REGISTRY.register(EnumParticleTypes.SWEEP_ATTACK.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.SWEEP_ATTACK.getParticleName()), new ParticleSweepAttack.Factory());
      REGISTRY.register(EnumParticleTypes.TOTEM.getParticleID(), new ResourceLocation("minecraft", EnumParticleTypes.TOTEM.getParticleName()), new ParticleTotem.Factory());

      for(EnumParticleTypes e : EnumParticleTypes.values())
        if(nextId < e.getParticleID() + 1)
            nextId = e.getParticleID() + 1;

      Orion.log.info("Registering minecraft particles finished! Registered " + nextId + " particles!");
  }

  public static void registerParticle(ResourceLocation particleName, ParticleBase.Factory factory)
  {
    REGISTRY.register(nextId, particleName, factory);
    partManager.registerParticle(nextId, factory);
    nextId++;
  }

  public static void spawnParticle(ResourceLocation particleName, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
  {
    partManager.spawnEffectParticle(REGISTRY.getIDForObject(REGISTRY.getObject(particleName)), xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
  }

  public static ResourceLocation getParticleNameByID(int id) { return REGISTRY.getNameForObject(REGISTRY.getObjectById(id)); }

  public static int getParticleIDByName(ResourceLocation name) { return REGISTRY.getIDForObject(REGISTRY.getObject(name)); }

  public static int getMaxIDValue() { return nextId - 1; }
}

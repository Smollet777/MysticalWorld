package epicsquid.mysticalworld.entity;

import epicsquid.mysticalworld.init.ModItems;
import epicsquid.mysticalworld.init.ModSounds;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class EntitySprout extends EntityAnimal {
  public final static ResourceLocation LOOT_TABLE_GREEN = new ResourceLocation("mysticalworld:entity/sprout_green");
  public static final ResourceLocation LOOT_TABLE_TAN = new ResourceLocation("mysticalworld:entity/sprout_tan");
  public static final ResourceLocation LOOT_TABLE_RED = new ResourceLocation("mysticalworld:entity/sprout_red");
  public static final ResourceLocation LOOT_TABLE_PURPLE = new ResourceLocation("mysticalworld:entity/sprout_purple");
  public static final ResourceLocation LOOT_TABLE_HELL = new ResourceLocation("mysticalworld:entity/sprout_hell");

  public static final DataParameter<Integer> variant = EntityDataManager.<Integer>createKey(EntitySprout.class, DataSerializers.VARINT);

  public EntitySprout(World world) {
    super(world);
    setSize(0.5f, 1.0f);
    this.experienceValue = 3;
  }

  @Nullable
  @Override
  public EntityAgeable createChild(EntityAgeable ageable) {
    return new EntitySprout(ageable.world);
  }

  @Override
  public boolean isBreedingItem(@Nonnull ItemStack stack) {
    return stack.getItem() == ModItems.aubergine;
  }

  @Override
  protected float getSoundVolume() {
    return 0.3f;
  }

  @Nullable
  @Override
  protected SoundEvent getAmbientSound() {
    if (rand.nextInt(14) == 0) {
      return ModSounds.Sprout.AMBIENT;
    }

    return null;
  }

  @Override
  protected void entityInit() {
    super.entityInit();
    // Hell Sprouts aren't spawned in this fashion
    this.getDataManager().register(variant, rand.nextInt(4));
  }

  @Override
  protected void initEntityAI() {
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new EntityAIPanic(this, 1.5D));
    this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
    if (getVariant() != 4) {
      this.tasks.addTask(3, new EntityAITempt(this, 1.25D, ModItems.aubergine, false));
    }
    this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.tasks.addTask(7, new EntityAIMate(this, 1.0D));
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
  }

  public int getVariant() {
    return getDataManager().get(EntitySprout.variant);
  }

  @Override
  public ResourceLocation getLootTable() {
    switch (getVariant()) {
      case 0:
        return LOOT_TABLE_GREEN;
      case 1:
        return LOOT_TABLE_TAN;
      case 2:
        return LOOT_TABLE_RED;
      case 3:
        return LOOT_TABLE_PURPLE;
      case 4:
        return LOOT_TABLE_HELL;
      default: {
        return LOOT_TABLE_GREEN;
      }
    }
  }

  @Override
  public float getEyeHeight() {
    return this.isChild() ? this.height : 1.3F;
  }

  @Override
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    getDataManager().set(variant, compound.getInteger("variant"));
    getDataManager().setDirty(variant);
  }

  @Override
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setInteger("variant", getDataManager().get(variant));
  }
}

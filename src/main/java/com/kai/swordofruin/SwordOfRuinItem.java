package com.kai.swordofruin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.List;

public class SwordOfRuinItem extends ItemSword {

    public SwordOfRuinItem(ToolMaterial material) {
        super(material);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
        tooltip.add(EnumChatFormatting.DARK_RED + "Uma lamina forjada nas cinzas do nether.");
        tooltip.add(EnumChatFormatting.RED + "Ao acertar: Forca II + Resistencia I");
        tooltip.add(EnumChatFormatting.GOLD + "Execucao: com 20% de vida o alvo vira bola de fogo");
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        World world = attacker.worldObj;

        if (!world.isRemote) {
            if (attacker instanceof EntityPlayer) {
                EntityPlayer p = (EntityPlayer) attacker;
                p.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 160, 1));
                p.addPotionEffect(new PotionEffect(Potion.resistance.id, 160, 0));
            }

            target.setFire(6);
            target.addPotionEffect(new PotionEffect(Potion.wither.id, 100, 0));

            for (int i = 0; i < 15; i++) {
                world.spawnParticle(EnumParticleTypes.FLAME,
                    target.posX + (world.rand.nextDouble() - 0.5) * 0.6,
                    target.posY + 0.8 + (world.rand.nextDouble() - 0.5) * 0.6,
                    target.posZ + (world.rand.nextDouble() - 0.5) * 0.6,
                    0, 0.05, 0);
            }
            for (int i = 0; i < 10; i++) {
                world.spawnParticle(EnumParticleTypes.CRIT,
                    target.posX + (world.rand.nextDouble() - 0.5) * 0.6,
                    target.posY + 0.8 + (world.rand.nextDouble() - 0.5) * 0.6,
                    target.posZ + (world.rand.nextDouble() - 0.5) * 0.6,
                    0, 0, 0);
            }

            float hp = target.getHealth();
            float max = target.getMaxHealth();
            if (hp > 0 && max > 0 && hp / max <= 0.20F) {
                triggerFireExecution(target, attacker, world);
            }
        }

        return super.hitEntity(stack, target, attacker);
    }

    private void triggerFireExecution(EntityLivingBase target, EntityLivingBase attacker, World world) {
        double x = target.posX;
        double y = target.posY + 1.0;
        double z = target.posZ;

        for (int i = 0; i < 60; i++) {
            world.spawnParticle(EnumParticleTypes.FLAME,
                x + (world.rand.nextDouble() - 0.5) * 1.2,
                y + (world.rand.nextDouble() - 0.5) * 1.5,
                z + (world.rand.nextDouble() - 0.5) * 1.2,
                0, 0.1, 0);
        }
        for (int i = 0; i < 30; i++) {
            world.spawnParticle(EnumParticleTypes.LAVA,
                x + (world.rand.nextDouble() - 0.5) * 1.0,
                y + (world.rand.nextDouble() - 0.5) * 1.0,
                z + (world.rand.nextDouble() - 0.5) * 1.0,
                0, 0, 0);
        }
        world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, x, y, z, 0, 0, 0);

        world.playSoundEffect(x, y, z, "random.explode", 1.5F, 1.0F);
        world.playSoundEffect(x, y, z, "mob.blaze.shoot", 1.0F, 0.8F);

        target.setHealth(0);

        double r = 3.0;
        AxisAlignedBB area = AxisAlignedBB.fromBounds(x - r, y - r, z - r, x + r, y + r, z + r);
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, area);
        for (EntityLivingBase e : entities) {
            if (e == attacker || e == target) continue;
            double dist = e.getDistance(x, y, z);
            if (dist > r) continue;
            float dmg = (float) (20.0 * (1.0 - dist / r));
            e.attackEntityFrom(DamageSource.magic, dmg);
            e.setFire(5);
        }
    }
                     }

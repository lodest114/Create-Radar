package com.happysg.radar.compat.cbc;

import com.happysg.radar.mixin.AbstractCannonAccessor;
import com.happysg.radar.mixin.AutoCannonAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedAutocannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedBigCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannons.autocannon.IAutocannonBlockEntity;
import rbasamoyai.createbigcannons.cannons.autocannon.material.AutocannonMaterialProperties;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBehavior;
import rbasamoyai.createbigcannons.cannons.big_cannons.IBigCannonBlockEntity;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlock;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCannonPropellantBlock;

import java.util.Collections;
import java.util.Map;

public class CannonUtil {

    public static int getFrontBarrelLength(AbstractMountedCannonContraption cannon) {
        if (cannon == null)
            return 0;
        return ((AbstractCannonAccessor) cannon).getFrontBarrelCount();
    }

    public static int getChargePower(ServerLevel level, PitchOrientedContraptionEntity contraptionEntity) {
        if(contraptionEntity == null) return 0;
        Map<BlockPos, BlockEntity> presentBlockEntities = contraptionEntity.getContraption().presentBlockEntities;
        int chargePower = 0;
        for (BlockEntity blockEntity : presentBlockEntities.values()) {
            if (!(blockEntity instanceof IBigCannonBlockEntity cannonBlockEntity)) continue;
            BigCannonBehavior behavior = cannonBlockEntity.cannonBehavior();
            StructureTemplate.StructureBlockInfo containedBlockInfo = behavior.block();

            Block block = containedBlockInfo.state().getBlock();
            if (block instanceof BigCannonPropellantBlock propellantBlock) {
                chargePower += (int) propellantBlock.getChargePower(containedBlockInfo);
            } else if (block instanceof ProjectileBlock<?> projectileBlock) {
                AbstractBigCannonProjectile projectile = projectileBlock.getProjectile(level, Collections.singletonList(containedBlockInfo));
                chargePower += (int) projectile.addedChargePower();
            }
        }
        return chargePower;
    }

    public static float getInitialVelocity(AbstractMountedCannonContraption cannon, ServerLevel level) {
        if (cannon instanceof MountedBigCannonContraption bigCannon && bigCannon.entity instanceof PitchOrientedContraptionEntity) {
            return getChargePower(level, (PitchOrientedContraptionEntity)bigCannon.entity);
        } else if (cannon instanceof MountedAutocannonContraption auto) {
            return getACSpeed(auto);
        }
        return 0;
    }

    public static double getProjectileGravity(AbstractMountedCannonContraption cannon) {
        return isAutoCannon(cannon) ? -0.025 : -0.05;
    }

    public static double getProjectileDrag(AbstractMountedCannonContraption cannon) { //TODO implement
        return 0.99;
    }

    public static boolean isBigCannon(AbstractMountedCannonContraption cannon) {
        return cannon instanceof MountedBigCannonContraption;
    }

    public static boolean isAutoCannon(AbstractMountedCannonContraption cannon) {
        return cannon instanceof MountedAutocannonContraption;
    }

    public static int getChargeCount(MountedBigCannonContraption cannon) {
        if (cannon.isDropMortar())
            return 0;
        return 1;
    }

    public static float getACSpeed(MountedAutocannonContraption autocannon) {
        if (autocannon == null)
            return 0;

        if (((AutoCannonAccessor) autocannon).getMaterial() == null)
            return 0;

        AutocannonMaterialProperties properties = ((AutoCannonAccessor) autocannon).getMaterial().properties();
        float speed = properties.baseSpeed();
        BlockPos currentPos = autocannon.getStartPos().relative(autocannon.initialOrientation());
        int barrelTravelled = 0;

        while (autocannon.presentBlockEntities.get(currentPos) instanceof IAutocannonBlockEntity) {
            ++barrelTravelled;
            if (barrelTravelled <= properties.maxSpeedIncreases())
                speed += properties.speedIncreasePerBarrel();
            if (barrelTravelled > properties.maxBarrelLength()) {
                break;
            }
            currentPos = currentPos.relative(autocannon.initialOrientation());
        }
        return speed;
    }

}

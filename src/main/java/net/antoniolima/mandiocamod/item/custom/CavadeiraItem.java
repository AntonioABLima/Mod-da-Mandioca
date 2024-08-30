package net.antoniolima.mandiocamod.item.custom;

import net.antoniolima.mandiocamod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class CavadeiraItem extends ShovelItem {

    public CavadeiraItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (level.isEmptyBlock(pos.above())) {
            if (isEffectiveOn(block)) {
                level.setBlockAndUpdate(pos, ModBlocks.BLOCO_COM_BURACO.get().defaultBlockState());
            } else if (block == ModBlocks.BLOCO_COM_BURACO.get()) {
                level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            }

            level.playSound(null, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);

            context.getItemInHand().hurtAndBreak(1, context.getPlayer(),
                    player -> player.broadcastBreakEvent(context.getPlayer().getUsedItemHand()));

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private boolean isEffectiveOn(Block block) {
        return
                block == Blocks.DIRT ||
                block == Blocks.GRASS_BLOCK ||
                block == Blocks.DIRT_PATH ||
                block == Blocks.FARMLAND ||
                block == Blocks.COARSE_DIRT;
    }
}
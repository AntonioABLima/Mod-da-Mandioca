package net.antonio.tutorialmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CavadeiraItem extends ShovelItem {
    public CavadeiraItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);

        if ((blockState.getBlock() == Blocks.DIRT
                    || blockState.getBlock() == Blocks.GRASS_BLOCK
                    || blockState.getBlock() == Blocks.DIRT_PATH
                    || blockState.getBlock() == Blocks.FARMLAND)
                    && level.isEmptyBlock(pos.above())
        )

        {
            // Substitua pelo bloco de pedra desejado
            level.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
            // Realiza a lógica padrão da enxada (pode ser ajustado conforme necessário)
            super.useOn(context);
            return InteractionResult.SUCCESS;
        }

        // Se não for o bloco de terra cultivável, realiza a lógica padrão da enxada
        return super.useOn(context);
    }
}

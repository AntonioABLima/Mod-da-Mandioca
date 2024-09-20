package net.antoniolima.mandiocamod.block.entity;

import net.antoniolima.mandiocamod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PlantedMandiocaBlockEntity extends BlockEntity {

    public PlantedMandiocaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLANTED_MANDIOCA_BE.get(), pos, state);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!level.isClientSide) {
            BlockPos abovePos = getBlockPos().above();

            if (level.getBlockState(abovePos).isAir()) {
                level.setBlock(abovePos, ModBlocks.MANDIOCA_CROP.get().defaultBlockState(), 3);
            }

        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
    }
}

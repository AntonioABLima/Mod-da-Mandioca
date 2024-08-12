package net.antoniolima.mandiocamod.block.custom;

import net.antoniolima.mandiocamod.block.entity.ModBlockEntities;
import net.antoniolima.mandiocamod.block.entity.PlantedMandiocaBlockEntity;
import net.antoniolima.mandiocamod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PlantedMandiocaBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public PlantedMandiocaBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PlantedMandiocaBlockEntity(pos, state);
    }



    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.PLANTED_MANDIOCA_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof PlantedMandiocaBlockEntity mandiocaBlockEntity) {
                int growthStage = mandiocaBlockEntity.getGrowthStage();
                if (growthStage > 0) {
                    BlockPos abovePos = pos.above();
                    BlockState aboveBlockState = level.getBlockState(abovePos);

                    Block blockAbove = aboveBlockState.getBlock();
                    if (blockAbove instanceof AbstractMandiocaEstagioBlock) {

                        ItemStack drop = new ItemStack(ModItems.MANDIOCA_CRUA.get(), ((PlantedMandiocaBlockEntity) blockEntity).getGrowthStage());
                        Block.popResource(level, pos, drop);
                        level.destroyBlock(abovePos, true);
                    }
                }
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}

package net.antoniolima.mandiocamod.block.custom;

import com.mojang.serialization.MapCodec;
import net.antoniolima.mandiocamod.block.ModBlocks;
import net.antoniolima.mandiocamod.block.entity.BlocoComBuracoBlockEntity;
import net.antoniolima.mandiocamod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BlocoComBuracoBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    public BlocoComBuracoBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if(!pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

            if (blockEntity instanceof BlocoComBuracoBlockEntity blocoComBuracoBlockEntity) {
                ItemStack heldItem = pPlayer.getItemInHand(pHand);

                if (blocoComBuracoBlockEntity.isItemEmpty()) {
                    return handleEmptyState(pLevel, pPos, pPlayer, heldItem, blocoComBuracoBlockEntity);
                } else {
                    return handleFullState(pLevel, pPos, pPlayer, heldItem, blocoComBuracoBlockEntity);
                }
            }
        }

        return ItemInteractionResult.SUCCESS;
    }

    private ItemInteractionResult handleEmptyState(Level pLevel, BlockPos pPos, Player pPlayer, ItemStack heldItem, BlocoComBuracoBlockEntity blocoEntity) {
        if (heldItem.getItem() == ModItems.MANDIOCA_CAULE.get()) {
            ItemStack item = heldItem.copy();
            item.setCount(1);
            heldItem.shrink(1);
            blocoEntity.placeMandioca(pPlayer, item);
            pLevel.playSound(null, pPos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);

            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.FAIL;
    }

    private ItemInteractionResult handleFullState(Level level, BlockPos pos, Player player, ItemStack heldItem, BlocoComBuracoBlockEntity blocoEntity) {
        if (heldItem.getItem() != ModItems.CAVADEIRA.get()) {
            blocoEntity.drops();
        } else {
            handlePlanting(level, pos, blocoEntity);
        }
        return ItemInteractionResult.SUCCESS;
    }

    private void handlePlanting(Level level, BlockPos pos, BlocoComBuracoBlockEntity blocoEntity) {
        level.setBlockAndUpdate(pos, ModBlocks.PLANTED_MANDIOCA_BLOCK.get().defaultBlockState());
        level.playSound(null, pos, SoundEvents.ROOTED_DIRT_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlocoComBuracoBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof BlocoComBuracoBlockEntity) {
                if (pNewState.isAir()) {
                    ((BlocoComBuracoBlockEntity) blockEntity).drops();
                }
                pLevel.removeBlockEntity(pPos);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return super.getTicker(pLevel, pState, pBlockEntityType);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        Fluid fluid = pLevel.getFluidState(pPos.above()).getType();
        if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
            pLevel.setBlockAndUpdate(pPos, Blocks.DIRT.defaultBlockState());
        }

        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
    }

}


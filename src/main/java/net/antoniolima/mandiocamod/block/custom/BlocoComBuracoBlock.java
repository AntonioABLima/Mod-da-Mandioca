package net.antoniolima.mandiocamod.block.custom;

import net.antoniolima.mandiocamod.block.ModBlocks;
import net.antoniolima.mandiocamod.block.entity.BlocoComBuracoBlockEntity;
import net.antoniolima.mandiocamod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlocoComBuracoBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    public BlocoComBuracoBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);

            if(blockentity instanceof BlocoComBuracoBlockEntity blococomburacoblockentity) {
                ItemStack heldItem = pPlayer.getItemInHand(pHand);

                if(blococomburacoblockentity.isStackEmpty()) {
                    if (heldItem.getItem() == ModItems.MANDIOCA_CAULE.get()) {
                        ItemStack singleMandiocaCaule = heldItem.copy();
                        singleMandiocaCaule.setCount(1);

                        heldItem.shrink(1);

                        blococomburacoblockentity.placeMandioca(pPlayer, singleMandiocaCaule);

                        pLevel.playSound(null, pPos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);

                        return InteractionResult.SUCCESS;
                    }
                } else {
                    if (heldItem.getItem() != ModItems.CAVADEIRA.get()) {
                        blococomburacoblockentity.drops();
                        blococomburacoblockentity.cleanStack(pPlayer);
                    } else {
                        System.out.println("Fechando buraco com mandioca dentro!");
                        blococomburacoblockentity.cleanStack(pPlayer);
                        pLevel.setBlockAndUpdate(pPos, ModBlocks.PLANTED_MANDIOCA_BLOCK_TESTE.get().defaultBlockState()); // Mudar !!!!
                        pLevel.playSound(null, pPos, SoundEvents.ROOTED_DIRT_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);

                    }
                    return InteractionResult.SUCCESS;

                }

                return InteractionResult.FAIL;
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlocoComBuracoBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof BlocoComBuracoBlockEntity) {
                ((BlocoComBuracoBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
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
            System.out.println("O bloco com buraco entrou em contato com a água!");
            pLevel.setBlockAndUpdate(pPos, Blocks.DIRT.defaultBlockState());
        }
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
    }

}


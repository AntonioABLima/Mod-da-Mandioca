package net.antoniolima.mandiocamod.block.custom;

import net.antoniolima.mandiocamod.block.ModBlocks;
import net.antoniolima.mandiocamod.block.entity.PlantedMandiocaBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.InteractionResult;

public abstract class AbstractMandiocaEstagioBlock extends Block {
    private final VoxelShape shape;

    public AbstractMandiocaEstagioBlock(Properties pProperties, VoxelShape shape) {
        super(pProperties);
        this.shape = shape;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockPos belowPos = pos.below();
            System.out.println("Mandioca colhida!");

            // Transformar o bloco abaixo em BlocoComBuracoBlock
            level.setBlock(belowPos, ModBlocks.BL0CO_COM_BURACO.get().defaultBlockState(), 3);

            // Remover o bloco atual
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        if (!pLevel.isClientSide()) {
            BlockPos belowPos = pPos.below();
            BlockState belowBlockState = pLevel.getBlockState(belowPos);
            Block blockBelow = belowBlockState.getBlock();

            if (blockBelow instanceof PlantedMandiocaBlock) {
                BlockEntity blockEntity = pLevel.getBlockEntity(belowPos);

                if (blockEntity instanceof PlantedMandiocaBlockEntity mandiocaBlockEntity) {
                    System.out.println("Mandioca quebrada!");
                    mandiocaBlockEntity.restGrowthStage();
                }
            }
        }

        super.destroy(pLevel, pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return shape;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}

package net.antoniolima.mandiocamod.block.custom;
import net.antoniolima.mandiocamod.block.entity.PlantedMandiocaBlockEntityTeste;
import net.antoniolima.mandiocamod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlantedMandiocaBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public PlantedMandiocaBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PlantedMandiocaBlockEntityTeste(pos, state);
    }

    @Override
    public void onRemove(@NotNull BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {

            BlockPos abovePos = pos.above();
            BlockState aboveBlockState = level.getBlockState(abovePos);
            Block blockAbove = aboveBlockState.getBlock();

            BlockPos aboveAbovePos = pos.above().above();
            BlockState aboveAboveBlockState = level.getBlockState(aboveAbovePos);
            Block blockAboveAbove = aboveAboveBlockState.getBlock();

            if (blockAbove instanceof MandiocaCropBlock) {
                IntegerProperty ageProperty = MandiocaCropBlock.AGE;
                int ageValue = aboveBlockState.getValue(ageProperty);
                System.out.println(ageValue);

                ItemStack drop = new ItemStack(ModItems.MANDIOCA_CRUA.get(), ageValue);
                Block.popResource(level, pos, drop);
                level.destroyBlock(abovePos, true);
            }

            if (blockAboveAbove instanceof MandiocaCropBlock) {
                IntegerProperty ageProperty = MandiocaCropBlock.AGE;
                int ageValue = aboveAboveBlockState.getValue(ageProperty);


                ItemStack drop = new ItemStack(ModItems.MANDIOCA_CRUA.get(), ageValue - 3);
                Block.popResource(level, pos, drop);
                level.destroyBlock(aboveAbovePos, true);
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

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        Block blockAbove =  pLevel.getBlockState(pPos.above()).getBlock();
        if (blockAbove == Blocks.MOVING_PISTON) {
            pLevel.setBlockAndUpdate(pPos, Blocks.DIRT.defaultBlockState());
        }

        Fluid fluid = pLevel.getFluidState(pPos.above()).getType();
        if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
            pLevel.setBlockAndUpdate(pPos, Blocks.DIRT.defaultBlockState());
        }

        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
    }
}

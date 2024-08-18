package net.antoniolima.mandiocamod.block.custom;
import net.antoniolima.mandiocamod.block.entity.ModBlockEntities;
import net.antoniolima.mandiocamod.block.entity.PlantedMandiocaBlockEntityTeste;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PlantedMandiocaBlockTeste extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public PlantedMandiocaBlockTeste(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PlantedMandiocaBlockEntityTeste(pos, state);
    }

//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
//        if(pLevel.isClientSide()) {
//            return null;
//        }
//
//        return createTickerHelper(pBlockEntityType, ModBlockEntities.PLANTED_MANDIOCA_TESTE_BE.get(),
//                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
//    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {

            BlockPos abovePos = pos.above();
            BlockState aboveBlockState = level.getBlockState(abovePos);
            Block blockAbove = aboveBlockState.getBlock();

            if (blockAbove instanceof MandiocaCropBlock) {
                IntegerProperty growthStage = ((MandiocaCropBlock) blockAbove).getAgeProperty();
                int growthStageValue = aboveBlockState.getValue(growthStage);

                ItemStack drop = new ItemStack(ModItems.MANDIOCA_CRUA.get(), growthStageValue);
                Block.popResource(level, pos, drop);
                level.destroyBlock(abovePos, true);
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

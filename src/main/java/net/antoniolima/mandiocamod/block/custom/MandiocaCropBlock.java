package net.antoniolima.mandiocamod.block.custom;

import net.antoniolima.mandiocamod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class MandiocaCropBlock extends CropBlock implements BonemealableBlock {
    public static final int INITIAL_STAGE = 0; // Novo estágio inicial
    public static final int FIRST_STAGE_MAX_AGE = 2;
    public static final int SECOND_STAGE_MAX_AGE = 1;

    public MandiocaCropBlock(Properties pProperties) {
        super(pProperties);
    }

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(7.25, 0, 7.25, 8.75, 2, 8.75), // Novo estágio
            Block.box(7.625, 2, 7.625, 8.375, 4.125, 8.375),
            Block.box(7.5, 2, 7.5, 8.5, 5.875, 8.5),
            Block.box(7.25, 2, 7.25, 8.75, 11.25, 8.75)
    };

    public static final IntegerProperty AGE = IntegerProperty.create("age", INITIAL_STAGE, 3);

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE_BY_AGE[this.getAge(pState)];
    }

    @Override
    public void randomTick(@NotNull BlockState pState, ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return;
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {

            int currentAge = this.getAge(pState);
            if (currentAge < this.getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, pLevel, pPos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / growthSpeed) + 1) == 0)) {
                    if (currentAge == FIRST_STAGE_MAX_AGE) {
                        if (pLevel.getBlockState(pPos.above(1)).is(Blocks.AIR)) {
                            pLevel.setBlock(pPos.above(1), this.getStateForAge(currentAge + 1), 2);
                        }
                    } else {
                        pLevel.setBlock(pPos, this.getStateForAge(currentAge + 1), 2);
                        System.out.println("Cresceu");
                    }

                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }


    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!level.isClientSide) {
            ItemStack heldItem = player.getItemInHand(hand);

            if(heldItem.getItem() == Items.BONE_MEAL || state.getValue(getAgeProperty()) == 0){
                return InteractionResult.PASS;
            }

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

            if (blockBelow instanceof PlantedMandiocaBlockTeste) {
                System.out.println("Mandioca quebrada!");

                pLevel.setBlock(pPos, ModBlocks.MANDIOCA_CROP.get().defaultBlockState(), 3);
            }
        }

        super.destroy(pLevel, pPos, pState);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return true;
    }

    @Override
    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState) {
        int nextAge = this.getAge(pState) + this.getBonemealAgeIncrease(pLevel);
        int maxAge = this.getMaxAge();
        if (nextAge > maxAge) {
            nextAge = maxAge;
        }

        if (this.getAge(pState) == FIRST_STAGE_MAX_AGE && pLevel.getBlockState(pPos.above(1)).is(Blocks.AIR)) {
            pLevel.setBlock(pPos.above(1), this.getStateForAge(nextAge), 2);
        } else {
            pLevel.setBlock(pPos, this.getStateForAge(nextAge - SECOND_STAGE_MAX_AGE), 2);
        }
    }

    @Override
    public int getMaxAge() {
        return INITIAL_STAGE + FIRST_STAGE_MAX_AGE + SECOND_STAGE_MAX_AGE;
    }

    @Override
    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}

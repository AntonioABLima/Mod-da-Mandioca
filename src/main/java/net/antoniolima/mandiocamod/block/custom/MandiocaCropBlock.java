package net.antoniolima.mandiocamod.block.custom;

import net.antoniolima.mandiocamod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import org.jetbrains.annotations.NotNull;

public class MandiocaCropBlock extends CropBlock implements BonemealableBlock {
    public static final int INITIAL_STAGE = 0; // Novo estágio inicial
    public static final int MAX_AGE = 5;

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;



    public MandiocaCropBlock(Properties pProperties) {
        super(pProperties);

//        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, INITIAL_STAGE).setValue(FACING, Direction.SOUTH));
    }

    public void chandeWhereIsFacing(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving){
        if (!world.isClientSide) {
            // Escolher uma direção aleatória ou específica
            Direction chosenDirection = Direction.Plane.HORIZONTAL.getRandomDirection(world.getRandom());

            // Atualizar o estado do bloco com a nova direção
            BlockState newState = state.setValue(FACING, chosenDirection);

            // Colocar o bloco com o novo estado
            world.setBlock(pos, newState, 2);

            System.out.println("O bloco Mandioca foi colocado no mundo virado para: " + chosenDirection);
        }
    }
//    @Override
//    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
////        super.onPlace(state, world, pos, oldState, isMoving);
//
//        // Verifique se está no lado do servidor para evitar que o código seja executado duas vezes (lado cliente e servidor)
//        if (!world.isClientSide) {
//            // Escolher uma direção aleatória ou específica
//            Direction chosenDirection = Direction.Plane.HORIZONTAL.getRandomDirection(world.getRandom());
//
//            // Atualizar o estado do bloco com a nova direção
//            BlockState newState = state.setValue(FACING, chosenDirection);
//
//            // Colocar o bloco com o novo estado
////            world.setBlock(pos, newState, 2);
//
//            System.out.println("O bloco Mandioca foi colocado no mundo virado para: " + chosenDirection);
//        }
//    }

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            // Estágio 0
            Block.box(7.25, 0, 7.25, 8.75, 2, 8.75), // Novo estágio
            // Estágio 1
            Block.box(7.625, 2, 7.625, 8.375, 4.125, 8.375),
            // Estágio 2
            Block.box(7.5, 2, 7.5, 8.5, 5.875, 8.5),
            // Estágio 3
            Shapes.or(
                Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
                Block.box(8.375, 6, 6.375, 9.625, 10.125, 7.625)
            ),
            // Estágio 4
            Shapes.or(
                Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
                Block.box(8.375, 6, 6.375, 9.625, 11, 7.625),
                Block.box(9.375, 11, 6.375, 10.625, 17.125, 7.625)
            ),
            // Estágio 5
            Shapes.or(
                Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
                Block.box(8.375, 6, 7.375, 9.625, 29.125, 8.625)
            ),
    };

    public static final IntegerProperty AGE = IntegerProperty.create("age", INITIAL_STAGE, 5);

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
                    growCrops(pLevel, pPos, pState);
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
            level.setBlock(belowPos, ModBlocks.BLOCO_COM_BURACO.get().defaultBlockState(), 3);

            // Remover o bloco atual
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
//        super.destroy(pLevel, pPos, pState);

        if (!pLevel.isClientSide()) {
            BlockPos belowPos = pPos.below();
            BlockState belowBlockState = pLevel.getBlockState(belowPos);
            Block blockBelow = belowBlockState.getBlock();

            if (blockBelow instanceof PlantedMandiocaBlockTeste) {
                System.out.println("Mandioca quebrada!");

                pLevel.setBlock(pPos, ModBlocks.MANDIOCA_CROP.get().defaultBlockState(), 3);
            }
        }

    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return true;
    }

    @Override
    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState) {
        int currentAge = this.getAge(pState);
        int nextAge = currentAge + 1;

        int maxAge = this.getMaxAge();
        if (nextAge > maxAge) {
            nextAge = maxAge;
        }

        if (currentAge == MAX_AGE && pLevel.getBlockState(pPos.above(1)).is(Blocks.AIR)) {
            pLevel.setBlock(pPos.above(1), this.getStateForAge(nextAge), 2);
        } else {
            pLevel.setBlock(pPos, this.getStateForAge(nextAge), 2);
        }
        chandeWhereIsFacing(this.getStateForAge(nextAge), pLevel, pPos, pState, false);
    }
//    BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving
    @Override
    public int getMaxAge() {
        return  MAX_AGE;
    }

    @Override
    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE, FACING);
    }
    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        growCrops(pLevel, pPos, pState);
    }
    @Override
    protected int getBonemealAgeIncrease(Level pLevel) {
        return Mth.nextInt(pLevel.random, 1, 2);
    }
}

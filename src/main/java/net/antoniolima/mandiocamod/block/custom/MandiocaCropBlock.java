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
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

public class MandiocaCropBlock extends CropBlock implements BonemealableBlock {
    public static final int INITIAL_STAGE = 0; // Novo estágio inicial
    public static final int MAX_AGE = 5;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;;
    public static final IntegerProperty AGE = IntegerProperty.create("age", INITIAL_STAGE, 5);

    protected static final VoxelShape ESTAGE_0 = Block.box(7.25, 0, 7.25, 8.75, 2, 8.75);
    protected static final VoxelShape ESTAGE_1 =  Block.box(7.625, 2, 7.625, 8.375, 4.125, 8.375);
    protected static final VoxelShape ESTAGE_2 =  Block.box(7.5, 2, 7.5, 8.5, 5.875, 8.5);
    protected static final VoxelShape ESTAGE_3_NORTH = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(8.375, 6, 6.375, 9.625, 10.125, 7.625)
    );
    protected static final VoxelShape ESTAGE_3_EAST = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(8.375, 6, 6.375 + 2, 9.625, 10.125, 7.625 + 2)
    );
    protected static final VoxelShape ESTAGE_3_SOUTH = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(8.375 - 2, 6, 6.375 + 2, 9.625 - 2, 10.125, 7.625 + 2)
    );
    protected static final VoxelShape ESTAGE_3_WEST = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(8.375 - 2, 6, 6.375, 9.625 - 2, 10.125, 7.625)
    );
    protected static final VoxelShape  ESTAGE_4_NORTH = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(8.375, 6, 6.375, 9.625, 11, 7.625),
            Block.box(9.375, 11, 6.375, 10.625, 17.125, 7.625)
    );
    protected static final VoxelShape ESTAGE_4_EAST = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(8.375, 6, 6.375 + 2, 9.625, 11, 7.625 + 2),
            Block.box(9.375 - 1, 11 , 6.375 + 3, 10.625 - 1, 17.125, 7.625 + 3)
    );
    protected static final VoxelShape ESTAGE_4_SOUTH = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(8.375 - 2, 6, 6.375 + 2, 9.625 - 2, 11, 7.625 + 2),
            Block.box(9.375 - 4, 11, 6.375 + 2, 10.625 - 4, 17.125, 7.625 + 2)
    );
    protected static final VoxelShape ESTAGE_4_WEST = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(8.375 - 2, 6, 6.375, 9.625 - 2, 11, 7.625),
            Block.box(9.375 - 3, 11, 6.375 - 1, 10.625 - 3, 17.125, 7.625 - 1)
    );
    protected static final VoxelShape ESTAGE_5_NORTH = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(8.375, 6, 7.375, 9.625, 29.125, 8.625)
    );
    protected static final VoxelShape ESTAGE_5_EAST = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(6.375 + 1, 6, 7.375 + 1, 7.625 + 1, 29.125, 8.625 + 1)
    );
    protected static final VoxelShape ESTAGE_5_SOUTH = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(8.375 -2, 6, 7.375, 9.625 - 2, 29.125, 8.625)
    );
    protected static final VoxelShape ESTAGE_5_WEST = Shapes.or(
            Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
            Block.box(8.375 - 1, 6, 7.375 - 1, 9.625 - 1, 29.125, 8.625 - 1)
    );


    public MandiocaCropBlock(Properties pProperties) {
        super(pProperties);
    }

    public void changeWhereIsFacing(BlockState state, Level world, BlockPos pos){
        if (!world.isClientSide) {
            Direction chosenDirection = Direction.Plane.HORIZONTAL.getRandomDirection(world.getRandom());
            BlockState newState = state.setValue(FACING, chosenDirection);
            world.setBlock(pos, newState, 2);
            System.out.println("O bloco Mandioca foi colocado no mundo virado para: " + chosenDirection);
        }
    }

    private static final VoxelShape[] SHAPE_BY_AGE_NORTH = new VoxelShape[]{
            ESTAGE_0,
            ESTAGE_1,
            ESTAGE_2,
            ESTAGE_3_NORTH, // Estágio 3
            ESTAGE_4_NORTH, // Estágio 4
            ESTAGE_5_NORTH  // Estágio 5
    };

    private static final VoxelShape[] SHAPE_BY_AGE_EAST = new VoxelShape[]{
            ESTAGE_0,
            ESTAGE_1,
            ESTAGE_2,
            ESTAGE_3_EAST, // Estágio 3
            ESTAGE_4_EAST, // Estágio 4
            ESTAGE_5_EAST  // Estágio 5
    };

    private static final VoxelShape[] SHAPE_BY_AGE_SOUTH = new VoxelShape[]{
            ESTAGE_0,
            ESTAGE_1,
            ESTAGE_2,
            ESTAGE_3_SOUTH, // Estágio 3
            ESTAGE_4_SOUTH, // Estágio 4
            ESTAGE_5_SOUTH  // Estágio 5
    };

    private static final VoxelShape[] SHAPE_BY_AGE_WEST = new VoxelShape[]{
            ESTAGE_0,
            ESTAGE_1,
            ESTAGE_2,
            ESTAGE_3_WEST, // Estágio 3
            ESTAGE_4_WEST, // Estágio 4
            ESTAGE_5_WEST  // Estágio 5
    };

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        int age = this.getAge(pState);
        Direction facing =  pState.getValue(FACING);
        switch (facing.getName()) {
            case "east":
                return SHAPE_BY_AGE_EAST[age];
            case "south":
                return SHAPE_BY_AGE_SOUTH[age];
            case "west":
                return  SHAPE_BY_AGE_WEST[age];
            default:
                return SHAPE_BY_AGE_NORTH[age];
        }
    }

    @Override
    public void randomTick(@NotNull BlockState pState, ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return;

        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            int currentAge = this.getAge(pState);
            if (currentAge < this.getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, pLevel, pPos);
                if (ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / growthSpeed) + 1) == 0)) {
                    growCrops(pLevel, pPos, pState);
                    ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
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
        changeWhereIsFacing(this.getStateForAge(nextAge), pLevel, pPos);
    }
//    BlockState state, Level world, BlockPos pos
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

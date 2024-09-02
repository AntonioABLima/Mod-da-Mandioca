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
    public static final int INITIAL_STAGE = 0;
    public static final int MAX_AGE = 5;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final IntegerProperty AGE = IntegerProperty.create("age", INITIAL_STAGE, MAX_AGE + 1);

    protected static final VoxelShape ESTAGE_0 = Block.box(7.25, 0, 7.25, 8.75, 2, 8.75);
    protected static final VoxelShape ESTAGE_1 = Block.box(7.625, 2, 7.625, 8.375, 4.125, 8.375);
    protected static final VoxelShape ESTAGE_2 = Block.box(7.5, 2, 7.5, 8.5, 5.875, 8.5);
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
    protected static final VoxelShape ESTAGE_4_NORTH = Shapes.or(
        Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
        Block.box(8.375, 6, 6.375, 9.625, 11, 7.625),
        Block.box(9.375, 11, 6.375, 10.625, 17.125, 7.625)
    );
    protected static final VoxelShape ESTAGE_4_EAST = Shapes.or(
        Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
        Block.box(8.375, 6, 6.375 + 2, 9.625, 11, 7.625 + 2),
        Block.box(9.375 - 1, 11, 6.375 + 3, 10.625 - 1, 17.125, 7.625 + 3)
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
        Block.box(8.375, 6, 7.375, 9.625, 28.125, 8.625)
    );
    protected static final VoxelShape ESTAGE_5_EAST = Shapes.or(
        Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
        Block.box(6.375 + 1, 6, 7.375 + 1, 7.625 + 1, 28.125, 8.625 + 1)
    );
    protected static final VoxelShape ESTAGE_5_SOUTH = Shapes.or(
        Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
        Block.box(8.375 - 2, 6, 7.375, 9.625 - 2, 28.125, 8.625)
    );
    protected static final VoxelShape ESTAGE_5_WEST = Shapes.or(
        Block.box(7.375, 2, 7.375, 8.625, 6, 8.625),
        Block.box(8.375 - 1, 6, 7.375 - 1, 9.625 - 1, 28.125, 8.625 - 1)
    );
    protected static final VoxelShape ESTAGE_6_NORTH = Shapes.or(
        Block.box(7.375, 2 - 16, 7.375, 8.625, 6 - 16, 8.625),
        Block.box(8.375, 6 -16, 7.375, 9.625, 28.125 - 16, 8.625)
    );
    protected static final VoxelShape ESTAGE_6_EAST = Shapes.or(
        Block.box(7.375, 2 - 16, 7.375, 8.625, 6 - 16, 8.625),
        Block.box(6.375 + 1, 6 - 16, 7.375 + 1, 7.625 + 1, 28.125 - 16, 8.625 + 1)
    );
    protected static final VoxelShape ESTAGE_6_SOUTH = Shapes.or(
        Block.box(7.375, 2 - 16, 7.375, 8.625, 6 - 16, 8.625),
        Block.box(8.375 - 2, 6 - 16, 7.375, 9.625 - 2, 28.125 - 16, 8.625)
    );
    protected static final VoxelShape ESTAGE_6_WEST = Shapes.or(
        Block.box(7.375, 2 - 16, 7.375, 8.625, 6 - 16, 8.625),
        Block.box(8.375 - 1, 6 - 16, 7.375 - 1, 9.625 - 1, 28.125 - 16, 8.625 - 1)
    );


    public MandiocaCropBlock(Properties pProperties) {
        super(pProperties);
    }

    public void changeWhereIsFacing(BlockState state, Level world, BlockPos pos) {
        if (!world.isClientSide) {
            Direction chosenDirection = Direction.Plane.HORIZONTAL.getRandomDirection(world.getRandom());
            BlockState newState = state.setValue(FACING, chosenDirection);
            world.setBlock(pos, newState, 2);
        }
    }

    private static final VoxelShape[] SHAPE_BY_AGE_NORTH = new VoxelShape[]{
            ESTAGE_0,
            ESTAGE_1,
            ESTAGE_2,
            ESTAGE_3_NORTH,
            ESTAGE_4_NORTH,
            ESTAGE_5_NORTH,
            ESTAGE_6_NORTH
    };

    private static final VoxelShape[] SHAPE_BY_AGE_EAST = new VoxelShape[]{
            ESTAGE_0,
            ESTAGE_1,
            ESTAGE_2,
            ESTAGE_3_EAST,
            ESTAGE_4_EAST,
            ESTAGE_5_EAST,
            ESTAGE_6_EAST
    };

    private static final VoxelShape[] SHAPE_BY_AGE_SOUTH = new VoxelShape[]{
            ESTAGE_0,
            ESTAGE_1,
            ESTAGE_2,
            ESTAGE_3_SOUTH,
            ESTAGE_4_SOUTH,
            ESTAGE_5_SOUTH,
            ESTAGE_6_SOUTH
    };

    private static final VoxelShape[] SHAPE_BY_AGE_WEST = new VoxelShape[]{
            ESTAGE_0,
            ESTAGE_1,
            ESTAGE_2,
            ESTAGE_3_WEST,
            ESTAGE_4_WEST,
            ESTAGE_5_WEST,
            ESTAGE_6_WEST
    };

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        int age = this.getAge(pState);
        Direction facing = pState.getValue(FACING);
        return switch (facing.getName()) {
            case "east" -> SHAPE_BY_AGE_EAST[age];
            case "south" -> SHAPE_BY_AGE_SOUTH[age];
            case "west" -> SHAPE_BY_AGE_WEST[age];
            default -> SHAPE_BY_AGE_NORTH[age];
        };
    }

    @Override
    public void randomTick(@NotNull BlockState pState, ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return;

        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            int currentAge = this.getAge(pState);
            if (currentAge < this.getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, pLevel, pPos);
                if (ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int) (25.0F / growthSpeed) + 1) == 0)) {
                    growCrops(pLevel, pPos, pState);
                    ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!level.isClientSide) {
            ItemStack heldItem = player.getItemInHand(hand);

            if (heldItem.getItem() == Items.BONE_MEAL || state.getValue(getAgeProperty()) == 0) {
                return InteractionResult.PASS;
            }

            BlockPos belowPos = pos.below();
            int currentAge = this.getAge(state);

            if (currentAge == 6) {
                BlockPos belowBelowPos = belowPos.below();
                level.setBlock(belowBelowPos, ModBlocks.BLOCO_COM_BURACO.get().defaultBlockState(), 3);
                level.setBlock(belowPos, Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

            } else {
                level.setBlock(belowPos, ModBlocks.BLOCO_COM_BURACO.get().defaultBlockState(), 3);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);

        if (!pLevel.isClientSide()) {
            BlockPos belowPos = pPos.below();
            BlockState belowBlockState = pLevel.getBlockState(belowPos);
            Block blockBelow = belowBlockState.getBlock();

            int currentAge = this.getAge(pState);

            System.out.println("Mandioca quebrada!");

            if (currentAge == 6) {
                if (blockBelow instanceof  MandiocaCropBlock) {
                    pLevel.setBlock(belowPos, ModBlocks.MANDIOCA_CROP.get().defaultBlockState(), 3);
                }
                pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
            }
            if (currentAge == 5) {
                BlockPos abovePos = pPos.above();
                pLevel.setBlock(abovePos, Blocks.AIR.defaultBlockState(), 3);
            }
            if (currentAge <= 5 && blockBelow instanceof  PlantedMandiocaBlockTeste) {
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

        if (nextAge == MAX_AGE ) {
            BlockPos abovePos = pPos.above();
            BlockState aboveState = pLevel.getBlockState(abovePos);
            if (aboveState.isAir()) {
                pLevel.setBlock(pPos, this.getStateForAge(nextAge), 2);
                pLevel.setBlock(abovePos, this.getStateForAge(6), 2);

                // Altera a direção dos blocos
                changeWhereIsFacing(this.getStateForAge(nextAge), pLevel, pPos);
                Direction facing = pLevel.getBlockState(pPos).getValue(FACING);
                pLevel.setBlock(abovePos, this.getStateForAge(6).setValue(FACING, facing), 2);
            }
        }

        if (nextAge <= MAX_AGE - 1) {
            pLevel.setBlock(pPos, this.getStateForAge(nextAge), 2);

            // Altera a direção do bloco
            changeWhereIsFacing(this.getStateForAge(nextAge), pLevel, pPos);
        }
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
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


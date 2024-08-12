package net.antoniolima.mandiocamod.block.entity;

import net.antoniolima.mandiocamod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PlantedMandiocaBlockEntity extends BlockEntity {
    private int growthStage = 0;
    private int time = 0;

    public PlantedMandiocaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLANTED_MANDIOCA_BE.get(), pos, state);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide) {
            increaseTime();

            // Crescimento a cada 2.5 segundos (150 ticks)
            if (time >= 75 && growthStage <= 3) {
                increaseGrowthStage();
                System.out.println("Growth Stage: " + growthStage);

                resetTime();
                setChanged();

                // Notifica o cliente para atualizar a renderização
                level.sendBlockUpdated(pos, state, state, 3);

                BlockPos abovePos = pos.above();
                BlockPos twoBlocksAbovePos = pos.above().above();

                BlockState aboveBlockState = level.getBlockState(abovePos);
                BlockState twoBlocksAboveBlockState = level.getBlockState(twoBlocksAbovePos);

                if (twoBlocksAboveBlockState.isAir()) {
                    switch (growthStage) {
                        case 1:
                            level.setBlock(abovePos, ModBlocks.MANDIOCA_ESTAGIO_1.get().defaultBlockState(), 3);
                            break;
                        case 2:
                            level.setBlock(abovePos, ModBlocks.MANDIOCA_ESTAGIO_2.get().defaultBlockState(), 3);
                            break;
                        case 3:
                            level.setBlock(abovePos, ModBlocks.MANDIOCA_ESTAGIO_3.get().defaultBlockState(), 3);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void increaseTime() {
        time++;
    }

    private void resetTime() {
        time = 0;
    }

    private void increaseGrowthStage() {
        if (growthStage < 3) {
            growthStage++;
        }
    }

    public void restGrowthStage() {
        growthStage = 0;
    }

    public int getGrowthStage() {
        return growthStage;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        // Adicione qualquer lógica adicional necessária ao carregar a entidade de bloco
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        // Adicione qualquer lógica adicional necessária ao invalidar capacidades
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("GrowthStage", growthStage);
        pTag.putInt("Time", time);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.growthStage = pTag.getInt("GrowthStage");
        this.time = pTag.getInt("Time");
    }
}

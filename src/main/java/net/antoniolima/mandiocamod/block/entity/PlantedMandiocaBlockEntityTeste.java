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

public class PlantedMandiocaBlockEntityTeste extends BlockEntity {

    public PlantedMandiocaBlockEntityTeste(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLANTED_MANDIOCA_TESTE_BE.get(), pos, state);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!level.isClientSide) {
            BlockPos abovePos = getBlockPos().above();

            if (level.getBlockState(abovePos).isAir()) {
                level.setBlock(abovePos, ModBlocks.MANDIOCA_CROP.get().defaultBlockState(), 3);
            }

        }
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        // Adicione qualquer lógica adicional necessária ao invalidar capacidades
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
    }
}

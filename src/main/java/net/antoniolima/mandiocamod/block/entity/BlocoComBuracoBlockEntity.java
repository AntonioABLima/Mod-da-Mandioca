package net.antoniolima.mandiocamod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class BlocoComBuracoBlockEntity extends BlockEntity {
    private final NonNullList<ItemStack> item;

    public BlocoComBuracoBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BLOCO_COM_BURACO_BE.get(), pPos, pBlockState);
        this.item = NonNullList.withSize(1, ItemStack.EMPTY);
    }

    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.item.clear();
        ContainerHelper.loadAllItems(pTag, this.item, pRegistries);

    }

    public NonNullList<ItemStack> getItem() {
        return this.item;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        ContainerHelper.saveAllItems(pTag, this.item, true, pRegistries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        CompoundTag $$1 = new CompoundTag();
        ContainerHelper.saveAllItems($$1, this.item, true, pRegistries);
        return $$1;
    }


    public void placeMandioca(@Nullable LivingEntity pEntity, ItemStack pItem) {
        this.item.set(0, pItem.consumeAndReturn(1, pEntity));
        this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(pEntity, this.getBlockState()));
        this.markUpdated();

    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }


    public boolean isItemEmpty() {
        return this.item.get(0).isEmpty();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(this.item.getFirst());
        Containers.dropContents(this.level, this.worldPosition, inventory);

        this.markUpdated();
    }
}


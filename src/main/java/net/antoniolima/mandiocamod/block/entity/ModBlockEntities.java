package net.antoniolima.mandiocamod.block.entity;

import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MandiocaMod.MOD_ID);



    public static final RegistryObject<BlockEntityType<BlocoComBuracoBlockEntity>> BLOCO_COM_BURACO_BE =
            BLOCK_ENTITIES.register("bloco_com_buraco_be", () ->
                    BlockEntityType.Builder.of(BlocoComBuracoBlockEntity::new,
                            ModBlocks.BLOCO_COM_BURACO.get()).build(null));


    public static final RegistryObject<BlockEntityType<PlantedMandiocaBlockEntity>> PLANTED_MANDIOCA_BE =
            BLOCK_ENTITIES.register("planted_mandioca_be", () ->
                    BlockEntityType.Builder.of(PlantedMandiocaBlockEntity::new,
                            ModBlocks.PLANTED_MANDIOCA_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}

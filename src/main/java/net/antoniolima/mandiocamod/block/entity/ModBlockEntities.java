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
                            ModBlocks.BL0CO_COM_BURACO.get()).build(null));


    public static final RegistryObject<BlockEntityType<PlantedMandiocaBlockEntityTeste>> PLANTED_MANDIOCA_TESTE_BE =
            BLOCK_ENTITIES.register("planted_mandioca_teste_be", () ->
                    BlockEntityType.Builder.of(PlantedMandiocaBlockEntityTeste::new,
                            ModBlocks.PLANTED_MANDIOCA_BLOCK_TESTE.get()).build(null));




    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}

package net.antoniolima.mandiocamod.block;

import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.block.custom.*;
import net.antoniolima.mandiocamod.item.ModItems;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, MandiocaMod.MOD_ID);

    public static final RegistryObject<Block> BLOCO_COM_BURACO = registerBlock("bloco_com_buraco",
        () -> new BlocoComBuracoBlock(Properties.of()
            .mapColor(MapColor.DIRT)
            .strength(0.5F)
            .sound(SoundType.GRAVEL)
            .noOcclusion()));


    public static final RegistryObject<Block> PLANTED_MANDIOCA_BLOCK = registerBlock("planted_mandioca_block",
        () -> new PlantedMandiocaBlock(Properties
            .of()
            .mapColor(MapColor.DIRT)
            .strength(0.5F)
            .sound(SoundType.GRAVEL)
            .noOcclusion()));


    public static final RegistryObject<Block> MANDIOCA_CROP = BLOCKS.register("mandioca_crop",
        () -> new MandiocaCropBlock(Properties
            .of()
            .noOcclusion()
            .noCollission()));


    public static final RegistryObject<Block> BOLO_DE_MANDIOCA = registerBlock("bolo_de_mandioca",
            () -> new CakeBlock(Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}

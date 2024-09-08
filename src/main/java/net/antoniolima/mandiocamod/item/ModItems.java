package net.antoniolima.mandiocamod.item;

import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.block.ModBlocks;
import net.antoniolima.mandiocamod.item.custom.*;
import net.antoniolima.mandiocamod.sound.ModSounds;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MandiocaMod.MOD_ID);

    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(100)));

    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry",
            () -> new Item(new Item.Properties().food(ModFoods.STRAWBERRY)));

    public static final RegistryObject<Item> PINE_CONE = ITEMS.register("pine_cone",
            () -> new FuelItem(new Item.Properties(), 400));



    public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.STRAWBERRY_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> CORN_SEEDS = ITEMS.register("corn_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CORN_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORN = ITEMS.register("corn",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BAR_BRAWL_MUSIC_DISC = ITEMS.register("bar_brawl_music_disc",
            () -> new RecordItem(6, ModSounds.BAR_BRAWL, new Item.Properties().stacksTo(1), 2440));



    public static final RegistryObject<Item> CAVADEIRA = ITEMS.register("cavadeira",
            () -> new CavadeiraItem(Tiers.GOLD, 0, 0, new Item.Properties()));

    public static final RegistryObject<Item> MANDIOCA_CRUA = ITEMS.register("mandioca_crua",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> MANDIOCA_CAULE = ITEMS.register("mandioca_caule",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> MANDIOCA_DESCASCADA = ITEMS.register("mandioca_descascada",
            () -> new Item(new Item.Properties().food(ModFoods.MANDIOCA_DESCASCADA)));

    public static final RegistryObject<Item> MANDIOCA_RALADA = ITEMS.register("mandioca_ralada",
            () -> new Item(new Item.Properties().stacksTo(16).craftRemainder(Items.BOWL)));

    public static final RegistryObject<Item> TAPIOCA_DE_CARNE = ITEMS.register("tapioca_de_carne",
            () -> new Item(new Item.Properties().food(ModFoods.TAPIOCA_DE_CARNE).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> TAPIOCA_DE_FRANGO = ITEMS.register("tapioca_de_frango",
            () -> new Item(new Item.Properties().food(ModFoods.TAPIOCA_DE_FRANGO).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> TAPIOCA_DE_PORCO = ITEMS.register("tapioca_de_porco",
            () -> new Item(new Item.Properties().food(ModFoods.TAPIOCA_DE_PORCO).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> TAPIOCA_DE_CARNEIRO = ITEMS.register("tapioca_de_carneiro",
            () -> new Item(new Item.Properties().food(ModFoods.TAPIOCA_DE_CARNEIRO).stacksTo(16).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> TAPIOCA_DE_PEIXE = ITEMS.register("tapioca_de_peixe",
            () -> new Item(new Item.Properties().food(ModFoods.TAPIOCA_DE_PEIXE).stacksTo(16).craftRemainder(Items.BOWL)));



    public static final RegistryObject<Item> FACAO = ITEMS.register("facao",
            () -> new FacaoItem(new Item.Properties().stacksTo(1).durability(100)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

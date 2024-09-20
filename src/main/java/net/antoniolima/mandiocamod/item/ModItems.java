package net.antoniolima.mandiocamod.item;

import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.item.custom.*;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MandiocaMod.MOD_ID);


    public static final RegistryObject<Item> CAVADEIRA = ITEMS.register("cavadeira",
            () -> new CavadeiraItem(Tiers.GOLD, 0, 0, new Item.Properties()));

    public static final RegistryObject<Item> MANDIOCA_CRUA = ITEMS.register("mandioca_crua",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> MANDIOCA_CAULE = ITEMS.register("mandioca_caule",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> MANDIOCA_DESCASCADA = ITEMS.register("mandioca_descascada",
            () -> new Item(new Item.Properties().food(ModFoods.MANDIOCA_DESCASCADA)));

    public static final RegistryObject<Item> MANDIOCA_COZIDA = ITEMS.register("mandioca_cozida",
            () -> new Item(new Item.Properties().food(ModFoods.MANDIOCA_COZIDA)));

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

    public static final RegistryObject<Item> TAPIOCA_DE_COELHO = ITEMS.register("tapioca_de_coelho",
            () -> new Item(new Item.Properties().food(ModFoods.TAPIOCA_DE_COELHO).stacksTo(16).craftRemainder(Items.BOWL)));

    public static final RegistryObject<Item> MANDIOCA_DOURADA = ITEMS.register("mandioca_dourada",
            () -> new EnchantedGoldenAppleItem((new Item.Properties()).rarity(Rarity.EPIC).food(Foods.ENCHANTED_GOLDEN_APPLE)));


    public static final RegistryObject<Item> FACAO = ITEMS.register("facao",
            () -> new FacaoItem(new Item.Properties().stacksTo(1).durability(100)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

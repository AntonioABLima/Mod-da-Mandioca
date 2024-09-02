package net.antoniolima.mandiocamod.item;

import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MandiocaMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MANDIOCA_TAB = CREATIVE_MODE_TABS.register("mandioca_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MANDIOCA_CRUA.get()))
                    .title(Component.translatable("creativetab.mandioca_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.CAVADEIRA.get());
                        pOutput.accept(ModItems.MANDIOCA_CRUA.get());
                        pOutput.accept(ModItems.MANDIOCA_DESCASCADA.get());
                        pOutput.accept(ModItems.MANDIOCA_RALADA.get());
                        pOutput.accept(ModItems.MANDIOCA_CAULE.get());
                        pOutput.accept(ModItems.FACAO.get());
                        pOutput.accept(ModItems.TAPIOCA_DE_CARNE.get());
                        pOutput.accept(ModItems.TAPIOCA_DE_FRANGO.get());
                        pOutput.accept(ModItems.TAPIOCA_DE_PORCO.get());
                        pOutput.accept(ModItems.TAPIOCA_DE_CARNEIRO.get());
//                        pOutput.accept(ModBlocks.BOLO_DE_MANDIOCA.get());


                        pOutput.accept(ModItems.METAL_DETECTOR.get());

                        pOutput.accept(Items.DIAMOND);

                        pOutput.accept(ModItems.STRAWBERRY.get());
                        pOutput.accept(ModItems.PINE_CONE.get());

                        pOutput.accept(ModItems.STRAWBERRY_SEEDS.get());

                        pOutput.accept(ModItems.CORN.get());
                        pOutput.accept(ModItems.CORN_SEEDS.get());

                        pOutput.accept(ModItems.BAR_BRAWL_MUSIC_DISC.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

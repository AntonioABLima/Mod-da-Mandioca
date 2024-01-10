package net.antonio.tutorialmod.Item;

import net.antonio.tutorialmod.TutorialMod;
import net.antonio.tutorialmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TutorialMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("tutorial_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MANDIOCA.get()))
                    .title(Component.translatable("creativeTab.tutorial.tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.MANDIOCA.get());
                        pOutput.accept(ModItems.TOASTED_MANDIOCA.get());

                        pOutput.accept(ModBlocks.MANDIOCA_BLOCK.get());

                        pOutput.accept(ModItems.STRAWBERRY.get());

                        pOutput.accept(ModItems.STRAWBERRY_SEEDS.get());

                    })
                    .build());

    public static  void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

package net.antonio.tutorialmod.datagen;

import net.antonio.tutorialmod.block.ModBlocks;
import net.antonio.tutorialmod.datagen.loot.ModBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

public class ModLootTableProvider {
    public static LootTableProvider create(PackOutput output){
        return new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSet.BLOCK);
    }
}

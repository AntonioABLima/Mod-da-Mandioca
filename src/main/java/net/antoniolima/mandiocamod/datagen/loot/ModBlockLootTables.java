package net.antoniolima.mandiocamod.datagen.loot;

import net.antoniolima.mandiocamod.block.ModBlocks;
import net.antoniolima.mandiocamod.block.custom.MandiocaCropBlock;
import net.antoniolima.mandiocamod.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        this.add(ModBlocks.MANDIOCA_CROP.get(),
            LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.MANDIOCA_CAULE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))) // Para o estágio 1
                        .when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(ModBlocks.MANDIOCA_CROP.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MandiocaCropBlock.AGE, 1))
                        )
                    )
                    .add(LootItem.lootTableItem(ModItems.MANDIOCA_CAULE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))) // Para o estágio 2
                        .when(LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(ModBlocks.MANDIOCA_CROP.get())
                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MandiocaCropBlock.AGE, 2))
                        )
                    )
                    .add(LootItem.lootTableItem(ModItems.MANDIOCA_CAULE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))) // Para o estágio 3
                        .when(LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(ModBlocks.MANDIOCA_CROP.get())
                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MandiocaCropBlock.AGE, 3))
                        )
                    )
                    .add(LootItem.lootTableItem(ModItems.MANDIOCA_CAULE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))) // Para o estágio 4
                        .when(LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(ModBlocks.MANDIOCA_CROP.get())
                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MandiocaCropBlock.AGE, 4))
                        )
                    )
                    .add(LootItem.lootTableItem(ModItems.MANDIOCA_CAULE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))) // Para o estágio 5
                        .when(LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(ModBlocks.MANDIOCA_CROP.get())
                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MandiocaCropBlock.AGE, 5))
                        )
                    )
                    .add(LootItem.lootTableItem(ModItems.MANDIOCA_CAULE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))) // Para o estágio 6
                        .when(LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(ModBlocks.MANDIOCA_CROP.get())
                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MandiocaCropBlock.AGE, 6))
                        )
                    )
                )
        );





        this.dropOther(ModBlocks.BLOCO_COM_BURACO.get(), Blocks.DIRT);
        this.dropOther(ModBlocks.PLANTED_MANDIOCA_BLOCK.get(), Blocks.DIRT);

//        this.dropOther(ModBlocks.BOLO_DE_MANDIOCA.get(), Blocks.AIR);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}

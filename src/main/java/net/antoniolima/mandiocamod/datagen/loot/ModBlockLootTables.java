package net.antoniolima.mandiocamod.datagen.loot;

import net.antoniolima.mandiocamod.block.ModBlocks;
import net.antoniolima.mandiocamod.block.custom.CornCropBlock;
import net.antoniolima.mandiocamod.block.custom.MandiocaCropBlock;
import net.antoniolima.mandiocamod.block.custom.StrawberryCropBlock;
import net.antoniolima.mandiocamod.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
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
        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.STRAWBERRY_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StrawberryCropBlock.AGE, 5));

        this.add(ModBlocks.STRAWBERRY_CROP.get(), createCropDrops(ModBlocks.STRAWBERRY_CROP.get(), ModItems.STRAWBERRY.get(),
                ModItems.STRAWBERRY_SEEDS.get(), lootitemcondition$builder));


        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.CORN_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, 7))
                .or(LootItemBlockStatePropertyCondition
                        .hasBlockStateProperties(ModBlocks.CORN_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, 8)));

        this.add(ModBlocks.CORN_CROP.get(), createCropDrops(ModBlocks.CORN_CROP.get(), ModItems.CORN.get(),
                ModItems.CORN_SEEDS.get(), lootitemcondition$builder2));


//        LootItemCondition.Builder lootitemcondition$builder3 = LootItemBlockStatePropertyCondition
//                .hasBlockStateProperties(ModBlocks.MANDIOCA_CROP.get())
//                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MandiocaCropBlock.AGE, 2))
//                .or(LootItemBlockStatePropertyCondition
//                        .hasBlockStateProperties(ModBlocks.MANDIOCA_CROP.get())
//                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MandiocaCropBlock.AGE, 8)));
//
//        this.add(ModBlocks.MANDIOCA_CROP.get(), createCropDrops(ModBlocks.MANDIOCA_CROP.get(), ModItems.MANDIOCA_CRUA.get(),
//                ModItems.MANDIOCA_CAULE.get(), lootitemcondition$builder3));

        this.add(ModBlocks.MANDIOCA_CROP.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.MANDIOCA_CAULE.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))) // Valor base
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))) // Ajusta conforme necessário
                                        .when(LootItemBlockStatePropertyCondition
                                                .hasBlockStateProperties(ModBlocks.MANDIOCA_CROP.get())
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MandiocaCropBlock.AGE, 1))
                                        )
                                )
                        )
        );



        this.dropSelf(ModBlocks.CATMINT.get());
        this.add(ModBlocks.POTTED_CATMINT.get(), createPotFlowerItemTable(ModBlocks.CATMINT.get()));

        this.dropOther(ModBlocks.BL0CO_COM_BURACO.get(), Blocks.DIRT);
        this.dropOther(ModBlocks.PLANTED_MANDIOCA_BLOCK_TESTE.get(), Blocks.DIRT);

        this.dropOther(ModBlocks.BOLO_DE_MANDIOCA.get(), Blocks.AIR);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}

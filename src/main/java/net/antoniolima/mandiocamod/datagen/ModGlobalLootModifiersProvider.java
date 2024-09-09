package net.antoniolima.mandiocamod.datagen;

import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.item.ModItems;
import net.antoniolima.mandiocamod.loot.AddItemModifier;
import net.antoniolima.mandiocamod.loot.AddSusSandItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, MandiocaMod.MOD_ID);
    }

    @Override
    protected void start() {

        add("mandioca_caule_from_suspicious_sand", new AddSusSandItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("archaeology/desert_pyramid")).build() }, ModItems.MANDIOCA_CAULE.get()));

        add("mandioca_caule_from_desert_pyramid", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/desert_pyramid")).build() }, ModItems.MANDIOCA_CAULE.get()));

        add("mandioca_caule_from_jungle_temples", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build() }, ModItems.MANDIOCA_CAULE.get()));

        add("mandioca_caule_from_abandoned_mineshaft", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/abandoned_mineshaft")).build() }, ModItems.MANDIOCA_CAULE.get()));

        add("mandioca_caule_from_simple_dungeon", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/simple_dungeon")).build() }, ModItems.MANDIOCA_CAULE.get()));

        add("mandioca_caule_from_woodland_mansion", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/woodland_mansion")).build() }, ModItems.MANDIOCA_CAULE.get()));



        add("mandioca_caule_from_village_temple", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_temple")).build() }, ModItems.MANDIOCA_CAULE.get()));

        add("mandioca_caule_from_village_armorer", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_armorer")).build() }, ModItems.MANDIOCA_CAULE.get()));

        add("mandioca_caule_from_village_plains_house", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_plains_house")).build() }, ModItems.MANDIOCA_CAULE.get()));

        add("mandioca_caule_from_village_savanna_house", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_savanna_house")).build() }, ModItems.MANDIOCA_CAULE.get()));

        add("mandioca_caule_from_village_mason", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_mason")).build() }, ModItems.MANDIOCA_CAULE.get()));

        add("mandioca_caule_from_village_tannery", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_tannery")).build() }, ModItems.MANDIOCA_CAULE.get()));

        add("mandioca_caule_from_village_fisher", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_fisher")).build() }, ModItems.MANDIOCA_CAULE.get()));
    }
}

package net.antoniolima.mandiocamod.datagen;

import net.antoniolima.mandiocamod.block.ModBlocks;
import net.antoniolima.mandiocamod.item.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.CAVADEIRA.get())
                .pattern("ISI")
                .pattern("S S")
                .pattern("S S")
                .define('S', Items.STICK)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MANDIOCA_DESCASCADA.get())
                .requires(ModItems.MANDIOCA_CRUA.get())
                .requires(ModItems.FACAO.get())
                .unlockedBy(getHasName(ModItems.MANDIOCA_CRUA.get()), has(ModItems.MANDIOCA_CRUA.get()))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MANDIOCA_RALADA.get())
                .requires(ModItems.MANDIOCA_DESCASCADA.get())
                .requires(Items.BOWL)
                .unlockedBy(getHasName(ModItems.MANDIOCA_DESCASCADA.get()), has(ModItems.MANDIOCA_DESCASCADA.get()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModBlocks.BOLO_DE_MANDIOCA.get())
                .pattern("MLM")
                .pattern("SES")
                .pattern("WWW")
                .define('M', ModItems.MANDIOCA_RALADA.get())
                .define('L', Items.MILK_BUCKET)
                .define('S', Items.SUGAR)
                .define('E', Items.EGG)
                .define('W', Items.WHEAT)
                .unlockedBy(getHasName(ModItems.MANDIOCA_DESCASCADA.get()), has(ModItems.MANDIOCA_DESCASCADA.get()))
                .save(pRecipeOutput);

        createTapiocaRecipe(pRecipeOutput, ModItems.TAPIOCA_DE_CARNE.get(), Items.COOKED_BEEF);
        createTapiocaRecipe(pRecipeOutput, ModItems.TAPIOCA_DE_FRANGO.get(), Items.COOKED_CHICKEN);
        createTapiocaRecipe(pRecipeOutput, ModItems.TAPIOCA_DE_PORCO.get(), Items.COOKED_PORKCHOP);
        createTapiocaRecipe(pRecipeOutput, ModItems.TAPIOCA_DE_CARNEIRO.get(), Items.COOKED_MUTTON);
        createTapiocaRecipe(pRecipeOutput, ModItems.TAPIOCA_DE_COELHO.get(), Items.COOKED_RABBIT);
        createTapiocaRecipeWithFish(pRecipeOutput, ModItems.TAPIOCA_DE_PEIXE.get());

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.MANDIOCA_DESCASCADA.get()), RecipeCategory.FOOD, ModItems.MANDIOCA_COZIDA.get(), 0.35F, 200)
            .unlockedBy(getHasName(ModItems.MANDIOCA_DESCASCADA.get()), has(ModItems.MANDIOCA_DESCASCADA.get()))
            .save(pRecipeOutput, "mandiocamod:mandioca_cozida_smelting");

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.MANDIOCA_DESCASCADA.get()), RecipeCategory.FOOD, ModItems.MANDIOCA_COZIDA.get(), 0.35F, 600)
            .unlockedBy(getHasName(ModItems.MANDIOCA_DESCASCADA.get()), has(ModItems.MANDIOCA_DESCASCADA.get()))
            .save(pRecipeOutput, "mandiocamod:mandioca_cozida_campfire_cooking");

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.MANDIOCA_DESCASCADA.get()), RecipeCategory.FOOD, ModItems.MANDIOCA_COZIDA.get(), 0.35F, 100)
                .unlockedBy(getHasName(ModItems.MANDIOCA_DESCASCADA.get()), has(ModItems.MANDIOCA_DESCASCADA.get()))
                .save(pRecipeOutput, "mandiocamod:mandioca_cozida_smoking");
    }

    private void createTapiocaRecipe(RecipeOutput pRecipeOutput, ItemLike tapiocaItem, ItemLike meatIngredient) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, tapiocaItem)
            .pattern("MMM")
            .pattern("CCC")
            .pattern("MMM")
            .define('M', ModItems.MANDIOCA_RALADA.get())
            .define('C', meatIngredient)
            .unlockedBy(getHasName(ModItems.MANDIOCA_RALADA.get()), has(meatIngredient))
            .save(pRecipeOutput);
    }

    private void createTapiocaRecipeWithFish(RecipeOutput pRecipeOutput, ItemLike tapiocaItem) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, tapiocaItem)
            .pattern("MMM")
            .pattern("CCC")
            .pattern("MMM")
            .define('M', ModItems.MANDIOCA_RALADA.get())
            .define('C', Ingredient.of(Items.COOKED_COD, Items.COOKED_SALMON))
            .unlockedBy("has_mandioca_ralada_and_cooked_cod",
                    InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MANDIOCA_RALADA.get(), Items.COOKED_COD))
            .unlockedBy("has_mandioca_ralada_and_cooked_salmon",
                    InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MANDIOCA_RALADA.get(), Items.COOKED_SALMON))
            .save(pRecipeOutput);
    }
}

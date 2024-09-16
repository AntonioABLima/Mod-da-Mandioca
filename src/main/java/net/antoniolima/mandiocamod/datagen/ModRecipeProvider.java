package net.antoniolima.mandiocamod.datagen;

import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.block.ModBlocks;
import net.antoniolima.mandiocamod.item.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.CAVADEIRA.get())
                .pattern("ISI")
                .pattern("S S")
                .pattern("S S")
                .define('S', Items.STICK)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MANDIOCA_DESCASCADA.get())
                .requires(ModItems.MANDIOCA_CRUA.get())
                .requires(ModItems.FACAO.get())
                .unlockedBy(getHasName(ModItems.MANDIOCA_CRUA.get()), has(ModItems.MANDIOCA_CRUA.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MANDIOCA_RALADA.get())
                .requires(ModItems.MANDIOCA_DESCASCADA.get())
                .requires(Items.BOWL)
                .unlockedBy(getHasName(ModItems.MANDIOCA_DESCASCADA.get()), has(ModItems.MANDIOCA_DESCASCADA.get()))
                .save(pWriter);

//        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModBlocks.BOLO_DE_MANDIOCA.get())
//                .pattern("MLM")
//                .pattern("SES")
//                .pattern("WWW")
//                .define('M', ModItems.MANDIOCA_RALADA.get())
//                .define('L', Items.MILK_BUCKET)
//                .define('S', Items.SUGAR)
//                .define('E', Items.EGG)
//                .define('W', Items.WHEAT)
//                .unlockedBy(getHasName(ModItems.MANDIOCA_DESCASCADA.get()), has(ModItems.MANDIOCA_DESCASCADA.get()))
//                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.TAPIOCA_DE_CARNE.get())
                .pattern("MMM")
                .pattern("CCC")
                .pattern("MMM")
                .define('M', ModItems.MANDIOCA_RALADA.get())
                .define('C', Items.COOKED_BEEF)
                .unlockedBy(getHasName(ModItems.MANDIOCA_RALADA.get()), has(Items.COOKED_BEEF))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.TAPIOCA_DE_FRANGO.get())
                .pattern("MMM")
                .pattern("CCC")
                .pattern("MMM")
                .define('M', ModItems.MANDIOCA_RALADA.get())
                .define('C', Items.COOKED_CHICKEN)
                .unlockedBy(getHasName(ModItems.MANDIOCA_RALADA.get()), has(Items.COOKED_CHICKEN))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.TAPIOCA_DE_PORCO.get())
                .pattern("MMM")
                .pattern("CCC")
                .pattern("MMM")
                .define('M', ModItems.MANDIOCA_RALADA.get())
                .define('C', Items.COOKED_PORKCHOP)
                .unlockedBy(getHasName(ModItems.MANDIOCA_RALADA.get()), has(Items.COOKED_PORKCHOP))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.TAPIOCA_DE_CARNEIRO.get())
                .pattern("MMM")
                .pattern("CCC")
                .pattern("MMM")
                .define('M', ModItems.MANDIOCA_RALADA.get())
                .define('C', Items.COOKED_MUTTON)
                .unlockedBy(getHasName(ModItems.MANDIOCA_RALADA.get()), has(Items.COOKED_MUTTON))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.TAPIOCA_DE_PEIXE.get())
                .pattern("MMM")
                .pattern("CCC")
                .pattern("MMM")
                .define('M', ModItems.MANDIOCA_RALADA.get())
                .define('C', Ingredient.of(Items.COOKED_COD, Items.COOKED_SALMON)) // Aceitar qualquer um dos peixes
                .unlockedBy("has_mandioca_ralada_and_cooked_cod",
                        InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MANDIOCA_RALADA.get(), Items.COOKED_COD)) // Mandioca ralada + bacalhau cozido
                .unlockedBy("has_mandioca_ralada_and_cooked_salmon",
                        InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MANDIOCA_RALADA.get(), Items.COOKED_SALMON)) // Mandioca ralada + salmão cozido
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.TAPIOCA_DE_COELHO.get())
                .pattern("MMM")
                .pattern("CCC")
                .pattern("MMM")
                .define('M', ModItems.MANDIOCA_RALADA.get())
                .define('C', Items.COOKED_RABBIT)
                .unlockedBy(getHasName(ModItems.MANDIOCA_RALADA.get()), has(Items.COOKED_RABBIT))
                .save(pWriter);

    }
}

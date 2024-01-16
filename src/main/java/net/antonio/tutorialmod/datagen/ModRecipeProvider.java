package net.antonio.tutorialmod.datagen;

import net.antonio.tutorialmod.Item.ModItems;
import net.antonio.tutorialmod.TutorialMod;
import net.antonio.tutorialmod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider  extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput){
        super(pOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MANDIOCA_BLOCK.get())
                .pattern("MMM")
                .pattern("MMM")
                .pattern("MMM")
                .unlockedBy(getHasName(ModItems.MANDIOCA.get()), has(ModItems.MANDIOCA.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MANDIOCA.get(), 9)
                .unlockedBy(getHasName(ModBlocks.MANDIOCA_BLOCK.get()), has(ModBlocks.MANDIOCA_BLOCK.get()))
                .save(recipeOutput);


    }



}

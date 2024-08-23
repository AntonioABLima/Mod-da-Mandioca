package net.antoniolima.mandiocamod.datagen;

import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.block.ModBlocks;
import net.antoniolima.mandiocamod.block.custom.CornCropBlock;
import net.antoniolima.mandiocamod.block.custom.MandiocaCropBlock;
import net.antoniolima.mandiocamod.block.custom.StrawberryCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MandiocaMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        makeStrawberryCrop((CropBlock) ModBlocks.STRAWBERRY_CROP.get(), "strawberry_stage", "strawberry_stage");
        makeCornCrop(((CropBlock) ModBlocks.CORN_CROP.get()), "corn_stage_", "corn_stage_");

        simpleBlockWithItem(ModBlocks.CATMINT.get(), models().cross(blockTexture(ModBlocks.CATMINT.get()).getPath(),
                blockTexture(ModBlocks.CATMINT.get())).renderType("cutout"));
        simpleBlockWithItem(ModBlocks.POTTED_CATMINT.get(), models().singleTexture("potted_catmint", new ResourceLocation("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.CATMINT.get())).renderType("cutout"));

        simpleBlockWithItem(ModBlocks.BL0CO_COM_BURACO.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/bloco_com_buraco_final")));

        simpleBlockWithItem(ModBlocks.PLANTED_MANDIOCA_BLOCK_TESTE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/planted_mandioca_block")));

        makeMandiocaCrop(((CropBlock) ModBlocks.MANDIOCA_CROP.get()), "mandioca_crop_stage", "mandioca_crop_stage");

    }

    public void makeStrawberryCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> strawberryStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] strawberryStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()),
                new ResourceLocation(MandiocaMod.MOD_ID, "block/" + textureName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    public void makeCornCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> cornStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] cornStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        int age = state.getValue(((CornCropBlock) block).getAgeProperty());

        models[0] = new ConfiguredModel(models().crop(modelName + age,
                new ResourceLocation(MandiocaMod.MOD_ID, "block/" + textureName + age)).renderType("cutout"));

        return models;
    }

    public void makeMandiocaCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> mandiocaCropStates(state, block, modelName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] mandiocaCropStates(BlockState state, CropBlock block, String modelName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        int age = state.getValue(((MandiocaCropBlock) block).getAgeProperty());

        models[0] = new ConfiguredModel(models().getExistingFile(
                new ResourceLocation(MandiocaMod.MOD_ID, "block/" + modelName + "_" + age))
        );

        return models;
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}

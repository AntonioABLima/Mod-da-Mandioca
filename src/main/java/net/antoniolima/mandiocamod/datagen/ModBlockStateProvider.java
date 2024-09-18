package net.antoniolima.mandiocamod.datagen;

import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MandiocaMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        simpleBlockWithItem(ModBlocks.BLOCO_COM_BURACO.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/bloco_com_buraco_final")));

        simpleBlockWithItem(ModBlocks.PLANTED_MANDIOCA_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/planted_mandioca_block")));

//        makeMandiocaCrop(((CropBlock) ModBlocks.MANDIOCA_CROP.get()), "mandioca_crop_stage", "mandioca_crop_stage");

    }


//    public void makeMandiocaCrop(CropBlock block, String modelName, String textureName) {
//        Function<BlockState, ConfiguredModel[]> function = state -> mandiocaCropStates(state, block, modelName);
//
//        getVariantBuilder(block).forAllStates(function);
//    }
//
//    private ConfiguredModel[] mandiocaCropStates(BlockState state, CropBlock block, String modelName) {
//        ConfiguredModel[] models = new ConfiguredModel[1];
//        int age = state.getValue(((MandiocaCropBlock) block).getAgeProperty());
//
//        models[0] = new ConfiguredModel(models().getExistingFile(
//                new ResourceLocation(MandiocaMod.MOD_ID, "block/" + modelName + "_" + age))
//        );
//
//        return models;
//    }
}

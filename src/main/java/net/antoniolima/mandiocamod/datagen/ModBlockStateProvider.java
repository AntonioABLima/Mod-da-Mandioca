package net.antoniolima.mandiocamod.datagen;

import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.block.ModBlocks;
import net.antoniolima.mandiocamod.block.custom.MandiocaCropBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CropBlock;
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

        makeMandiocaCrop((CropBlock) ModBlocks.MANDIOCA_CROP.get(), "mandioca_crop");

        simpleBlockItem(ModBlocks.BOLO_DE_MANDIOCA.get(),
                new ModelFile.UncheckedModelFile("mandiocamod:block/bolo_de_mandioca"));
        makeBolo((CakeBlock) ModBlocks.BOLO_DE_MANDIOCA.get(), "bolo_de_mandioca");
    }

    public void makeMandiocaCrop(CropBlock block, String modelNameBase) {
        Function<BlockState, ConfiguredModel[]> function = state -> mandiocaCropStates(state, block, modelNameBase);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] mandiocaCropStates(BlockState state, CropBlock block, String modelNameBase) {
        int age = state.getValue(MandiocaCropBlock.AGE); // Propriedade "age"
        Direction facing = state.getValue(MandiocaCropBlock.FACING); // Propriedade "facing"

        // Determina o modelo com base no age
        String modelName;
        if (age <= 4) {
            modelName = modelNameBase + "_stage_" + age;
        } else if (age == 5) {
            modelName = modelNameBase + "_stage_5_bottom";
        } else {
            modelName = modelNameBase + "_stage_5_top";
        }

        // Determina a rotação no eixo Y com base no facing
        int yRotation = switch (facing) {
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0; // NORTH ou padrão
        };

        // Retorna o modelo configurado
        return new ConfiguredModel[] {
            new ConfiguredModel(
                models().getExistingFile( ResourceLocation.fromNamespaceAndPath(MandiocaMod.MOD_ID, "block/" + modelName)),
                0, // Rotação no eixo X
                yRotation, // Rotação no eixo Y
                false // UV lock
            )
    };
    }

    public void makeBolo(CakeBlock block, String baseModelName) {
        Function<BlockState, ConfiguredModel[]> function = state -> boloStates(state, baseModelName);
        getVariantBuilder(block).forAllStates(function);


    }

    private ConfiguredModel[] boloStates(BlockState state, String baseModelName) {
        int bites = state.getValue(CakeBlock.BITES);

        // Constrói o nome do modelo correspondente
        String modelName = bites == 0
            ? baseModelName
            : baseModelName + "_slice" + bites;

        // Retorna o modelo configurado
        return new ConfiguredModel[] {
            new ConfiguredModel(
                models().getExistingFile(ResourceLocation.fromNamespaceAndPath(MandiocaMod.MOD_ID, "block/" + modelName))
            )
        };
    }
}

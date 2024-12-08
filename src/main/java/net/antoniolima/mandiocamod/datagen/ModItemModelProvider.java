package net.antoniolima.mandiocamod.datagen;

import com.sun.jna.platform.win32.WinNT;
import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.block.ModBlocks;
import net.antoniolima.mandiocamod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MandiocaMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.CAVADEIRA.get());
        basicItem(ModItems.FACAO.get());

        basicItem(ModItems.MANDIOCA_CAULE.get());
        basicItem(ModItems.MANDIOCA_CRUA.get());
        basicItem(ModItems.MANDIOCA_DESCASCADA.get());
        basicItem(ModItems.MANDIOCA_COZIDA.get());
        basicItem(ModItems.MANDIOCA_RALADA.get());
        basicItem(ModItems.MANDIOCA_DOURADA.get());

        basicItem(ModItems.TAPIOCA_DE_CARNE.get());
        basicItem(ModItems.TAPIOCA_DE_FRANGO.get());
        basicItem(ModItems.TAPIOCA_DE_PORCO.get());
        basicItem(ModItems.TAPIOCA_DE_PEIXE.get());
        basicItem(ModItems.TAPIOCA_DE_COELHO.get());
        basicItem(ModItems.TAPIOCA_DE_CARNEIRO.get());
        makeBoloItem("bolo_de_mandioca");
    }

    private void makeBoloItem(String baseModelName) {
        String itemModelPath = MandiocaMod.MOD_ID + ":item/" + baseModelName;

        getBuilder(baseModelName)
                .parent(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath("minecraft", "item/generated")))
                .texture("layer0", itemModelPath);  // Definir a textura para a camada 0 do item
    }
}

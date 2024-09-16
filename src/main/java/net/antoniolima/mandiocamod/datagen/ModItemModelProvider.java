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

        simpleItem(ModItems.METAL_DETECTOR);
        simpleItem(ModItems.PINE_CONE);
        simpleItem(ModItems.STRAWBERRY);
        simpleItem(ModItems.STRAWBERRY_SEEDS);

        simpleItem(ModItems.CORN);
        simpleItem(ModItems.CORN_SEEDS);

        simpleItem(ModItems.BAR_BRAWL_MUSIC_DISC);

        simpleItem(ModItems.MANDIOCA_CRUA);
        simpleItem(ModItems.CAVADEIRA);
        simpleItem(ModItems.MANDIOCA_CAULE);
        simpleItem(ModItems.MANDIOCA_DESCASCADA);
        simpleItem(ModItems.MANDIOCA_RALADA);
//        simpleItem(ModItems.TAPIOCA_DE_CARNE);
//        simpleItem(ModItems.TAPIOCA_DE_FRANGO);
//        simpleItem(ModItems.TAPIOCA_DE_PORCO);
//        simpleItem(ModItems.TAPIOCA_DE_PEIXE);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MandiocaMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}

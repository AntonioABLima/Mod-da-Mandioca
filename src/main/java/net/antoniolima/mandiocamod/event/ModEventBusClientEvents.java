package net.antoniolima.mandiocamod.event;

import net.antoniolima.mandiocamod.MandiocaMod;
import net.antoniolima.mandiocamod.block.entity.ModBlockEntities;
import net.antoniolima.mandiocamod.block.entity.renderer.BlocoComBuracoEntityRenderer;
import net.antoniolima.mandiocamod.block.entity.renderer.GemPolishingBlockEntityRenderer;
import net.antoniolima.mandiocamod.entity.client.ModModelLayers;
import net.antoniolima.mandiocamod.entity.client.RhinoModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MandiocaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.RHINO_LAYER, RhinoModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.BLOCO_COM_BURACO_BE.get(), BlocoComBuracoEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.GEM_POLISHING_BE.get(), GemPolishingBlockEntityRenderer::new);

    }
}

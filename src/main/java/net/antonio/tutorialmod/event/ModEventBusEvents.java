package net.antonio.tutorialmod.event;

import net.antonio.tutorialmod.TutorialMod;
import net.antonio.tutorialmod.entity.ModEntities;
import net.antonio.tutorialmod.entity.client.RhinoModel;
import net.antonio.tutorialmod.entity.custom.RhinoEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.RHINO.get(), RhinoEntity.createAtrributes().build());
    }
}

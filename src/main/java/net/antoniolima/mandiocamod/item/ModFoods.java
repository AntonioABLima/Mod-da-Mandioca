package net.antoniolima.mandiocamod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties MANDIOCA_DESCASCADA = new FoodProperties.Builder().nutrition(2).build();
    public static final FoodProperties MANDIOCA_COZIDA = new FoodProperties.Builder().nutrition(10).build();
    public static final FoodProperties TAPIOCA_DE_CARNE = new FoodProperties.Builder().nutrition(18).build();
    public static final FoodProperties TAPIOCA_DE_FRANGO = new FoodProperties.Builder().nutrition(18).build();
    public static final FoodProperties TAPIOCA_DE_PORCO = new FoodProperties.Builder().nutrition(18).build();
    public static final FoodProperties TAPIOCA_DE_CARNEIRO = new FoodProperties.Builder().nutrition(18).build();
    public static final FoodProperties TAPIOCA_DE_PEIXE = new FoodProperties.Builder().nutrition(18).build();
    public static final FoodProperties TAPIOCA_DE_COELHO = new FoodProperties.Builder().nutrition(18).build();
    public static final FoodProperties MANDIOCA_DOURADA = (new FoodProperties.Builder())
            .nutrition(4)
            .saturationModifier(1.2F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F)
            .alwaysEdible()
            .build();




}

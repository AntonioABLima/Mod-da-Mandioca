package net.antoniolima.mandiocamod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties STRAWBERRY = new FoodProperties.Builder().nutrition(2).fast()
            .saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.1f).build();

    public static final FoodProperties MANDIOCA_DESCASCADA = new FoodProperties.Builder().nutrition(2).build();

    public static final FoodProperties TAPIOCA_DE_CARNE = new FoodProperties.Builder().nutrition(18).build();
    public static final FoodProperties TAPIOCA_DE_FRANGO = new FoodProperties.Builder().nutrition(18).build();
    public static final FoodProperties TAPIOCA_DE_PORCO = new FoodProperties.Builder().nutrition(18).build();
    public static final FoodProperties TAPIOCA_DE_CARNEIRO = new FoodProperties.Builder().nutrition(18).build();
    public static final FoodProperties TAPIOCA_DE_PEIXE = new FoodProperties.Builder().nutrition(18).build();
    public static final FoodProperties TAPIOCA_DE_COELHO = new FoodProperties.Builder().nutrition(18).build();



}

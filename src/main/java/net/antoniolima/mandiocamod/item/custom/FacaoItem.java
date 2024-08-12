package net.antoniolima.mandiocamod.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FacaoItem extends Item {
    public FacaoItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        // Decrease durability by 1
        ItemStack result = itemStack.copy();
        result.setDamageValue(itemStack.getDamageValue() + 1);

        // Check if the item is broken
        if (result.getDamageValue() >= result.getMaxDamage()) {
            return ItemStack.EMPTY; // Return an empty stack if the item is broken
        }

        return result;
    }

}

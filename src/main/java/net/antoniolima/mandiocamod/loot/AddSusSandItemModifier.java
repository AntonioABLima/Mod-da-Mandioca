package net.antoniolima.mandiocamod.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class AddSusSandItemModifier extends LootModifier {
    public static final Supplier<MapCodec<AddSusSandItemModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec().listOf()
                    .fieldOf("items").forGetter(m -> m.items))
            .apply(inst, AddSusSandItemModifier::new)));

    private final List<Item> items;

    public AddSusSandItemModifier(LootItemCondition[] conditionsIn, List<Item> items) {
        super(conditionsIn);
        this.items = items;
    }

    public AddSusSandItemModifier(LootItemCondition[] conditionsIn, Item... items) {
        this(conditionsIn, Arrays.asList(items));
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> objectArrayList, LootContext arg) {
        for(LootItemCondition condition : this.conditions) {
            if(!condition.test(arg)) return objectArrayList;
        }

        if(arg.getRandom().nextFloat() < 0.5f) { // 50% WAY TOO HIGH!
            objectArrayList.clear();
            objectArrayList.add(new ItemStack((ItemLike) this.items));
        }

        return objectArrayList;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
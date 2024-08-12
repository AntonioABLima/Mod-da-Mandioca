package net.antoniolima.mandiocamod.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MandiocaEstagio1Block extends AbstractMandiocaEstagioBlock{
    public static final VoxelShape SHAPE = Block.box(7.25, 2, 7.25, 8.75, 4.25, 8.75);

    public MandiocaEstagio1Block(Properties pProperties) {
        super(pProperties, SHAPE);
    }
}

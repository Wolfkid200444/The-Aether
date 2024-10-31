package com.aetherteam.aether.block.construction;

import com.aetherteam.aether.blockentity.SkyrootHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SkyrootCeilingHangingSignBlock extends CeilingHangingSignBlock {
    public SkyrootCeilingHangingSignBlock(WoodType type, Properties properties) {
        super(type, properties);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(this);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SkyrootHangingSignBlockEntity(pos, state);
    }
}

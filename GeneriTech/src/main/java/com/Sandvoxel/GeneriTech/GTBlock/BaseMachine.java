package com.Sandvoxel.GeneriTech.GTBlock;

import com.Sandvoxel.GeneriTech.EnumTypes.EnumMachine;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;

import java.util.Random;


public class BaseMachine extends DirectionalMachine {

    public static final PropertyEnum ONOFF = PropertyEnum.create("onoff", EnumMachine.class);



    public BaseMachine(Material blockMaterial, SoundType stepSound, CreativeTabs tab) {
        super(blockMaterial, stepSound, tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ONOFF, EnumMachine.OFF));
    }


    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (!worldIn.isRemote) {
            if (!worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, EnumMachine.OFF).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            } else if (worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, EnumMachine.ON).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            }
        }
    }

    /**
     * Called when a neighboring block changes.
     */
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (!worldIn.isRemote) {
            if (!worldIn.isBlockPowered(pos)) {
                worldIn.scheduleUpdate(pos, this, 4);
            } else if (worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, EnumMachine.ON).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            }
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (!worldIn.isRemote) {
            if (!worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, EnumMachine.OFF).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            }

        }
    }






    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ONOFF,FACING);
    }



    @Override
    public int getMetaFromState(IBlockState state)
    {
        EnumMachine type = (EnumMachine) state.getValue(ONOFF);

        int i = 0;

        i = i | state.getValue(FACING).getHorizontalIndex();

        if (state.getValue(ONOFF) == EnumMachine.ON){
            i |= 4;
        }


        return i;
    }
    @Override
    public IBlockState getStateFromMeta(int meta ) {
        return getDefaultState().withProperty(ONOFF,(meta & 4) > 0 ? EnumMachine.ON : EnumMachine.OFF ).withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

}
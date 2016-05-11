package com.Sandvoxel.GeneriTech.RendersGT;

import com.Sandvoxel.GeneriTech.GTBlock.GTBlocks;
import com.Sandvoxel.GeneriTech.Misc.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;


public class Blocks {


    public static void registerRenders(){
        registerRender(GTBlocks.pulverizer, "pulverizer");
    }

    public static void registerRender(Block blockIn, String resourceLocation){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(blockIn), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + resourceLocation, "inventory"));
    }
}

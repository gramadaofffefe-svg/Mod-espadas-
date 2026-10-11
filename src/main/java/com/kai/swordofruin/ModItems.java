package com.kai.swordofruin;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
    public static Item swordOfRuin;

    public static final ToolMaterial RUIN_MATERIAL = EnumHelper.addToolMaterial(
        "RUIN", 3, 2500, 10.0F, 5.0F, 22
    );

    public static void init() {
        swordOfRuin = new SwordOfRuinItem(RUIN_MATERIAL);
        swordOfRuin.setUnlocalizedName("sword_of_ruin");
        swordOfRuin.setCreativeTab(CreativeTabs.tabCombat);
        GameRegistry.registerItem(swordOfRuin, "sword_of_ruin");
    }
}

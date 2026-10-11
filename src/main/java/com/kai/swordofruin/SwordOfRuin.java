package com.kai.swordofruin;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SwordOfRuin.MODID, version = SwordOfRuin.VERSION)
public class SwordOfRuin {
    public static final String MODID = "swordofruin";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();
    }
}

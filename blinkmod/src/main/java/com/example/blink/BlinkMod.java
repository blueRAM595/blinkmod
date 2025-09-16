package com.example.blink;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class BlinkMod implements ClientModInitializer {
    public static KeyBinding toggleKey;
    public static boolean enabled = false;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.blink.toggle",
                GLFW.GLFW_KEY_G,
                "category.blink"
        ));

        BlinkKeyHandler.register();
    }
}

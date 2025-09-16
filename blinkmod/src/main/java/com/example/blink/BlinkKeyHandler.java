package com.example.blink;

import com.example.blink.mixin.BlinkMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class BlinkKeyHandler {
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (BlinkMod.toggleKey.wasPressed()) {
                BlinkMod.enabled = !BlinkMod.enabled;
                if (!BlinkMod.enabled) {
                    if (client.getNetworkHandler() != null) {
                        ((BlinkMixin)(Object)client.getNetworkHandler().getConnection()).flushPackets();
                    }
                }
            }
        });
    }
}

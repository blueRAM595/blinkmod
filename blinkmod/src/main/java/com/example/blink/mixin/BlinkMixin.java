package com.example.blink.mixin;

import com.example.blink.BlinkMod;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ClientConnection.class)
public class BlinkMixin {
    private final List<Packet<?>> delayedPackets = new ArrayList<>();

    @Shadow private boolean disconnected;

    @Inject(method = "send(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSend(Packet<?> packet, CallbackInfo ci) {
        if (BlinkMod.enabled && !disconnected) {
            delayedPackets.add(packet);
            ci.cancel();
        }
    }

    public void flushPackets() {
        if (!delayedPackets.isEmpty()) {
            for (Packet<?> p : delayedPackets) {
                ((ClientConnection)(Object)this).send(p);
            }
            delayedPackets.clear();
        }
    }
}

package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.AudioHooks;
import com.aetherteam.aether.fabric.events.CancellableCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;

public class AudioListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        //SoundEngineMixin.aetherFabric$adjustSoundInstance -> AudioListener.onPlaySound
        ClientTickEvents.END_CLIENT_TICK.register(client -> onClientTick());
        //ClientPacketListenerMixin.aetherFabric$onPlayerClone -> AudioListener.onPlayerRespawn
    }

    /**
     * @see AudioHooks#shouldCancelMusic(SoundInstance)
     */
    public static void onPlaySound(SoundEngine soundEngine, SoundInstance sound, CancellableCallback callback) {
        if (AudioHooks.shouldCancelMusic(sound) || AudioHooks.preventAmbientPortalSound(soundEngine, sound)) {
            callback.setCanceled(true);
        }
        AudioHooks.overrideActivatedPortalSound(soundEngine, sound);
    }

    /**
     * @see AudioHooks#tick()
     */
    public static void onClientTick() {
        AudioHooks.tick();
    }

    /**
     * @see AudioHooks#stop()
     */
    public static void onPlayerRespawn() {
        AudioHooks.stop();
    }
}

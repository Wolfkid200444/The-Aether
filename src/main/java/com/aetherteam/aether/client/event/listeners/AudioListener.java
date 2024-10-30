package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.AudioHooks;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.resources.sounds.SoundInstance;

public class AudioListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        // TODO: [Fabric Porting] IMPLEMENT EVENTS FOR THIS
        //bus.addListener(AudioListener::onPlaySound);
        ClientTickEvents.END_CLIENT_TICK.register(client -> onClientTick());
        //bus.addListener(AudioListener::onPlayerRespawn);
    }

    /**
     * @see AudioHooks#shouldCancelMusic(SoundInstance)
     */
//    public static void onPlaySound(PlaySoundEvent event) {
//        SoundEngine soundEngine = event.getEngine();
//        SoundInstance sound = event.getOriginalSound();
//        if (AudioHooks.shouldCancelMusic(sound) || AudioHooks.preventAmbientPortalSound(soundEngine, sound)) {
//            event.setSound(null);
//        }
//        AudioHooks.overrideActivatedPortalSound(soundEngine, sound);
//    }

    /**
     * @see AudioHooks#tick()
     */
    public static void onClientTick() {
        AudioHooks.tick();
    }

    /**
     * @see AudioHooks#stop()
     */
//    public static void onPlayerRespawn(ClientPlayerNetworkEvent.Clone event) {
//        AudioHooks.stop();
//    }
}

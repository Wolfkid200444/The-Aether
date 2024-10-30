package com.aetherteam.aether.fabric.events;

public interface CancellableCallback {

    boolean isCanceled();

    void setCanceled(boolean value);
}

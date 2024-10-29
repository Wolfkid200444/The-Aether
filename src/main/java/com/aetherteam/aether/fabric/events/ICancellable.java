package com.aetherteam.aether.fabric.events;

public interface ICancellable {

    boolean isCanceled();

    void setCanceled(boolean value);
}

package com.aetherteam.aetherfabric.events;

public interface CancellableCallback {

    boolean isCanceled();

    void setCanceled(boolean value);
}

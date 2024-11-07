package com.aetherteam.aetherfabric.events;

public class CancellableCallbackImpl implements CancellableCallback {

    public CancellableCallbackImpl() {}

    private boolean isCancelled = false;

    public boolean isCanceled() {
        return this.isCancelled;
    }

    public void setCanceled(boolean value) {
        this.isCancelled = value;
    }
}

package com.aetherteam.aether.fabric.events;

public abstract class Cancellable implements ICancellable {

    protected Cancellable() {}

    private boolean isCancelled = false;

    public boolean isCanceled() {
        return this.isCancelled;
    }

    public void setCanceled(boolean value) {
        this.isCancelled = value;
    }
}

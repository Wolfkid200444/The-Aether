package com.aetherteam.aether.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;

import java.util.function.Consumer;

/**
 * Fired on {@link PackRepository} creation to allow mods to add new pack finders.
 */
public class AddPackFindersEvent {
    private final PackType packType;
    private final Consumer<RepositorySource> sources;

    protected AddPackFindersEvent(PackType packType, Consumer<RepositorySource> sources) {
        this.packType = packType;
        this.sources = sources;
    }

    /**
     * Adds a new source to the list of pack finders.
     *
     * @param source the pack finder
     */
    public void addRepositorySource(RepositorySource source) {
        sources.accept(source);
    }

    /**
     * @return the {@link PackType} of the pack repository being constructed.
     */
    public PackType getPackType() {
        return packType;
    }

    public void sendEvent() {
        EVENT.invoker().findPacks(this);
    }

    //--

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback c : callbacks) c.findPacks(event);
    });

    public interface Callback {
        void findPacks(AddPackFindersEvent event);
    }

    public static AddPackFindersEvent invokeEvent(PackType packType, Consumer<RepositorySource> sources) {
        var event = new AddPackFindersEvent(packType, sources);

        EVENT.invoker().findPacks(event);

        return event;
    }
}

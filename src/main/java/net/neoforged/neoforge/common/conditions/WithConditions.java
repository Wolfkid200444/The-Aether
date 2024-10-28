package net.neoforged.neoforge.common.conditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import org.apache.commons.lang3.Validate;

public record WithConditions<A>(List<ResourceCondition> conditions, A carrier) {

    public static <A> Codec<Optional<WithConditions<A>>> createCodec(Codec<A> codec) {
        var conditionMapCodec = ResourceCondition.CONDITION_CODEC.fieldOf(ResourceConditions.CONDITIONS_KEY);
    }

    public WithConditions(A carrier, ResourceCondition... conditions) {
        this(List.of(conditions), carrier);
    }

    public WithConditions(A carrier) {
        this(List.of(), carrier);
    }

    public static <A> Builder<A> builder(A carrier) {
        return new Builder<A>().withCarrier(carrier);
    }
    public static class Builder<T> {
        private final List<ResourceCondition> conditions = new ArrayList<>();
        private T carrier;

        public Builder<T> addCondition(ResourceCondition... condition) {
            this.conditions.addAll(List.of(condition));
            return this;
        }

        public Builder<T> addCondition(Collection<ResourceCondition> conditions) {
            this.conditions.addAll(conditions);
            return this;
        }

        public Builder<T> withCarrier(T carrier) {
            this.carrier = carrier;
            return this;
        }

        public WithConditions<T> build() {
            Validate.notNull(this.carrier, "You need to supply a carrier to create a WithConditions");
            Validate.notEmpty(this.conditions, "You need to supply at least one condition to create a WithConditions");

            return new WithConditions<>(this.conditions, this.carrier);
        }
    }
}

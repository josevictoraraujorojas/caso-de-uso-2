package org.example.casodeuso2.model;

import jakarta.annotation.Nullable;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Objects;

@Node
public class LimiteAmbiental {
    @Id
    @GeneratedValue
    private @Nullable Long id;
    private float valorMin;
    private float valorMax;

    public LimiteAmbiental() {
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public float getValorMin() {
        return valorMin;
    }

    public void setValorMin(float valorMin) {
        this.valorMin = valorMin;
    }

    public float getValorMax() {
        return valorMax;
    }

    public void setValorMax(float valorMax) {
        this.valorMax = valorMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LimiteAmbiental that = (LimiteAmbiental) o;
        return id!=null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

package com.legindus.shoplyft.firebase.utils;

public abstract class ModelBuilder {
    public static abstract class Builder<T extends Builder<T, V>, V> {
        public abstract V build();
    }
}

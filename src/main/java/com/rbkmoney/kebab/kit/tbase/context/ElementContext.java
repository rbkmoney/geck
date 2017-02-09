package com.rbkmoney.kebab.kit.tbase.context;

/**
 * Created by tolkonepiu on 09/02/2017.
 */
public interface ElementContext {

    default boolean isTBaseElementContext() {
        return this instanceof TBaseElementContext;
    }

    default boolean isCollectionElementContext() {
        return this instanceof CollectionElementContext;
    }

    default boolean isMapElementContext() {
        return this instanceof MapElementContext;
    }

    default boolean isMapKeyElementContext() {
        return this instanceof MapKeyElementContext;
    }

    default boolean isMapValueElementContext() {
        return this instanceof MapValueElementContext;
    }

}

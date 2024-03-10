package com.example.interpreter.model.values;

import com.example.interpreter.model.types.RefType;
import com.example.interpreter.model.types.Type;

public class RefValue implements Value{
    private final int address;
    private final Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }
    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", address, locationType);
    }
}
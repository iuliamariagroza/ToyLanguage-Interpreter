package com.example.interpreter.model.values;

import com.example.interpreter.model.types.Type;

public interface Value {
    Type getType();
    Value deepCopy();
}
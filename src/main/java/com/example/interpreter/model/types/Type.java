package com.example.interpreter.model.types;

import com.example.interpreter.model.values.Value;

public interface Type {
    boolean equals(Type anotherType);
    Value defaultValue();
    Type deepCopy();
}

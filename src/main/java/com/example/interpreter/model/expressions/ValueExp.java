package com.example.interpreter.model.expressions;

import com.example.interpreter.exceptions.ExpressionEvaluationException;
import com.example.interpreter.model.types.Type;
import com.example.interpreter.model.ADTS.MyIDictionary;
import com.example.interpreter.model.ADTS.MyIHeap;
import com.example.interpreter.model.values.Value;

public class ValueExp implements Exp {
    Value value;

    public ValueExp(Value value) {
        this.value = value;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionEvaluationException {
        return value.getType();
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap heap) {
        return this.value;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}


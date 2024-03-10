package com.example.interpreter.model.expressions;

import com.example.interpreter.exceptions.ADTException;
import com.example.interpreter.exceptions.ExpressionEvaluationException;
import com.example.interpreter.model.types.Type;
import com.example.interpreter.model.ADTS.MyIDictionary;
import com.example.interpreter.model.ADTS.MyIHeap;
import com.example.interpreter.model.values.Value;

public class VarExp implements Exp {
    String key;

    public VarExp(String key) {
        this.key = key;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionEvaluationException, ADTException {
        return typeEnv.lookUp(key);
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap heap) throws ADTException {
        return table.lookUp(key);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(key);
    }

    @Override
    public String toString() {
        return key;
    }
}

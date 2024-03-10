package com.example.interpreter.model.expressions;

import com.example.interpreter.exceptions.ADTException;
import com.example.interpreter.exceptions.DivisionByZero;
import com.example.interpreter.exceptions.ExpressionEvaluationException;
import com.example.interpreter.model.types.RefType;
import com.example.interpreter.model.types.Type;
import com.example.interpreter.model.ADTS.MyIDictionary;
import com.example.interpreter.model.ADTS.MyIHeap;
import com.example.interpreter.model.values.RefValue;
import com.example.interpreter.model.values.Value;

public class ReadHeapExp implements Exp {
    private final Exp expression;

    public ReadHeapExp(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionEvaluationException, ADTException {
        Type type = expression.typeCheck(typeEnv);
        if (type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        } else
            throw new ExpressionEvaluationException("The rH argument is not a RefType.");
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symTable, MyIHeap heap) throws ExpressionEvaluationException, ADTException, DivisionByZero {
        Value value = expression.evaluate(symTable, heap);
        if (value instanceof RefValue) {
            RefValue refValue = (RefValue) value;
            if (heap.containsKey(refValue.getAddress()))
                return heap.get(refValue.getAddress());
            else
                throw new ExpressionEvaluationException("The address is not defined on the heap!");
        } else
            throw new ExpressionEvaluationException(String.format("%s not of RefType", value));
    }

    @Override
    public Exp deepCopy() {
        return new ReadHeapExp(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("ReadHeap(%s)", expression);
    }
}
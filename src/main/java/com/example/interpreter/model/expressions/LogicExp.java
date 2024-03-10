package com.example.interpreter.model.expressions;


import com.example.interpreter.exceptions.ADTException;
import com.example.interpreter.exceptions.DivisionByZero;
import com.example.interpreter.exceptions.ExpressionEvaluationException;
import com.example.interpreter.model.types.BoolType;
import com.example.interpreter.model.types.Type;
import com.example.interpreter.model.ADTS.MyIDictionary;
import com.example.interpreter.model.ADTS.MyIHeap;
import com.example.interpreter.model.values.BoolValue;
import com.example.interpreter.model.values.Value;

import java.util.Objects;

public class LogicExp implements Exp {
    Exp expression1;
    Exp expression2;
    String operation;

    public LogicExp(String operation, Exp expression1, Exp expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionEvaluationException, ADTException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        type2 = expression2.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new ExpressionEvaluationException("Second operand is not a boolean.");
        } else
            throw new ExpressionEvaluationException("First operand is not a boolean.");

    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionEvaluationException, ADTException, DivisionByZero {
        Value value1, value2;
        value1 = this.expression1.evaluate(table, heap);
        if (value1.getType().equals(new BoolType())) {
            value2 = this.expression2.evaluate(table, heap);
            if (value2.getType().equals(new BoolType())) {
                BoolValue bool1 = (BoolValue) value1;
                BoolValue bool2 = (BoolValue) value2;
                boolean b1, b2;
                b1 = bool1.getValue();
                b2 = bool2.getValue();
                if (Objects.equals(this.operation, "&&")) {
                    return new BoolValue(b1 && b2);
                } else if (Objects.equals(this.operation, "||")) {
                    return new BoolValue(b1 || b2);
                }
            } else {
                throw new ExpressionEvaluationException("Second operand is not a boolean.");
            }
        } else {
            throw new ExpressionEvaluationException("First operand is not a boolean.");
        }
        return null;
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(operation, expression1.deepCopy(), expression2.deepCopy());
    }
}



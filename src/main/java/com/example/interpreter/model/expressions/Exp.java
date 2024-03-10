package com.example.interpreter.model.expressions;

import com.example.interpreter.exceptions.ADTException;
import com.example.interpreter.exceptions.DivisionByZero;
import com.example.interpreter.exceptions.ExpressionEvaluationException;
import com.example.interpreter.model.types.Type;
import com.example.interpreter.model.ADTS.MyIDictionary;
import com.example.interpreter.model.ADTS.MyIHeap;
import com.example.interpreter.model.values.Value;


public interface Exp {
    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionEvaluationException, ADTException;
    Value evaluate(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionEvaluationException, ADTException, DivisionByZero;
    Exp deepCopy();
}

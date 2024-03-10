package com.example.interpreter.model.statements;

import com.example.interpreter.exceptions.ADTException;
import com.example.interpreter.exceptions.DivisionByZero;
import com.example.interpreter.exceptions.ExpressionEvaluationException;
import com.example.interpreter.exceptions.StatementExecutionException;
import com.example.interpreter.model.expressions.Exp;
import com.example.interpreter.model.programState.ProgramState;
import com.example.interpreter.model.types.RefType;
import com.example.interpreter.model.types.Type;
import com.example.interpreter.model.ADTS.MyIDictionary;
import com.example.interpreter.model.ADTS.MyIHeap;
import com.example.interpreter.model.values.RefValue;
import com.example.interpreter.model.values.Value;

public class WriteHeap implements IStmt {
    private final String varName;
    private final Exp expression;

    public WriteHeap(String varName, Exp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException, DivisionByZero {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (symTable.isDefined(varName)) {
            Value value = symTable.lookUp(varName);
            if (value.getType() instanceof RefType) {
                RefValue refValue = (RefValue) value;
                if (heap.containsKey(refValue.getAddress())) {
                    Value evaluated = expression.evaluate(symTable, heap);
                    if (evaluated.getType().equals(refValue.getLocationType())) {
                        heap.update(refValue.getAddress(), evaluated);
                        state.setHeap(heap);
                    } else
                        throw new StatementExecutionException(String.format("Write heap (expression %s type does not match %s type)", evaluated, refValue.getLocationType()));
                } else
                    throw new StatementExecutionException(String.format("Write heap (Ref value %s not defined)", value));
            } else
                throw new StatementExecutionException(String.format("Write heap (%s must be RefType)", value));
        } else
            throw new StatementExecutionException(String.format("Write heap (%s not present in the heap table)", varName));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        if (typeEnv.lookUp(varName).equals(new RefType(expression.typeCheck(typeEnv))))
            return typeEnv;
        else
            throw new StatementExecutionException("WriteHeap: right hand side and left hand side have different types.");
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeap(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varName, expression);
    }
}
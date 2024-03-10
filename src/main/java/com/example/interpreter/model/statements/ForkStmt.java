package com.example.interpreter.model.statements;

import com.example.interpreter.exceptions.ADTException;
import com.example.interpreter.exceptions.ExpressionEvaluationException;
import com.example.interpreter.exceptions.StatementExecutionException;
import com.example.interpreter.model.programState.ProgramState;
import com.example.interpreter.model.types.Type;
import com.example.interpreter.model.ADTS.MyDictionary;
import com.example.interpreter.model.ADTS.MyIDictionary;
import com.example.interpreter.model.ADTS.MyIStack;
import com.example.interpreter.model.ADTS.MyStack;
import com.example.interpreter.model.values.Value;

import java.util.Map;

public class ForkStmt implements IStmt {
    private final IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        MyIStack<IStmt> newStack = new MyStack<>();
        newStack.push(statement);
        MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
        for (Map.Entry<String, Value> entry: state.getSymTable().getContent().entrySet()) {
            newSymTable.put(entry.getKey(), entry.getValue().deepCopy());
        }

        return new ProgramState(newStack, newSymTable, state.getOut(), state.getFileTable(), state.getHeap());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Fork(%s)", statement.toString());
    }
}
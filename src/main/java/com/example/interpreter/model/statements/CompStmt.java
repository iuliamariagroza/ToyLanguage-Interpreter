package com.example.interpreter.model.statements;

import com.example.interpreter.exceptions.ADTException;
import com.example.interpreter.exceptions.ExpressionEvaluationException;
import com.example.interpreter.exceptions.StatementExecutionException;
import com.example.interpreter.model.programState.ProgramState;
import com.example.interpreter.model.types.Type;
import com.example.interpreter.model.ADTS.MyIDictionary;
import com.example.interpreter.model.ADTS.MyIStack;


public class CompStmt implements IStmt {
    private final IStmt firstStatement;
    private final IStmt secondStatement;

    public CompStmt(IStmt firstStatement, IStmt secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState state){
        MyIStack<IStmt> stack = state.getExeStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        return secondStatement.typeCheck(firstStatement.typeCheck(typeEnv));
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(firstStatement.deepCopy(), secondStatement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(%s|%s)", firstStatement.toString(), secondStatement.toString());
    }
}
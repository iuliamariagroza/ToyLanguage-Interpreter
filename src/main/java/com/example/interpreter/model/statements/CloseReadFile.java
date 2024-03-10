package com.example.interpreter.model.statements;

import com.example.interpreter.exceptions.ADTException;
import com.example.interpreter.exceptions.DivisionByZero;
import com.example.interpreter.exceptions.ExpressionEvaluationException;
import com.example.interpreter.exceptions.StatementExecutionException;
import com.example.interpreter.model.expressions.Exp;
import com.example.interpreter.model.programState.ProgramState;
import com.example.interpreter.model.types.StringType;
import com.example.interpreter.model.types.Type;
import com.example.interpreter.model.ADTS.MyIDictionary;
import com.example.interpreter.model.values.StringValue;
import com.example.interpreter.model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements IStmt {
    private final Exp expression;

    public CloseReadFile(Exp expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException, DivisionByZero {
        Value value = expression.evaluate(state.getSymTable(), state.getHeap());

        if (value.getType().equals(new StringType())) {

            StringValue fileName = (StringValue) value;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();

            if (fileTable.isDefined(fileName.getValue())) {
                BufferedReader br = fileTable.lookUp(fileName.getValue());
                try {
                    br.close();
                } catch (IOException e) {
                    throw new StatementExecutionException(String.format("CloseFile: Unexpected error in closing %s", value));
                }
                fileTable.remove(fileName.getValue());
                state.setFileTable(fileTable);
            } else
                throw new StatementExecutionException(String.format("CloseFile: %s is not present in the FileTable", value));
        } else
            throw new StatementExecutionException(String.format("CloseFile: %s does not evaluate to StringValue", expression));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        if (expression.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new StatementExecutionException("CloseReadFile requires a string expression.");
    }

    @Override
    public IStmt deepCopy() {
        return new CloseReadFile(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("CloseReadFile(%s)", expression.toString());
    }
}
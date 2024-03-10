package com.example.interpreter.model.statements;

import com.example.interpreter.exceptions.ADTException;
import com.example.interpreter.exceptions.DivisionByZero;
import com.example.interpreter.exceptions.ExpressionEvaluationException;
import com.example.interpreter.exceptions.StatementExecutionException;
import com.example.interpreter.model.expressions.Exp;
import com.example.interpreter.model.expressions.RelationalExp;
import com.example.interpreter.model.programState.ProgramState;
import com.example.interpreter.model.types.Type;
import com.example.interpreter.model.ADTS.MyIDictionary;
import com.example.interpreter.model.ADTS.MyIStack;

public class SwitchStmt implements IStmt {
    final Exp mainExp;
    final Exp exp1;
    final IStmt stmt1;
    final Exp exp2;
    final IStmt stmt2;
    final IStmt stmt3;

    public SwitchStmt(Exp main, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3)
    {
        this.mainExp = main;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementExecutionException, ExpressionEvaluationException, ADTException, DivisionByZero, InterruptedException {
        MyIStack<IStmt> stack = state.getExeStack();
        IStmt ifStmt = new IfStmt(new RelationalExp("==", mainExp, exp1), stmt1,
                           new IfStmt(new RelationalExp("==", mainExp, exp2), stmt2,
                                      stmt3));
        stack.push(ifStmt);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        Type maintype = mainExp.typeCheck(typeEnv);
        Type type1 = exp1.typeCheck(typeEnv);
        Type type2 = exp2.typeCheck(typeEnv);

        if(maintype.equals(type1) && maintype.equals(type2))
        {
            stmt1.typeCheck(typeEnv.deepCopy());
            stmt2.typeCheck(typeEnv.deepCopy());
            stmt3.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new StatementExecutionException("Expression types must match in switch");
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(mainExp.deepCopy(), exp1.deepCopy(), stmt1.deepCopy(), exp2.deepCopy(), stmt2.deepCopy(), stmt3.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("switch(%s){ (case %s: %s)(case %s: %s)(default: %s) }", mainExp, exp1, stmt1, exp2, stmt2, stmt3);
    }
}

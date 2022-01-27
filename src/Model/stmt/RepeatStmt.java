package Model.stmt;

import Exceptions.ExpressionEvaluationException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.exp.LogicExp;
import Model.exp.ValueExp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;

import java.util.Objects;

public class RepeatStmt implements IStmt{
    IStmt stmt;
    Exp exp;

    public RepeatStmt(IStmt s, Exp e) {
        stmt = s;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if (!Objects.equals(exp.eval(state.getSymTable(), state.getHeap()).getType().toString(), "bool"))
            throw new ExpressionEvaluationException("Expression must be boolean");
        BoolValue e = (BoolValue) exp.eval(state.getSymTable(), state.getHeap());
        if (!e.getValue()) {
            state.pushToStack(new WhileStmt(new LogicExp("!", exp, exp), stmt));
        }
        state.pushToStack(stmt);
        return null;
    }

    @Override
    public String toString() {
        return "repeat " + stmt.toString() + " until " + exp.toString();
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new Exception("The condition of REPEAT has not the type bool");
    }
}

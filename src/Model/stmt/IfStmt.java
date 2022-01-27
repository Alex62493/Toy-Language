package Model.stmt;

import Exceptions.ExpressionEvaluationException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;

import java.util.Objects;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt stmt1, stmt2;

    public IfStmt(Exp e, IStmt s1, IStmt s2) {
        exp = e;
        stmt1 = s1;
        stmt2 = s2;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if (!Objects.equals(exp.eval(state.getSymTable(), state.getHeap()).getType().toString(), "bool"))
            throw new ExpressionEvaluationException("Expression must be boolean");
        BoolValue e = (BoolValue) exp.eval(state.getSymTable(), state.getHeap());
        if (e.getValue())
            state.pushToStack(stmt1);
        else
            state.pushToStack(stmt2);
        return null;
    }

    @Override
    public String toString() {
        return "If " + exp.toString() + " then " + stmt1.toString() + ", else " + stmt2.toString();
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt1.typecheck(typeEnv);
            stmt2.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new Exception("The condition of IF does not have the type bool");
    }
}

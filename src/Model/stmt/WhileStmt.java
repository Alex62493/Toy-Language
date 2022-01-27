package Model.stmt;

import Exceptions.ExpressionEvaluationException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;

import java.util.Objects;

public class WhileStmt implements IStmt {
    Exp exp;
    IStmt stmt1;

    public WhileStmt(Exp e, IStmt s1) {
        exp = e;
        stmt1 = s1;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if (!Objects.equals(exp.eval(state.getSymTable(), state.getHeap()).getType().toString(), "bool"))
            throw new ExpressionEvaluationException("Expression must be boolean");
        BoolValue e = (BoolValue) exp.eval(state.getSymTable(), state.getHeap());
        if (e.getValue()) {
            state.pushToStack(new WhileStmt(exp, stmt1));
            state.pushToStack(stmt1);
        }
        return null;
    }

    @Override
    public String toString() {
        return "While " + exp.toString() + " do " + stmt1.toString();
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt1.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new Exception("The condition of WHIlE has not the type bool");
    }
}

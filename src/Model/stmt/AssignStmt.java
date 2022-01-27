package Model.stmt;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;

import java.util.Objects;

public class AssignStmt implements IStmt{
    String id;
    Exp ce;

    public AssignStmt(String i, Exp c) {
        id = i;
        ce = c;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if (state.getSymTable().lookup(id) == null)
            throw new ADTException("Variable doesn't exist!");

        if (!Objects.equals(ce.eval(state.getSymTable(), state.getHeap()).getType().toString(), state.getSymTable().lookup(id).getType().toString()))
            throw new ExpressionEvaluationException("Types don't match!");
        state.getSymTable().update(id, ce.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public String toString() {
        return id + "=" + ce.toString();
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typevar = typeEnv.lookup(id);
        IType typexp = ce.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new Exception("Assignment: right hand side and left hand side have different types ");
    }
}

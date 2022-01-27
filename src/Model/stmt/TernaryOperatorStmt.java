package Model.stmt;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;

import java.util.Objects;

public class TernaryOperatorStmt implements IStmt {
    Exp exp1, exp2, exp3;
    String id;

    public TernaryOperatorStmt(String v, Exp e1, Exp e2, Exp e3)
    {
        id = v;
        exp1 = e1;
        exp2 = e2;
        exp3 = e3;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if (!Objects.equals(exp1.eval(state.getSymTable(), state.getHeap()).getType().toString(), "bool"))
            throw new ExpressionEvaluationException("Expression must be boolean");
        BoolValue e = (BoolValue) exp1.eval(state.getSymTable(), state.getHeap());
        if (state.getSymTable().lookup(id) == null)
            throw new ADTException("Variable doesn't exist!");
        if (e.getValue()) {
            if (!Objects.equals(exp2.eval(state.getSymTable(), state.getHeap()).getType().toString(), state.getSymTable().lookup(id).getType().toString()))
                throw new ExpressionEvaluationException("Types don't match!");
            state.getSymTable().update(id, exp2.eval(state.getSymTable(), state.getHeap()));
        }
        else {
            if (!Objects.equals(exp3.eval(state.getSymTable(), state.getHeap()).getType().toString(), state.getSymTable().lookup(id).getType().toString()))
                throw new ExpressionEvaluationException("Types don't match!");
            state.getSymTable().update(id, exp3.eval(state.getSymTable(), state.getHeap()));
        }
        return null;
    }

    @Override
    public String toString() {
        return id + " = " + exp1.toString() + "?" + exp2.toString() + ";" + exp3.toString();
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typevar = typeEnv.lookup(id);
        IType typexp1 = exp1.typecheck(typeEnv);
        IType typexp2 = exp2.typecheck(typeEnv);
        IType typexp3 = exp3.typecheck(typeEnv);
        if (typexp1.equals(new BoolType())) {
            if (typevar.equals(typexp2))
                if (typevar.equals(typexp3))
                    return typeEnv;
                else
                    throw new Exception("Assignment: right hand side and the false expression have different types ");
            else
                throw new Exception("Assignment: right hand side and the true expression have different types ");
        }
        else
            throw new Exception("The condition of the TERNARY OPERATOR does not have the type bool");
    }
}

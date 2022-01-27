package Model.stmt;

import Exceptions.ExpressionEvaluationException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.exp.RelExp;
import Model.exp.VarExp;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IntValue;

import java.util.Objects;

public class ForStmt implements IStmt {
    String id;
    Exp exp1, exp2, exp3;
    IStmt stmt;

    public ForStmt(String v, Exp e1, Exp e2, Exp e3, IStmt s) {
        id = v;
        exp1 = e1;
        exp2 = e2;
        exp3 = e3;
        stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if (state.getSymTable().isDefined(id))
            throw new ExpressionEvaluationException("Variable already exists");
        state.pushToStack(new CompStmt(new VarDeclStmt(id, new IntType()), new CompStmt(new AssignStmt(id, exp1), new WhileStmt(new RelExp("<", new VarExp(id), exp2), new CompStmt(stmt, new AssignStmt(id, exp3))))));
        return null;
    }

    @Override
    public String toString() {
        return "for(" + id + " = " + exp1.toString() + "; " + id + " < " + exp2.toString() + "; " + id + " = " + exp3.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        if (typeEnv.isDefined(id))
            throw new ExpressionEvaluationException("Variable already exists");
        typeEnv.add(id, new IntType());
        IType typexp1 = exp1.typecheck(typeEnv);
        IType typexp2 = exp2.typecheck(typeEnv);
        IType typexp3 = exp3.typecheck(typeEnv);
        if (typexp1.equals(new IntType()) && typexp2.equals(new IntType()) && typexp3.equals(new IntType())) {
            stmt.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new Exception("One of the expressions in the FOR is not INT");
    }
}

package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public class CompStmt implements IStmt{
    IStmt stmt1, stmt2;
    public CompStmt(IStmt s1, IStmt s2) {
        stmt1 = s1;
        stmt2 = s2;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        state.pushToStack(stmt2);
        state.pushToStack(stmt1);
        return null;
    }

    @Override
    public String toString() {
        return stmt1.toString() + ";" + stmt2.toString();
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        return stmt2.typecheck(stmt1.typecheck(typeEnv));
    }
}

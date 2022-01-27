package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws Exception {
        return null;
    }

    @Override
    public String toString() {
        return "Nop";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        return typeEnv;
    }
}

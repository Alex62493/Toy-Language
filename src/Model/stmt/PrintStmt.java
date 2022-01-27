package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp e) {
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        state.addToOut(exp.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public String toString() {
        return "Print(" + exp.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}

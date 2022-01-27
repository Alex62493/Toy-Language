package Model.stmt;

import Model.PrgState;
import Model.adt.Dict;
import Model.adt.ExeStack;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.IValue;

public class ForkStmt implements IStmt{

    IStmt stmt;

    public ForkStmt(IStmt s) {
        stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> newStack = new ExeStack<IStmt>();
        newStack.push(stmt);
        Dict<String, IValue> symTable = (Dict<String, IValue>) state.getSymTable();
        return new PrgState(newStack, symTable.clone(), state.getOut(), stmt, state.getFileTable(), state.getHeap(), state.getCyclicBarrier());
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        return stmt.typecheck(typeEnv);
    }
}

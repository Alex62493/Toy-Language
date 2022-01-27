package Model.CyclicBarrier;

import Model.PrgState;
import Model.adt.IDict;
import Model.stmt.IStmt;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IntValue;

public class AwaitStmt implements IStmt
{
    private String var;

    public AwaitStmt(String v)
    {
        var = v;
    }

    @Override
    public PrgState execute(PrgState p) throws Exception {
        boolean isFound = p.getSymTable().isDefined(var);
        if (!isFound) return null;

        IntValue aux = (IntValue) p.getSymTable().lookup(var);
        int index = aux.getValue();
        if (!p.getCyclicBarrier().contains(index)) return null;

        synchronized (p.getCyclicBarrier())
        {
            if (p.getCyclicBarrier().get(index).getFirst() == p.getCyclicBarrier().get(index).getSecond().size())
                return null;
            else
            {
                p.getStack().push(new AwaitStmt(var));
                p.getCyclicBarrier().get(index).getSecond().add(p.getId());
                return null;
            }
        }
    }

    @Override
    public String toString()
    {
        return "Await("+var+")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typevar = typeEnv.lookup(var);
        if (typevar.equals(new IntType()))
            return typeEnv;
        else
            throw new Exception("In AWAIT: var is not INT");
    }
}

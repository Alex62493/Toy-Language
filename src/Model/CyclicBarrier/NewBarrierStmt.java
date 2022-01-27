package Model.CyclicBarrier;

import Model.adt.IDict;
import Model.adt.List;
import Model.exp.Exp;
import Model.PrgState;
import Model.PrgState;
import Model.stmt.IStmt;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class NewBarrierStmt implements IStmt
{
    private String var;
    private Exp expr;
    private Integer nextFree = 0;

    public NewBarrierStmt(String v, Exp e)
    {
        var = v;
        expr = e;
    }

    @Override
    public PrgState execute(PrgState p)
    {
        try
        {
            IntValue iv= (IntValue) expr.eval(p.getSymTable(), p.getHeap());
            if (iv.getValue() != 0)
            {
                synchronized (p.getCyclicBarrier())
                {
                    p.getCyclicBarrier().add(nextFree,new Pair<Integer, ArrayList<Integer>>(iv.getValue(), new ArrayList<Integer>()));
                }
                if (p.getSymTable().isDefined(var))
                    p.getSymTable().update(var, new IntValue(nextFree));
                else
                    p.getSymTable().add(var,new IntValue(nextFree));
            }
            nextFree++;
            return null;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "NewBarrierStmt(" + var + ", "+ expr + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typevar = typeEnv.lookup(var);
        IType typexp = expr.typecheck(typeEnv);
        if (typevar.equals(new IntType()) && typexp.equals(new IntType()))
            return typeEnv;
        else
            throw new Exception("In NEW BARRIER: var or exp is not INT");
    }
}

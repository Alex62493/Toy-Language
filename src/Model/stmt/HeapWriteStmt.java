package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.exp.VarExp;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

import java.util.Objects;

public class HeapWriteStmt implements IStmt{
    String var;
    Exp exp;

    public HeapWriteStmt(String v, Exp e) {
        var = v;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        Exp varExp = new VarExp(var);
        if (!(state.getSymTable().lookup(var) instanceof RefValue))
            throw new Exception("The variable isn't of type RefType");
        RefValue refValue = (RefValue) varExp.eval(state.getSymTable(), state.getHeap());
        IValue expVal = exp.eval(state.getSymTable(), state.getHeap());
        if (!Objects.equals(refValue.getLocationType().toString(), expVal.getType().toString()))
            throw new Exception("Location type from the RefValue " + var +" doesn't match the type from the given expression");
        
        state.getHeap().update(refValue.getAddress(), expVal);
        return null;
    }

    @Override
    public String toString() {
        return "wH(" + var + ", " + exp.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typevar = typeEnv.lookup(var);
        RefType refTypeVar = (RefType) typevar;
        typevar = refTypeVar.getInner();
        IType typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new Exception("HEAP ALLOC stmt type check");
    }
}

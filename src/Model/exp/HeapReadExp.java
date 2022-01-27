package Model.exp;

import Model.adt.IDict;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class HeapReadExp extends Exp {
    Exp exp;

    public HeapReadExp(Exp e) {
        exp = e;
    }

    public IValue eval(IDict<String, IValue> symTable, IDict<Integer, IValue> heap) throws Exception {
        if (!(exp.eval(symTable, heap).getType() instanceof RefType))
            throw new Exception("The variable isn't of type RefType");
        RefValue refValue = (RefValue) exp.eval(symTable, heap);
        int addr = refValue.getAddress();
        return heap.lookup(addr);
    }

    public String toString() {
        return "rH(" + exp.toString() + ")";
    }

    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        }
        else
            throw new Exception("the rH argument is not a Ref Type");
    }
}

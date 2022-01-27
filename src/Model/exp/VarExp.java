package Model.exp;
import Model.adt.IDict;
import Model.types.IType;
import Model.value.IValue;

public class VarExp extends Exp {
    String id;

    public VarExp(String a) {
        id = a;
    }

    public IValue eval(IDict<String, IValue> symTable, IDict<Integer, IValue> heap) throws Exception {
        return symTable.lookup(id);
    }

    public String toString() {
        return id;
    }

    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        return typeEnv.lookup(id);
    }
}

package Model.exp;
import Model.adt.IDict;
import Model.types.IType;
import Model.value.IValue;

public class ValueExp extends Exp{
    IValue value;

    public ValueExp(IValue n) {
        value = n.deepCopy();
    }

    public IValue eval(IDict<String,IValue> symTable, IDict<Integer, IValue> heap) throws Exception {
        return value;
    }

    public String toString() {
        return value.toString();
    }

    public IType typecheck(IDict<String,IType> typeEnv) throws Exception {
        return value.getType();
    }
}

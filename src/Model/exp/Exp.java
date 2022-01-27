package Model.exp;
import Model.adt.IDict;
import Model.types.IType;
import Model.value.IValue;

public abstract class Exp {

    public abstract IValue eval(IDict<String,IValue> symTable, IDict<Integer, IValue> heap) throws Exception;
    public abstract String toString();
    public abstract IType typecheck(IDict<String,IType> typeEnv) throws Exception;
}

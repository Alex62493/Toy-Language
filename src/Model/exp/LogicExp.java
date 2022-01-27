package Model.exp;

import Exceptions.ExpressionEvaluationException;
import Model.adt.IDict;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.Objects;

public class LogicExp extends Exp{

    String op;
    Exp e1, e2;

    public LogicExp(String o, Exp a, Exp b) {
        op = o;
        e1 = a;
        e2 = b;
    }

    public IValue eval(IDict<String, IValue> symTable, IDict<Integer, IValue> heap) throws Exception{
        if (!Objects.equals(e1.eval(symTable, heap).getType().toString(), "bool") || !Objects.equals(e2.eval(symTable, heap).getType().toString(), "bool"))
            throw new ExpressionEvaluationException("One of the variables isn't a boolean");
        BoolValue i1 = (BoolValue) e1.eval(symTable, heap);
        BoolValue i2 = (BoolValue) e2.eval(symTable, heap);
        if (Objects.equals(op, "&&")) return (new BoolValue(i1.getValue() && i2.getValue()));
        else if (Objects.equals(op, "||")) return (new BoolValue(i1.getValue() || i2.getValue()));
        else if (Objects.equals(op, "!")) return (new BoolValue(!i1.getValue()));
        else throw new Exception("Operand doesn't exist");
    }

    public String getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    public String toString() {
        if (Objects.equals(op, "||") || Objects.equals(op, "&&"))
            return e1.toString() + " " + op + " " + e2.toString();
        else
            return "!" + e1.toString();
    }

    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            }
            else
                throw new Exception("second operand is not a boolean");
        }
        else throw new Exception("first operand is not a boolean");
    }
}

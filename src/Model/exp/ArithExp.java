package Model.exp;
import Exceptions.ExpressionEvaluationException;
import Model.adt.IDict;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.Objects;

public class ArithExp extends Exp{
    char op;
    Exp e1, e2;

    public ArithExp(char o, Exp a, Exp b) {
        op = o;
        e1 = a;
        e2 = b;
    }

    public IValue eval(IDict<String, IValue> symTable, IDict<Integer, IValue> heap) throws Exception{
        if (!Objects.equals(e1.eval(symTable, heap).getType().toString(), "int") || !Objects.equals(e2.eval(symTable, heap).getType().toString(), "int"))
            throw new ExpressionEvaluationException("One of the variables isn't a integer");
        IntValue i1 = (IntValue) e1.eval(symTable, heap);
        IntValue i2 = (IntValue) e2.eval(symTable, heap);
        if (op == '+') return (new IntValue(i1.getValue() + i2.getValue()));
        else if (op == '-') return (new IntValue(i1.getValue() - i2.getValue()));
        else if (op == '*') return (new IntValue(i1.getValue() * i2.getValue()));
        else if (op == '/') {
            if (i2.getValue() == 0)
                throw new ExpressionEvaluationException("The divisor can't be 0");
            return (new IntValue(i1.getValue() / i2.getValue()));
        }
        else throw new Exception("Operand doesn't exist");
    }

    public char getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    public String toString() { return e1.toString() + " " + op + " " + e2.toString(); }

    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            }
            else
                throw new Exception("second operand is not an integer");
        }
        else throw new Exception("first operand is not an integer");
    }
}

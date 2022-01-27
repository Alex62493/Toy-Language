package Model.value;

import Model.types.IType;
import Model.types.RefType;

public class RefValue implements IValue{
    int address;
    IType locationType;

    public RefValue(int a, IType i)
    {
        address = a;
        locationType = i;
    }

    public IType getLocationType() {
        return locationType;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || o.getClass() != this.getClass())
            return false;
        RefValue o_value = (RefValue) o;
        return o_value.address == address && o_value.locationType == locationType;
    }

    @Override
    public String toString()
    {
        return "(" + address + ", " + locationType.toString() + ")";
    }
}

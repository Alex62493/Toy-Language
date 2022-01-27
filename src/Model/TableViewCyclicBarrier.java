package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableViewCyclicBarrier
{
    private SimpleIntegerProperty index;
    private SimpleStringProperty list;
    private SimpleIntegerProperty value;

    public TableViewCyclicBarrier(Integer i, Integer v, String l)
    {
        index= new SimpleIntegerProperty(i);
        list = new SimpleStringProperty(l);
        value = new SimpleIntegerProperty(v);
    }

    public int getIndex() {
        return index.get();
    }

    public SimpleIntegerProperty indexProperty() {
        return index;
    }

    public void setIndex(int index) {
        this.index.set(index);
    }

    public String getList() {
        return list.get();
    }

    public SimpleStringProperty listProperty() {
        return list;
    }

    public void setList(String list) {
        this.list.set(list);
    }

    public int getValue() {
        return value.get();
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }
}

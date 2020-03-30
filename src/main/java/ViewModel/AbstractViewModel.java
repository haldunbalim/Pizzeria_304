package ViewModel;

import Model.AbstractModel;

public abstract class AbstractViewModel {

    protected AbstractModel model;

    public AbstractViewModel(AbstractModel model) {
        this.model = model;
    }

    public abstract Object getColumnView(int col);

    public abstract void setValueAt(int col, Object value);

    public AbstractModel getModel() {
        return model;
    }
}

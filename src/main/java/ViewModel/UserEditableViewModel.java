package ViewModel;

import Model.User;

public class UserEditableViewModel extends AbstractViewModel {


    public static String[] columnNames = {"Name", "Surname", "Phone Number", ""};
    protected User model;

    public UserEditableViewModel(User model) {
        super(model);
    }

    public Object getColumnView(int col) {
        switch (col) {
            case 0:
                return model.getName();
            case 1:
                return model.getSurname();
            case 2:
                return model.getPhoneNumber();
            case 3:
                return false;
        }
        return null;
    }

    @Override
    public void setValueAt(int col, Object value) {

    }

    public static Class getColumnClassAt(int col) {
        return col != 3 ? String.class : Boolean.class;
    }
}

package ViewModel;

import Model.User;

public class UserManagerViewModel extends AbstractViewModel {


    public static String[] columnNames = {"Name", "Surname", "Phone Number", ""};

    public UserManagerViewModel(User model) {
        super(model);
    }

    public Object getColumnView(int col) {
        User model = ((User) this.model);
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
        User model = ((User) this.model);
        switch (col) {
            case 0:
                model.setName((String) value);
            case 1:
                model.setSurname((String) value);
            case 2:
                model.setPhoneNumber((String) value);
        }
    }

    public static Class getColumnClassAt(int col) {
        return col != 3 ? String.class : Boolean.class;
    }
}

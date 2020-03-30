package DataSource;

import Model.Deliverable;

import java.util.ArrayList;

public class DeliverableDataSource extends AbstractDataSource {

    private static DeliverableDataSource instance = new DeliverableDataSource();

    private DeliverableDataSource() {
    }

    public static DeliverableDataSource getInstance() {
        return instance;
    }


    // TODO: returns an arrayList of Deliverables for currentUser's affiliated restaurant
    // access current user via LoginManager
    public ArrayList<Deliverable> getDeliverables() {
        ArrayList<Deliverable> list = new ArrayList<>();
        list.add(new Deliverable(1, "Del1", 5.5));
        list.add(new Deliverable(2, "Del2", 4.5));
        list.add(new Deliverable(3, "Del3", 7.5));
        list.add(new Deliverable(1, "Del1", 5.5));
        list.add(new Deliverable(2, "Del2", 4.5));
        list.add(new Deliverable(3, "Del3", 7.5));
        return list;
    }

    // TODO: given an updated deliverable model saves the update in db
    public void updateDeliverableData(Deliverable model) {

    }

    // TODO: given an deliverable model removes it from the db
    public void removeDeliverableData(Deliverable model) {

    }

    // TODO: given props create a new deliverable in db and return
    public Deliverable createNewDeliverable(String name, Double price) {
        return new Deliverable(134312421, name, price);
    }


}

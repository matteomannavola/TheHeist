package Adventure.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdvObjectContainer extends AdvObject {

    private List<AdvObject> list = new ArrayList<>();

    public AdvObjectContainer(int id) {
        super(id);
    }

    public AdvObjectContainer(int id, String name) {
        super(id, name);
    }

    public AdvObjectContainer(int id, String name, String description, String examine) {
        super(id, name, description, examine);
    }

    public AdvObjectContainer(int id, String name, String description, String examine, Set<String> alias) {
        super(id, name, description, examine, alias);
    }
    
    public AdvObjectContainer(int id, String name, String description, String examine, Set<String> alias, int contained) {
        super(id, name, description, examine, alias, contained);
    }

    public List<AdvObject> getList() {
        return list;
    }

    public void setList(List<AdvObject> list) {
        this.list = list;
    }

    public void add(AdvObject o) {
        list.add(o);
        o.setContained(this.getId());
    }

    public void remove(AdvObject o) {
        list.remove(o);
        o.setContained(-1);
    }

}

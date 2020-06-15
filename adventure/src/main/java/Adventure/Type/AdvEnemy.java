package Adventure.Type;

import java.util.Set;

public class AdvEnemy extends AdvNPC {
    
    private boolean alive = true;
    
    private String examineDead;
    
    public AdvEnemy (int id) {
        super(id);
    }
    
    public AdvEnemy(int id, String name) {
        super(id, name);
    }
    
    public AdvEnemy(int id, String name, String examine) {
        super(id, name, examine);
    }
    
    public AdvEnemy(int id, String name, String examine, String look) {
        super(id, name, examine, look);
    }
    
    public AdvEnemy(int id, String name, String examine, String look, Set<String> alias) {
        super(id, name, examine, look, alias);
    }
    
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getExamineDead() {
        return examineDead;
    }

    public void setExamineDead(String examineDead) {
        this.examineDead = examineDead;
    }
}

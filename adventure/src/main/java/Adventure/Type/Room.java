package Adventure.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable{

    private final int id;

    private String name;
    
    private String firstDescription;

    private String description;
    
    private String lockedDescription = null;

    private String look;

    private boolean visited = false;

    private Room south = null;

    private Room north = null;

    private Room east = null;

    private Room west = null;
    
    private boolean locked=false;
    
    private final List<AdvObject> objects=new ArrayList<>();
    
    private final List<AdvNPC> NPCs = new ArrayList<>();

    public Room(int id) {
        this.id = id;
    }
    
    public Room(int id, String name, boolean locked) {
        this.id = id;
        this.name = name;
        this.locked=locked;
    }

    public Room(int id, String name, String firstDescription, String description, boolean locked) {
        this.id = id;
        this.name = name;
        this.firstDescription = firstDescription;
        this.description = description;
        this.locked=locked;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getFirstDescription() {
        return firstDescription;
    }

    public void setFirstDescription(String firstDescription) {
        this.firstDescription = firstDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Room getSouth() {
        return south;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public Room getNorth() {
        return north;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public Room getEast() {
        return east;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public Room getWest() {
        return west;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public List<AdvObject> getObjects() {
        return objects;
    }    
    
    public List<AdvNPC> getNPCs() {
        return NPCs;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public String getLook() {
        return look;
    }

    public void setLook(String look) {
        this.look = look;
    }

    public String getLockedDescription() {
        return lockedDescription;
    }

    public void setLockedDescription(String lockedDescription) {
        this.lockedDescription = lockedDescription;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    
}

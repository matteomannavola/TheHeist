package Adventure.Parser;

import Adventure.Type.AdvCharacter;
import Adventure.Type.AdvEnemy;
import Adventure.Type.AdvObject;
import Adventure.Type.Command;

public class ParserOutput{

    private Command command;

    private AdvObject object1 = null;
    private AdvObject invObject1 = null;
    private AdvCharacter character1 = null;
    private AdvEnemy enemy1 = null;
    
    private AdvObject object2 = null;
    private AdvObject invObject2 = null;
    private AdvCharacter character2 = null;
    private AdvEnemy enemy2 = null;
    
    private boolean extraWords;

    public ParserOutput(Command command, AdvObject object, boolean extraWords) {
        this.command = command;
        this.object1 = object;
        this.extraWords = extraWords;
    }

    public ParserOutput(Command command, AdvObject object1, AdvObject invObject1, AdvCharacter character1, AdvEnemy enemy1, AdvObject object2, AdvObject invObject2, AdvCharacter character2, AdvEnemy enemy2, boolean extraWords) {
        this.command = command;
        this.object1 = object1;
        this.invObject1 = invObject1;
        this.character1 = character1;
        this.enemy1 = enemy1;
        this.object2 = object2;
        this.invObject2 = invObject2;
        this.character2 = character2;
        this.enemy2 = enemy2;
        this.extraWords = extraWords;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public AdvObject getObject1() {
        return object1;
    }
    
    public AdvObject getObject2() {
        return object2;
    }

    public void setObject1(AdvObject object) {
        this.object1 = object;
    }
    
    public void setObject2(AdvObject object) {
        this.object2 = object;
    }

    public AdvObject getInvObject1() {
        return invObject1;
    }
    
    public AdvObject getInvObject2() {
        return invObject2;
    }

    public void setInvObject1(AdvObject invObject) {
        this.invObject1 = invObject;
    }
    
    public void setInvObject2(AdvObject invObject) {
        this.invObject2 = invObject;
    }

    public AdvCharacter getCharacter1() {
        return character1;
    }

    public void setCharacter1(AdvCharacter character1) {
        this.character1 = character1;
    }

    public AdvCharacter getCharacter2() {
        return character2;
    }

    public void setCharacter2(AdvCharacter character2) {
        this.character2 = character2;
    }
    
    public AdvEnemy getEnemy1() {
        return enemy1;
    }

    public void setEnemy1(AdvEnemy enemy1) {
        this.enemy1 = enemy1;
    }

    public AdvEnemy getEnemy2() {
        return enemy2;
    }

    public void setEnemy2(AdvEnemy enemy2) {
        this.enemy2 = enemy2;
    }
    
    public boolean hasExtraWords() {
        return extraWords;
    }

    public void setExtraWords(boolean extraWords) {
        this.extraWords = extraWords;
    }
}

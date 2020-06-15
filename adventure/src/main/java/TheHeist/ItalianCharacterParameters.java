package TheHeist;

import java.io.Serializable;

public class ItalianCharacterParameters implements InterfaceCharacterParameters, Serializable {
    @Override
    public String HectorName() {
        return "hector";
    }
    @Override
    public String[] HectorAliases(){
        return new String[]{"ettore","autista","l'autista","amico","compagno"};
    }
}

package TheHeist;

import Adventure.GameDescription;
import Adventure.Parser.ParserOutput;
import Adventure.Type.AdvCharacter;
import Adventure.Type.AdvEnemy;
import Adventure.Type.AdvNPC;
import Adventure.Type.AdvObject;
import Adventure.Type.AdvObjectContainer;
import Adventure.Type.Command;
import Adventure.Type.CommandType;
import Adventure.Type.Room;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Random;
import java.util.List;

public class HeistGame extends GameDescription {

    InterfaceCommandNames commandNames;
    InterfaceObjectParameters objectParameters;
    InterfaceCharacterParameters characterParameters;
    InterfaceEnemyParameters enemyParameters;

    private int health = 5;
    private final int MAX_HEALTH = 5;
    private final int KIT_HEAL_AMOUNT = 2;

    private final int MAX_EQUIPPABLE = 2;

    private final int ID_ROOM_OUTSIDE = 0;
    private final int ID_ROOM_ENTRANCE = 1;
    private final int ID_ROOM_KNIFE_ROOM = 2;
    private final int ID_ROOM_GLASSES_ROOM = 3;
    private final int ID_ROOM_FINGERPRINT_ROOM = 4;
    private final int ID_ROOM_CAMERA_ROOM = 5;
    private final int ID_ROOM_DIAMOND_ROOM = 6;

    private final int ID_OBJECT_GUN = 0;
    private final int ID_OBJECT_GUN_AMMO = 1;
    private final int ID_OBJECT_FINGER = 2;
    private final int ID_OBJECT_KNIFE = 3;
    private final int ID_OBJECT_GLASSES = 4;
    private final int ID_OBJECT_FINGER_CONTAINER = 5;
    private final int ID_OBJECT_CAMERA_CONTROLLER = 6;
    private final int ID_OBJECT_DIAMOND = 7;
    private final int ID_OBJECT_FIRST_AID = 8;
    private final int ID_OBJECT_BUTTON = 9;

    private final int ID_CHARACTER_HECTOR = -1;

    private final int ID_ENEMY_CAMERA = 0;
    private final int ID_ENEMY_GUARD1 = 1;
    private final int ID_ENEMY_GUARD2 = 2;

    private boolean eventGotAmmo = false;
    private boolean eventLoadedGun = false;
    private boolean eventGotKit = false;
    private boolean eventCutFinger = false;
    private boolean eventPutFinger = false;
    private boolean eventSpottedLasers = false;
    private boolean eventTurnedOffCamera = false;
    private boolean eventBadlyDamaged = false;
    private boolean eventDiamondTaken = false;

    public HeistGame(InterfaceCommandNames commandNames, InterfaceObjectParameters objectParameters, InterfaceCharacterParameters characterParameters, InterfaceEnemyParameters enemyParameters) {
        this.commandNames = commandNames;
        this.objectParameters = objectParameters;
        this.characterParameters = characterParameters;
        this.enemyParameters = enemyParameters;
    }

    @Override
    public void init() throws Exception {
        //Commands
        Command north = new Command(CommandType.NORD, commandNames.north());
        north.setAlias(commandNames.northAliases());
        getCommands().add(north);
        Command south = new Command(CommandType.SOUTH, commandNames.south());
        south.setAlias(commandNames.southAliases());
        getCommands().add(south);
        Command east = new Command(CommandType.EAST, commandNames.east());
        east.setAlias(commandNames.eastAliases());
        getCommands().add(east);
        Command west = new Command(CommandType.WEST, commandNames.west());
        west.setAlias(commandNames.westAliases());
        getCommands().add(west);
        Command inventory = new Command(CommandType.INVENTORY, commandNames.inventory());
        inventory.setAlias(commandNames.inventoryAliases());
        getCommands().add(inventory);
        Command end = new Command(CommandType.END, commandNames.end());
        end.setAlias(commandNames.endAliases());
        getCommands().add(end);
        Command look = new Command(CommandType.LOOK, commandNames.look());
        look.setAlias(commandNames.lookAliases());
        getCommands().add(look);
        Command examine = new Command(CommandType.EXAMINE, commandNames.examine());
        examine.setAlias(commandNames.examineAliases());
        getCommands().add(examine);
        Command pickup = new Command(CommandType.PICK_UP, commandNames.pick_up());
        pickup.setAlias(commandNames.pick_upAliases());
        getCommands().add(pickup);
        Command open = new Command(CommandType.OPEN, commandNames.open());
        open.setAlias(commandNames.openAliases());
        getCommands().add(open);
        Command close = new Command(CommandType.CLOSE, commandNames.close());
        close.setAlias(commandNames.closeAliases());
        getCommands().add(close);
        Command drop = new Command(CommandType.DROP, commandNames.drop());
        drop.setAlias(commandNames.dropAliases());
        getCommands().add(drop);
        Command put = new Command(CommandType.PUT, commandNames.put());
        put.setAlias(commandNames.putAliases());
        getCommands().add(put);
        Command push = new Command(CommandType.PUSH, commandNames.push());
        push.setAlias(commandNames.pushAliases());
        getCommands().add(push);
        Command attack = new Command(CommandType.ATTACK, commandNames.attack());
        attack.setAlias(commandNames.attackAliases());
        getCommands().add(attack);
        Command equip = new Command(CommandType.EQUIP, commandNames.equip());
        equip.setAlias(commandNames.equipAliases());
        getCommands().add(equip);
        Command unequip = new Command(CommandType.UNEQUIP, commandNames.unequip());
        unequip.setAlias(commandNames.unequipAliases());
        getCommands().add(unequip);
        Command talk = new Command(CommandType.TALK_TO, commandNames.talk_to());
        talk.setAlias(commandNames.talk_toAliases());
        getCommands().add(talk);
        Command give = new Command(CommandType.GIVE, commandNames.give());
        give.setAlias(commandNames.giveAliases());
        getCommands().add(give);
        Command use = new Command(CommandType.USE, commandNames.use());
        use.setAlias(commandNames.useAliases());
        getCommands().add(use);
        Command cut = new Command(CommandType.CUT, commandNames.cut());
        cut.setAlias(commandNames.cutAliases());
        getCommands().add(cut);
        Command turnoff = new Command(CommandType.TURN_OFF, commandNames.turnoff());
        turnoff.setAlias(commandNames.turnoffAliases());
        getCommands().add(turnoff);
        Command save = new Command(CommandType.SAVE, commandNames.save());
        save.setAlias(commandNames.saveAliases());
        getCommands().add(save);
        Command load = new Command(CommandType.LOAD, commandNames.load());
        load.setAlias(commandNames.loadAliases());
        getCommands().add(load);
        Command reload = new Command(CommandType.RELOAD, commandNames.reload());
        reload.setAlias(commandNames.reloadAliases());
        getCommands().add(reload);
        //Rooms
        Room outside = new Room(ID_ROOM_OUTSIDE, "Retro della gioielleria", false);
        outside.setFirstDescription("Finalmente sei arrivato!\nDopo anni di rapine in una carriera da criminale niente male...\n"
                + "ti trovi nei pressi dell'ingresso sul retro della gioielleria piu' prestigiosa del mondo!\n"
                + "Vorresti tanto fare piazza pulita e rubare tutto, ma il tuo obiettivo e' uno: il diamante piu' "
                + "costoso di sempre!\nUn prezzo stratosferico! Piu' zeri di quanti ne entrino nella tua calcolatrice!\n"
                + "Il tuo compagno Hector ti aspetta qui con il suo van, pronto a portarti via insieme alla refurtiva.\n"
                + "Non c'e' tempo da perdere... L'ingresso e' di fronte a te!\n");
        outside.setDescription("L'esterno della gioielleria, nei pressi dell'ingresso sul retro.");
        outside.setLook("Il cielo e' limpido, il sole splende e tu sei pronto a incassare qualche milione!");

        Room entrance = new Room(ID_ROOM_ENTRANCE, "Ingresso sul retro", false);
        entrance.setFirstDescription("Furtivo e sull'attenti, metti piede nella gioielleria.\nSei sorpreso per quanto "
                + "sia stato facile entrare... questo ingresso sul retro e' molto sorvegliato di solito."
                + "\nMa lasci da parte ogni dubbio e ti prepari a sgraffignare quel diamante!"
                + "\nDi fronte a te c'e' un lungo corridoio, mentre a sinistra vedi il bagno e a destra un ufficio.");
        entrance.setDescription("L'ingresso sul retro della gioielleria.");
        entrance.setLook("Sei nell'ingresso sul retro della gioielleria piu' lussuosa del mondo!\n"
                + "Di fronte a te c'e' un lungo corridoio che ha tutta l'aria di portare verso "
                + "qualcosa di importante.\nA sinistra vedi il bagno e a destra un ufficio.");

        Room knifeRoom = new Room(ID_ROOM_KNIFE_ROOM, "Bagno", false);
        knifeRoom.setFirstDescription("Sei nel bagno della gioielleria piu' pregiata del mondo...\ne una guardia di fronte a un orinatoio "
                + "sta espletando i suoi bisogni fisiologici.\nNon sembra particolarmente contenta di vederti e "
                + "si direbbe che abbia deciso di farti fuori con la sua pistola.");
        knifeRoom.setDescription("Il bagno della gioielleria.");
        knifeRoom.setLook("E' decisamente un bagno degno della gioielleria piu' lussuosa del mondo.\nPeccato che sia stato ridotto "
                + "a diventare il teatrino di una sparatoria.");

        Room glassesRoom = new Room(ID_ROOM_GLASSES_ROOM, "Ufficio", false);
        glassesRoom.setFirstDescription("Entri nell'ufficio e... Una guardia!\nNon esita a prendere la pistola, nonostante "
                + "la mira non sia il suo forte, e ti punta immediatamente.\nOrmai ti ha visto e non "
                + "fara' finta di niente...");
        glassesRoom.setDescription("Un ufficio all'interno della gioielleria.");
        glassesRoom.setLook("Un ufficio vicino all'entrata sul retro.\nNon porta ad alcuna altra stanza e non stuzzica "
                + "in modo particolare il tuo interesse... a parte la sua lussuosita'.");

        Room fingerprintRoom = new Room(ID_ROOM_FINGERPRINT_ROOM, "Corridoio", false);
        fingerprintRoom.setFirstDescription("Percorri il lungo corridoio di fronte a te e i tuoi sospetti vengono confermati...\n"
                + "Di fronte a te c'e' proprio la stanza in cui e' custodito il diamante! E ovviamente e' bloccata...\n"
                + "anche se quel grande pulsante rosso al suo fianco non lascia molto spazio all'immaginazione.\n"
                + "Sulla destra, invece, noti un'altra porta bloccata, a fianco della quale si trova una misteriosa fessura...");
        fingerprintRoom.setDescription("Un lungo corridoio che porta verso la stanza del diamante.");
        fingerprintRoom.setLook("Un lungo e lussuoso corridoio in fondo a cui si trova la stanza dei sogni...\n"
                + "e, al suo fianco, un gran pulsante rosso che implora di essere premuto.\n"
                + "Sulla destra, invece, c'e' un'altra porta bloccata, con al suo fianco una fessura.\n"
                + "Vedendola da vicino, sembrerebbe destinata al rilevamento di impronte digitali.");

        Room cameraRoom = new Room(ID_ROOM_CAMERA_ROOM, "Stanza delle telecamere", true);
        cameraRoom.setFirstDescription("Sopraffatto dalla curiosita', entri nella stanza e...\n"
                + "Ti ritrovi di fronte a svariati computer e, in particolare, numerosi monitor.\n"
                + "Ognuno di essi mostra delle immagini rilevate in tempo reale dalle telecamere della gioielleria.");
        cameraRoom.setDescription("La stanza in cui vengono monitorate e gestite le telecamere.");
        cameraRoom.setLockedDescription("Com'era prevedibile, la misteriosa stanza e' bloccata, e non sembra volersi sbloccare da sola...");
        cameraRoom.setLook("La stanza e' buia e la tua attenzione non puo' che andare sui monitor che proiettano "
                + "le immagini delle telecamere.\nE sono completamente alla tua merce'! Sei pervaso da un senso di onnipotenza...");

        Room diamondRoom = new Room(ID_ROOM_DIAMOND_ROOM, "Stanza del diamante", true);
        diamondRoom.setFirstDescription("Con tua grande sorpresa non e' stato poi cosi' difficile arrivare qui, ma... e' proprio cosi'!\n"
                + "Di fronte a te c'e' proprio il famigerato diamante su cui non vedi l'ora di mettere le mani...\n"
                + "Prevedibilmente, la stanza e' cosparsa di telecamere in ogni angolo. Meglio non fare passi falsi...");
        diamondRoom.setDescription("La stanza in cui e' custodito il preziosissimo diamante!");
        diamondRoom.setLockedDescription("La stanza del diamante e', ovviamente, bloccata.");
        diamondRoom.setLook("Una stanza lussuosissima per custodire un oggetto lussuosissimo! Qualcosa ti sembra sospetto pero'...\n"
                + "Un gioiello cosi' prezioso, protetto solo da banali telecamere?\nLa tua esperienza ti fa sospettare che "
                + "ci sia dell'altro...");

        //maps
        outside.setNorth(entrance);
        entrance.setSouth(outside);
        entrance.setWest(knifeRoom);
        entrance.setEast(glassesRoom);
        entrance.setNorth(fingerprintRoom);
        knifeRoom.setEast(entrance);
        glassesRoom.setWest(entrance);
        fingerprintRoom.setSouth(entrance);
        fingerprintRoom.setEast(cameraRoom);
        fingerprintRoom.setNorth(diamondRoom);
        cameraRoom.setWest(fingerprintRoom);
        diamondRoom.setSouth(fingerprintRoom);

        getRooms().add(outside);
        getRooms().add(entrance);
        getRooms().add(knifeRoom);
        getRooms().add(glassesRoom);
        getRooms().add(fingerprintRoom);
        getRooms().add(cameraRoom);
        getRooms().add(diamondRoom);

        //objects
        AdvObjectContainer gun = new AdvObjectContainer(ID_OBJECT_GUN, objectParameters.gunName());
        gun.setAlias(objectParameters.gunAliases());
        gun.setDescription("la tua Glock 19");
        gun.setExamine("La tua fidata Glock 19, con cui non hai mai mancato un colpo. "
                + "Ne avete viste delle belle insieme...");
        gun.setEquippable(true);
        gun.setOpenable(true);
        getInventory().add(gun);

        AdvObject gunAmmo = new AdvObject(ID_OBJECT_GUN_AMMO, objectParameters.gunAmmoName());
        gunAmmo.setAlias(objectParameters.gunAmmoAliases());
        gunAmmo.setDescription("proiettili per la tua pistola");
        gunAmmo.setExamine("Proiettili per la tua Glock 19. Non il massimo dell'utilita' fuori da una pistola.");

        AdvObject finger = new AdvObject(ID_OBJECT_FINGER, objectParameters.fingerName());
        finger.setAlias(objectParameters.fingerAliases());
        finger.setDescription("il dito mozzato di una guardia");
        finger.setUnreachableDescription("Si puo' sapere cosa hai intenzione di fare col corpo di quest'uomo?");
        finger.setExamine("Il dito mozzato di una guardia che hai poco gentilmente freddato.\n"
                + "Avrai intenzione di conservartelo come trofeo di guerra? Contento tu...");
        finger.setReachable(false);

        AdvObject knife = new AdvObject(ID_OBJECT_KNIFE, objectParameters.knifeName());
        knife.setAlias(objectParameters.knifeAliases());
        knife.setDescription("un affilatissimo coltello");
        knife.setExamine("Un coltello decisamente affilato. A occhio e croce, vista la tua grande esperienza in materia...\n"
                + "riesci a stimare che e' almeno piu' affilato di un coltello da burro.");
        knife.setEquippable(true);

        AdvObject glasses = new AdvObject(ID_OBJECT_GLASSES, objectParameters.glassesName());
        glasses.setAlias(objectParameters.glassesAliases());
        glasses.setDescription("un peculiare paio di occhiali");
        glasses.setExamine("Un paio di occhiali alquanto inusuale. Non ne hai mai visto uno simile... "
                + "e' decisamente fuori dall'ordinario.");
        glasses.setEquippable(true);

        AdvObjectContainer fingerContainer = new AdvObjectContainer(ID_OBJECT_FINGER_CONTAINER, objectParameters.fingerContainerName());
        fingerContainer.setAlias(objectParameters.fingerContainerAliases());
        fingerContainer.setDescription("un riconoscitore di impronte digitali");
        fingerContainer.setExamine("Una fessura contenente un riconoscitore di impronte digitali.\nDubito che i proprietari della gioielleria "
                + "siano stati cosi' gentili da fargli accettare anche le tue.");
        fingerContainer.setPickupable(false);
        fingerContainer.setOpen(true);
        fingerprintRoom.getObjects().add(fingerContainer);

        AdvObject cameraController = new AdvObject(ID_OBJECT_CAMERA_CONTROLLER, objectParameters.cameraControllerName());
        cameraController.setAlias(objectParameters.cameraControllerAliases());
        cameraController.setDescription("sistema di controllo delle telecamere");
        cameraController.setExamine("Il sistema che controlla tutte le telecamere della gioielleria.");
        cameraController.setPickupable(false);
        cameraRoom.getObjects().add(cameraController);

        AdvObject diamond = new AdvObject(ID_OBJECT_DIAMOND, objectParameters.diamondName());
        diamond.setAlias(objectParameters.diamondAliases());
        diamond.setDescription("la refurtiva della tua missione!");
        diamond.setUnreachableDescription("Impaziente di mettere le mani su quel diamante, fai per avvicinarti e...\n"
                + "ZAP! Senti improvvisamente dei forti dolori inspiegabili. Qualcosa non quadra...");
        diamond.setExamine("Non riesci a credere di averlo di fronte agli occhi, ma la tua vista non ti inganna...\n"
                + "E' proprio lui! Il diamante piu' costoso di sempre!");
        diamond.setReachable(false);
        diamondRoom.getObjects().add(diamond);

        AdvObject firstAid = new AdvObject(ID_OBJECT_FIRST_AID, objectParameters.firstAidName());
        firstAid.setAlias(objectParameters.firstAidAliases());
        firstAid.setDescription("kit di pronto soccorso");
        firstAid.setExamine("Un kit di pronto soccorso gentilmente donato dal tuo compagno Hector.\n"
                + "Ti puo' far recuperare un po' di salute, se usato.");
        firstAid.setUsable(true);

        AdvObject button = new AdvObject(ID_OBJECT_BUTTON, objectParameters.buttonName());
        button.setAlias(objectParameters.buttonAliases());
        button.setDescription("bottone");
        button.setExamine("Un bel pulsantone rosso vicino alla stanza del tesoro. Cosa potra' mai significare?");
        button.setPickupable(false);
        button.setPushable(true);
        fingerprintRoom.getObjects().add(button);

        //characters
        AdvCharacter Hector = new AdvCharacter(ID_CHARACTER_HECTOR, characterParameters.HectorName());
        Hector.setAlias(characterParameters.HectorAliases());
        Hector.setExamine("Il tuo compagno Hector. A poca distanza da lui vedi gia' il van con cui scapperete con la refurtiva.");
        Hector.setLook("C'e' il tuo compagno Hector col suo van, in attesa che tu torni col diamante.");
        Hector.setFirstDialogue("Hector: \"Finalmente ci siamo, compagno! Il nostro colpo piu' grosso... Com'e' che dici?\"\n"
                + "Con non poca vergogna, riferisci a Hector di aver dimenticato le munizioni della pistola nel van.\n"
                + "Hector: \"Non ho parole... Ecco qua, tieni. Ti ricorderai almeno come metterle da solo nella pistola, spero...\"\n"
                + "Hector ti ha dato dei proiettili.");
        Hector.putDialogue("idle", "Hector: \"Che ci fai ancora qui? Muoviti a rubare quel diamante, di corsa!\"");
        Hector.putDialogue("enteringWithoutAmmo", "Hector: \"Fermo, campione! Guarda cos'e' rimasto nel van...\"\n"
                + "Hector, sbuffando, prende qualcosa dal van.\n"
                + "Hector: \"Dove pensavi di andare senza proiettili in quella pistola? Prendi...\"\n"
                + "Hector ti ha dato dei proiettili per la tua pistola.");
        Hector.putDialogue("enteringWithUnloadedGun", "Hector: \"Ehi, geniaccio, dov'e' che vai? "
                + "Hai intenzione di lanciare quei proiettili a mano come fossero freccette?!\n"
                + "Mettili nella pistola prima che perda la pazienza e me ne scappi senza di te...\"");
        Hector.putDialogue("firstAid", "Hector: \"Ehi, socio, non hai un bell'aspetto! Ti sei gia' fatto crivellare per bene, vedo...\"\n"
                + "Hector prende un kit di pronto soccorso.\n"
                + "Hector: \"Tieni, usa questo e rimettiti in sesto. Ma e' l'unico che ho... Fattelo bastare!\"\n"
                + "Hector ti ha dato un kit di pronto soccorso.");
        Hector.putDialogue("giveCorrect", "Hector: \"Bene... Beh, agenti, io qui ho finito. Tenete questo stupido diamante e fate quello che dovete fare.\"\n\n"
                + "Prima ancora che tu possa processare lo shock per le parole di Hector, ti ritrovi circondato da poliziotti!\n\n"
                + "Hector: \"E' stato bello essere soci per tutti questi anni... ma vedi, eri cosi' ricercato dalla polizia che\n"
                + "si sono persino offerti di scendere a patti con me pur di averti in pugno. Beh, addio!\"\n\n"
                + "Hector se ne va, completamente libero, e tu vieni portato dritto in carcere... Cosa hai imparato?\n"
                + "Quando sarai libero, fra qualche decina di anni... starai piu' attento a scegliere i tuoi compagni!");
        Hector.putDialogue("giveWrong", "Hector: \"Che me ne dovrei fare io di questo?!\"");
        Hector.putDialogue("gotDiamond", "Hector: \"E cosi' ce l'hai fatta! Incredibile... Da' qua quel diamante e diamocela a gambe!\"");
        Hector.putDialogue("attack", "Hector: \"Ehi, che ti e' preso, socio?! Non e' contro di me che dovresti puntare quella pistola!\"");
        Hector.putDialogue("pickUp", "Hector: \"Ma sei impazzito?! Vai a prendere quel diamante, non me!\"");
        Hector.putDialogue("open", "Hector: \"Ehi, che stai cercando di aprire?!\"");
        Hector.putDialogue("put", "Hector, terrorizzato dal tuo sguardo, si sigilla nel van.");
        Hector.addInventory(gunAmmo);
        Hector.addInventory(firstAid);
        outside.getNPCs().add(Hector);

        //enemies
        AdvEnemy camera = new AdvEnemy(ID_ENEMY_CAMERA, enemyParameters.cameraName());
        camera.setAlias(enemyParameters.cameraAliases());
        camera.setExamine("Svariate telecamere dall'aspetto molto resistente, tutte a guardia del preziosissimo diamante.\n"
                + "Sono troppo in alto affinche' tu le possa raggiungere.");
        camera.setExamineDead("Un mucchio di telecamere disattivate. Non faranno tante storie se prenderai in prestito quel bel diamante...");
        camera.setLook("Ci sono diverse telecamere a guardia del diamante.");
        diamondRoom.getNPCs().add(camera);

        AdvEnemy guard1 = new AdvEnemy(ID_ENEMY_GUARD1, enemyParameters.guard1Name());
        guard1.setAlias(enemyParameters.guard1Aliases());
        guard1.setExamine("Una guardia della gioielleria. A giudicare dal suo sguardo, non gli stai molto simpatico...\n"
                + "Dubito che ti invitera' a sorseggiare amorevolmente un po' di te' insieme.\nPer tua fortuna, pero', "
                + "la sua mira con la pistola non sembra delle migliori.\nNoti che possiede anche un coltello.");
        guard1.setExamineDead("Una guardia che hai gentilmente interrotto e ucciso nel mezzo di un suo meritato attimo di relax.");
        guard1.setLook("C'e' anche una guardia poco amichevole che sta facendo del suo meglio per spararti, ma non ha una gran mira.");
        guard1.addInventory(finger);
        guard1.addInventory(knife);
        knifeRoom.getNPCs().add(guard1);

        AdvEnemy guard2 = new AdvEnemy(ID_ENEMY_GUARD2, enemyParameters.guard2Name());
        guard2.setAlias(enemyParameters.guard2Aliases());
        guard2.setExamine("Una guardia della gioielleria. Non ha una gran mira, ma sta cercando di farti fuori.\n"
                + "Ha degli strambi occhiali dall'aspetto peculiare.");
        guard2.setExamineDead("Una guardia che stava solo facendo il suo lavoro e che hai brutalmente privato "
                + "del meraviglioso dono della vita.\nSembrava cosi' fiera di possedere "
                + "quegli strani occhiali...");
        guard2.setLook("C'e' anche una guardia poco amichevole che sta facendo del suo meglio per spararti, ma non ha una gran mira.");
        guard2.addInventory(finger);
        guard2.addInventory(glasses);
        glassesRoom.getNPCs().add(guard2);

        //set starting room
        setCurrentRoom(outside);
    }

    @Override
    public String nextMove(ParserOutput p) {
        StringBuilder output = new StringBuilder();

        if (p.getCommand() == null) {
            output.append("Inserisci un comando valido come prima parola!");
        } else {
            
            if (p.getCommand().getType() == CommandType.NORD) {
                output = commandNorth(p, output);

            } else if (p.getCommand().getType() == CommandType.SOUTH) {
                output = commandSouth(p, output);

            } else if (p.getCommand().getType() == CommandType.EAST) {
                output = commandEast(p, output);

            } else if (p.getCommand().getType() == CommandType.WEST) {
                output = commandWest(p, output);
                
            } else if (p.getCommand().getType() == CommandType.INVENTORY) {
                output = commandInventory(p, output);

            } else if (p.getCommand().getType() == CommandType.LOOK) {
                output = commandLook(p, output);

            } else if (p.getCommand().getType() == CommandType.EXAMINE) {
                output = commandExamine(p, output);

            } else if (p.getCommand().getType() == CommandType.PICK_UP) {
                output = commandPickUp(p, output);

            } else if (p.getCommand().getType() == CommandType.ATTACK) {
                output = commandAttack(p, output);

            } else if (p.getCommand().getType() == CommandType.OPEN) {
                output = commandOpen(p, output);

            } else if (p.getCommand().getType() == CommandType.CLOSE) {
                output = commandClose(p, output);
                
            } else if (p.getCommand().getType() == CommandType.DROP) {
                output = commandDrop(p, output);

            } else if (p.getCommand().getType() == CommandType.PUT) {
                output = commandPut(p, output);

            } else if (p.getCommand().getType() == CommandType.PUSH) {
                output = commandPush(p, output);

            } else if (p.getCommand().getType() == CommandType.EQUIP) {
                output = commandEquip(p, output);

            } else if (p.getCommand().getType() == CommandType.UNEQUIP) {
                output = commandUnequip(p, output);

            } else if (p.getCommand().getType() == CommandType.TALK_TO) {
                output = commandTalkTo(p, output);

            } else if (p.getCommand().getType() == CommandType.GIVE) {
                output = commandGive(p, output);

            } else if (p.getCommand().getType() == CommandType.USE) {
                output = commandUse(p, output);

            } else if (p.getCommand().getType() == CommandType.CUT) {
                output = commandCut(p, output);

            } else if (p.getCommand().getType() == CommandType.TURN_OFF) {
                output = commandTurnOff(p, output);

            } else if (p.getCommand().getType() == CommandType.RELOAD) {
                output = commandReload(p, output);
            }

            
            if (getCurrentRoom().getId() == ID_ROOM_DIAMOND_ROOM) {
                for (AdvObject o : getInventory()) {
                    if (o.getId() == ID_OBJECT_GLASSES && o.isEquipped() && !eventSpottedLasers) {
                        eventSpottedLasers = true;
                        output.append("\nNoti una intricata disposizione di raggi laser!\nOra che li riesci a vedere, "
                                + "sara' un gioco da ragazzi per te evitarli e prendere quel diamante...");
                    }
                }
            }

            if (getCurrentRoom().getNPCs().size() > 0) {
                for (AdvNPC npc : getCurrentRoom().getNPCs()) {
                    if (npc instanceof AdvEnemy) {
                        if (npc.getId() != ID_ENEMY_CAMERA) {
                            if (((AdvEnemy)npc).isAlive()) {
                                Random random = new Random();
                                int randomChoice = random.nextInt(2);
                                switch (randomChoice) {
                                    case 0:
                                        output.append("\nSei stato sparato da: " + npc.getName() + "!");
                                        output = takeDamage(output);
                                        break;
                                    case 1:
                                        output.append("\nSei stato mancato da: " + npc.getName() + ". Si vede che non e' pagato abbastanza...");
                                        break;
                                }
                            }
                        }
                    }
                }
            }

            if (health <= 0) {
                output.append("\n\nLa tua carriera da ladro finisce con la tua vita. Ne e' valsa la pena?\n"
                        + "Avevi tutto il tempo per rivalutare le tue scelte e magari darti al giardinaggio...");
                setEnd(true);
            }

        }

        output.append("\n");
        return output.toString();
    }

    // ------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------------------------
    
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private StringBuilder takeDamage(StringBuilder output) {
        health--;
        output.append("\nSalute attuale: " + health);
        if (health <= 3 && !eventBadlyDamaged) {
            output.append("\n\nNon sembri conciato bene... Fai un salto da Hector, potrebbe avere qualcosa di utile per te...");
            eventBadlyDamaged = true;
        }
        return output;
    }

    private StringBuilder printNPCLooks(StringBuilder output) {
        if (getCurrentRoom().getNPCs().size() > 0) {
            for (AdvNPC npc : getCurrentRoom().getNPCs()) {
                if (!(npc instanceof AdvEnemy && !((AdvEnemy)npc).isAlive())) {
                    output.append("\n" + npc.getLook());
                }
            }
        }
        return output;
    }
    
    private StringBuilder checkMovement(StringBuilder output, boolean noroom, boolean move, Room roomLocked) {
        if (noroom) {
            Random random = new Random();
            int randomChoice = random.nextInt(3);
            switch (randomChoice) {
                case 0:
                    output.append("Da quella parte non si puo' andare, c'e' un muro! Non hai ancora acquisito i poteri per oltrepassare i muri...");
                    break;
                case 1:
                    output.append("Puoi provare a prendere a testate la parete quanto vuoi, ma quel muro non crollera'...");
                    break;
                case 2:
                    output.append("Un vicolo cieco, da qui non si puo' proprio andare da nessuna parte. Ti conviene cambiare direzione...");
            }
        } else if (move) {
            output.append("////   " + getCurrentRoom().getName() + "   ////");
            if (getCurrentRoom().isVisited()) {
                output.append("\n" + getCurrentRoom().getDescription());
                output = printNPCLooks(output);
            } else {
                output.append("\n" + getCurrentRoom().getFirstDescription());
                getCurrentRoom().setVisited(true);
            }

            if (getCurrentRoom().getId() == ID_ROOM_OUTSIDE && !eventGotKit && eventBadlyDamaged) {
                for (AdvNPC npc : getCurrentRoom().getNPCs()) {
                    if (npc.getId() == ID_CHARACTER_HECTOR) {
                        output.append("\n" + ((AdvCharacter)npc).getDialogue("firstAid"));
                        getInventory().add(npc.getFromInventory(ID_OBJECT_FIRST_AID));
                        npc.removeFromInventory(ID_OBJECT_FIRST_AID);
                        eventGotKit = true;
                    }
                }
                
                
            }

        } else if (roomLocked != null) {
            output.append(roomLocked.getLockedDescription());
        }

        return output;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------------------------
    //nextMove commands:
    
    private StringBuilder commandNorth(ParserOutput p, StringBuilder output) {
        boolean noroom = false;
        boolean move = false;
        Room roomLocked = null;

        if (p.getObject1() != null || p.getInvObject1() != null || p.getCharacter1() != null || p.getEnemy1() != null || p.hasExtraWords()) {
            output.append("Forse volevi dire: " + p.getCommand().getName());
        } else if (getCurrentRoom().getId() == ID_ROOM_OUTSIDE && !eventGotAmmo) {
            for (AdvNPC npc : getCurrentRoom().getNPCs()) {
                if (npc.getId() == ID_CHARACTER_HECTOR) {
                    ((AdvCharacter)npc).setFirstDialogueFlag(false);
                    output.append(((AdvCharacter)npc).getDialogue("enteringWithoutAmmo"));
                    getInventory().add(npc.getFromInventory(ID_OBJECT_GUN_AMMO));
                    npc.removeFromInventory(ID_OBJECT_GUN_AMMO);
                    eventGotAmmo = true;
                }
            }
        } else if (getCurrentRoom().getId() == ID_ROOM_OUTSIDE && !eventLoadedGun) {
            for (AdvNPC npc : getCurrentRoom().getNPCs()) {
                if (npc.getId() == ID_CHARACTER_HECTOR) {
                    output.append(((AdvCharacter)npc).getDialogue("enteringWithUnloadedGun"));
                }
            }
        } else if (getCurrentRoom().getNorth() != null) {
            if (!getCurrentRoom().getNorth().isLocked()) {
                setCurrentRoom(getCurrentRoom().getNorth());
                move = true;
            } else {
                roomLocked = getCurrentRoom().getNorth();
            }
        } else {
            noroom = true;
        }

        output = checkMovement(output, noroom, move, roomLocked);
        return output;
    }

    private StringBuilder commandSouth(ParserOutput p, StringBuilder output) {
        boolean noroom = false;
        boolean move = false;
        Room roomLocked = null;

        if (p.getObject1() != null || p.getInvObject1() != null || p.getCharacter1() != null || p.getEnemy1() != null || p.hasExtraWords()) {
            output.append("Forse volevi dire: " + p.getCommand().getName());
        } else if (getCurrentRoom().getId() == ID_ROOM_OUTSIDE) {
            output.append("L'ingresso della gioielleria e' a nord! Non starai cercando di dartela a gambe...?");
        } else if (getCurrentRoom().getSouth() != null) {
            if (!getCurrentRoom().getSouth().isLocked()) {
                setCurrentRoom(getCurrentRoom().getSouth());
                move = true;
            } else {
                roomLocked = getCurrentRoom().getSouth();
            }
        } else {
            noroom = true;
        }

        output = checkMovement(output, noroom, move, roomLocked);
        return output;
    }
    
    private StringBuilder commandEast(ParserOutput p, StringBuilder output) {
        boolean noroom = false;
        boolean move = false;
        Room roomLocked = null;

        if (p.getObject1() != null || p.getInvObject1() != null || p.getCharacter1() != null || p.getEnemy1() != null || p.hasExtraWords()) {
            output.append("Forse volevi dire: " + p.getCommand().getName());
        } else if (getCurrentRoom().getId() == ID_ROOM_OUTSIDE) {
            output.append("L'ingresso della gioielleria e' a nord! Non starai cercando di dartela a gambe...?");
        } else if (getCurrentRoom().getEast() != null) {
            if (!getCurrentRoom().getEast().isLocked()) {
                setCurrentRoom(getCurrentRoom().getEast());
                move = true;
            } else {
                roomLocked = getCurrentRoom().getEast();
            }
        } else {
            noroom = true;
        }

        output = checkMovement(output, noroom, move, roomLocked);
        return output;
    }
    
    private StringBuilder commandWest(ParserOutput p, StringBuilder output) {
        boolean noroom = false;
        boolean move = false;
        Room roomLocked = null;

        if (p.getObject1() != null || p.getInvObject1() != null || p.getCharacter1() != null || p.getEnemy1() != null || p.hasExtraWords()) {
            output.append("Forse volevi dire: " + p.getCommand().getName());
        } else if (getCurrentRoom().getId() == ID_ROOM_OUTSIDE) {
            output.append("L'ingresso della gioielleria e' a nord! Non starai cercando di dartela a gambe...?");
        } else if (getCurrentRoom().getWest() != null) {
            if (!getCurrentRoom().getWest().isLocked()) {
                setCurrentRoom(getCurrentRoom().getWest());
                move = true;
            } else {
                roomLocked = getCurrentRoom().getWest();
            }
        } else {
            noroom = true;
        }

        output = checkMovement(output, noroom, move, roomLocked);
        return output;
    }
    
    private StringBuilder commandInventory(ParserOutput p, StringBuilder output) {
        if (p.getObject1() != null || p.getInvObject1() != null || p.getCharacter1() != null || p.getEnemy1() != null || p.hasExtraWords()) {
            output.append("Se vuoi vedere il tuo inventario, scrivi solo \"inventario\"... geniaccio.");
        } else if (getInventory().size() > 0) {
            output.append("Nel tuo inventario ci sono:");
            for (AdvObject o : getInventory()) {
                if (o.getContained() == -1) {
                    output.append("\n- " + o.getName() + ": " + o.getDescription());
                    if (o.isOpen()) {
                        output.append(" (aperto)");
                    }
                    if (o.isEquipped()) {
                        output.append(" (equipaggiato)");
                    }
                }
            }
        } else {
            output.append("Il tuo inventario e' vuoto.");
        }

        return output;
    }
    
    private StringBuilder commandLook(ParserOutput p, StringBuilder output) {
        if (p.getObject1() != null || p.getInvObject1() != null || p.getCharacter1() != null || p.getEnemy1() != null || p.hasExtraWords()) {
            output.append("Frena... scrivi solo \"osserva\" se vuoi guardarti intorno. Altrimenti usa \"guarda [oggetto]\" per esaminarlo.");
        } else {
            output.append(getCurrentRoom().getLook());
            output = printNPCLooks(output);

            boolean first = true;
            for (AdvObject o : getCurrentRoom().getObjects()) {
                if (o.isDropped()) {
                    if (first) {
                        output.append("\nInoltre, hai lasciato in questa stanza: ");
                        first = false;
                    }
                    output.append("\n- " + o.getName());
                }
            }
        }

        return output;
    }
  
    private StringBuilder commandExamine(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi esaminare un solo oggetto alla volta!");

        } else if (p.getObject1() != null && !(p.getObject1() instanceof AdvObjectContainer)) {
            if (p.getObject1().isReachable()) {
                output.append(p.getObject1().getExamine());
            } else {
                output.append(p.getObject1().getUnreachableDescription());
                if (p.getObject1().getId() == ID_OBJECT_DIAMOND) {
                    output = takeDamage(output);
                }
            }

        } else if (p.getObject1() != null && p.getObject1() instanceof AdvObjectContainer) {
            output.append(p.getObject1().getExamine());

            if (((AdvObjectContainer) p.getObject1()).isOpen() && p.getObject1().getId() != ID_OBJECT_FINGER_CONTAINER) {
                boolean first = true;
                for (AdvObject o : getCurrentRoom().getObjects()) {
                    if (o.getContained() == p.getObject1().getId()) {
                        if (first) {
                            output.append("\n" + p.getObject1().getName() + " contiene:");
                            first = false;
                        }
                        output.append("\n- " + o.getName());
                    }
                }

                if (first) {
                    output.append(p.getObject1().getName() + " e' vuoto.");
                }
            }

        } else if (p.getInvObject1() != null && !(p.getInvObject1() instanceof AdvObjectContainer)) {
            output.append(p.getInvObject1().getExamine());

        } else if (p.getInvObject1() != null && p.getInvObject1() instanceof AdvObjectContainer) {
            output.append(p.getInvObject1().getExamine());

            if (((AdvObjectContainer) p.getInvObject1()).isOpen()) {
                boolean first = true;
                for (AdvObject o : getInventory()) {
                    if (o.getContained() == p.getInvObject1().getId()) {
                        if (first) {
                            output.append("\n" + p.getInvObject1().getName() + " contiene:");
                            first = false;
                        }
                        output.append("\n- " + o.getName());
                    }
                }

                if (first) {
                    output.append(p.getInvObject1().getName() + " e' vuoto.");
                }
            }

        } else if (p.getCharacter1() != null) {
            output.append(p.getCharacter1().getExamine());

        } else if (p.getEnemy1() != null) {
            if (p.getEnemy1().isAlive()) {
                output.append(p.getEnemy1().getExamine());
            } else {
                output.append(p.getEnemy1().getExamineDead());
            }

        } else if (p.hasExtraWords()) {
            output.append("Non vedo niente del genere qua intorno...");
        } else {
            output.append("Specifica un oggetto da esaminare, o scrivi solo \"osserva\" per guardarti intorno.");
        }

        return output;
    }
    
    private StringBuilder commandPickUp(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi raccogliere un solo oggetto alla volta!");
        } else if (p.getInvObject1() != null && p.getInvObject1().getContained() == -1) {
            output.append("Possiedi gia' questo oggetto.");
        } else if (p.getInvObject1() != null && p.getInvObject1().getContained() != -1) {
            p.getInvObject1().setContained(-1);
            output.append("Hai tirato fuori " + p.getInvObject1().getName() + " nell'inventario.");
        } else if (p.getCharacter1() != null) {
            if (p.getCharacter1().containsDialogue("pickUp")) {
                output.append(p.getCharacter1().getDialogue("pickUp"));
            } else output.append("Non puoi certo raccogliere un personaggio...");
        } else if (p.getEnemy1() != null) {
            output.append("Non puoi certo raccogliere un nemico...");
        } else if (p.getObject1() != null) {
            if (p.getObject1().isReachable()) {
                if (p.getObject1().isPickupable()) {
                    
                    boolean cameraAlive = false;
                    for(AdvNPC npc : getRooms().get(ID_ROOM_DIAMOND_ROOM).getNPCs()) {
                        if (npc.getId() == ID_ENEMY_CAMERA && ((AdvEnemy)npc).isAlive()) {
                            cameraAlive = true;
                        }
                    }
                    
                    if (!(p.getObject1().getId() == ID_OBJECT_DIAMOND && cameraAlive)) {
                        p.getObject1().setContained(-1);
                        getInventory().add(p.getObject1());
                        getCurrentRoom().getObjects().remove(p.getObject1());
                        p.getObject1().setDropped(false);
                        output.append("Hai raccolto: " + p.getObject1().getDescription());

                        if (p.getObject1().getId() == ID_OBJECT_DIAMOND) {
                            eventDiamondTaken = true;
                            getRooms().get(ID_ROOM_DIAMOND_ROOM).setLook("La stanza del diamante... privata del suo diamante! Ce l'hai fatta!");
                            output.append("\nPresto, torna da Hector e dagli il diamante!");
                        }

                        if (p.getObject1() instanceof AdvObjectContainer) {
                            List<AdvObject> l = getCurrentRoom().getObjects();
                            if (!l.isEmpty()) {
                                Iterator<AdvObject> it = l.iterator();
                                while (it.hasNext()) {
                                    AdvObject next = it.next();
                                    if (p.getObject1().getId() != next.getId()) {
                                        if (p.getObject1().getId() == next.getContained()) {
                                            getInventory().add(next);
                                            it.remove();
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        output.append("Fermo! Non vorrai farti vedere da tutte quelle telecamere!");
                    }
                } else {
                    output.append("Non puoi raccogliere questo oggetto.");
                }
            } else {
                output.append(p.getObject1().getUnreachableDescription());
                if (p.getObject1().getId() == ID_OBJECT_DIAMOND) {
                    output = takeDamage(output);
                }
            }
        } else if (p.hasExtraWords()) {
            output.append("Non vedo niente del genere qua intorno...");
        } else {
            output.append("Specifica cosa vuoi raccogliere.");
        }
        
        return output;
    }
    
    private StringBuilder commandAttack(ParserOutput p, StringBuilder output) {
        if (p.getObject1() != null) {
            output.append("Non puoi attaccare un oggetto!");
        } else if (p.getInvObject1() != null) {
            output.append("Non puoi attaccare un oggetto nel tuo inventario!");
            
        } else if (p.getCharacter1() != null) {
            if (p.getCharacter1().containsDialogue("attack")) {
                output.append(p.getCharacter1().getDialogue("attack"));
            } else output.append("Non puoi attaccare questo personaggio!");
            
        } else if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null) {
            output.append("Puoi attaccare solo un nemico!");
        } else if (p.getEnemy2() != null) {
            output.append("Puoi attaccare un solo nemico alla volta!");
        } else if (p.getEnemy1() == null && !p.hasExtraWords()) {
            output.append("Specifica un nemico da attaccare.");
        } else if (p.getEnemy1() == null && p.hasExtraWords()) {
            output.append("Non vedo niente del genere qua intorno...");
        } else {
            boolean found = false;
            for (AdvObject o : getInventory()) {
                if (o.getId() == ID_OBJECT_GUN) {
                    found = true;
                    if (o.isEquipped()) {
                        if (!o.isOpen()) {
                            if (((AdvObjectContainer) o).getList().size() > 0) {
                                if (p.getEnemy1().getId() != ID_ENEMY_CAMERA) {
                                    if (p.getEnemy1().isAlive()) {
                                        output.append("Hai ucciso: " + p.getEnemy1().getName() + "!");
                                        p.getEnemy1().setAlive(false);

                                        List<AdvObject> invEnemy = p.getEnemy1().getInventory();
                                        if (!invEnemy.isEmpty()) {
                                            Iterator<AdvObject> itInvEnemy = invEnemy.iterator();
                                            while (itInvEnemy.hasNext()) {
                                                AdvObject nextObjEnemy = itInvEnemy.next();
                                                getCurrentRoom().getObjects().add(nextObjEnemy);
                                                if (nextObjEnemy.getId() != ID_OBJECT_FINGER) {
                                                    output.append("\n... e ha lasciato: " + nextObjEnemy.getDescription());
                                                }
                                                itInvEnemy.remove();
                                            }
                                        }

                                    } else {
                                        output.append("Hai gia' ucciso: " + p.getEnemy1().getName() + "! Che bisogno c'e' di infierire?");
                                    }
                                } else {
                                    output.append("Queste telecamere sono troppo resistenti per essere distrutte da meri proiettili...");
                                }
                            } else {
                                output.append("Non puoi sparare senza proiettili!");
                            }
                        } else {
                            output.append("Non puoi sparare con la pistola aperta...");
                        }
                    } else {
                        output.append("Non hai ancora imparato a sparare con la forza del pensiero... Equipaggia la pistola prima di sparare!");
                    }
                }
            }
            if (!found && getInventory().isEmpty()) {
                output.append("Non hai niente nell'inventario con cui attaccare e non sei un ottimo lottatore corpo a corpo...");
            } else if (!found) {
                output.append("Un pistolero come te cerca di attaccare senza avere una pistola? Rivaluta le tue azioni...");
            }
        }

        return output;
    }
    
    private StringBuilder commandOpen(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi aprire solo una cosa alla volta!");
        } else {
            if (p.getObject1() != null) {
                if (p.getObject1().isReachable()) {
                    if (p.getObject1().isOpenable()) {
                        if (!p.getObject1().isOpen()) {
                            if (p.getObject1() instanceof AdvObjectContainer) {
                                p.getObject1().setOpen(true);
                                output.append("Hai aperto: " + p.getObject1().getName());
                                AdvObjectContainer c = (AdvObjectContainer) p.getObject1();
                                if (!c.getList().isEmpty()) {
                                    output.append("\n" + c.getName() + " contiene:");
                                    Iterator<AdvObject> it = c.getList().iterator();
                                    while (it.hasNext()) {
                                        AdvObject next = it.next();
                                        getCurrentRoom().getObjects().add(next);
                                        output.append("\n- " + next.getName());
                                        it.remove();
                                    }
                                }
                            } else {
                                output.append("Hai aperto: " + p.getObject1().getName());
                                p.getObject1().setOpen(true);
                            }
                        } else {
                            output.append("Questo oggetto e' gia' aperto!");
                        }
                    } else {
                        output.append("Questo oggetto non si puo' aprire!");
                    }
                } else {
                    output.append(p.getObject1().getUnreachableDescription());
                    if (p.getObject1().getId() == ID_OBJECT_DIAMOND) {
                        output = takeDamage(output);
                    }
                }

            } else if (p.getInvObject1() != null) {
                if (p.getInvObject1().isOpenable()) {
                    if (!p.getInvObject1().isOpen()) {
                        if (p.getInvObject1() instanceof AdvObjectContainer) {
                            p.getInvObject1().setOpen(true);
                            output.append("Hai aperto nel tuo inventario: " + p.getInvObject1().getName());
                            AdvObjectContainer c = (AdvObjectContainer) p.getInvObject1();
                            if (!c.getList().isEmpty()) {
                                output.append("\n" + c.getName() + " contiene:");
                                Iterator<AdvObject> it = c.getList().iterator();
                                while (it.hasNext()) {
                                    AdvObject next = it.next();
                                    getInventory().add(next);
                                    output.append("\n- " + next.getName());
                                    it.remove();
                                }
                            }
                        } else {
                            output.append("Hai aperto nel tuo inventario: " + p.getInvObject1().getName());
                            p.getInvObject1().setOpen(true);
                        }
                    } else {
                        output.append("Questo oggetto e' gia' aperto!");
                    }
                } else {
                    output.append("Questo oggetto non si puo' aprire!");
                }

            } else if (p.getCharacter1() != null) {
                if (p.getCharacter1().containsDialogue("open")) {
                    output.append(p.getCharacter1().getDialogue("open"));
                } else output.append("Non puoi aprire un personaggio...");
            } else if (p.getEnemy1() != null) {
                output.append("Non puoi aprire un nemico...");
            } else if (p.hasExtraWords()) {
                output.append("Non vedo niente del genere qua intorno...");
            } else {
                output.append("Specifica cosa vuoi aprire.");
            }
        }

        return output;
    }
    
    private StringBuilder commandClose(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi chiudere solo una cosa alla volta!");
        } else {
            if (p.getObject1() != null) {
                if (p.getObject1().isReachable()) {
                    if (p.getObject1().isOpenable()) {
                        if (p.getObject1().isOpen()) {
                            if (p.getObject1() instanceof AdvObjectContainer) {
                                List<AdvObject> l = getCurrentRoom().getObjects();
                                if (!l.isEmpty()) {
                                    Iterator<AdvObject> it = l.iterator();
                                    while (it.hasNext()) {
                                        AdvObject next = it.next();
                                        if (p.getObject1().getId() != next.getId()) {
                                            if (p.getObject1().getId() == next.getContained()) {
                                                ((AdvObjectContainer) p.getObject1()).add(next);
                                                it.remove();
                                            }
                                        }
                                    }
                                    p.getObject1().setOpen(false);
                                }
                            } else {
                                p.getObject1().setOpen(false);
                            }
                            output.append("Hai chiuso: " + p.getObject1().getName());
                        } else {
                            output.append("Questo oggetto e' gia' chiuso!");
                        }
                    } else {
                        output.append("Questo oggetto non si puo' chiudere!");
                    }
                } else {
                    output.append(p.getObject1().getUnreachableDescription());
                    if (p.getObject1().getId() == ID_OBJECT_DIAMOND) {
                        output = takeDamage(output);
                    }
                }

            } else if (p.getInvObject1() != null) {
                if (p.getInvObject1().isOpenable()) {
                    if (p.getInvObject1().isOpen()) {
                        if (p.getInvObject1() instanceof AdvObjectContainer) {
                            List<AdvObject> l = getInventory();
                            if (!l.isEmpty()) {
                                Iterator<AdvObject> it = l.iterator();
                                while (it.hasNext()) {
                                    AdvObject next = it.next();
                                    if (p.getInvObject1().getId() != next.getId()) {
                                        if (p.getInvObject1().getId() == next.getContained()) {
                                            ((AdvObjectContainer) p.getInvObject1()).add(next);
                                            it.remove();
                                        }
                                    }
                                }
                                p.getInvObject1().setOpen(false);
                            }
                        } else {
                            p.getInvObject1().setOpen(false);
                        }
                        output.append("Hai chiuso nel tuo inventario: " + p.getInvObject1().getName());
                    } else {
                        output.append("Questo oggetto e' gia' chiuso!");
                    }
                } else {
                    output.append("Questo oggetto non si puo' chiudere!");
                }

            } else if (p.getCharacter1() != null) {
                output.append("Non puoi chiudere un personaggio...");

            } else if (p.getEnemy1() != null) {
                output.append("Non puoi chiudere un nemico...");

            } else if (p.hasExtraWords()) {
                output.append("Non vedo niente del genere qua intorno...");

            } else {
                output.append("Specifica cosa vuoi chiudere.");
            }
        }

        return output;
    }
    
    private StringBuilder commandDrop(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi lasciare un solo oggetto alla volta.");
        } else if (p.getObject1() != null) {
            output.append("Prima di lasciare un oggetto dovresti averlo, magari...");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi lasciare un personaggio!");
        } else if (p.getEnemy1() != null) {
            output.append("Non puoi lasciare un nemico!");
        } else if (p.getInvObject1() != null) {
            if (!p.getInvObject1().isEquipped()) {
                getCurrentRoom().getObjects().add(p.getInvObject1());
                getInventory().remove(p.getInvObject1());
                p.getInvObject1().setContained(-1);
                p.getInvObject1().setDropped(true);
                output.append("Hai lasciato " + p.getInvObject1().getName() + " in: " + getCurrentRoom().getName().toLowerCase());
            } else {
                output.append("Disequipaggia l'oggetto prima di lasciarlo!");
            }
        } else if (p.hasExtraWords()) {
            output.append("Non vedo niente del genere qua intorno...");
        } else {
            output.append("Specifica cosa vuoi lasciare.");
        }

        return output;
    }

    private StringBuilder commandPut(ParserOutput p, StringBuilder output) {
        if (p.getObject1() != null) {
            output.append("Prendi questo oggetto prima di metterlo da qualche parte...");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi mettere un personaggio in un contenitore!");
        } else if (p.getCharacter2() != null) {
            if (p.getCharacter2().containsDialogue("put")) {
                output.append(p.getCharacter2().getDialogue("put"));
            } else output.append("Non puoi mettere un oggetto in un personaggio!");
        } else if (p.getEnemy1() != null) {
            output.append("Non puoi mettere un nemico in un contenitore!");
        } else if (p.getEnemy2() != null) {
            output.append("Non puoi mettere un oggetto in un nemico!");
        } else if (p.getInvObject1() != null) {
            if (!p.getInvObject1().isEquipped()) {
                if (p.getObject2() != null || p.getInvObject2() != null) {
                    if (p.getObject2() instanceof AdvObjectContainer || p.getInvObject2() instanceof AdvObjectContainer) {
                        if (p.getObject2() != null && p.getObject2().isOpen()) {
                            if (p.getObject2().isReachable()) {
                                if (!(p.getInvObject1().getId() != ID_OBJECT_FINGER && p.getObject2().getId() == ID_OBJECT_FINGER_CONTAINER)) {
                                    getCurrentRoom().getObjects().add(p.getInvObject1());
                                    getInventory().remove(p.getInvObject1());
                                    output.append("Hai inserito " + p.getInvObject1().getName() + " in " + p.getObject2().getName());

                                    if (p.getInvObject1().getId() == ID_OBJECT_FINGER && p.getObject2().getId() == ID_OBJECT_FINGER_CONTAINER
                                            && !eventPutFinger) {
                                        getRooms().get(ID_ROOM_CAMERA_ROOM).setLocked(false);
                                        eventPutFinger = true;
                                        output.append("\nCome previsto, la misteriosa stanza si e' aperta!");
                                    }
                                } else {
                                    output.append("Cosa ti aspetti di ottenere inserendo qui questo oggetto?");
                                }
                            } else {
                                output.append(p.getObject2().getUnreachableDescription());
                            }
                        } else if (p.getInvObject2() != null && p.getInvObject2().isOpen()) {
                            if (p.getInvObject1().getId() != p.getInvObject2().getId()) {
                                if (!(p.getInvObject1().getId() != ID_OBJECT_GUN_AMMO && p.getInvObject2().getId() == ID_OBJECT_GUN)) {
                                    ((AdvObjectContainer) p.getInvObject2()).add(p.getInvObject1());

                                    output.append("Hai inserito " + p.getInvObject1().getName() + " in " + p.getInvObject2().getName() + " nell'inventario");
                                    if (p.getInvObject1().getId() == ID_OBJECT_GUN_AMMO && p.getInvObject2().getId() == ID_OBJECT_GUN) {
                                        eventLoadedGun = true;
                                    }
                                } else {
                                    output.append("Prima di procedere, ti torna in mente che la tua pistola, purtroppo, spara solo proiettili.");
                                }
                            } else {
                                output.append("Non puoi mettere un oggetto dentro se' stesso!");
                            }

                        } else if (p.getObject2() != null && !p.getObject2().isOpen()) {
                            if (p.getObject2().isReachable()) {
                                output.append("Questo contenitore e' chiuso!");
                            } else {
                                output.append(p.getObject2().getUnreachableDescription());
                            }
                        } else if (p.getInvObject2() != null && !p.getInvObject2().isOpen()) {
                            output.append("Questo contenitore e' chiuso!");
                        }
                    } else {
                        output.append("Come pensi di inserire qui quest'oggetto?");
                    }
                } else {
                    output.append("Specifica un contenitore in cui inserire l'oggetto!");
                }
            } else {
                output.append("Disequipaggia l'oggetto prima di inserirlo.");
            }
        } else if (p.hasExtraWords()) {
            output.append("Non vedo niente del genere qui...");
        } else {
            output.append("Specifica un oggetto dall'inventario.");
        }

        return output;
    }
    
    private StringBuilder commandPush(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi premere un solo oggetto alla volta!");

        } else if (p.getObject1() != null && p.getObject1().isPushable()) {
            if (p.getObject1().isReachable()) {
                output.append("Hai premuto: " + p.getObject1().getName());
                if (p.getObject1().getId() == ID_OBJECT_BUTTON) {
                    if (!p.getObject1().isPush()) {
                        p.getObject1().setPush(true);
                        getRooms().get(ID_ROOM_DIAMOND_ROOM).setLocked(false);
                        output.append("\nCome previsto... La stanza del diamante si e' aperta!");
                    } else {
                        p.getObject1().setPush(false);
                        getRooms().get(ID_ROOM_DIAMOND_ROOM).setLocked(true);
                        output.append("\nHai chiuso la stanza del diamante... perche' dovresti?");
                    }
                }
            } else {
                output.append(p.getObject1().getUnreachableDescription());
                if (p.getObject1().getId() == ID_OBJECT_DIAMOND) {
                    output = takeDamage(output);
                }
            }

        } else if (p.getInvObject1() != null && p.getInvObject1().isPushable()) {
            p.getInvObject1().setPush(true);
            output.append("Hai premuto: " + p.getInvObject1().getName());
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi premere un personaggio!");
        } else if (p.getEnemy1() != null) {
            output.append("Non puoi premere un nemico!");
        } else if (p.hasExtraWords()) {
            output.append("Non vedo niente del genere qui...");
        } else {
            output.append("Specifica cosa vuoi premere.");
        }

        return output;
    }
    
    private StringBuilder commandEquip(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi specificare solo un oggetto da equipaggiare alla volta!");
        } else if (p.getObject1() != null) {
            output.append("Prendi questo oggetto prima di equipaggiarlo...");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi equipaggiare un personaggio!");
        } else if (p.getEnemy1() != null) {
            output.append("Non puoi equipaggiare un nemico!");
        } else if (p.getInvObject1() == null && !p.hasExtraWords()) {
            output.append("Specifica quale oggetto equipaggiare dal tuo inventario.");
        } else if (p.getInvObject1() == null && p.hasExtraWords()) {
            output.append("Non hai niente del genere da equipaggiare...");
        } else if (!p.getInvObject1().isEquippable()) {
            output.append("Questo oggetto non e' equipaggiabile!");
        } else if (p.getInvObject1().getContained() != -1) {
            output.append("Tira fuori l'oggetto dal contenitore prima di equipaggiarlo!");
        } else {
            int equippedItems = 0;
            for (AdvObject o : getInventory()) {
                if (o.isEquipped()) {
                    equippedItems++;
                }
            }

            if (equippedItems < MAX_EQUIPPABLE) {
                p.getInvObject1().setEquipped(true);
                output.append("Hai equipaggiato: " + p.getInvObject1().getName());
                switch (p.getInvObject1().getId()) {
                    case ID_OBJECT_GLASSES:
                        for (AdvObject o : getRooms().get(ID_ROOM_DIAMOND_ROOM).getObjects()) {
                            if (o.getId() == ID_OBJECT_DIAMOND) {
                                o.setReachable(true);
                            }
                        }
                }
            } else {
                output.append("Hai gia' equipaggiato il numero massimo di oggetti! Togliti qualcosa prima di procedere.");
            }
        }

        return output;
    }
    
    private StringBuilder commandUnequip(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi disequipaggiare un solo oggetto alla volta!");
        } else if (p.getObject1() != null) {
            output.append("Non hai equipaggiato questo oggetto!");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi disequipaggiare un personaggio!");
        } else if (p.getEnemy1() != null) {
            output.append("Non puoi disequipaggiare un nemico!");
        } else if (p.getInvObject1() == null && !p.hasExtraWords()) {
            output.append("Specifica quale oggetto disequipaggiare.");
        } else if (p.getInvObject1() == null && p.hasExtraWords()) {
            output.append("Non hai equipaggiato nulla del genere...");
        } else if (!p.getInvObject1().isEquippable()) {
            output.append("Questo oggetto non e' equipaggiabile!");
        } else if (!p.getInvObject1().isEquipped()) {
            output.append("Non hai equipaggiato questo oggetto!");
        } else {
            p.getInvObject1().setEquipped(false);
            output.append("Hai disequipaggiato: " + p.getInvObject1().getName());
            switch (p.getInvObject1().getId()) {
                case ID_OBJECT_GLASSES:
                    for (AdvObject o : getRooms().get(ID_ROOM_DIAMOND_ROOM).getObjects()) {
                        if (o.getId() == ID_OBJECT_DIAMOND && !eventDiamondTaken) {
                            o.setReachable(false);
                        }
                    }
            }
        }

        return output;
    }
   
    private StringBuilder commandTalkTo(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi parlare con un solo personaggio alla volta!");
        } else if (p.getObject1() != null) {
            output.append("Non puoi parlare con un oggetto!");
        } else if (p.getInvObject1() != null) {
            output.append("Non puoi parlare con un oggetto nel tuo inventario!");
        } else if (p.getEnemy1() != null) {
            output.append("Non puoi parlare con un nemico!");
        } else if (p.getCharacter1() != null) {
            if (p.getCharacter1().isFirstDialogue()) {
                p.getCharacter1().setFirstDialogueFlag(false);
                output.append(p.getCharacter1().getFirstDialogue());
                if (p.getCharacter1().getId() == ID_CHARACTER_HECTOR) {
                    getInventory().add(p.getCharacter1().getFromInventory(ID_OBJECT_GUN_AMMO));
                    p.getCharacter1().removeFromInventory(ID_OBJECT_GUN_AMMO);
                    eventGotAmmo = true;
                }
            } else {
                switch (p.getCharacter1().getId()) {
                    case ID_CHARACTER_HECTOR:
                        boolean diamondFound = false;
                        for (AdvObject o : getInventory()) {
                            if (o.getId() == ID_OBJECT_DIAMOND) {
                                diamondFound = true;
                            }
                        }
                        if (diamondFound) {
                            output.append(p.getCharacter1().getDialogue("gotDiamond"));
                        } else {
                            output.append(p.getCharacter1().getDialogue("idle"));
                        }
                        break;
                }
            }
        } else if (p.hasExtraWords()) {
            output.append("Non ho trovato nessuno del genere qui... sara' un tuo amico immaginario.");
        } else {
            output.append("Specifica con chi vuoi parlare!");
        }

        return output;
    }
    
    private StringBuilder commandGive(ParserOutput p, StringBuilder output) {
        if (p.getInvObject1() != null) {
            if (p.getCharacter2() != null) {
                if (!p.getInvObject1().isEquipped()) {
                    switch (p.getCharacter2().getId()) {
                        case ID_CHARACTER_HECTOR:
                            if (p.getInvObject1().getId() == ID_OBJECT_DIAMOND) {
                                p.getCharacter2().addInventory(p.getInvObject1());
                                getInventory().remove(p.getInvObject1());
                                output.append(p.getCharacter2().getDialogue("giveCorrect"));
                                setEnd(true);
                            } else {
                                output.append(p.getCharacter2().getDialogue("giveWrong"));
                            }
                            break;
                    }
                } else {
                    output.append("Disequipaggia l'oggetto prima di darlo a qualcuno!");
                }
            } else if (p.getObject2() != null) {
                output.append("Non puoi dare un oggetto a un oggetto!");
            } else if (p.getInvObject2() != null) {
                output.append("Non puoi dare un oggetto a un oggetto del tuo inventario!");
            } else if (p.getEnemy2() != null) {
                output.append("Non puoi dare un oggetto a un nemico!");
            } else if (p.hasExtraWords()) {
                output.append("Non ho trovato un personaggio a cui dare l'oggetto!");
            } else {
                output.append("Specifica un personaggio a cui dare questo oggetto.");
            }
        } else if (p.getObject1() != null) {
            output.append("Non possiedi questo oggetto!");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi dare un personaggio!");
        } else if (p.getEnemy1() != null) {
            output.append("Non puoi dare un nemico!");
        } else if (p.hasExtraWords()) {
            output.append("Non hai niente del genere da dare...");
        } else {
            output.append("Specifica un oggetto da dare a un personaggio.");
        }

        return output;
    }
    
    private StringBuilder commandUse(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi usare un solo oggetto alla volta!");
        } else if (p.getObject1() != null && p.getObject1().getId() != ID_OBJECT_CAMERA_CONTROLLER) {
            output.append("Non puoi usare questo oggetto!");
        } else if (p.getInvObject1() != null && p.getInvObject1().getId() != ID_OBJECT_FIRST_AID) {
            output.append("Non puoi usare questo oggetto!");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi usare un personaggio!");
        } else if (p.getEnemy1() != null) {
            output.append("Non puoi usare un nemico!");
        } else if (p.getInvObject1() != null && p.getInvObject1().getId() == ID_OBJECT_FIRST_AID) {
            if(health+KIT_HEAL_AMOUNT <= MAX_HEALTH) {
                health += KIT_HEAL_AMOUNT;
            } else {
                health = 5;
            }

            List<AdvObject> inv = getInventory();
            if (!inv.isEmpty()) {
                Iterator<AdvObject> it = inv.iterator();
                while (it.hasNext()) {
                    AdvObject next = it.next();
                    if (next.getId() == ID_OBJECT_FIRST_AID) {
                        it.remove();
                    }
                }
            }

            output.append("Ti sei curato con il kit di pronto soccorso, recuperando un po' di salute.");
            output.append("\nSalute attuale: " + health);

        } else if (p.getObject1() != null && p.getObject1().getId() == ID_OBJECT_CAMERA_CONTROLLER) {
            if (!eventTurnedOffCamera) {
                for (AdvNPC npc : getRooms().get(ID_ROOM_DIAMOND_ROOM).getNPCs()) {
                    if (npc.getId() == ID_ENEMY_CAMERA) {
                        ((AdvEnemy)npc).setAlive(false);
                    }
                }
                getRooms().get(ID_ROOM_CAMERA_ROOM).setLook("Una stanza ancora piu' buia di prima con tanti monitor spenti.\nOra si' che puoi agire indisturbato!");
                eventTurnedOffCamera = true;
                output.append("Hai disattivato le telecamere!");
            } else {
                output.append("Quante volte hai intenzione di disattivare quelle telecamere?");
            }

        } else if (p.hasExtraWords()) {
            output.append("Oggetto non trovato!");
        } else {
            output.append("Specifica cosa vuoi usare.");
        }

        return output;
    }
   
    private StringBuilder commandCut(ParserOutput p, StringBuilder output) {
        boolean found = false;
            for (AdvObject o1 : getInventory()) {
                if (o1.getId() == ID_OBJECT_KNIFE) {
                    found = true;
                    if (o1.isEquipped()) {
                        if (p.getObject1() != null && p.getObject1().getId() == ID_OBJECT_FINGER
                                && p.getEnemy2() != null && !p.getEnemy2().isAlive()
                                && (p.getEnemy2().getId() == ID_ENEMY_GUARD1 || p.getEnemy2().getId() == ID_ENEMY_GUARD2)
                                && !eventCutFinger) {

                            for (AdvObject o2 : getCurrentRoom().getObjects()) {
                                if (o2.getId() == ID_OBJECT_FINGER) {
                                    o2.setReachable(true);
                                    eventCutFinger = true;
                                    output.append("Potresti privare questo pover'uomo della sua mano o addirittura del suo intero braccio...\n"
                                            + "ma sei cosi' misericordioso da privarlo solo di un suo dito.\n"
                                            + "Non che faccia una grossa differenza, considerando che e' morto.");
                                }
                            }

                        } else if (p.getEnemy1() != null && p.getEnemy1().isAlive()
                                && p.getObject2() == null && p.getInvObject2() == null && p.getCharacter2() == null && p.getEnemy2() == null) {
                            if (p.getEnemy1().getId() != ID_ENEMY_CAMERA) {
                                output.append("Provi a tagliare il nemico, ma schiva il tuo colpo...");
                            } else {
                                output.append("La telecamera e' troppo in alto perche' tu la possa raggiungere!");
                            }

                        } else if (p.getEnemy1() != null && !p.getEnemy1().isAlive()
                                && (p.getEnemy1().getId() == ID_ENEMY_GUARD1 || p.getEnemy1().getId() == ID_ENEMY_GUARD2)) {
                            output.append("La tua risolutezza e' notevole, ma faresti bene a specificare meglio le tue intenzioni...");
                        } else if (p.getObject1() != null && p.getObject1().getId() == ID_OBJECT_FINGER
                                && p.getObject2() == null && p.getInvObject2() == null && p.getCharacter2() == null && p.getEnemy2() == null) {
                            output.append("Fossi in te disambiguerei... Non vorrai tagliare il tuo stesso dito!");

                        } else {
                            output.append("Stai calmo con quel coltello...");
                        }
                    } else {
                        output.append("Equipaggia il coltello per tagliare!");
                    }
                }
            }
            if (!found && getInventory().isEmpty()) {
                output.append("Con cosa pensi di tagliare se non hai nulla nell'inventario?");
            } else if (!found) {
                output.append("Ti servirebbe un coltello per tagliare...");
            }

        return output;
    }
    
    private StringBuilder commandTurnOff(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi spegnere un solo oggetto alla volta!");
        } else if (p.getObject1() != null && p.getObject1().getId() == ID_OBJECT_CAMERA_CONTROLLER) {
            if (!eventTurnedOffCamera) {
                for (AdvNPC npc : getRooms().get(ID_ROOM_DIAMOND_ROOM).getNPCs()) {
                    if (npc.getId() == ID_ENEMY_CAMERA) {
                        ((AdvEnemy)npc).setAlive(false);
                    }
                }
                getRooms().get(ID_ROOM_CAMERA_ROOM).setLook("Una stanza ancora piu' buia di prima con tanti monitor spenti.\nOra si' che puoi agire indisturbato!");
                eventTurnedOffCamera = true;
                output.append("Hai disattivato le telecamere!");
            } else {
                output.append("Quante volte hai intenzione di disattivare quelle telecamere?");
            }
        } else {
            output.append("Cosa stai cercando di spegnere?");
        }

        return output;
    }
    
    private StringBuilder commandReload(ParserOutput p, StringBuilder output) {
        if (p.getObject2() != null || p.getInvObject2() != null || p.getCharacter2() != null || p.getEnemy2() != null) {
            output.append("Puoi ricaricare un solo oggetto alla volta!");
        } else if (p.getInvObject1() != null && p.getInvObject1().getId() == ID_OBJECT_GUN) {
            int gunIndex = -1;
            int ammoIndex = -1;
            for (int i = 0; i < getInventory().size(); i++) {
                if (getInventory().get(i).getId() == ID_OBJECT_GUN && getInventory().get(i).getContained() == -1) {
                    gunIndex = i;

                } else if (getInventory().get(i).getId() == ID_OBJECT_GUN_AMMO && getInventory().get(i).getContained() == -1) {
                    ammoIndex = i;
                }
            }
            if (gunIndex != -1 && ammoIndex != -1) {
                getInventory().get(gunIndex).setOpen(true);
                ((AdvObjectContainer) getInventory().get(gunIndex)).add(getInventory().get(ammoIndex));
                getInventory().remove(ammoIndex);
                getInventory().get(gunIndex).setOpen(false);
                eventLoadedGun = true;
                output.append("Hai ricaricato la pistola!");
            } else {
                if (ammoIndex == -1) {
                    output.append("Non ho trovato proiettili con cui ricaricare!");
                }
            }
        } else if (p.getObject1() != null && p.getObject1().getId() != ID_OBJECT_GUN
                || p.getInvObject1() != null || p.getCharacter1() != null || p.getEnemy1() != null) {
            output.append("Non puoi ricaricare questo oggetto!");
        } else if (p.getObject1() != null && p.getObject1().getId() == ID_OBJECT_GUN) {
            output.append("Prendi la pistola prima di ricaricarla...");
        } else if (p.hasExtraWords()) {
            output.append("Non vedo niente del genere qui...");
        } else {
            output.append("Specifica cosa ricaricare!");
        }

        return output;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------------------------
    
    public void save() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("HeistGame.dat"));
        out.writeObject(this);
        out.close();
    }

    public GameDescription load() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("HeistGame.dat"));
        HeistGame game = (HeistGame) in.readObject();
        in.close();
        return game;
    }

}

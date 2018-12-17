package LogicBot;

public abstract class AbstractLogicBot {

    abstract public void send (Integer id, String Messages);

    abstract public void sendVoiceMessage (Integer id, String messages);

    abstract public void sendKeyboard ();

}

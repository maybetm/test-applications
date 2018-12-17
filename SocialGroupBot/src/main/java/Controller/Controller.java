package Controller;


import Application.Settings.BotSettings;

public class Controller extends AbstractController {

    public Controller() {
        setActor(new Integer(
                 new BotSettings().getProperty("app_id")),
                 new BotSettings().getProperty("token_group_global"));
        start();
    }


}

package Controller;

import com.vk.api.sdk.client.actors.GroupActor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class AbstractController extends Thread {

    public GroupActor actor = null;
    public Logger logger = LogManager.getLogger(AbstractController.class);

    public void setActor (Integer appID, String token) {
        actor = new GroupActor(appID, token);
    }

    @Override
    public void run() {

        while (true) {
            logger.debug("play Thread");
        }

    }
}

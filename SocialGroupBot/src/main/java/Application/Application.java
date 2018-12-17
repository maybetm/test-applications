package Application;

import Application.Settings.BotSettings;
import Controller.Controller;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.apache.logging.log4j.Logger;


public class Application {

    private static Logger logger;


    public static void main(String[] args) throws ClientException, ApiException {
//        logger = LogManager.getLogger(Application.class);
//        logger.debug(Application.class);

        new Controller();

        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        GroupActor actor = new GroupActor(new Integer( new BotSettings().getProperty("app_id")),
                new BotSettings().getProperty("token_group_global"));

        System.out.println("test respons: " + vk.messages().send(actor).peerId(140858629).message("test").execute());
        // TODO Auto-generated method stub

    }

}

import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.SlackClientFactory;
import com.hubspot.slack.client.SlackClientRuntimeConfig;

public class BasicRuntimeConfig {

    public static SlackClient getClient() {
        return SlackClientFactory.defaultFactory().build(get());
    }

    public static SlackClientRuntimeConfig get() {
        return SlackClientRuntimeConfig.builder()
                .setTokenSupplier(() -> "xoxb-1529342876677-1539263615285-xHCr8Vq7CPZbRZBg1pYYapPt")
                .build();
    }

}

import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.SlackClientFactory;
import com.hubspot.slack.client.SlackClientRuntimeConfig;

public class BasicRuntimeConfig {

    public static SlackClient getClient() {
        return SlackClientFactory.defaultFactory().build(get());
    }

    public static SlackClientRuntimeConfig get() {
        return SlackClientRuntimeConfig.builder()
                .setTokenSupplier(() -> "xoxb-1529342876677-1540500152644-37z28we99vW4cKlDBsTc1oBl")
                .build();
    }
}
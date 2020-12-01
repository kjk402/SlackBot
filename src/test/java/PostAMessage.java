
import com.hubspot.algebra.Result;
import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import com.hubspot.slack.client.methods.params.files.FilesUploadParams;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.chat.ChatPostMessageResponse;
import com.hubspot.slack.client.models.response.files.FilesUploadResponse;
import org.json.simple.parser.ParseException;


import javax.swing.*;
import java.io.IOException;
import java.util.Collections;

public class PostAMessage {

    //private static final Logger LOG = LoggerFactory.getLogger(PostAMessage.class);

    public static ChatPostMessageResponse messageChannel(String channelToPostIn,String Emoji,String UserName, SlackClient slackClient, String text) {
        String rr = ":umb:";
        Boolean bbb = Boolean.parseBoolean(rr);
        Result<ChatPostMessageResponse, SlackError> postResult = slackClient.postMessage(
                ChatPostMessageParams.builder()
                        .setText(text)
                        .setIconEmoji(Emoji)
                        .setUsername(UserName)
                        .setChannelId(channelToPostIn)
                        .build()
        ).join();
        return postResult.unwrapOrElseThrow(); // release failure here as a RTE
    }
    public static SlackError upload(String channelToPostIn, SlackClient slackClient, String filename,String content) {
        Result<FilesUploadResponse, SlackError> post = slackClient.uploadFile(FilesUploadParams.builder()
                .setChannels(Collections.singletonList(channelToPostIn))
                .setFilename(filename+".txt")
                .setTitle(filename)
                .setContent(content)
                .build()).join();
        return post.unwrapErrOrElseThrow();
    }



    public static void main(String[] args) throws IOException, ParseException {
    //public void testSlackChatMessage() {

        SlackClient client = BasicRuntimeConfig.getClient();

        ChatPostMessageResponse response = messageChannel("일반", ":umb:","TEst",client, "안녕하세요");
        //LOG.info("Got: {}", response);
        SlackError upload = upload("일반",client,"test.txt","cccc");

    }

}

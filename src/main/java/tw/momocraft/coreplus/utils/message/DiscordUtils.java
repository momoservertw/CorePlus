package tw.momocraft.coreplus.utils.message;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;

import java.util.Map;

public class DiscordUtils {

    public void sendDiscordMsg(String channelName, String input) {
        String channelId = getChannelId(channelName);
        if (channelId == null)
            return;
        DiscordUtil.sendMessage(getTextChannel(channelId), input);
    }

    public Map<String, String> getChannelMap() {
        return DiscordSRV.getPlugin().getChannels();
    }

    public String getChannelId(String channelName) {
        return DiscordSRV.getPlugin().getChannels().get(channelName);
    }

    public TextChannel getTextChannel(String channelId) {
        return DiscordUtil.getTextChannelById(channelId);
    }

    public boolean isChannelExist(String channelName) {
        return getChannelId(channelName) != null;
    }
}

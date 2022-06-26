package tw.momocraft.coreplus.utils.message;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;

import java.util.Map;
import java.util.UUID;

public class DiscordUtils {

    public void sendMsg(String channelName, String input) {
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

    public void setMemberNick(UUID uuid, String name) {
        String memberID = DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(uuid);
        DiscordUtil.getMemberById(memberID).modifyNickname(name);
    }

    public String getMemberNick(UUID uuid) {
        String memberID = DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(uuid);
        return DiscordUtil.getMemberById(memberID).getNickname();
    }

    public String getName(UUID uuid) {
        String memberID = DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(uuid);
        return DiscordUtil.getMemberById(memberID).getUser().getName();
    }
}

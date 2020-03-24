package net.newlydev.qqrobot.demo;

import net.newlydev.qqrobot.PCTIM.sdk.API;
import net.newlydev.qqrobot.PCTIM.sdk.Plugin;
import net.newlydev.qqrobot.PCTIM.sdk.QQMessage;

public class Main implements Plugin {
    private API api;
    @Override
    public String author() {
        return null;
    }

    @Override
    public String Version() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void onLoad(API api) {
        this.api=api;
    }

    @Override
    public void onMessageHandler(QQMessage message) {
        if("test".equals(message.Message))
        {
            api.SendGroupTextMessage(message.Group_uin,"ok");
        }
    }
}

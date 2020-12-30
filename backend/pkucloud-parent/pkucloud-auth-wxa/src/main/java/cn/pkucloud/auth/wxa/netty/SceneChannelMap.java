package cn.pkucloud.auth.wxa.netty;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SceneChannelMap {
    private static final Map<String, Channel> map = new ConcurrentHashMap<>();

    public static void put(String scene, Channel channel) {
        map.put(scene, channel);
        System.out.println("map = " + map);
    }

    public static void removeByScene(String scene) {
        map.remove(scene);
    }

    public static String removeByChannelIdStr(String channelIdStr) {
        for (String scene : map.keySet()) {
            Channel channel = map.get(scene);
            if (channel.id().asLongText().equals(channelIdStr)) {
                map.remove(scene);
                return scene;
            }
        }
        return null;
    }

    public static Channel get(String scene) {
        return map.get(scene);
    }
}

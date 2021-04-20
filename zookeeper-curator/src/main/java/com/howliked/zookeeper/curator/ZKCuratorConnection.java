package com.howliked.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

public class ZKCuratorConnection {
    private static final String CONN_STRING = "127.0.0.1:2181";
    private static final String ROOT_NODE = "/howliked-curator";

    public static void main(String[] args) {
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString(CONN_STRING).build();
        //启动curator
        zkClient.start();
        //一、创建root节点
    }
}

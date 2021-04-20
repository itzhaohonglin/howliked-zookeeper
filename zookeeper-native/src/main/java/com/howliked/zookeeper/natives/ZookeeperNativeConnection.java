package com.howliked.zookeeper.natives;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperNativeConnection {
    private static final String CONN_STRING = "localhost:2181";
    private static final String ROOT_NODE = "/howliked-zookeeper";
    private static final String ROOT_1 = "/howliked-zookeeper/zk1";
    private static final String ROOT_2 = "/howliked-zookeeper/zk2";
    private static final String ROOT_3 = "/howliked-zookeeper/zk3";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(CONN_STRING, 5000, new ZKConnWatch());
            countDownLatch.await();
            Stat exists = zooKeeper.exists(ROOT_1, true);
            if (exists == null) {
                String s = zooKeeper.create(ROOT_1, "This is a Root 1 ZNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                System.out.println("create " + ROOT_1 + " zNode, result =" + s);
            } else {
                zooKeeper.delete(ROOT_1, -1);
            }
            System.out.println("获取" + ROOT_NODE + "节点下的值:" + zooKeeper.getChildren(ROOT_NODE, true));
            System.out.println("获取" + ROOT_NODE + "节点下的值大小:" + zooKeeper.getChildren(ROOT_NODE, true).size());
            System.out.println("finished!");
        } catch (IOException e) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    private static class ZKConnWatch implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            Event.EventType type = event.getType();
            Event.KeeperState state = event.getState();
            if (state == Event.KeeperState.SyncConnected) {
                if (type == Event.EventType.None) {
                    System.out.println("监听到None path=" + event.getPath());
                } else if (type == Event.EventType.NodeDataChanged) {
                    System.out.println("监听到NodeDataChanged path=" + event.getPath());
                } else if (type == Event.EventType.NodeDeleted) {
                    System.out.println("监听到NodeDeleted path=" + event.getPath());
                } else if (type == Event.EventType.NodeChildrenChanged) {
                    System.out.println("监听到NodeChildrenChanged path=" + event.getPath());
                } else if (type == Event.EventType.NodeCreated) {
                    System.out.println("监听到NodeCreated path=" + event.getPath());
                }
            }
            countDownLatch.countDown();
        }
    }
}

package com.howliked.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.TimeUnit;

public class ZKClientConnection {
    private static final String CONN_STRING = "127.0.0.1:2181";
    private static final String ROOT_NODE = "/howliked-zkclient";

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(CONN_STRING, 5000);
        zkClient.subscribeDataChanges(ROOT_NODE, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(String.format("handleDataChange. dataPath:%s,o=%s",s,o));
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(String.format("handleDataDeleted.dataPath:%s",s));
            }
        });
        //一、创建root node节点
        //1.1 自定义创建模式节点 - 持久化模式
//        zkClient.create(ROOT_NODE, "This is a customer createMode", CreateMode.PERSISTENT);
        //1.2创建指定持久化节点方式
//        zkClient.createPersistentSequential(ROOT_NODE+"/1", "This is a root/1 znode");

        //二、更新
//        zkClient.writeData(ROOT_NODE, "This is a write data");
        //2.1更新带版本号
//        zkClient.writeData(ROOT_NODE, "This is a write data",-1);
        //2.2 更新带版本号返回stat
//        Stat stat = zkClient.writeDataReturnStat(ROOT_NODE, "update data", -1);
//        System.out.println(String.format("aversion:%s - ctime:%s -version:%s", stat.getAversion(),stat.getCtime(),stat.getVersion()));
        //三、查询
//        Object o = zkClient.readData(ROOT_NODE);
//        System.out.println("readData(path):" + o);
//        Object os = zkClient.readData(ROOT_NODE, stat);
//        System.out.println(String.format("aversion:%s - ctime:%s -version:%s", stat.getAversion(), stat.getCtime(), stat.getVersion()));
//        Object o1 = zkClient.readData(ROOT_NODE + 1, true);
//        System.out.println("readData(path,boolean):" + o1);
        //四、删除
        boolean delete = zkClient.deleteRecursive(ROOT_NODE);
        System.out.println("delete result = "+delete);
        try {
            //休息一下，可以异步接收到watch
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
        }
    }
}

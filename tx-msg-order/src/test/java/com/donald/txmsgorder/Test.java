package com.donald.txmsgorder;

import java.sql.SQLException;
import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @author donald
 * @date 2022/07/07
 */
public class Test {

    public static void main(String[] args) throws SQLException {
        // 创建订单库 RM实例
        Connection orderConnection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/tx_msg_order?useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true&autoReconnect=true&failOverReadOnly=false&useSSL=false",
                "test", "test");
        // 这里的这个true参数，是说打印出来XA分布式事务的一些日志
        XAConnection orderXAConnection = new MysqlXAConnection(
                (com.mysql.jdbc.Connection)orderConnection, true);
        // 这个XAResource其实你可以认为是RM（Resource Manager）的一个代码中的对象实例
        XAResource orderResource = orderXAConnection.getXAResource();

        // 创建库存库 的RM实例
        Connection stockConnection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/tx_msg_stock?useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true&autoReconnect=true&failOverReadOnly=false&useSSL=false",
                "test", "test");
        XAConnection stockXAConnection = new MysqlXAConnection(
                (com.mysql.jdbc.Connection)stockConnection, true);
        XAResource stockResource = stockXAConnection.getXAResource();

        // 下面俩东西是分布式事务id（txid）的构成部分
        byte[] gtrid = "g12345".getBytes();
        int formatId = 1;

        try {
            // 这是说在分布式事务中的订单库的子事务的标识
            // 我们在订单库要执行的操作隶属于分布式事务的一个子事务，子事务有自己的一个标识
            byte[] bqual1 = "b00001".getBytes();
            Xid xid1 = new MysqlXid(gtrid, bqual1, formatId); // 这个xid代表了订单库中的子事务

            // 这就是说通过START和END两个操作，定义好了分布式事务中，订单库中要执行的SQL语句
            // 但是这里的SQL绝对不会执行的，只是说先定义好我要在分布式事务中，这个数据库里要执行哪些SQL语句
            orderResource.start(xid1, XAResource.TMNOFLAGS);
            PreparedStatement orderPreparedStatement = orderConnection.prepareStatement(
                    "INSERT INTO `order` (id, create_time, order_no, product_id,  pay_count) " +
                            "VALUES (1, NOW(), 1, 1, 1)");
            orderPreparedStatement.execute();
            orderResource.end(xid1, XAResource.TMSUCCESS);

            // 这是说在分布式事务中的库存库的子事务的标识
            // 大家看下，库存库的子事务的xid中的，gtrid和formatId是一样的，bqual是不一样的
            // 在一个分布式事务中，涉及到多个数据库的子事务，每个子事务的txid，有一部分是一样的，一部分是不一样的
            byte[] bqual2 = "b00002".getBytes();
            Xid xid2 = new MysqlXid(gtrid, bqual2, formatId);
            // 这就是说通过START和END两个操作，定义好了分布式事务中，库存库中要执行的SQL语句
            stockResource.start(xid2, XAResource.TMNOFLAGS);
            PreparedStatement stockPreparedStatement = stockConnection.prepareStatement(
                    "UPDATE stock SET total_count = total_count - 1 where id = 1");
            stockPreparedStatement.execute();
            stockResource.end(xid2, XAResource.TMSUCCESS);

            // 到这里为止，其实还啥都没干呢，不过就是定义了分布式事务中的两个库要执行的SQL语句罢了

            // 2PC的阶段一：向两个库都发送prepare消息，执行事务中的SQL语句，但是不提交
            int orderPrepareResult = orderResource.prepare(xid1);
            int stockPrepareResult = stockResource.prepare(xid2);

            // 2PC的阶段二：两个库都发送commit消息，提交事务
            // 如果两个库对prepare都返回ok，那么就全部commit，对每个库都发送commit消息，完成自己本地事务的提交
            if (orderPrepareResult == XAResource.XA_OK
                    && stockPrepareResult == XAResource.XA_OK) {
                orderResource.commit(xid1, false);
                stockResource.commit(xid2, false);
            } else {
                // 如果如果不是所有库都对prepare返回ok，那么就全部rollback
                orderResource.rollback(xid1);
                stockResource.rollback(xid2);
            }
        } catch (XAException e) {
            e.printStackTrace();
        }
    }
}

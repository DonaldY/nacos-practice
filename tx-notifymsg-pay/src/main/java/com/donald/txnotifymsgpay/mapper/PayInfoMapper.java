package com.donald.txnotifymsgpay.mapper;

import com.donald.txnotifymsgpay.model.PayInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author donald
 * @date 2022/05/08
 */
public interface PayInfoMapper {

    /**
     * 查询是否存在充值记录
     */
    Integer isExistsPayInfo(@Param("txNo") String txNo);

    /**
     * 保存充值记录
     */
    int savePayInfo(@Param("payInfo") PayInfo payInfo);

    /**
     * 查询指定的充值信息
     */
    PayInfo getPayInfoByTxNo(@Param("txNo") String txNo);
}

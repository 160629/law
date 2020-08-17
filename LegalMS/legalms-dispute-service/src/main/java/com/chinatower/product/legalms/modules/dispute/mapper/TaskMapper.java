package com.chinatower.product.legalms.modules.dispute.mapper;


import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.dispute.entity.TaskNumVO;

import java.util.List;

/**
 * @author 刘晓亮
 * @create 2019-10-30 15:49
 */
public interface TaskMapper {

    int insertTask(@Param("taskNumVOS") List<TaskNumVO> taskNumVOS);

    List<TaskNumVO> queryPersonTaskCount(@Param("userId") String userId, @Param("lastUpdateDate") String lastUpdateDate);
}

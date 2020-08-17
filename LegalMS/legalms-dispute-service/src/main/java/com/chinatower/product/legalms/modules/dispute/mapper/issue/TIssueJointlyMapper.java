package com.chinatower.product.legalms.modules.dispute.mapper.issue;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueJointly;
import com.chinatower.product.legalms.modules.dispute.vo.jointly.JointlyParam;
import com.chinatower.product.legalms.modules.dispute.vo.jointly.JointlyVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueJointlyListVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowLogVO;
public interface TIssueJointlyMapper {

    /**
     * 根据主键删除（物理删除）
     * @param jointlyId
     * @return
     */
    int deleteByPrimaryKey(String jointlyId);

    JointlyVO findOneTIssueJointly(String jointlyId);

    /**
     * 查询下一个编号
     * @param unitCode 单位编码
     * @param orgCode   组织编码
     * @return
     */
    String selectCode(@Param("unitCode") String unitCode, @Param("orgCode") String orgCode);
    /**
     * 案件协办单列表查询
     */
    List<TIssueJointly> selectAll(@Param("param") JointlyParam param, @Param("loginAcct") String loginAcct);

    /**
     * 案件协办更新
     * @param
     * @return
     */
    int updateByPrimaryKeySelective(@Param("jointly")TIssueJointly jointly);


    /**
     * 案件协办单起草
     * @param tIssueJointly
     * @return
     */
    int insertSelective(@Param("tIssueJointly") TIssueJointly tIssueJointly);


    /**
     * 查询当前活动
     * @param flowId
     * @return
     */
    TFlowLogVO selectCurrActivity(Long flowId);

    int deleteTIssueJointly(List<String> jointlyIds);

    List<TIssueJointly> findTIssueJointlyList(@Param("param")JointlyParam param);

    TIssueJointly selectByPrimaryKey(String jointlyId);

    List<TIssueJointly> selectAlles(JointlyParam param, String loginAcct);

    int updateByPrimaryKey(@Param("jointly")TIssueJointly jointly);

    List<TIssueJointly> selectWholeData(@Param("param") TIssueJointlyListVO param);

    String getHeadOrgCode(String enterpType);
}
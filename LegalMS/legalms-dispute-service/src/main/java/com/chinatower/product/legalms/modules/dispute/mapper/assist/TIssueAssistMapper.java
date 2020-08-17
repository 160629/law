package com.chinatower.product.legalms.modules.dispute.mapper.assist;


import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssist;
import com.chinatower.product.legalms.modules.dispute.vo.assist.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TIssueAssistMapper {
    /**
     * 查询编号
     * @param unitName 单位编码
     * @param moduleCode 业务类别编码
     * @param orgCode 公司编码
     * @return
     */
    String selectCode(@Param("unitName") String unitName, @Param("moduleCode") String moduleCode, @Param("orgCode") String orgCode);

    /**
     * 新增
     * @param model
     * @return
     */
    int insertSelective(@Param("model") TIssueAssist model);

    /**
     * 修改
     * @param model
     * @return
     */
    int updateByPrimaryKey(@Param("model") TIssueAssist model);

    /**
     * 列表查询
     * @param param
     * @return
     */
    List<TIssueAssist> selectAll(@Param("param") SelectAllListParamVO param, @Param("info") UserInfo info);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteTIssueAssist(@Param("ids") List<String> ids);

    /**
     * 协助执行流程关联单查询
     * @param param
     * @return
     */
    List<TIssueAssist> selectRelationshipList(@Param("param") SelectRelationshipListParamVO param, @Param("info") UserInfo info, @Param("relations") String[] relations);

    /**
     * 根据主键查询协助执行业务主表
     * @param id
     * @return
     */
    TIssueAssist selectByPrimaryKey(@Param("id") String id);

    /**
     * 根据业务主键查询表单关联的文件列表
     * @param id
     * @return
     */
    List<Map<String, Object>> selectFile(@Param("id") String id);

    /**
     * 查询业务主表详情及流程日志
     * @param id
     * @return
     */
    TIssueAssistVO findOne(@Param("id") String id);

    List<TIssueAssist> selectWholeData(@Param("param") FindTIssueAssistListListParamVO param);

    String getHeadOrgCode(@Param("enterpType") String enterpType);

    List<TIssueAssist> selectByLogs(@Param("param") FindTIssueAssistListListParamVO param, @Param("info") UserInfo info);

    String selectOrgLevelByOrgId(@Param("orgId") String orgId);

    List<TIssueAssist> selectRelationshipListInCreate(@Param("param") SelectRelationshipListInCreateParamVO param, @Param("info") UserInfo info);

    List<Map<String, String>> selectAssistIdAndTitleByAssistIds(@Param("ids") String[] ids);

    List<Map<String, Object>> selectFileByIds(@Param("fileIds") String[] fileIds);

    TIssueAssist selectBySubFlowId(@Param("flowId") String flowId);

    List<TIssueAssist> selectAllUnit(@Param("param") FindTIssueAssistListListParamVO param, @Param("info") UserInfo info);
}

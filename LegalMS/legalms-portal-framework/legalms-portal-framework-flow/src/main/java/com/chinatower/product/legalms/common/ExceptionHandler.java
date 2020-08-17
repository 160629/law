package com.chinatower.product.legalms.common;

/**
 * @author 刘晓亮
 */
public class ExceptionHandler {
    private static final String MESSAGE = "Message: ";
    public static String flowExceptionFilter(Exception e) {
        try {
            String message = e.getMessage();
            if (message.contains("21050002")) {
                String user = message.substring(message.indexOf(MESSAGE, 0)).substring(message.substring(message.indexOf(MESSAGE, 0)).indexOf('[', 0), message.substring(message.indexOf(MESSAGE, 0)).indexOf(']', 0) + 1).trim();
                return ", 当前登录账号为" + user + "没有该环节审批权限";
            }
            if (message.contains("workitem state should be running(10) or wait_receive(4)")) {
                return "当前环节不是运行状态，无法审批";
            }
            return "";
        } catch (Exception e1) {
            return "";
        }
    }
    private ExceptionHandler(){

    }
}

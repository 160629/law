package com.chinatower.product.legalms.common;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;



@Component
@Aspect
public class JobAspect {
	private static final  Logger logger = LoggerFactory.getLogger(JobAspect.class);
	
	
	private String targetName;
	
	@Pointcut("@within(com.chinatower.product.legalms.common.JobAround)")
	public void jobPointcut() {
		logger.info("定时工作切入点");
	}
	
	@Around("jobPointcut()")
	public void around(ProceedingJoinPoint point) throws Throwable{
		logger.info("开始定时工作");
		Class<? extends Object> pclass = point.getTarget().getClass();
        targetName = pclass.getName();
        JobAround jobBefore = getJobBefore();

    	if(isMini(jobBefore.serviceName())) {
    		point.proceed();
    	}
	}
	
	@Autowired
	DiscoveryClient discoveryClient;
	
    public  long ipToLong(String ipAddress) {
        long result = 0;
        String[] ipAddressInArray = ipAddress.split("\\.");
        for (int i = 3; i >= 0; i--) {
            long ip = Long.parseLong(ipAddressInArray[3 - i]);
            result |= ip << (i * 8);
        }
        return result;
    }
    /**
     * 获取当前机器的IP
     *
     * @return
     */
    public  String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> enumNic = NetworkInterface.getNetworkInterfaces();
                 enumNic.hasMoreElements(); ) {
                NetworkInterface ifc = enumNic.nextElement();
                if (ifc.isUp()) {
                    for (Enumeration<InetAddress> enumAddr = ifc.getInetAddresses();
                         enumAddr.hasMoreElements(); ) {
                        InetAddress address = enumAddr.nextElement();
                        if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                            return address.getHostAddress();
                        }
                    }
                }
            }
            return InetAddress.getLocalHost().getHostAddress();
        } catch (IOException e) {
        	logger.info(e.getMessage());
        }
        return null;
    }
    public  boolean ipCompare(List<URI> serviceUrl) {
        try {
            String localIpStr = SpringContextUtil.getLocalIpStr();
    		logger.info("定时工作服务地址：{}",localIpStr);
            long localIpLong = ipToLong(localIpStr);
            int size = serviceUrl.size();
            if (size == 0) {
                return false;
            }
 
            Long[] longHost = new Long[size];
            for (int i = 0; i < serviceUrl.size(); i++) {
                String host = serviceUrl.get(i).getHost();
                logger.info("定时工作服务集群{}号地址：{}",i,host);
                longHost[i] = ipToLong(host);
            }
            Arrays.sort(longHost);
            if (localIpLong == longHost[0]) {
                return true;
            }
        } catch (Exception e) {
        	logger.info(e.getMessage());
        }
        return false;
    }
    
    public  boolean isMini(String serviceName) {
      	List<ServiceInstance> list = discoveryClient.getInstances(serviceName);
        List<URI> urlList = new ArrayList<>();
        if (null!=list && !list.isEmpty()) {
        	list.forEach(si -> urlList.add(si.getUri()));
        }
        return ipCompare(urlList);
    }
    
	public JobAround getJobBefore() {
		try {
			Class targetClass = Class.forName(targetName);
			return (JobAround) targetClass.getAnnotation(JobAround.class);
		} catch (Exception e) {
		  	logger.info(e.getMessage());
		}
		return null;
	}
}

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface JobAround {
    String serviceName() default "";
    String localIpStr() default "";
}

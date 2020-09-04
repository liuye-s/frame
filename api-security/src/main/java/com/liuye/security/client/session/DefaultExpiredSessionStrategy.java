package com.liuye.security.client.session;

import com.liuye.security.core.properties.SecurityProperties;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 * @program: baseframe
 * @description 并发登录导致session失效时，默认的处理策略
 * @author: liuhui
 * @create: 2019-05-19 11:06
 **/
public class DefaultExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public DefaultExpiredSessionStrategy(SecurityProperties securityPropertie) {
        super(securityPropertie);
    }



    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }


    @Override
    protected boolean isConcurrency() {
        return true;
    }
}

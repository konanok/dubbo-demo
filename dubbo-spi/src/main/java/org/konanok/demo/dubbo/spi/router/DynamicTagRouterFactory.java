package org.konanok.demo.dubbo.spi.router;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.cluster.Router;
import org.apache.dubbo.rpc.cluster.RouterFactory;

@Activate(group = CommonConstants.CONSUMER_SIDE)
public class DynamicTagRouterFactory implements RouterFactory {

    @Override
    public Router getRouter(URL url) {
        return new DynamicTagRouter(url);
    }

}

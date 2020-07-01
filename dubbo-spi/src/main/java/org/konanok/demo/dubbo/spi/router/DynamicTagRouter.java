package org.konanok.demo.dubbo.spi.router;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.router.AbstractRouter;
import org.apache.dubbo.rpc.cluster.router.tag.TagRouter;

import java.util.List;

@Slf4j
public class DynamicTagRouter extends AbstractRouter {

    /**
     * In order to load before TagRouter
     *
     * @see TagRouter#TAG_ROUTER_DEFAULT_PRIORITY
     * @see org.apache.dubbo.rpc.cluster.RouterChain#sort()
     */
    private static final Integer DEFAULT_PRIORITY = 99;


    /**
     * Consumer tag, can be dynamically updated by config-center such as apollo, nacos, zookeeper...
     */
    @Getter
    private String tag = "A";

    public DynamicTagRouter(URL url) {
        super(url);
        this.priority = DEFAULT_PRIORITY;
    }

    public void setTag(String newTag) {
        log.debug("update consumer tag form {} to {}", tag, newTag);
        this.tag = newTag;
    }

    @Override
    public void setPriority(int priority) {
        log.warn("set priority {} ignored, the default value is {}", priority, DEFAULT_PRIORITY);
        // do nothing
    }

    @Override
    public int getPriority() {
        return DEFAULT_PRIORITY;
    }


    /**
     * No routing in this method, only set consumer tag and pass the tag to the TagRouter
     *
     * @see #tag
     * @see #getPriority()
     * @see org.apache.dubbo.rpc.RpcContext#setAttachment(String, Object)
     *
     * @param invokers
     * @param url
     * @param invocation
     * @param <T>
     * @return invokers, first parameter
     * @throws RpcException
     */
    @Override
    public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        log.debug("set consumer tag: {}", tag);
        RpcContext.getContext().setAttachment(CommonConstants.TAG_KEY, tag);
        return invokers;
    }

}

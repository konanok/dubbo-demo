# TagRouter filter all providers which has dynamic tag

- [x] I have searched the [issues](https://github.com/apache/dubbo/issues) of this repository and believe that this is not a duplicate.
- [x] I have checked the [FAQ](https://github.com/apache/dubbo/blob/master/FAQ.md) of this repository and believe that this is not a duplicate.

### Environment

* Dubbo version: 2.7.7
* Operating System version: macOS 10.15.5
* Java version: OpenJDK 11.0.7 2020-04-14
* Config Center: nacos 1.3.0

### Steps to reproduce this issue

[demo-github]()

1. start [dubbo-provider-demo]() with no tag
2. start [dubbo-consumer-demo]() with [DynamicTagRouter](), and it will set the consumer tag with "A"
```java
RpcContext.getContext().setAttachment(CommonConstants.TAG_KEY, tag);
```
3. create dubbo-provider-demo.tag-router on nacos
```yaml
enabled: true
force: true
key: dubbo-provider-demo
priority: 0
runtime: true
tags:
- addresses:
  - 192.168.80.8:21881
  name: A
```
4. invoke dubbo-consumer-demo's http-api to invoke dubbo-provider-demo's dubbo-api
```shell script
curl http://localhost:8282/dubbo/hello
```

### Expected Result

Response successfully, such as:

```text
Hello from host 192.168.80.8 at 2020-07-01 10:21:00
```

### Actual Result

Response error, is:

```text
{"timestamp":"2020-07-01T02:24:40.794+0000","status":500,"error":"Internal Server Error","message":"Failed to invoke the method hello in the service org.konanok.demo.dubbo.api.IDubboService. No provider available for the service org.konanok.demo.dubbo.api.IDubboService from registry 192.168.80.240:8851 on the consumer 192.168.80.8 using the dubbo version 2.7.7. Please check if the providers have been started and registered.","path":"/dubbo/hello"}
```

YouTrack:

```youtrack
ERROR 95351 --- [nio-8282-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.apache.dubbo.rpc.RpcException: Failed to invoke the method hello in the service org.konanok.demo.dubbo.api.IDubboService. No provider available for the service org.konanok.demo.dubbo.api.IDubboService from registry 192.168.80.240:8848 on the consumer 192.168.80.8 using the dubbo version 2.7.7. Please check if the providers have been started and registered.] with root cause

org.apache.dubbo.rpc.RpcException: Failed to invoke the method hello in the service org.konanok.demo.dubbo.api.IDubboService. No provider available for the service org.konanok.demo.dubbo.api.IDubboService from registry 192.168.80.240:8848 on the consumer 192.168.80.8 using the dubbo version 2.7.7. Please check if the providers have been started and registered.
	at org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker.checkInvokers(AbstractClusterInvoker.java:282) ~[dubbo-2.7.7.jar:2.7.7]
	at org.apache.dubbo.rpc.cluster.support.FailoverClusterInvoker.doInvoke(FailoverClusterInvoker.java:59) ~[dubbo-2.7.7.jar:2.7.7]
	at org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker.invoke(AbstractClusterInvoker.java:259) ~[dubbo-2.7.7.jar:2.7.7]
	at org.apache.dubbo.rpc.cluster.interceptor.ClusterInterceptor.intercept(ClusterInterceptor.java:47) ~[dubbo-2.7.7.jar:2.7.7]
	at org.apache.dubbo.rpc.cluster.support.wrapper.AbstractCluster$InterceptorInvokerNode.invoke(AbstractCluster.java:92) ~[dubbo-2.7.7.jar:2.7.7]
	at org.apache.dubbo.rpc.cluster.support.wrapper.MockClusterInvoker.invoke(MockClusterInvoker.java:82) ~[dubbo-2.7.7.jar:2.7.7]
	at org.apache.dubbo.rpc.proxy.InvokerInvocationHandler.invoke(InvokerInvocationHandler.java:74) ~[dubbo-2.7.7.jar:2.7.7]
	at org.apache.dubbo.common.bytecode.proxy0.hello(proxy0.java) ~[dubbo-2.7.7.jar:2.7.7]
	at org.konanok.demo.dubbo.consumer.controller.DubboController.hello(DubboController.java:19) ~[classes/:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190) ~[spring-web-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138) ~[spring-web-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:105) ~[spring-webmvc-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:879) ~[spring-webmvc-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:793) ~[spring-webmvc-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1040) ~[spring-webmvc-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943) ~[spring-webmvc-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006) ~[spring-webmvc-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898) ~[spring-webmvc-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:634) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883) ~[spring-webmvc-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:741) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53) ~[tomcat-embed-websocket-9.0.34.jar:9.0.34]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.6.RELEASE.jar:5.2.6.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:541) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:373) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:868) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1590) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628) ~[na:na]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.34.jar:9.0.34]
	at java.base/java.lang.Thread.run(Thread.java:834) ~[na:na]
```

### Question

I read the TagRouter source code, and notice that, on **Line 100**, dubbo try to get the consumer's tag from **URL** and **Invocation** only, but not **RpcContext**.

```java
@Override
public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
    // ...

    String tag = StringUtils.isEmpty(invocation.getAttachment(TAG_KEY)) ? url.getParameter(TAG_KEY) :
            invocation.getAttachment(TAG_KEY);

    // ...
}
```

[TagRouter document](https://dubbo.apache.org/zh-cn/docs/user/demos/routing-rule.html) tell me to set consumer tag by **RpcContext**... 😓😓😓

> RpcContext.getContext().setAttachment(Constants.REQUEST_TAG_KEY,"tag1");

The document also has a problem, **Constants.REQUEST_TAG_KEY** is gone, current correct key is **CommonConstants.TAG_KEY**, maybe someone should fix it.

I have no idea that why dubbo didn't try to get tag from RpcContext at this place, does anybody can explain this? if not, I think RpcContext's attachment tag should have a high priority, and the code should be fixed such as: 

```java
@Override
public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
    // ...

    String tag = StringUtils.isEmpty(RpcContext.getContext().getAttachment(TAG_KEY)) ? (
            StringUtils.isEmpty(invocation.getAttachment(TAG_KEY)) ? 
                    url.getParameter(TAG_KEY) : 
                    invocation.getAttachment(TAG_KEY)) : 
            RpcContext.getContext().getAttachment(TAG_KEY);

    // ...
}
```

Thanks!
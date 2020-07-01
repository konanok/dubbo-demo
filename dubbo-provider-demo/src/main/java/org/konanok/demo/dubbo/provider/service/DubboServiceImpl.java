package org.konanok.demo.dubbo.provider.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.konanok.demo.dubbo.api.IDubboService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@DubboService
public class DubboServiceImpl implements IDubboService {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");


    private String ip = "0.0.0.0";

    public DubboServiceImpl() {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String hello() {
        return "Hello from host " + ip + " at " + LocalDateTime.now().format(DEFAULT_FORMATTER);
    }

}

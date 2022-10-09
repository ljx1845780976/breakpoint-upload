package com.websocket;/**
 *
 **/

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.PageUtil;
import com.alibaba.fastjson.JSON;
import com.common.Constant;
import com.entity.FileHistory;
import com.entity.MyFile;
import com.service.FileHistoryService;
import com.service.FileService;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @introduction
 * @author ljz
 * @date 2022年03月02日 12:55
 */
@ServerEndpoint(value = "/upload",configurator = GetHttpSessionConfigurator.class)
@Component
@Data
public class PushEndpoint {

    private static Logger logger= LoggerFactory.getLogger(PushEndpoint.class);

    private static Map<String, PushEndpoint> endpointMap = new HashMap<>();

    private static Map<String, ArrayBlockingQueue<byte[]>> fileMap=new HashMap<>();

    private static FileService fileService;

    private  String username ;

    private Session session;

    private HttpSession httpSession;

    @Autowired
    public void setFileService(FileService fileService){
        PushEndpoint.fileService=fileService;
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        httpSession= (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
        username=httpSession.getAttribute("username").toString();
        endpointMap.put(username, this);
        //容量为1的阻塞队列
        fileMap.put(username,new ArrayBlockingQueue<>(1));
        System.out.println(username+"连接成功");
    }

    @OnMessage
    public void onMessage(byte[] message,Session session) {
        fileMap.get(username).add(message);
    }
    @OnMessage
    public void onMessage(String message,Session session) {
        MyFile myFile= JSON.parseObject(message, MyFile.class);
        ArrayBlockingQueue<byte[]> queue = fileMap.get(username);
        try {
            byte[] part = queue.poll(1000, TimeUnit.MILLISECONDS);
            //如果为空，则表示传输byte数组失败。则打印错误信息并结束该方法后续的执行
            Assert.notNull(part,"file-null-exception");
            myFile.setFilePart(part);
        } catch (Exception e) {
            logger.error("用户:{},文件:{},部分:{},上次失败",username,myFile.getName(),myFile.getPart());
            push(Constant.FAILURE,username);
        }
        System.out.println(myFile);
        String originName = myFile.getName();
        String result=fileService.filesSave(myFile,username,originName);
        if (myFile.getPart().equals(myFile.getTotal()))  {
            try {
                result= fileService.filesMerge(username,originName);
            } catch (IOException e) {
                logger.error("用户:{},文件:{},合并失败",username,myFile.getName());
                push(Constant.FAILURE,username);
            }
            System.out.println("文件合并成功");
        }
        push(result,username);
    }

    @OnClose
    public void onClose(Session session) {
        endpointMap.remove(username);
        fileMap.remove(username);
    }

    /*注意：非@onXxx方法，不能通过 'this.变量' 获取当前endpoint对象的变量*/
    public void push(String result,String username) {
        PushEndpoint thisEndpoint = endpointMap.get(username);
        if (BeanUtil.isNotEmpty(thisEndpoint)) {
            RemoteEndpoint.Basic basicRemote = thisEndpoint.session.getBasicRemote();
            try {
                basicRemote.sendText(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

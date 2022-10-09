package com.service.impl;/**
 *
 **/

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.Constant;
import com.entity.FileHistory;
import com.entity.MyFile;
import com.service.FileHistoryService;
import com.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @introduction
 * @author ljz
 * @date 2022年06月13日 19:11
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileHistoryService fileHistoryService;

    @Override
    public String filesMerge(String originName) throws IOException {
        String fileName = originName.substring(0, originName.lastIndexOf("."));
        File fileDir = new File(Constant.FILEPATH + fileName);
        File[] files = fileDir.listFiles();
        Arrays.sort(files, (a,b)->Integer.valueOf(a.getName())-Integer.valueOf(b.getName()));
        FileOutputStream fos = new FileOutputStream(fileDir + "\\" + originName);
        for (File file : files) {
            FileInputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len=fis.read(buf))!=-1){
                fos.write(buf,0,len);
            }
            //真正用到时还得用finally处理close
            if (fis!=null)fis.close();
        }
        if (fos!=null)fos.close();
        filesDelete(files,originName);
        return Constant.FINISH;
    }

    @Override
    public String filesSave(MyFile myFile, String originName) {
        //创建文件夹
        String fileName = originName.substring(0, originName.lastIndexOf("."));
        File fileDir = new File(Constant.FILEPATH + fileName);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        //创建片段文件
        File file = new File(fileDir + "\\" + myFile.getPart());
        try {
            myFile.getFile().transferTo(file);
            FileHistory fileHistory = new FileHistory();
            fileHistory.setName(originName);
            fileHistory.setPart(myFile.getPart());
            fileHistory.setTotal(myFile.getTotal());
            fileHistoryService.saveOrUpdate(fileHistory, new QueryWrapper<FileHistory>().eq("name", originName));
        } catch (IOException e) {
            e.printStackTrace();
            return Constant.FAILURE;
        }
        return Constant.SUCCESS;

    }

    private void filesDelete(File[] files,String originName){
        for (File file : files) {
            file.delete();
        }
        fileHistoryService.remove(new QueryWrapper<FileHistory>().eq("name",originName));
    }
}

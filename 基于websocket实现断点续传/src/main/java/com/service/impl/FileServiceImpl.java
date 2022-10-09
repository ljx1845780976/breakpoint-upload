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
    public String filesSave(MyFile myFile,String username, String originName)  {
        //创建文件夹
        String fileName = originName.substring(0, originName.lastIndexOf("."));
        File fileDir = new File(Constant.FILEPATH +username+"/"+fileName);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        //创建片段文件
        File file = new File(fileDir + "\\" + myFile.getPart());
        FileOutputStream fos=null;
        try {
            fos = new FileOutputStream(file);
            fos.write(myFile.getFilePart());
            FileHistory fileHistory = new FileHistory();
            fileHistory.setFileName(originName);
            fileHistory.setUsername(username);
            fileHistory.setPart(myFile.getPart());
            fileHistory.setTotal(myFile.getTotal());
            fileHistoryService.saveOrUpdate(fileHistory, new QueryWrapper<FileHistory>()
                    .eq("file_name", originName)
                    .eq("username",username));
        } catch (IOException e) {
            e.printStackTrace();
            return Constant.FAILURE;
        }finally {
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Constant.SUCCESS;

    }

    @Override
    public String filesMerge(String username,String originName) throws IOException {
        String fileName = originName.substring(0, originName.lastIndexOf("."));
        File fileDir = new File(Constant.FILEPATH +username+"/"+ fileName);
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
        filesDelete(files,username,originName);
        return Constant.FINISH;
    }

    private void filesDelete(File[] files,String username,String originName){
        for (File file : files) {
            file.delete();
        }
        fileHistoryService.remove(new QueryWrapper<FileHistory>()
                .eq("file_name",originName)
                .eq("username",username));
    }
}

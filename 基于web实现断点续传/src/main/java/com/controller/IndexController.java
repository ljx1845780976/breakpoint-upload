package com.controller;/**
 *
 **/

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.Constant;
import com.entity.FileHistory;
import com.entity.MyFile;
import com.service.FileHistoryService;
import com.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @introduction
 * @author ljz
 * @date 2022年06月13日 13:38
 */
@RestController
@RequestMapping
public class IndexController {

    @Autowired
    FileHistoryService fileHistoryService;
    @Autowired
    FileService fileService;


    @PostMapping("/upload")
    public String upload(MyFile myFile) throws IOException {
        System.out.println(myFile);
        String originName = myFile.getName();
        String result=fileService.filesSave(myFile, originName);
        if (myFile.getPart() .equals(myFile.getTotal()))  {
            result= fileService.filesMerge(originName);
            System.out.println("文件合并成功");
        }
        return result;
    }

    @GetMapping("/checkFile/{fileName}")
    public FileHistory checkFile(@PathVariable("fileName") String name) throws IOException {
        FileHistory fileHistory = fileHistoryService.getOne(new QueryWrapper<FileHistory>().eq("name", name));
        if (fileHistory != null) {
            return fileHistory;
        } else {
            return null;
        }
    }
}

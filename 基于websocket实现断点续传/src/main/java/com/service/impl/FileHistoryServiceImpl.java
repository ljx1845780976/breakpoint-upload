package com.service.impl;/**
 *
 **/

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.FileHistory;
import com.mapper.FileHistoryMapper;
import com.service.FileHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @introduction
 * @author ljz
 * @date 2022年06月13日 15:23
 */
@Service
public class FileHistoryServiceImpl  extends ServiceImpl<FileHistoryMapper, FileHistory> implements FileHistoryService {

    @Autowired
    FileHistoryMapper fileHistoryMapper;
    @Override
    public FileHistory getFileHistory(String username, String filename) {
        return fileHistoryMapper.getFileHistory(username,filename);
    }
}

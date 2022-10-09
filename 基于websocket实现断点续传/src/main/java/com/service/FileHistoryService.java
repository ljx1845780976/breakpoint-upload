package com.service;/**
 *
 **/

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.FileHistory;

/**
 * @introduction
 * @author ljz
 * @date 2022年06月13日 15:22
 */
public interface FileHistoryService extends IService<FileHistory> {
    FileHistory getFileHistory(String username,String filename);
}

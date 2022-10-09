package com.service.impl;/**
 *
 **/

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.FileHistory;
import com.entity.MyFile;
import com.mapper.FileHistoryMapper;
import com.service.FileHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

/**
 * @introduction
 * @author ljz
 * @date 2022年06月13日 15:23
 */
@Service
public class FileHistoryServiceImpl  extends ServiceImpl<FileHistoryMapper, FileHistory> implements FileHistoryService {

}

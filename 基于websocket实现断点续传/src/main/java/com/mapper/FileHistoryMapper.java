package com.mapper;/**
 *
 **/

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.FileHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @introduction
 * @author ljz
 * @date 2022年06月13日 15:19
 */
@Mapper
public interface FileHistoryMapper extends BaseMapper<FileHistory> {
    FileHistory getFileHistory(@Param("username") String username,@Param("filename") String filename);
}

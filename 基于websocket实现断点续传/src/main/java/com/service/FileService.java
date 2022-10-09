package com.service;/**
 *
 **/

import com.entity.MyFile;

import java.io.IOException;

/**
 * @introduction
 * @author ljz
 * @date 2022年06月13日 19:10
 */
public interface FileService {

    String filesMerge(String username,String originName) throws IOException;

    String filesSave(MyFile myFile,String username, String originName);
}

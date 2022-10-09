package com.service;/**
 *
 **/

import com.entity.MyFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @introduction
 * @author ljz
 * @date 2022年06月13日 19:10
 */
public interface FileService {

    public String filesMerge(String originName) throws IOException;

    public String filesSave( MyFile myFile,String originName);
}

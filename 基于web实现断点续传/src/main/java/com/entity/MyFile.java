package com.entity;/**
 *
 **/

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @introduction
 * @author ljz
 * @date 2022年06月13日 13:55
 */
@Data
public class MyFile implements Serializable {

    private String name;

    private MultipartFile file;

    private Integer total;//总块数

    private Integer part;//当前是第几块

    @Override
    public String toString() {
        return "MyFile{" +
                "name='" + name + '\'' +
                ", total=" + total +
                ", part=" + part +
                '}';
    }
}

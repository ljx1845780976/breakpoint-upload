package com.entity;/**
 *
 **/

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @introduction
 * @author ljz
 * @date 2022年06月13日 13:55
 */
@Data
public class MyFile implements Serializable {

    private String name;

    private byte[] filePart;

    private Integer total;//总块数

    private Integer part;//当前是第几块

    @Override
    public String toString() {
        return "MyFile{" +
                "name='" + name + '\'' +
                ", file=" + filePart.length +
                ", total=" + total +
                ", part=" + part +
                '}';
    }
}

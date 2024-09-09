package com.yisheng.pojo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;


@Data
public class Session {
    private Integer id;
    private Integer createUser;
    private LocalDateTime title;
    private String  content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

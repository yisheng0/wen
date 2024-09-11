package com.yisheng.service;

import com.yisheng.pojo.Result;
import com.yisheng.pojo.Session;

import java.util.List;

public interface SessionService {
    String add(Session session);
    List<Session> findById();
    Session findContentById(Integer id);
    Result update(Integer id, String content);
}

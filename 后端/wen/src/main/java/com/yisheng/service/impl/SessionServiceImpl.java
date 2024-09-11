package com.yisheng.service.impl;

import com.yisheng.mapper.SessionMapper;
import com.yisheng.pojo.Result;
import com.yisheng.pojo.Session;
import com.yisheng.service.SessionService;
import com.yisheng.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionMapper sessionMapper;

    public String add(Session session) {
        System.out.println(11111);
        System.out.println(session.getContent());
        session.setTitle(LocalDateTime.now());
        session.setCreateTime(LocalDateTime.now());
        session.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        session.setCreateUser((Integer) map.get("id"));

        sessionMapper.add(session);
        return null;
    }

    public List<Session> findById() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Session> session = sessionMapper.findById(id);
        return session;
    }

    @Override
    public Session findContentById(Integer id) {
        return null;
    }

    @Override
    public Result update(Integer id, String content) {
        sessionMapper.update(id, content);
        return Result.success();
    }

//    public void delete(Integer id) {
//        sessionMapper.delete(id);
//    }
//
//    public List<session> list() {
//        return sessionMapper.list();
//    }
//
//   public void update(session session) {}
}

package com.yisheng.controller;

import com.yisheng.pojo.Result;
import com.yisheng.pojo.User;
import com.yisheng.service.UserService;
import com.yisheng.utils.JwtUtil;
import com.yisheng.utils.Md5Util;
import com.yisheng.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    //注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User u =  userService.findByUserName(username);
        if(u == null){
            userService.register(username,password);
            return Result.success();
        }else{
            return Result.error("用户名已被占用");
        }
    };

    //登入
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User loginUser = userService.findByUserName(username);
        System.out.println(loginUser);
        if(loginUser == null){
            return Result.error("用户名错误");
        };
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
              Map<String, Object>  claims = new HashMap<>();
              claims.put("id", loginUser.getId());
              claims.put("username", loginUser.getUsername());
              String token = JwtUtil.genToken(claims);

              return Result.success(token);
        }
        return Result.error("密码错误");
    };

    //获取用户详细信息
    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
//        System.out.println("map : "+map);
        String username = (String) map.get("username");
//        System.out.println("username : "+username);
        User user = userService.findByUserName(username);
//        System.out.println("user : "+user);
        return Result.success(user);
    }


    // 更新用户基本信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }


    @PatchMapping("updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String token) {
        //1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }

        //原密码是否正确
        //调用userService根据用户名拿到原密码,再和old_pwd比对
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码填写不正确");
        }

        //newPwd和rePwd是否一样
        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码不一样");
        }

        return Result.success();
    }
}

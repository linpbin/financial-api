package com.my.financial.users.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.my.financial.exception.BusinessRuntimeException;
import com.my.financial.model.Login;
import com.my.financial.model.User;
import com.my.financial.result.CommResult;
import com.my.financial.users.dao.UserDao;
import com.my.financial.users.service.UserService;
import com.my.financial.util.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lin.pingbin on 2018/2/25.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public CommResult getVerifyCode(String phone) {
        CommResult commResult = null;
        CommResult existResult = null;
        if (phone!=null){
            existResult = getUserByPhone(phone);
            if (existResult.getResultCode()==0){
                //生成验证码 放入 redis 缓存
                String verifyCode="123456";
                try {
                    CommResult commResult1 =SendSms.sendSms(phone,verifyCode);
                    System.out.println(commResult1.toString());
                } catch (ClientException e) {
                    new BusinessRuntimeException("阿里云发送验证码异常");
                }
                String isexist = stringRedisTemplate.opsForValue().get(phone);
                if (isexist!=null){
                    return new CommResult(1,"验证码已发送，请在有效期内输入");
                }
                //设置验证码过期时间为60s
                stringRedisTemplate.opsForValue().set(phone,verifyCode,60, TimeUnit.SECONDS);
                commResult = new CommResult(null);
                return commResult;
            }else {
                //生成验证码 放入 redis 缓存
                String verifyCode="123456";
                try {
                    SendSms.sendSms(phone,verifyCode);
                } catch (ClientException e) {
                    new BusinessRuntimeException("阿里云发送验证码异常");
                }
                String isexist = stringRedisTemplate.opsForValue().get(phone);
                if (isexist!=null){
                    return new CommResult(1,"验证码已发送，请在有效期内输入");
                }
                //设置验证码过期时间为60*5s
                stringRedisTemplate.opsForValue().set(String.valueOf(phone),verifyCode,60*5, TimeUnit.SECONDS);
                commResult = new CommResult(null);
                return commResult;
            }

        }else {
            return  new CommResult(1,"手机号不能为空");
        }

    }

    @Override
    public CommResult getUsers() {
        CommResult commResult = null;
        List<User> userList = userDao.getUsers();
        if (userList.size()>0&&userList!=null){
            commResult = new CommResult(userList);
            return  commResult;
        }else {
            commResult = new CommResult(1,"用户列表为空");
            return  commResult;
        }
    }

    @Override
    public CommResult getUserByPhone(String phone) {
        CommResult commResult  = null;
        User user = userDao.getUserByPhone(phone);
        if (user!=null){
            commResult = new CommResult(user);
            return  commResult;
        }else {
            commResult = new CommResult(1,"用户不存在!");
            return  commResult;
        }
    }
    @Override
    public CommResult getUserByUsername(String username) {
        CommResult commResult  = null;
        User user = userDao.getUserByUsername(username);
        if (user!=null){
            commResult = new CommResult(user);
            commResult.setResultCode(0);
            return  commResult;
        }else {
            commResult.setResultCode(1);
            commResult = new CommResult(1,"用户不存在!");
            return  commResult;
        }
    }
    @Override
    public CommResult getUserById(Integer id) {
        CommResult commResult  = null;
        User user = userDao.getUserById(id);
        if (user!=null){
            commResult = new CommResult(user);
            return  commResult;
        }else {
            commResult = new CommResult(1,"用户不存在!");
            return  commResult;
        }
    }

    @Override
    public CommResult getUserByUsernameAndPassword(String username, String password) {
        CommResult commResult = null;
        if (username!=null&&password!=null){
            User user = userDao.getUserByIdAndPassword(username,password);
            if (user!=null){
                commResult = new CommResult(user);
                return  commResult;
            }else {
                commResult = new CommResult(1,"用户名或密码错误！");
                return  commResult;
            }
        }else {
            commResult = new CommResult(1,"用户名或密码不能为空！");
            return  commResult;
        }
    }

    @Override
    public CommResult createUserOfPhone(User user, String verifycode) {
        CommResult commResult = new CommResult();
        String code = stringRedisTemplate.opsForValue().get(String.valueOf(user.getPhone()));
        if (code!=null&&code.equals(verifycode)){
            User exist = userDao.getUserByPhone(user.getPhone());
            if (exist!=null){
                return new CommResult(1,"手机号已经注册!");
            }else {
                Integer result = userDao.createUserByPhone(user.getPhone());
                User userinfo = userDao.getUserByPhone(user.getPhone());
                if (result!=0){
                    commResult = new CommResult(userinfo);
                    return commResult;
                }else {
                    commResult = new CommResult(1,"注册失败");
                    return  commResult;
                }
            }


        }else {
            commResult = new CommResult(1,"验证码不正确或者已失效！");
            return commResult;
        }
    }

    @Override
    public CommResult deleteUser(Integer id) {
        CommResult commResult = null;
        int result = userDao.deleteUser(id);
        if (result!=0){
            commResult = new CommResult(null);
            return  commResult;
        }else {
            commResult = new CommResult(1,"删除用户失败!");
            return  commResult;
        }
    }

    @Override
    public CommResult updateUser(Integer id, User user) {
        CommResult commResult = null;
       user.setId(id);
        int result = userDao.updateUser(user);
        if (result!=0){
            commResult = new CommResult(null);
            return  commResult;
        }else {
            commResult = new CommResult(1,"更新资料失败");
            return commResult;
        }
    }

    @Override
    public CommResult updatePhone(Integer id, Login login) {
        String code = stringRedisTemplate.opsForValue().get(String.valueOf(login.getPhone()));
        if (code!=null&&code.equals(login.getVerifyCode())){
            User exist = userDao.getUserByPhone(login.getPhone());
            if (exist!=null){
                return new CommResult("手机号已经被注册");
            }else {
                int result = userDao.updatePhone(id,login.getPhone());
                if (result!=0){
                    return new CommResult(null);
                }else {
                    return new CommResult(1,"绑定失败");
                }
            }

        }else {
            return new CommResult(1, "验证码错误");
        }

    }

    @Override
    public CommResult updatePassword(User user) {
        int reselt = userDao.updateUser(user);
        if (reselt!=0){
            return new CommResult(null);
        }
        return new CommResult(1,"更改密码失败");
    }

    @Override
    public CommResult findPw(String phone, String verifyCode) {
        String code = stringRedisTemplate.opsForValue().get(String.valueOf(phone));
        System.out.println(code);
        System.out.println(verifyCode);
        System.out.println(code!=null&&code.equals(verifyCode));
        if (code!=null&&code.equals(verifyCode)){
            User exist = userDao.getUserByPhone(phone);
            if(exist==null) return new CommResult(1,"手机号未注册");
            return new CommResult(null);
        }else {
            return new CommResult(1,"验证码错误");
        }

    }

    @Override
    public CommResult updatePwByPhone(User user) {
        int result = userDao.updateUserByPhone(user);
        if (result!=0){
            return new CommResult(null);
        }else {
            return new CommResult(1,"修改密码失败");
        }
    }
}

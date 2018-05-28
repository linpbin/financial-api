package com.my.financial.users.service;

import com.my.financial.model.Login;
import com.my.financial.model.User;
import com.my.financial.result.CommResult;

/**
 * Created by lin.pingbin on 2018/2/25.
 */
public interface UserService {
    //get verifycode
    CommResult getVerifyCode(String phone);
    //get
    CommResult getUsers();
    //get by id id
    CommResult getUserById(Integer id);
    CommResult getUserByPhone(String phone);
    CommResult getUserByUsername(String username);
    //get by id and pw
    CommResult getUserByUsernameAndPassword(String username,String password);
    //post
    CommResult createUserOfPhone(User user,String verifycode);
    //delete
    CommResult deleteUser(Integer id);
    //put
    CommResult updateUser(Integer id,User user);
    CommResult updatePhone(Integer id,Login login);

    CommResult updatePassword(User user);

    CommResult findPw(String phone, String verifyCode);

    CommResult updatePwByPhone(User user);
}

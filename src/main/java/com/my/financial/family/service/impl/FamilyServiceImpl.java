package com.my.financial.family.service.impl;

import com.my.financial.family.dao.FamilyDao;
import com.my.financial.family.service.FamilyService;
import com.my.financial.model.Family;
import com.my.financial.model.User;
import com.my.financial.result.CommResult;
import com.my.financial.users.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lin.pingbin on 2018/3/28.
 */
@Service
public class FamilyServiceImpl implements FamilyService {
    @Autowired
    private FamilyDao familyDao;
    @Autowired
    private UserDao userDao;
    @Override
    public CommResult getFamily() {
        return null;
    }

    @Override
    public CommResult getFamilyById(Integer id) {
        return null;
    }

    @Override
    public CommResult getFamilyByUserId(Integer userId) {
        CommResult commResult = null;
        Family family = familyDao.getFamilyByUserId(userId);
        if (family!=null){
            commResult = new CommResult(family);
        }else {
            commResult = new CommResult(1,"该用户暂未创建家庭");
        }
        return commResult;
    }

    @Override
    public CommResult createFamily(Family family) {
        CommResult commResult = null;
        User user = userDao.getUserById(family.getFamilyAdmin());
        if (user.getFamilyId()!=null){
            return new CommResult(1,"用户家庭已存在");
        }
        family.setCreateDate(new Date());
        family.setMembersNum(1);
        int result = familyDao.createFamily(family);
        if (result!=0){
            int updateFamily = familyDao.addUser(family.getId(),family.getFamilyAdmin());
            if (updateFamily!=0) return new CommResult(null);
            else {
                familyDao.deleteFamily(family.getId());
                commResult = new CommResult(1,"创建失败");
                return commResult;
            }
        }else {
            return new CommResult(1,"创建失败");
        }

    }

    @Override
    public CommResult updateFamily(Integer id, Family family) {

        Family family1 = familyDao.getFamilyById(family.getId());
        if (family1.getFamilyAdmin().equals(id)){
            int result = familyDao.updateFamily(family);
            if (result!=0){
                return new CommResult(null);
            }else {
                return new CommResult(1,"修改失败");
            }
        }else {
            return new CommResult(1,"只有管理员才能修改用户信息");
        }

    }

    @Override
    public CommResult addFamilyUser(Integer userId, String username,Integer familyId) {
        User user = userDao.getUserByUsername(username);
        Family family = familyDao.getFamilyById(familyId);
        if (!family.getFamilyAdmin().equals(userId)){
            return new CommResult(1,"只有管理员才能添加成员");
        }else {
            int result = familyDao.addUser(familyId,user.getId());
            familyDao.updateNum(family.getMembersNum()+1,familyId);
            if (result!=0){
                return new CommResult(null);
            }else {
                return new CommResult(1,"添加失败");
            }
        }
    }

    @Override
    public CommResult deleteFamilyUser(Integer userId, Integer familyId,Integer delId) {
        User user =userDao.getUserById(userId);
        Family family = familyDao.getFamilyById(familyId);
        if (!family.getFamilyAdmin().equals(userId)){
            return new CommResult(1,"只有管理员才能删除家庭成员");
        }else {
            int result = familyDao.delUser(familyId,delId);
            familyDao.updateNum(family.getMembersNum()-1,familyId);
            if (result!=0){
                return new CommResult(null);
            }else {
                return new CommResult(1,"删除失败");
            }
        }
    }

    @Override
    public CommResult deleteFamily(Integer userId, Integer id) {
        CommResult commResult = null;
        User user =userDao.getUserById(userId);
        List<User> userList = userDao.fingUserByFamilyId(id);
        Family family = familyDao.getFamilyById(id);
        if (!family.getFamilyAdmin().equals(userId)){
            return new CommResult(1,"只有管理员才能删除家庭");
        }else {
            int result = familyDao.deleteFamily(id);
            if (result!=0){
                for (int i = 0;i<userList.size();i++){
                    familyDao.delUser(id,userList.get(i).getId());
                }
                return new CommResult(null);
            }else {
                return new CommResult(1,"删除失败");
            }
        }
    }

    @Override
    public CommResult getFamilyMemeberByUserId(Integer userId) {
        CommResult commResult = null;
        Family family = familyDao.getFamilyByUserId(userId);
        if (family!=null){
            List<User> userList =userDao.getUsersByFamilyId(family.getId());
            if (userList.size()>0&&userList!=null){
                commResult= new CommResult(userList);
            }else {
                commResult = new CommResult(1,"failure");
            }
        }else {
            new CommResult(1,"failure");
        }
        return commResult;
    }
}

package com.my.financial.announces.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.my.financial.announces.dao.AnnouncesDao;
import com.my.financial.announces.service.AnnouncesService;
import com.my.financial.family.dao.FamilyDao;
import com.my.financial.model.Announces;
import com.my.financial.model.Condition;
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
public class AnnouncesServiceImpl implements AnnouncesService{
    @Autowired
    private AnnouncesDao announcesDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FamilyDao familyDao;
    @Override
    public CommResult findAnnouncesList(Integer familyId, Condition condition) {
        CommResult<PageInfo<Announces>> commResult = null;
        if (condition.getPageSize() == null || condition.getPageSize() == 0) {
            condition.setPageSize(10);
        }
        if (condition.getPageNo() == null || condition.getPageNo() == 0) {
            condition.setPageNo(1);
        }
        PageHelper.startPage(condition.getPageNo(), condition.getPageSize());
        List<Announces> announces = announcesDao.findAnnouncesList(familyId);
        PageInfo<Announces> page = new PageInfo<>(announces);
        if (page.getList() != null && page.getList().

                size() > 0)

        {
            commResult = new CommResult(page);
        } else

        {
            commResult = new CommResult(1, "没有记录");
        }
        return commResult;
    }

    @Override
    public CommResult createAnnounces(Integer familyId, Announces announces,Integer userId) {
        User user = userDao.getUserById(userId);
        Family family =familyDao.getFamilyByUserId(userId);
        if (family.getFamilyAdmin() == userId){
            if (user!=null){
                announces.setCreateBy(user.getName());
                announces.setUpdateBy(user.getName());
                announces.setCreateDate(new Date());
                announces.setUpdateDate(new Date());
                announces.setFamilyId(familyId);
                int result = announcesDao.createAnnounces(announces);
                if (result!=0){
                    return new CommResult(null);
                }else{
                    return new CommResult(1,"新增失败");
                }
            }else {
                return new CommResult(1,"系统错误");
            }
        }else {
            return new CommResult(1, "只有管理员才能发布公告");
        }

    }

    @Override
    public CommResult updateAnnounces(Integer familyId, Integer userId,Integer aid,Announces announces) {
        Family family = familyDao.getFamilyByUserId(userId);
        User user = userDao.getUserById(userId);
        if (family.getFamilyAdmin()==userId){
            announces.setId(aid);
            announces.setUpdateDate(new Date());
            announces.setUpdateBy(user.getName());
            int result = announcesDao.updateAnnounces(announces);
            if (result!=0){
                return new CommResult(null);
            }else {
                return new CommResult(1,"系统错误");
            }
        }else {
            return new CommResult(1,"只有管理员才能更新公告");
        }
    }

    @Override
    public CommResult deleteAnnounces(Integer familyId,Integer userId, Integer aid) {
        Family family = familyDao.getFamilyByUserId(userId);
        if (family.getFamilyAdmin()==userId){
            int result = announcesDao.deleteAnnounces(aid);
            if (result!=0){
                return new CommResult(null);
            }else {
                return new CommResult(1,"系统错误");
            }
        }else {
            return new CommResult(1,"只有管理员才能删除公告");
        }
    }
}

package com.my.financial.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.my.financial.message.service.MessageService;
import com.my.financial.model.*;
import com.my.financial.result.CommResult;
import com.my.financial.users.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.financial.message.dao.*;

import java.util.Date;
import java.util.List;

/**
 * Created by lin.pingbin on 2018/3/28.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;

    @Override
    public CommResult deleteMessage(Integer familyId, Integer userId, Integer aid) {
        int result = messageDao.deleteMessage(aid);
        if (result!=0){
            return new CommResult(null);
        }else {
            return new CommResult(1,"系统错误");
        }
    }

    @Override
    public CommResult updateMessage(Integer familyId, Integer userId, Integer aid, Message message) {
        User user = userDao.getUserById(userId);
        message.setId(aid);
        message.setUpdateDate(new Date());
        message.setUpdateBy(user.getName());
        int result = messageDao.updateMessage(message);
        if (result!=0){
            return new CommResult(null);
        }else {
            return new CommResult(1,"系统错误");
        }
    }

    @Override
    public CommResult createMessage(Integer familyId, Message message, Integer userId) {
        User user = userDao.getUserById(userId);
        if (user != null) {
            message.setCreateBy(user.getName());
            message.setUpdateBy(user.getName());
            message.setCreateDate(new Date());
            message.setUpdateDate(new Date());
            message.setFamilyId(familyId);
            int result = messageDao.createMessage(message);
            if (result != 0) {
                return new CommResult(null);
            } else {
                return new CommResult(1, "新增失败");
            }
        } else {
            return new CommResult(1, "系统错误");
        }
    }
    @Override
    public CommResult findMessageList(Integer familyId, Condition condition) {
        CommResult<PageInfo<Message>> commResult = null;
        if (condition.getPageSize() == null || condition.getPageSize() == 0) {
            condition.setPageSize(10);
        }
        if (condition.getPageNo() == null || condition.getPageNo() == 0) {
            condition.setPageNo(1);
        }
        PageHelper.startPage(condition.getPageNo(), condition.getPageSize());
        List<Message> announces = messageDao.findMessageList(familyId);
        PageInfo<Message> page = new PageInfo<>(announces);
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
}

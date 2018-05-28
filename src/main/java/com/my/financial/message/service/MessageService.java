package com.my.financial.message.service;

import com.my.financial.model.Condition;
import com.my.financial.model.Message;
import com.my.financial.result.CommResult;

/**
 * Created by lin.pingbin on 2018/3/28.
 */
public interface MessageService {

    CommResult deleteMessage(Integer familyId, Integer userId, Integer aid);

    CommResult updateMessage(Integer familyId, Integer userId, Integer aid, Message message);

    CommResult createMessage(Integer familyId, Message message, Integer userId);

    CommResult findMessageList(Integer familyId, Condition condition);
}

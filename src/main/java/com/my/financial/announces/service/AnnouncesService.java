package com.my.financial.announces.service;

import com.my.financial.model.Announces;
import com.my.financial.model.Condition;
import com.my.financial.result.CommResult;

/**
 * Created by lin.pingbin on 2018/3/28.
 */
public interface AnnouncesService {
    CommResult findAnnouncesList(Integer familyId, Condition condition);
    CommResult createAnnounces(Integer familyId, Announces announces,Integer userId);
    CommResult updateAnnounces(Integer familyId,Integer userId,Integer aid,Announces announces);
    CommResult deleteAnnounces(Integer familyId,Integer userId,Integer aid);
}

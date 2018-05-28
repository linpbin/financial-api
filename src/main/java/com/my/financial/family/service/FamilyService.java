package com.my.financial.family.service;

import com.my.financial.model.Family;
import com.my.financial.result.CommResult;

/**
 * Created by lin.pingbin on 2018/3/28.
 */
public interface FamilyService {
    CommResult getFamily();
    CommResult getFamilyById(Integer id);
    CommResult getFamilyByUserId(Integer userId);
    CommResult createFamily(Family family);
    CommResult updateFamily(Integer id, Family family);

    CommResult addFamilyUser(Integer userId, String username,Integer familyId);

    CommResult deleteFamilyUser(Integer userId, Integer familyId,Integer delId);

    CommResult deleteFamily(Integer userId, Integer id);

    CommResult getFamilyMemeberByUserId(Integer userId);
}

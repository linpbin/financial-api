package com.my.financial.web;

import com.my.financial.announces.service.AnnouncesService;
import com.my.financial.expend.service.ExpendService;
import com.my.financial.family.service.FamilyService;
import com.my.financial.graph.service.GraphService;
import com.my.financial.income.service.IncomeService;
import com.my.financial.message.service.MessageService;
import com.my.financial.query.service.QuerySerivce;
import com.my.financial.type.service.typeService;
import com.my.financial.model.*;
import com.my.financial.result.CommResult;
import com.my.financial.users.service.UserService;
import com.my.financial.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class RestApiController {
    private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
    //查询模块
    @Autowired
    private QuerySerivce querySerivce;

    @GetMapping("/query/{userId}")
    public CommResult queryAll(@PathVariable Integer userId,
                               @RequestParam(value = "pageNo",required = false) Integer pageNo,
                               @RequestParam(value = "pageSize",required = false) Integer pageSize,
                               @RequestParam(value = "minSum",required = false) Double minSum,
                               @RequestParam(value = "maxSum",required = false) Double maxSum,
                               @RequestParam(value = "startTime",required = false) String startTime,
                               @RequestParam(value = "endTime",required = false) String endTime,
                               @RequestParam(value = "remark",required = false) String remark,
                               @RequestParam(value = "type",required = false) String type,
                               @RequestParam(value = "typeName",required = false) String typeName){
        CommResult commResult = new CommResult();
        Condition condition = new Condition();
        if (minSum!=null) condition.setMinSum(minSum);
        if (maxSum!=null) condition.setMaxSum(maxSum);
        if (remark!=null) condition.setRemark(remark);
        if (type!=null) condition.setType(type);
        if (typeName!=null) condition.setTypeName(typeName);
        if (endTime!=null)  condition.setEndTime(endTime);
        if (startTime!=null)  condition.setStartTime(startTime);
        condition.setPageSize(pageSize);
        condition.setPageNo(pageNo);
        commResult = querySerivce.queryAll(userId,condition);
        return commResult;
    }
    //类别管理
    @Autowired
    private typeService typeService;
    @GetMapping("/income/type/{userId}")
    public CommResult findAllIncomeTypeByUserId(@PathVariable("userId") String userId){
        CommResult commResult = new CommResult();
        commResult = typeService.findIncomeTypeListByUserId(Integer.parseInt(userId));
        return commResult;
    }
    @GetMapping("/income/type/user/{userId}")
    public CommResult findAllIncomeTypeForUser(@PathVariable("userId") String userId){
        CommResult commResult = new CommResult();
        commResult = typeService.findIncomeTypeListForUser(Integer.parseInt(userId));
        return commResult;
    }
    @GetMapping("/expend/type/user/{userId}")
    public CommResult findAllExpendTypeForUser(@PathVariable("userId") String userId){
        CommResult commResult = new CommResult();
        commResult = typeService.findExpendTypeListForUser(Integer.parseInt(userId));
        return commResult;
    }
    @GetMapping("/expend/type/{userId}")
    public CommResult findAllEexpendTypeByUserId(@PathVariable("userId") String userId){
        CommResult commResult = new CommResult();
        commResult = typeService.findExpendTypeListByUserId(Integer.parseInt(userId));
        return commResult;
    }
    @PostMapping("/income/type")
    public CommResult createIncomeType(@RequestBody String params){
        CommResult commResult = new CommResult();
        IncomeType incomeType = JacksonUtil.readValue(params,IncomeType.class);
        commResult = typeService.createIncomeType(incomeType);
        return commResult;
    }
    @PostMapping("/expend/type")
    public CommResult createExpendType(@RequestBody String params){
        CommResult commResult = new CommResult();
        ExpendType expendType = JacksonUtil.readValue(params,ExpendType.class);
        commResult = typeService.createExpendType(expendType);
        return commResult;
    }
    @DeleteMapping("/expends/{id}")
    public CommResult deleteEexpendById(@PathVariable("id") String id){
        CommResult commResult = new CommResult();
        commResult = typeService.deleteExpendById(Integer.parseInt(id));
        return commResult;
    }
    @DeleteMapping("/incomes/{id}")
    public CommResult deleteIncomeById(@PathVariable("id") String id){
        CommResult commResult = new CommResult();
        commResult = typeService.deleteIncomeById(Integer.parseInt(id));
        return commResult;
    }
    @PutMapping("/income/type/{id}")
    public CommResult updateIncomeType(@RequestBody String params,@PathVariable Integer id){
        CommResult commResult = new CommResult();
        IncomeType incomeType = JacksonUtil.readValue(params,IncomeType.class);
        incomeType.setId(id);
        commResult = typeService.updateIncomeType(incomeType);
        return commResult;
    }
    @PutMapping("/incomes/{id}")
    public CommResult updateIncome(@RequestBody String params,@PathVariable Integer id){
        CommResult commResult = new CommResult();
        System.out.println(params);
        Income incomeType = JacksonUtil.readValue(params,Income.class);
        commResult = incomeService.updateIncome(id,incomeType);
        return commResult;
    }
    @PutMapping("/expends/{id}")
    public CommResult updateExpend(@RequestBody String params,@PathVariable Integer id){
        CommResult commResult = new CommResult();
        Expend incomeType = JacksonUtil.readValue(params,Expend.class);
        commResult = expendService.updateExpend(id,incomeType);
        return commResult;
    }
    @PutMapping("/expend/type/{id}")
    public CommResult updateExpendType(@RequestBody String params,@PathVariable Integer id){
        CommResult commResult = new CommResult();
        ExpendType expendType = JacksonUtil.readValue(params,ExpendType.class);
        expendType.setId(id);
        commResult = typeService.updateExpendType(expendType);
        return commResult;
    }
    @DeleteMapping("/expend/type/{id}")
    public CommResult deleteEexpendTypeById(@PathVariable("id") String id){
        CommResult commResult = new CommResult();
        commResult = typeService.deleteExpendTypeById(Integer.parseInt(id));
        return commResult;
    }
    @DeleteMapping("/income/type/{id}")
    public CommResult deleteIncomeTypeById(@PathVariable("id") String id){
        CommResult commResult = new CommResult();
        commResult = typeService.deleteIncomeTypeById(Integer.parseInt(id));
        return commResult;
    }
    //花费 expend
    @Autowired
    private ExpendService expendService;
    @GetMapping("/expend")
    public CommResult GetExpend(@RequestParam("userId") Integer userId,
                                @RequestParam(value = "pageNo",required = false) Integer pageNo,
                                @RequestParam(value = "pageSize",required = false) Integer pageSize){
        CommResult commResult = new CommResult();
        Condition condition = new Condition();
        condition.setPageSize(pageSize);
        condition.setPageNo(pageNo);
        commResult = expendService.getAllExpend(userId,condition);
        return commResult;
    }
    @PostMapping("/expend")
    public CommResult PostExpend(@RequestBody String params){
        CommResult commResult = new CommResult();
        Expend expend = JacksonUtil.readValue(params,Expend.class);
        commResult=expendService.createExpend(expend);
        return commResult;
    }
    @DeleteMapping("/expend/{id}")
    public CommResult DeleteExpend(@PathVariable Integer id){
        CommResult commResult = new CommResult();
        commResult = expendService.deleteExpend(id);
        return commResult;
    }
    @PutMapping("/expend/{id}")
    public CommResult PutExpend(@PathVariable("id") Integer id,@RequestBody String params){
        CommResult commResult = new CommResult();
        Expend expend = JacksonUtil.readValue(params,Expend.class);
        commResult = expendService.updateExpend(id,expend);
        return commResult;
    }
    @Autowired
    private GraphService graphService;
    //Graph
    @GetMapping("/graph/incomes")
    public CommResult graphIncomelist(@RequestParam("userId") Integer userId,
                                      @RequestParam(value = "startTime",required = false) String startTime,
                                      @RequestParam(value = "endTime",required = false) String endTime){
        CommResult commResult = new CommResult();
        Condition condition = new Condition();
        condition.setStartTime(startTime);
        condition.setEndTime(endTime);
        commResult = graphService.getIncomeTotal(userId,condition);
        return commResult;
    }
    @GetMapping("/graph/expends")
    public CommResult graphExpendlist(@RequestParam("userId") Integer userId,
                                      @RequestParam(value = "startTime",required = false) String startTime,
                                      @RequestParam(value = "endTime",required = false) String endTime){
        CommResult commResult = new CommResult();
        Condition condition = new Condition();
        condition.setStartTime(startTime);
        condition.setEndTime(endTime);
        commResult = graphService.getExpendTotal(userId,condition);
        return commResult;
    }
    //收入
    @Autowired
    private IncomeService incomeService;
    @GetMapping("/income")
    public CommResult GetIncome(@RequestParam("userId") Integer userId,
                                @RequestParam(value = "pageNo",required = false) Integer pageNo,
                                @RequestParam(value = "pageSize",required = false) Integer pageSize){
        CommResult commResult = new CommResult();
        Condition condition = new Condition();
        condition.setPageSize(pageSize);
        condition.setPageNo(pageNo);
        commResult = incomeService.getAllIncome(userId,condition);
        return commResult;
    }
    @PostMapping("/income")
    public CommResult PostIncome(@RequestBody String params){
        CommResult commResult = new CommResult();
        Income income = JacksonUtil.readValue(params,Income.class);
        commResult=incomeService.createIncome(income);
        return commResult;
    }
    @DeleteMapping("/income/{id}")
    public CommResult DeleteIncome(@PathVariable Integer id){
        CommResult commResult = new CommResult();
        commResult = incomeService.deleteIncome(id);
        return commResult;
    }
    @PutMapping("/income/{id}")
    public CommResult PutIncome(@PathVariable Integer id,@RequestBody String params){
        CommResult commResult = new CommResult();
        Income income = JacksonUtil.readValue(params,Income.class);
        commResult = incomeService.updateIncome(id,income);
        return commResult;
    }
    //用户
    @Autowired
    private UserService userService;
    //获取验证码
    @GetMapping("/user/captcha/{phone}")
    public CommResult getVerfityCode(@PathVariable String phone){
        CommResult commResult = new CommResult();
        commResult = userService.getVerifyCode(phone);
        return commResult;
    }
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //登陆
    @PostMapping("/user/login")
    public CommResult login(@RequestBody String params){
        CommResult commResult = new CommResult();
        Login login = JacksonUtil.readValue(params,Login.class);
        CommResult existUser = new CommResult();
        if (login.getUsername()!=null){
            existUser = userService.getUserByUsername(login.getUsername());
            if (existUser.getResultCode()==0){
                commResult = userService.getUserByUsernameAndPassword(login.getUsername(),login.getPassword());
                return commResult;
            }else {
                return existUser;
            }
        }else if (login.getPhone()!=null){
            System.out.println(login.getPhone()+"--> "+login.getVerifyCode());
            existUser = userService.getUserByPhone(login.getPhone());
            if (existUser.getResultCode()==0){
                String code = stringRedisTemplate.opsForValue().get(String.valueOf(login.getPhone()));
                if (login.getVerifyCode().equals(code)){
                    return existUser;
                }else {
                    commResult.setResultCode(1);
                    commResult.setResultMsg("验证码错误！");
                    return commResult;
                }

            }else {
                return existUser;
            }
        }
        return null;

    }
    //注册
    @PostMapping("/user/register/{verfityCode}")
    public CommResult register(@RequestBody String params,@PathVariable String verfityCode){
        User user = JacksonUtil.readValue(params,User.class);
        CommResult commResult = new CommResult();
        commResult = userService.createUserOfPhone(user,verfityCode);
        return  commResult;
    }
    @GetMapping("/user/{id}")
    public CommResult findUserById(@PathVariable Integer id){
        CommResult commResult = userService.getUserById(id);
        return commResult;
    }
    @PutMapping("/user/{id}")
    public CommResult updateUserById(@PathVariable Integer id,@RequestBody String params){
        User user = JacksonUtil.readValue(params,User.class);
        CommResult commResult = userService.updateUser(id,user);
        return commResult;
    }
    @GetMapping("/user/{phone}/{verifyCode}")
    public CommResult findPw(@PathVariable String phone,@PathVariable String verifyCode){
        CommResult commResult = null;
        commResult = userService.findPw(phone,verifyCode);
        return commResult;
    }
    //绑定手机号
    @PutMapping("/user/{id}/phone")
    public CommResult updatePhone(@PathVariable Integer id,@RequestBody String params){
        CommResult commResult = new CommResult<>();
        Login login = JacksonUtil.readValue(params,Login.class);
        commResult = userService.updatePhone(id,login);
        return commResult;
    }
    @PutMapping("/user/password/{id}")
    public CommResult updatePassword(@PathVariable Integer id,@RequestBody String params){
        CommResult commResult = null;
        User user = JacksonUtil.readValue(params,User.class);
        user.setId(id);
        commResult = userService.updatePassword(user);
        return commResult;
    }
    @PostMapping("/user/repw")
    public CommResult updatePwByPhone(@RequestBody String parms){
        CommResult commResult =null;
        Login login = JacksonUtil.readValue(parms,Login.class);
        User user = new User();
        user.setPhone(login.getPhone());
        user.setPassword(login.getPassword());
        commResult = userService.updatePwByPhone(user);
        return commResult;
    }
    //家庭
    @Autowired
    private FamilyService familyService;
    //查找用户家庭
    @GetMapping("/familys/{userId}")
    public CommResult findFamilyByUserId(@PathVariable Integer userId){
        CommResult commResult = null;
        commResult = familyService.getFamilyByUserId(userId);
        return commResult;
    }
    //创建家庭
    @PostMapping("/familys/{userId}")
    public CommResult createFamily(@PathVariable Integer userId,@RequestBody String params){
        Family family = JacksonUtil.readValue(params,Family.class);
        family.setFamilyAdmin(userId);
        CommResult commResult = familyService.createFamily(family);
        return  commResult;
    }
    //修改家庭信息
    @PutMapping("/familys/{userId}/{id}")
    public CommResult updateFamilyByUserId(@PathVariable Integer userId,@RequestBody String params,@PathVariable Integer id){
        Family family = JacksonUtil.readValue(params,Family.class);
        family.setId(id);
        CommResult commResult = familyService.updateFamily(userId,family);
        return commResult;
    }
    //删除家庭
    @DeleteMapping("/familys/{userId}/{id}")
    public CommResult deleteFamilyById(@PathVariable Integer userId,@PathVariable Integer id){
        CommResult commResult = familyService.deleteFamily(userId,id);
        return commResult;
    }
    //添加成员
    @PostMapping("/family/{userId}/{familyId}/add")
    public CommResult addFamilyUser(@PathVariable Integer userId,@PathVariable Integer familyId,@RequestBody String params){
        Login login = JacksonUtil.readValue(params,Login.class);
        CommResult exist = userService.getUserByUsernameAndPassword(login.getUsername(),login.getPassword());
        CommResult commResult = null;
        if (exist.getResultCode() == 0){
            commResult = familyService.addFamilyUser(userId,login.getUsername(),familyId);
            return commResult;
        }else {
            return new CommResult(1,"用户名或密码错误");
        }
    }
    //查找家庭成员
    @GetMapping("/family/members/{userId}")
    public CommResult getFamilyUser(@PathVariable Integer userId){
        CommResult commResult = null;
        commResult = familyService.getFamilyMemeberByUserId(userId);
        return commResult;
    }
    //删除成员
    @DeleteMapping("/family/{userId}/{familyId}/del/{delId}")
    public CommResult deleteFamilyUser(@PathVariable Integer userId,@PathVariable Integer familyId,@PathVariable Integer delId){
        CommResult commResult = null;
        commResult  = familyService.deleteFamilyUser(userId, familyId,delId);
        return commResult;
    }
    //公告
    @Autowired
    private AnnouncesService announcesService;
    //查询公告列表
    @GetMapping("/family/announces/{familyId}")
    public CommResult findAnnouncesList(@PathVariable Integer familyId,
                                        @RequestParam(value = "pageNo",required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize",required = false) Integer pageSize){
        CommResult commResult = null;
        Condition condition = new Condition();
        condition.setPageSize(pageSize);
        condition.setPageNo(pageNo);
        commResult = announcesService.findAnnouncesList(familyId,condition);
        return commResult;
    }
    //发布公告
    @PostMapping("/family/{familyId}/announces/{userId}")
    public CommResult createAnnounces(@PathVariable Integer familyId,@PathVariable Integer userId,@RequestBody String params){
        CommResult commResult = null;
        Announces announces = JacksonUtil.readValue(params,Announces.class);
        commResult = announcesService.createAnnounces(familyId,announces,userId);
        return commResult;
    }
    //修改公告
    @PutMapping("/family/{familyId}/announces/{userId}/{aid}")
    public CommResult updateAnnounces(@PathVariable Integer familyId,@PathVariable Integer userId,@PathVariable Integer aid,@RequestBody String params){
        CommResult commResult = null;
        Announces announces = JacksonUtil.readValue(params,Announces.class);
        commResult = announcesService.updateAnnounces(familyId,userId,aid,announces);
        return commResult;
    }
    //删除公告
    @DeleteMapping("/family/{familyId}/announces/{userId}/{aid}")
    public CommResult deleteAnnounces(@PathVariable Integer familyId,@PathVariable Integer aid,@PathVariable Integer userId){
        CommResult commResult = null;
        commResult = announcesService.deleteAnnounces(familyId,userId,aid);
        return commResult;
    }
    //留言
    @Autowired
    private MessageService messageService;
    //查询留言列表
    @GetMapping("/family/message/{familyId}")
    public CommResult findMessageList(@PathVariable Integer familyId,
                                      @RequestParam(value = "pageNo",required = false) Integer pageNo,
                                      @RequestParam(value = "pageSize",required = false) Integer pageSize){
        CommResult commResult = null;
        Condition condition = new Condition();
        condition.setPageSize(pageSize);
        condition.setPageNo(pageNo);
        commResult = messageService.findMessageList(familyId,condition);
        return commResult;
    }

    //发布留言
    @PostMapping("/family/{familyId}/message/{userId}")
    public CommResult createMessage(@PathVariable Integer familyId,@PathVariable Integer userId,@RequestBody String params){
        CommResult commResult = null;
        Message message = JacksonUtil.readValue(params,Message.class);
        commResult = messageService.createMessage(familyId,message,userId);
        return commResult;
    }
    //修改留言
    @PutMapping("/family/{familyId}/message/{userId}/{aid}")
    public CommResult updateMessage(@PathVariable Integer familyId,@PathVariable Integer userId,@PathVariable Integer aid,@RequestBody String params){
        CommResult commResult = null;
        Message message = JacksonUtil.readValue(params,Message.class);
        commResult = messageService.updateMessage(familyId,userId,aid,message);
        return commResult;
    }
    //删除留言
    @DeleteMapping("/family/{familyId}/message/{userId}/{aid}")
    public CommResult deleteMessage(@PathVariable Integer familyId,@PathVariable Integer aid,@PathVariable Integer userId){
        CommResult commResult = null;
        commResult = messageService.deleteMessage(familyId,userId,aid);
        return commResult;
    }
}

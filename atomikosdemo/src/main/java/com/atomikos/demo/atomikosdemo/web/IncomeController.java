package com.atomikos.demo.atomikosdemo.web;

import com.atomikos.demo.atomikosdemo.dao.income.IncomeMapper;
import com.atomikos.demo.atomikosdemo.dao.user.UserMapper;
import com.atomikos.demo.atomikosdemo.domain.Income;
import com.atomikos.demo.atomikosdemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/income")
public class IncomeController {
    public static final String RESULT_SUCCESS = "success";
    public static final String RESULT_FAILED = "failed";
    @Autowired
    private IncomeMapper incomeMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/addincome/1")
    @Transactional
    public String addIncome1(@RequestParam("name") String name,@RequestParam("amount") double amount){
        try{
            User user = new User();
            user.setName(name);
            userMapper.insertUser(user);
            Income income = new Income();
            income.setUserId(user.getId());
            income.setAmount(amount);
            income.setOperateDate(new Timestamp((System.currentTimeMillis())));
            incomeMapper.insertIncome(income);
            return RESULT_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return RESULT_FAILED+":"+e.getMessage();
        }
    }
    @GetMapping("/addincome/2")
    @Transactional
    public String addIncome2(@RequestParam("name") String name,@RequestParam("amount") double amount){
        try {
            User user = new User();
            user.setName(name);
            userMapper.insertUser(user);

            this.throwRuntimeException();

            Income income = new Income();
            income.setUserId(user.getId());
            income.setAmount(amount);
            income.setOperateDate(new Timestamp(System.currentTimeMillis()));
            incomeMapper.insertIncome(income);

            return RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
            // return RESULT_FAILED + ":" + e.getMessage();
        }
    }
    public void throwRuntimeException() {
        throw new RuntimeException("User defined exceptions");
    }
}

package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.XxxRequest01;
import org.example.model.XxxRequest02;
import org.example.model.XxxResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author wyh
 */
@Slf4j
@Service
public class TestService {

    @Tool(description = "获取雇员信息")
    public XxxResponse getCompanyEmployee(XxxRequest01 xxxRequest01, XxxRequest02 xxxRequest02){
        XxxResponse xxxResponse = new XxxResponse();
        xxxResponse.setSalary(new Random().longs(10000).toString());
        xxxResponse.setDayManHour(String.valueOf(new Random().nextInt(24)));
        return xxxResponse;
    }
}

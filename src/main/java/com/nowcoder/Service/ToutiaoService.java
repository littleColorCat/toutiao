package com.nowcoder.Service;

import org.springframework.stereotype.Service;

/**
 * Created by admin on 2018/3/14.
 */
//定义为Service，每次系统启动，自动创建该对象
@Service
public class ToutiaoService {
    public String say(){
        return "This is from ToutiaoService";
    }
}

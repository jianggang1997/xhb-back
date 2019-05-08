package com.siki.xhb.aliyun.acton;

import com.siki.xhb.aliyun_i.action.AliyunActionI;
import com.siki.xhb.vo.model.ResObject;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jianggang
 * @Date 2019/5/8 13:12:00
 * @Description
 */

@RestController
public class AliyunAction implements AliyunActionI {

    @Override
    public ResObject getOssSignature() {
        return null;
    }

    @Override
    public ResObject ossServiceCallback() {
        return null;
    }
}

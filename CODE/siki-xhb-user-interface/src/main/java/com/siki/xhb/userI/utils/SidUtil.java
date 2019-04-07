package com.siki.xhb.userI.utils;

import java.util.UUID;

public class SidUtil {

    public static String generator(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }

}

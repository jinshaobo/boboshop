package com.jinshaobo.test;

import com.jinshaobo.utils.MD5Util;
import org.junit.Test;

public class MyTest {

    @Test
    public void testMd5(){
        String md5 = MD5Util.getMD5("123");
        System.out.println(md5);
    }
}

package com.company.test;

import com.company.utils.MD5Util;
import org.junit.Test;

public class MyTest {
    @Test
    public void testMD5(){
        String mi = MD5Util.getMD5("000000");
        System.out.println(mi);

        System.out.println("第二次提交GIT");

        System.out.println("hot-fix合并");
        System.out.println("push test");
    }
}

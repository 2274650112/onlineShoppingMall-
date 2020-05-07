package com.leyou.test;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class TesrDemo {
    private static final String pubPath="C:\\blbjava01\\temp\\rsa.pub";
    private static final String priPath="C:\\blbjava01\\temp\\rsa.pri";

    private PublicKey publicKey;
    private PrivateKey privateKey;

//    @Test
//    public void init() throws Exception {
//        RsaUtils.generateKey(pubPath, priPath, "ty555");
//    }


    @Before
    public void loadData() throws Exception {
        //获取公钥
        publicKey=RsaUtils.getPublicKey(pubPath);
        //获取私钥
        privateKey=RsaUtils.getPrivateKey(priPath);
    }

    @Test
    //产生token
    public void geToken() throws Exception {
        String token = JwtUtils.generateToken(new UserInfo(12L, "tom"), privateKey, 5);
        System.out.println(token);
    }

    @Test
    public void jiemi() throws Exception {
        String s="eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MTIsInVzZXJuYW1lIjoidG9tIiwiZXhwIjoxNTc4OTY5NjQ0fQ.VqSE7lRldk_fWMYOeboOLoIWC_PMaySrRghYtdGNGUQngyVduz4UJHIp9gJJ9luj9EclCKoTrp-Cc9KvUxdvMnpxxGj_nbdvaNSvtVKN_H-EUIuznPVfaFk8HYvRlMVQf1h41baRVzBu6tQFuAYZwZnXPGPI-LWdGaEIGx7KHPM";
        UserInfo userinfo = JwtUtils.getInfoFromToken(s, publicKey);
        System.out.println(userinfo);
    }

}

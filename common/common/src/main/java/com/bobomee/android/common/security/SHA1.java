package com.bobomee.android.common.security;

import java.security.NoSuchAlgorithmException;

/**
 * MD5摘要
 * <p/>
 * Created by zhaiyifan on 2015/8/3.
 */
public final class SHA1 extends Digest {

    public SHA1() throws NoSuchAlgorithmException {
        super("SHA-1");
    }
}

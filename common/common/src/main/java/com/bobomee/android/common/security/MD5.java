package com.bobomee.android.common.security;

import java.security.NoSuchAlgorithmException;

/**
 * MD5摘要
 * <p/>
 * Created by zhaiyifan on 2015/8/3.
 */
public final class MD5 extends Digest {

    public MD5() throws NoSuchAlgorithmException {
        super("MD5");
    }
}

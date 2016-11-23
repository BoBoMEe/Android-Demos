/*
 * Android_Common. lastModified by bobomee at 2016.5.16 10:59
 *
 * Copyright (C) 2016 bobomee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bobomee.android.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackageUtil {
    /**
     * Return the PackageInfo of this application's package.
     */
    public static PackageInfo getPackageInfo(Context context) {
        if (null == context) {
            return null;
        }
        String packageName = context.getPackageName();
        PackageInfo info = null;
        PackageManager manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

        }
        return info;
    }

    /**
     * Return the packageName of this application's package.
     */
    public static String getPackageName(Context context) {
        PackageInfo info = getPackageInfo(context);
        if (info != null) {
            return info.packageName;
        } else {
            return null;
        }
    }

    /**
     * Return the versionName of this application's package.
     */
    public static String getVersionName(Context context) {
        PackageInfo info = getPackageInfo(context);
        if (info != null) {
            return info.versionName;
        } else {
            return null;
        }
    }

    /**
     * Return a List of all packages that are installed
     */
    public static List<PackageInfo> getInstalledPackages(Context context) {
        PackageManager manager = context.getPackageManager();
        return manager.getInstalledPackages(0);
    }

    /**
     * Judging whether a program has been installed
     */
    public static boolean isInstalled(Context context, String packageName) {
        boolean isInstalled = false;
        List<PackageInfo> packageInfos = getInstalledPackages(context);
        for (PackageInfo packageInfo : packageInfos) {
            if (packageInfo.packageName.equals(packageName)) {
                isInstalled = true;
            }
        }
        return isInstalled;
    }

    /**
     * 判断是否是第三方软件
     */
    public static boolean isThirdPartyApp(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = pm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return isThirdPartyApp(packageInfo);
    }

    /**
     * 判断是否是第三方软件
     */
    public static boolean isThirdPartyApp(PackageInfo packageInfo) {
        if (null == packageInfo || null == packageInfo.applicationInfo) {
            return false;
        }
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) || ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }

    /**
     * 读取指定路径下APK文件签名
     */
    @SuppressWarnings({"unchecked", "resource"})
    public static String getJarSignature(String filePath) throws Exception {
        if (null == filePath) {
            return null;
        }
        String resultSign = "";
        String resultKey = "";
        List<ZipEntry> names = new ArrayList<ZipEntry>();
        ZipFile zf = new ZipFile(filePath);
        Enumeration<ZipEntry> zi = (Enumeration<ZipEntry>) zf.entries();
        while (zi.hasMoreElements()) {
            ZipEntry ze = zi.nextElement();
            String name = ze.getName();
            if (name.startsWith("META-INF/") && (name.endsWith(".RSA") || name.endsWith(".DSA"))) {
                names.add(ze);
            }
        }
        Collections.sort(names, new Comparator<ZipEntry>() {
            @Override
            public int compare(ZipEntry obj1, ZipEntry obj2) {
                if (obj1 != null && obj2 != null) {
                    return obj1.getName().compareToIgnoreCase(obj2.getName());
                }
                return 0;
            }
        });
        for (ZipEntry ze : names) {
            InputStream is = zf.getInputStream(ze);
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                CertPath cp = cf.generateCertPath(is, "PKCS7");
                List<?> list = cp.getCertificates();
                for (Object obj : list) {
                    if (!(obj instanceof X509Certificate)) continue;
                    X509Certificate cert = (X509Certificate) obj;
                    StringBuilder builder = new StringBuilder();
                    builder.setLength(0);
                    byte[] key = getPKBytes(cert.getPublicKey());
                    for (byte aKey : key) {
                        builder.append(String.format("%02X", aKey));
                    }
                    resultKey += builder.toString();
                    builder.setLength(0);
                    byte[] signature = cert.getSignature();

                    for (byte aSignature : signature) {
                        builder.append(String.format("%02X", aSignature));
                    }
                    resultSign += builder.toString();
                }
            } catch (CertificateException e) {
            }
            is.close();
        }
        if (!TextUtils.isEmpty(resultKey) && !TextUtils.isEmpty(resultSign)) {
            return hashCode(resultKey) + "," + hashCode(resultSign);
        }
        return null;
    }

    /**
     * 根据公钥获取key
     */
    private static byte[] getPKBytes(PublicKey pk) {
        if (pk instanceof RSAPublicKey) {
            RSAPublicKey k = (RSAPublicKey) pk;
            return k.getModulus().toByteArray();
        } else if (pk instanceof DSAPublicKey) {
            DSAPublicKey k = (DSAPublicKey) pk;
            return k.getY().toByteArray();
        }
        return null;
    }

    /**
     * 计算签名时的hashcode算法
     */
    public static int hashCode(String str) {
        int hash = 0;
        if (str != null) {
            int multiplier = 1;
            int offset = 0;
            int count = str.length();
            char[] value = new char[count];
            str.getChars(offset, count, value, 0);
            for (int i = offset + count - 1; i >= offset; i--) {
                hash += value[i] * multiplier;
                int shifted = multiplier << 5;
                multiplier = shifted - multiplier;
            }
        }
        return hash;
    }

    /**
     * 通过包名读取已安装APP数字签名
     */
    public static String getInstalledPackageSignature(String packageName) {
        Context context = UIUtil.getContext();
        if (null == context) {
            return null;
        }
        String signature = null;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_SIGNATURES);
            String apkPath = appInfo.sourceDir;
            signature = getJarSignature(apkPath);
        } catch (PackageManager.NameNotFoundException e) {
        } catch (Exception e) {
        }
        return signature;
    }

}

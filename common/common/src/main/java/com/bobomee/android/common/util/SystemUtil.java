/*
 * Android_Common. lastModified by bobomee at 2016.5.16 11:5
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

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 获取设备信息工具类
 */
public class SystemUtil {

    public static String getUUID(Context ctx) {
        final TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(ctx.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }

    /**
     * 获取手机品牌
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取android系统版本号
     */
    public static String getOSVersion() {
        return "android" + Build.VERSION.RELEASE;
    }

    /**
     * 获得android系统sdk版本号
     */
    public static String getOSVersionSDK() {
        return Build.VERSION.SDK;
    }

    /**
     * 获得android系统sdk版本号
     */
    public static int getOSVersionSDKINT() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * build id
     */
    public static String getBuildId() {
        return Build.ID;
    }

    /**
     * display
     */
    public static String getBuildDisplay() {
        return Build.DISPLAY;
    }

    /**
     * DEVICE
     */
    public static String getBuildDevice() {
        return Build.DEVICE;
    }

    /**
     * BOARD
     */
    public static String getBuildBord() {
        return Build.BOARD;
    }

    /**
     * CPU _ABI
     */
    public static String getCpuABI() {
        return Build.CPU_ABI;
    }

    /**
     * CPU_ABI2
     */
    public static String getCpuABI2() {
        return Build.CPU_ABI2;
    }

    /**
     * MANUFACTURER
     */
    public static String getBuildManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * BOOTLOADER
     */
    public static String getBootloader() {
        return Build.BOOTLOADER;
    }

    /**
     * RADIO
     */
    public static String getRadioVersion() {
        return Build.getRadioVersion();
    }

    /**
     * getRadioVersion
     */
    public static String getRadio() {
        return Build.RADIO;
    }

    /**
     * HARDWARE
     */
    public static String getHardware() {
        return Build.HARDWARE;
    }

    /**
     * SERIAL
     */
    public static String getSerial() {
        return Build.SERIAL;
    }

    /**
     * PRODUCT
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * BUILDTIME
     */
    public static long getBuildTime() {
        return Build.TIME;
    }

    /**
     * INCREMENTAL
     */
    public static String getIncremental() {
        return Build.VERSION.INCREMENTAL;
    }

    /**
     * HOST
     */
    public static String getHost() {
        return Build.HOST;
    }

    /**
     * TYPE
     */
    public static String getType() {
        return Build.TYPE;
    }

    /**
     * TAGS
     */
    public static String getTags() {
        return Build.TAGS;
    }

    /**
     * CODENAME
     */
    public static String getCodename() {
        return Build.VERSION.CODENAME;
    }

    /**
     * USER
     */
    public static String getUser() {
        return Build.USER;
    }

    /**
     * FINGERPRINT
     */
    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    public static int getMaxCpuFreq() {
        int result = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
            br = new BufferedReader(fr);
            String text = br.readLine();
            result = Integer.parseInt(text.trim());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(fr);
            IOUtil.closeQuietly(br);
        }

        return result;
    }

    /* 获取CPU最小频率（单位KHZ） */
    public static int getMinCpuFreq() {
        int result = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq");
            br = new BufferedReader(fr);
            String text = br.readLine();
            result = Integer.parseInt(text.trim());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(fr);
            IOUtil.closeQuietly(br);
        }
        return result;
    }

    /* 实时获取CPU当前频率（单位KHZ） */
    public static int getCurCpuFreq() {
        int result = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            br = new BufferedReader(fr);
            String text = br.readLine();
            result = Integer.parseInt(text.trim());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(fr);
            IOUtil.closeQuietly(br);
        }
        return result;
    }

    /* 获取CPU名字 */
    public static HashMap<String, String> getCpuInfos() {
        HashMap<String, String> mCpuInfos = new HashMap<String, String>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(fr);
            String thisLine;
            while ((thisLine = br.readLine()) != null) {
                if (!"".equals(thisLine)) {
                    String[] array = thisLine.split(":\\s+");
                    mCpuInfos.put(array[0], array[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(fr);
            IOUtil.closeQuietly(br);
        }
        return mCpuInfos;
    }


    /**
     * 获取launcher信息
     */
    private static ActivityInfo getLauncherInfo(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        return res.activityInfo;
    }

    /**
     * 获取桌面包名
     */

    public static String getLauncherPackName(Context context) {
        return getLauncherInfo(context).packageName;
    }

    /**
     * 获取桌面名称
     */
    public static String getLauncherName(Context context) {
        return getLauncherInfo(context).name;
    }

    /**
     * 获取设备的IMEI,硬件id
     */
    public static String getIMEI(Context context) {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
        return imei;
    }

    /**
     * 取得IMEI SV 设备的软件版本号： 返回移动终端的软件版本，例如：GSM手机的IMEI/SV码。 例如：the
     * IMEI/SV(software version) for GSM phones. Return null if the software
     * version is not available.
     */
    public static String getDeviceSoftwareVersion(Context context) {
        String mDeviceSoftwareVersion = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceSoftwareVersion();// String
        return mDeviceSoftwareVersion;
    }

    private static String get(Context context, String key) {
        String ret;
        try {
            ClassLoader cl = context.getClassLoader();
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
            Method get = SystemProperties.getMethod("get", String.class);
            ret = (String) get.invoke(SystemProperties, key);
        } catch (Exception e) {
            ret = "";
            e.printStackTrace();
        }

        return ret;

    }

    /**
     * clientidbase
     */
    public static String getClientidbase(Context context) {
        return get(context, "ro.com.google.clientidbase");
    }

    /**
     * modem
     */
    public static String getModem(Context context) {
        return get(context, "persist.radio.modem");
    }

    /**
     * description
     */
    public static String getDescription(Context context) {
        return get(context, "ro.build.description");
    }

    /**
     * serialno
     */
    public static String getSerialno(Context context) {
        return get(context, "ro.serialno");
    }

    /**
     * meminfo TotalRam 的字节数大小
     */
    public static long getTotalMem() {
        BufferedReader br = null;
        try {
            FileInputStream fis = new FileInputStream(new File("/proc/meminfo"));
            br = new BufferedReader(new InputStreamReader(fis));
            String totalInfo = br.readLine(); // 获取到全部RAM信息
            StringBuilder sb = new StringBuilder();
            for (char c : totalInfo.toCharArray()) {
                // 取出其中的数字信息,即所有的RAM
                if (c >= '0' && c <= '9') {
                    sb.append(c);
                }
            }
            // 得到所有RAM的字节数
            return Long.parseLong(sb.toString()) * 1024;
        } catch (Exception e) {
            return 0;
        } finally {
            IOUtil.closeQuietly(br);
        }
    }

    /**
     * 获取系统内核版本
     */
    public static String getKernelVersion() {
        String result = "";
        String line;
        String[] cmd = new String[]{"/system/bin/cat", "/proc/version"};
        String workdirectory = "/system/bin/";
        try {
            ProcessBuilder bulider = new ProcessBuilder(cmd);
            bulider.directory(new File(workdirectory));
            bulider.redirectErrorStream(true);
            Process process = bulider.start();
            InputStream in = process.getInputStream();
            InputStreamReader isrout = new InputStreamReader(in);
            BufferedReader brout = new BufferedReader(isrout, 8 * 1024);

            while ((line = brout.readLine()) != null) {
                result += line;
                // result += "\n";
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * MTK
     */
    /**
     * mediatek
     */
    public static String getMediatek(Context context) {
        return get(context, "ro.mediatek.version.release");
    }

    /**
     * mediatek.platform
     */
    public static String getPlatform(Context context) {
        return get(context, "ro.mediatek.platform");
    }

    /**
     * mediatek.chip_ver
     */
    public static String getChip_ver(Context context) {
        return get(context, "ro.mediatek.chip_ver");
    }

    /**
     * version.branch
     */
    public static String getBranch(Context context) {
        return get(context, "ro.mediatek.version.branch");
    }

    /**
     * 检测手机是否已插入SIM卡
     */
    public static boolean isCheckSimCardAvailable(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * sim卡是否可读
     */
    public static boolean isCanUseSim(Context context) {
        try {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 取得当前sim手机卡的imsi
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    /**
     * 返回本地手机号码，这个号码不一定能获取到
     */
    public static String getNativePhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    /**
     * 返回手机服务商名字
     */
    public static String getProvidersName(Context context) {
        String ProvidersName = null;
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = getIMSI(context);
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        } else {
            ProvidersName = "其他服务商:" + IMSI;
        }
        return ProvidersName;
    }

    /**
     * 获取当前设备的SN
     */
    public static String getSimSN(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    //获取手MAC地址
    public static String getMacAddress(Context context) {
        // 获取mac地址：
        String macAddress = "000000000000";
        try {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr
                    .getConnectionInfo());
            if (null != info) {
                if (!TextUtils.isEmpty(info.getMacAddress()))
                    macAddress = info.getMacAddress().replace(":", "");
                else
                    return macAddress;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return macAddress;
        }
        return operate(macAddress);
    }

    public static String operate(String s) {
        String str2 = "";
        for (int i = 0; i < s.length(); i++) {
            if (i * 2 + 2 > s.length()) {
                str2 += s.substring(i * 2, s.length());
                break;
            }
            str2 += s.substring(i * 2, i * 2 + 2) + ":";
        }
        if (str2.endsWith(":")) {
            str2 = str2.substring(0, str2.length() - 1);
        }

        return str2;
    }

    /**
     * 获得设备ip地址
     */
    public static String getLocalAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            LogUtil.e(e.toString());
        }
        return null;
    }

    /**
     * 获取屏幕的分辨率
     */
    @SuppressWarnings("deprecation")
    public static int[] getResolution(Context context) {
        WindowManager windowMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int[] res = new int[2];
        res[0] = windowMgr.getDefaultDisplay().getWidth();
        res[1] = windowMgr.getDefaultDisplay().getHeight();
        return res;
    }

    /**
     * 获得设备的横向dpi
     */
    public static float getWidthDpi(Context context) {

        return context.getApplicationContext().getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获得设备的纵向dpi
     */
    public static float getHeightDpi(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.ydpi;
    }

    /**
     * 判断手机CPU是否支持NEON指令集
     */
    public static boolean isNEON() {
        boolean isNEON = false;
        HashMap<String, String> cupinfo = getCpuInfos();
        if (cupinfo != null) {
            isNEON = cupinfo != null && cupinfo.containsKey("neon");
        }
        return isNEON;
    }

    /**
     * 获取当前设备cpu的型号
     */
    public static int getCPUModel() {
        return matchABI(getSystemProperty("ro.product.cpu.abi")) | matchABI(getSystemProperty("ro.product.cpu.abi2"));
    }

    /**
     * 匹配当前设备的cpu型号
     */
    private static int matchABI(String abiString) {
        if (TextUtils.isEmpty(abiString)) {
            return 0;
        }
        if ("armeabi".equals(abiString)) {
            return 1;
        } else if ("armeabi-v7a".equals(abiString)) {
            return 2;
        } else if ("x86".equals(abiString)) {
            return 4;
        } else if ("mips".equals(abiString)) {
            return 8;
        }
        return 0;
    }

    /**
     * 获取CPU核心数
     */
    public static int getCpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * getProp
     */
    public static String getExcChmod(String mod) {
        String str2;
        StringBuilder resusl = new StringBuilder();
        String resualStr = "";
        try {
            Process process = Runtime.getRuntime().exec(mod);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            BufferedReader input = new BufferedReader(ir);
            while ((str2 = input.readLine()) != null) {
                resusl.append(str2 + "<END>");
            }
            if (resusl != null) {
                resualStr = resusl.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resualStr;

    }


    /**
     * 获取Rom版本
     */
    public static String getRomversion() {
        String rom = "";
        try {
            String modversion = getSystemProperty("ro.modversion");
            String displayId = getSystemProperty("ro.build.display.id");
            if (modversion != null && !modversion.equals("")) {
                rom = modversion;
            }
            if (displayId != null && !displayId.equals("")) {
                rom = displayId;
            }
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
        return rom;
    }

    /**
     * 获取系统配置参数
     */
    public static String getSystemProperty(String key) {
        String pValue = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method m = c.getMethod("get", String.class);
            pValue = m.invoke(null, key).toString();
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
        return pValue;
    }

    /**
     * 获取系统中的Library包
     */
    public static List<String> getSystemLibs(Context context) {
        PackageManager pm = context.getPackageManager();
        String[] libNames = pm.getSystemSharedLibraryNames();
        List<String> listLibNames = Arrays.asList(libNames);
        LogUtil.d("SystemLibs: " + listLibNames);
        return listLibNames;
    }

    /**
     * 获取手机外部可用空间大小，单位为byte
     */
    @SuppressWarnings("deprecation")
    public static long getExternalTotalSpace(Context context) {
        long totalSpace = -1L;
        if (StorageUtil.hasSDCardMounted()) {
            try {
                String path = Environment.getExternalStorageDirectory().getPath();// 获取外部存储目录即 SDCard
                StatFs stat = new StatFs(path);
                long blockSize = stat.getBlockSize();
                long totalBlocks = stat.getBlockCount();
                totalSpace = totalBlocks * blockSize;
            } catch (Exception e) {
                LogUtil.e(e.toString());
            }
        }
        return totalSpace;
    }

    /**
     * 获取外部存储可用空间，单位为byte
     */
    @SuppressWarnings("deprecation")
    public static long getExternalSpace(Context context) {
        long availableSpace = -1L;
        if (StorageUtil.hasSDCardMounted()) {
            try {
                String path = Environment.getExternalStorageDirectory().getPath();
                StatFs stat = new StatFs(path);
                availableSpace = stat.getAvailableBlocks() * (long) stat.getBlockSize();
            } catch (Exception e) {
                LogUtil.e(e.toString());
            }
        }
        return availableSpace;
    }

    /**
     * 获取手机内部空间大小，单位为byte
     */
    @SuppressWarnings("deprecation")
    public static long getTotalInternalSpace() {
        long totalSpace = -1L;
        try {
            String path = Environment.getDataDirectory().getPath();
            StatFs stat = new StatFs(path);
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();// 获取该区域可用的文件系统数
            totalSpace = totalBlocks * blockSize;
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
        return totalSpace;
    }

    /**
     * 获取手机内部可用空间大小，单位为byte
     */
    @SuppressWarnings("deprecation")
    public static long getAvailableInternalMemorySize() {
        long availableSpace = -1l;
        try {
            String path = Environment.getDataDirectory().getPath();// 获取 Android 数据目录
            StatFs stat = new StatFs(path);// 一个模拟linux的df命令的一个类,获得SD卡和手机内存的使用情况
            long blockSize = stat.getBlockSize();// 返回 Int ，大小，以字节为单位，一个文件系统
            long availableBlocks = stat.getAvailableBlocks();// 返回 Int ，获取当前可用的存储空间
            availableSpace = availableBlocks * blockSize;
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
        return availableSpace;
    }

    /**
     * 获取单个应用最大分配内存，单位为byte
     */
    public static long getOneAppMaxMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getMemoryClass() * 1024 * 1024;
    }

    /**
     * 获取指定包名应用占用的内存，单位为byte
     */
    public static long getUsedMemory(Context context, String packageName) {
        if (StorageUtil.hasSDCardMounted()) {
            packageName = context.getPackageName();
        }
        long size = 0;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runapps = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runapp : runapps) { // 遍历运行中的程序
            if (packageName.equals(runapp.processName)) {// 得到程序进程名，进程名一般就是包名，但有些程序的进程名并不对应一个包名
                // 返回指定PID程序的内存信息，可以传递多个PID，返回的也是数组型的信息
                Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{runapp.pid});
                // 得到内存信息中已使用的内存，单位是K
                size = processMemoryInfo[0].getTotalPrivateDirty() * 1024;
            }
        }
        return size;
    }

    /**
     * 获取手机剩余内存，单位为byte
     */
    public static long getAvailableMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.availMem;
    }

    /**
     * 获取手机总内存，单位为byte
     */
    public static long getTotalMemory() {
        long size = 0;
        String path = "/proc/meminfo";// 系统内存信息文件
        try {
            String totalMemory = FileUtil.readProperties(path, "MemTotal", null);// 读出来是带单位kb的，并且单位前有空格，所以去掉最后三位
            if (!TextUtils.isEmpty(totalMemory) && totalMemory.length() > 3) {
                size = Long.valueOf(totalMemory.substring(0, totalMemory.length() - 3)) * 1024;
            }
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
        return size;
    }

    /**
     * 手机低内存运行阀值，单位为byte
     */
    public static long getThresholdMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.threshold;
    }

    /**
     * 手机是否处于低内存运行
     */
    public static boolean isLowMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.lowMemory;
    }

}

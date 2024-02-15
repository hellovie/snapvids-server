package io.github.hellovie.snapvids.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 项目变量获取工具。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ProjectUtils {

    private ProjectUtils() {}

    /**
     * 获取项目的 IP 地址，获取失败则为 null。
     *
     * @return 项目的 IP 地址
     */
    public static String getProjectIp() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // 获取失败返回 null
            return null;
        }
        return address.getHostAddress();
    }

    /**
     * 通过 {@code System.getProperty("user.dir")} 获取项目路径，再用字符分割获取项目名称。
     * <p>仅支持文件夹名与项目名称一致的项目。</p>
     *
     * @return 项目名称
     */
    public static String getProjectName() {
        String projectPath = System.getProperty("user.dir");
        int index = projectPath.lastIndexOf('\\');
        if (index == -1 || index + 1 >= projectPath.length()) {
            return projectPath;
        }

        return projectPath.substring(index + 1);
    }
}

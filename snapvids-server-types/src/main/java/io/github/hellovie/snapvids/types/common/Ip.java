package io.github.hellovie.snapvids.types.common;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import java.util.ArrayList;
import java.util.List;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.WRONG_IP_ADDRESS;

/**
 * [Domain Primitive] ip.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Ip implements Verifiable {

    /**
     * ip 地址
     */
    private final String address;

    public Ip(String address) {
        this.address = address;
        verify();
    }

    public Ip(int address) {
        this.address = int2Ip(address);
        verify();
    }

    /**
     * {@inheritDoc}
     *
     * @see Verifiable#verify()
     */
    @Override
    public void verify() throws InvalidParamException {
        final String pattern = "^((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}$";
        Validation.isNotBlankOrElseThrow(address, WRONG_IP_ADDRESS);
        Validation.isMatchOrElseThrow(address, pattern, WRONG_IP_ADDRESS);
    }

    public String getAddress() {
        return address;
    }

    public int getIntAddress() {
        return ip2Int(address);
    }

    /**
     * 将 long 转换为 ip 字符串。
     *
     * @param ipInt 用 int 表示的 ip 值
     * @return ip字符串，如 127.0.0.1
     */
    private static String int2Ip(int ipInt) {
        List<String> ipString = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            // 每 8 位为一段，这里取当前要处理的最高位的位置
            int pos = i * 8;
            // 取当前处理的 ip 段的值
            int and = ipInt & (255 << pos);
            // 将当前 ip 段转换为 0 ~ 255 的数字，注意这里必须使用无符号右移
            ipString.add(String.valueOf(and >>> pos));
        }
        return String.join(".", ipString);
    }

    /**
     * 将 ip 字符串转换为 int 类型的数字。
     * <p>
     * 思路就是将 ip 的每一段数字转为 8 位二进制数，并将它们放在结果的适当位置上
     *
     * @param ipString ip字符串，如 127.0.0.1
     * @return ip字符串对应的 int 值
     */
    public static int ip2Int(String ipString) {
        // 取 ip 的各段
        String[] ipSlices = ipString.split("\\.");
        int rs = 0;
        for (int i = 0; i < ipSlices.length; i++) {
            // 将 ip 的每一段解析为 int，并根据位置左移 8 位
            int intSlice = Integer.parseInt(ipSlices[i]) << 8 * i;
            // 或运算
            rs = rs | intSlice;
        }
        return rs;
    }
}

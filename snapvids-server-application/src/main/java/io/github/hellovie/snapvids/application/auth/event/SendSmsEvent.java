package io.github.hellovie.snapvids.application.auth.event;

import io.github.hellovie.snapvids.types.common.PhoneNumber;

/**
 * 发送短信验证码事件。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class SendSmsEvent {

    /**
     * 手机号码
     */
    private final PhoneNumber phoneNumber;

    public SendSmsEvent(String phoneNumber) {
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}

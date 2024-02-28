package io.github.hellovie.snapvids.interfaces.web.request;

/**
 * 发送短信表单。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class SendSmsRequest {

    /**
     * 手机号码
     */
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

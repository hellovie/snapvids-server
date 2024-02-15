package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.Gender;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 用户信息表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "UserInfo")
@Table(name = "user_info")
public class UserInfo extends BaseEntity {

    /**
     * 用户 ID
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * 用户封面 ID
     */
    @OneToOne
    @JoinColumn(name = "cover_id", referencedColumnName = "id")
    private File cover;

    /**
     * 用户头像 ID
     */
    @OneToOne
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private File avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户自我描述
     */
    private String description;

    /**
     * 用户名字
     */
    private String firstName;

    /**
     * 用户姓氏
     */
    private String lastName;

    /**
     * 用户性别（默认未知）
     */
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    /**
     * 用户生日
     */
    private Timestamp birthday;

    /**
     * 用户详细地址
     */
    private String address;

    /**
     * 用户所在城市
     */
    private String city;

    /**
     * 用户所在州/省
     */
    private String state;

    /**
     * 用户所在国家
     */
    private String country;

    /**
     * 用户信息私密级别（默认全展示）
     */
    private Integer privacyLevel;

    public UserInfo() {}

    public UserInfo(Long id) {
        super(id);
    }

    public User getUser() {
        return user;
    }

    public UserInfo setUser(User user) {
        this.user = user;
        return this;
    }

    public File getCover() {
        return cover;
    }

    public UserInfo setCover(File cover) {
        this.cover = cover;
        return this;
    }

    public File getAvatar() {
        return avatar;
    }

    public UserInfo setAvatar(File avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public UserInfo setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UserInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserInfo setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserInfo setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public UserInfo setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public UserInfo setBirthday(Timestamp birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserInfo setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserInfo setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public UserInfo setState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserInfo setCountry(String country) {
        this.country = country;
        return this;
    }

    public Integer getPrivacyLevel() {
        return privacyLevel;
    }

    public UserInfo setPrivacyLevel(Integer privacyLevel) {
        this.privacyLevel = privacyLevel;
        return this;
    }
}

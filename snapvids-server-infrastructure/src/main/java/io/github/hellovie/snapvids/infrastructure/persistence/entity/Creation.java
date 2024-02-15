package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.CreationType;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.CreationVisibility;

import javax.persistence.*;
import java.util.List;

/**
 * 作品表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "Creation")
@Table(name = "creation")
public class Creation extends AuditEntity {

    /**
     * 文案
     */
    private String caption;

    /**
     * 作品封面图片
     */
    @OneToOne
    @JoinColumn(name = "cover_id", referencedColumnName = "id")
    private File cover;

    /**
     * 音量
     */
    private Integer volume = 0;

    /**
     * 背景音乐
     */
    @OneToOne
    @JoinColumn(name = "bgm_id", referencedColumnName = "id")
    private File bgm;

    /**
     * BGM 音量
     */
    private Integer bgmVolume = 100;

    /**
     * 播放量
     */
    private Integer views = 0;

    /**
     * 作品类型（默认独创）
     */
    @Enumerated(EnumType.ORDINAL)
    private CreationType type;

    /**
     * 作品可见范围（默认全部可见）
     */
    @Enumerated(EnumType.ORDINAL)
    private CreationVisibility visibility;

    /**
     * 作品权限（默认无设置任何权限）
     */
    private Integer permissions;

    /**
     * 作者 ID
     * <p>使用作者 ID 去查询 {@link UserInfo}。</p>
     */
    private Long authorId;

    /**
     * At 的用户 ID（英文逗号分割）
     */
    private String atUserIds;

    /**
     * 推荐权重（0 - 不展示，权重越高，越容易被其他用户看见）
     */
    private Integer weight;

    /**
     * 作品标签
     */
    @OneToMany
    @JoinTable(
            name = "creation_tag",
            joinColumns = @JoinColumn(name = "creation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private List<Tag> tags;

    /**
     * 作品内容
     */
    @OneToMany
    @JoinTable(
            name = "creation_file",
            joinColumns = @JoinColumn(name = "creation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id")
    )
    private List<File> content;

    // Todo：是否能仅显示用户 ID？
    /**
     * 用户范围（可见用户列表、不给谁看列表）
     */
    @OneToMany
    @JoinTable(
            name = "creation_visibility",
            joinColumns = @JoinColumn(name = "creation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> usersScope;

    public Creation() {}

    public Creation(Long id) {
        super(id);
    }

    public String getCaption() {
        return caption;
    }

    public Creation setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public File getCover() {
        return cover;
    }

    public Creation setCover(File cover) {
        this.cover = cover;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public Creation setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public File getBgm() {
        return bgm;
    }

    public Creation setBgm(File bgm) {
        this.bgm = bgm;
        return this;
    }

    public Integer getBgmVolume() {
        return bgmVolume;
    }

    public Creation setBgmVolume(Integer bgmVolume) {
        this.bgmVolume = bgmVolume;
        return this;
    }

    public Integer getViews() {
        return views;
    }

    public Creation setViews(Integer views) {
        this.views = views;
        return this;
    }

    public CreationType getType() {
        return type;
    }

    public Creation setType(CreationType type) {
        this.type = type;
        return this;
    }

    public CreationVisibility getVisibility() {
        return visibility;
    }

    public Creation setVisibility(CreationVisibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public Integer getPermissions() {
        return permissions;
    }

    public Creation setPermissions(Integer permissions) {
        this.permissions = permissions;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Creation setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getAtUserIds() {
        return atUserIds;
    }

    public Creation setAtUserIds(String atUserIds) {
        this.atUserIds = atUserIds;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public Creation setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Creation setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public List<File> getContent() {
        return content;
    }

    public Creation setContent(List<File> content) {
        this.content = content;
        return this;
    }

    public List<User> getUsersScope() {
        return usersScope;
    }

    public Creation setUsersScope(List<User> usersScope) {
        this.usersScope = usersScope;
        return this;
    }
}

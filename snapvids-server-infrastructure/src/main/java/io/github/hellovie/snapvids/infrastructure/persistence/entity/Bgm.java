package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 作品背景音乐表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "Bgm")
@Table(name = "bgm")
public class Bgm extends BaseEntity {

    /**
     * BGM 名称
     */
    private String bgmName;

    /**
     * BGM 封面图片
     */
    @OneToOne
    @JoinColumn(name = "cover_id", referencedColumnName = "id")
    private File cover;

    /**
     * 背景音乐文件
     */
    @OneToOne
    @JoinColumn(name = "music_id", referencedColumnName = "id")
    private File music;

    /**
     * 作者 ID
     * <p>使用作者 ID 去查询 {@link UserInfo}。</p>
     */
    private Long authorId;

    /**
     * 时长
     */
    private Integer duration;

    /**
     * 使用次数
     */
    private Integer useCount;

    /**
     * 推荐权重（0 - 不展示，权重越高，越容易被其他用户看见）
     */
    private Integer weight;

    public Bgm() {}

    public Bgm(Long id) {
        super(id);
    }

    public String getBgmName() {
        return bgmName;
    }

    public Bgm setBgmName(String bgmName) {
        this.bgmName = bgmName;
        return this;
    }

    public File getCover() {
        return cover;
    }

    public Bgm setCover(File cover) {
        this.cover = cover;
        return this;
    }

    public File getMusic() {
        return music;
    }

    public Bgm setMusic(File music) {
        this.music = music;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Bgm setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public Bgm setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public Bgm setUseCount(Integer useCount) {
        this.useCount = useCount;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public Bgm setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }
}

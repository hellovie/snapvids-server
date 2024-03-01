package io.github.hellovie.snapvids.infrastructure.persistence.enums;

/**
 * 文件后缀。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum FileExt {

    JPG(FileType.IMAGE),
    PNG(FileType.IMAGE),
    MP3(FileType.AUDIO),
    MP4(FileType.VIDEO);

    private final FileType type;

    FileExt(FileType type) {
        this.type = type;
    }

    public FileType getType() {
        return type;
    }
}

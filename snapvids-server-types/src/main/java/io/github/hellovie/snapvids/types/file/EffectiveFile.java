package io.github.hellovie.snapvids.types.file;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;
import io.github.hellovie.snapvids.common.util.TypeConvertor;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileExt;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileType;
import org.springframework.web.multipart.MultipartFile;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.*;

/**
 * [Domain Primitive] effective file.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class EffectiveFile implements Verifiable {

    /**
     * 文件名
     */
    private final Filename filename;

    /**
     * 文件后缀
     */
    private final FileExt fileExt;

    /**
     * 文件类型
     */
    private final FileType fileType;

    /**
     * 文件哈希
     */
    private final FileKey fileKey;

    /**
     * 文件大小
     */
    private final FileSize fileSize;

    /**
     * 实际文件
     */
    private final MultipartFile file;

    public EffectiveFile(MultipartFile file) {
        this.file = file;
        verify();
        String _filename = file.getOriginalFilename();
        this.filename = new Filename(_filename.substring(0, _filename.lastIndexOf(".")));
        this.fileExt = (FileExt) TypeConvertor.toEnum(_filename.substring(_filename.lastIndexOf(".") + 1), FileExt.class);
        this.fileType = fileExt.getType();
        this.fileKey = new FileKey(FileKey.calculateHash(file));
        this.fileSize = new FileSize(file.getSize());
    }

    /**
     * {@inheritDoc}
     *
     * @see Verifiable#verify()
     */
    @Override
    public void verify() throws InvalidParamException {
        Validation.isNotNullOrElseThrow(file, UPLOAD_FILE_CANNOT_BE_EMPTY);
        // 文件名校验
        String _filename = file.getOriginalFilename();
        Validation.isNotBlankOrElseThrow(_filename, FILENAME_CANNOT_BE_EMPTY);
        Validation.executeWithException(() -> new Filename(_filename.substring(0, _filename.lastIndexOf("."))),
                FILENAME_CANNOT_BE_EMPTY);
        // 文件后缀校验
        Validation.executeWithException(() -> {
            String _ext = _filename.substring(_filename.lastIndexOf(".") + 1);
            Validation.isEnumNameOrElseThrow(_ext, FileExt.class, UNSUPPORTED_FILE_TYPES);
            FileExt _fileExt = (FileExt) TypeConvertor.toEnum(_ext, FileExt.class);
        }, UNSUPPORTED_FILE_TYPES);
    }

    public Filename getFilename() {
        return filename;
    }

    public FileExt getFileExt() {
        return fileExt;
    }

    public FileType getFileType() {
        return fileType;
    }

    public FileKey getFileKey() {
        return fileKey;
    }

    public FileSize getFileSize() {
        return fileSize;
    }

    public MultipartFile getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "EffectiveFile{" +
                "filename=" + filename +
                ", fileExt=" + fileExt +
                ", fileType=" + fileType +
                ", fileKey=" + fileKey +
                ", fileSize=" + fileSize +
                ", file=" + file +
                '}';
    }
}

package io.github.hellovie.snapvids.common.exception.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 异常模块管理器。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ExceptionManager {

    /**
     * 异常模块枚举
     */
    private final ExceptionModule[] modules;

    /**
     * 模块编号长度
     */
    private int moduleNumLen = 2;

    /**
     * 模块编号最小值
     */
    private int moduleMinNum = 1;

    /**
     * 错误编号长度
     */
    private int errorNumLen = 4;

    /**
     * 错误编号最小值
     */
    private int errorMinNum = 1;

    /**
     * 格式化异常状态码。
     * <p>格式：{@link ExceptionModule#getNumber} + {@link ExceptionSource#getLabel} + {@link ExceptionCode#getNumber}。</p>
     * <p>例如：01-X-0001。</p>
     * <p>注意：模块编号的长度默认为 2；错误编号的长度默认为 4。</p>
     *
     * @return 异常状态码
     */
    public String formatCode(final ExceptionCode exceptionCode) {
        String module = String.format("%0" + this.moduleNumLen + "d", exceptionCode.getModule().getNumber());
        String label = exceptionCode.getSource().getLabel();
        String errorCode = String.format("%0" + this.errorNumLen + "d", exceptionCode.getNumber());

        return module + "-" + label + "-" + errorCode;
    }

    /**
     * 获取异常状态码哈希表。
     *
     * @return 异常状态码哈希表
     * @throws RuntimeException 获取失败抛出异常
     */
    public Map<String, ExceptionCode> getCodeMap() throws RuntimeException {
        Map<String, ExceptionCode> exceptionCodeMap = new HashMap<>();

        for (ExceptionModule module : this.modules) {
            Class<? extends ExceptionCode> exceptionCodeClazz = module.getExceptionCodeClazz();
            if (exceptionCodeClazz == null) {
                throw new RuntimeException("找不到「" + module.getName() + "」绑定的异常状态码枚举类");
            }
            ExceptionCode[] codeEnums = exceptionCodeClazz.getEnumConstants();
            for (ExceptionCode exceptionCode : codeEnums) {
                exceptionCodeMap.put(formatCode(exceptionCode), exceptionCode);
            }
        }

        return exceptionCodeMap;
    }

    /**
     * 私有构造，外部只能通过 Builder 构造对象。
     *
     * @param modules {@link ExceptionModule} 数组
     * @throws RuntimeException 构造对象失败抛出异常
     */
    private ExceptionManager(final ExceptionModule[] modules) {
        this.modules = modules;
    }

    /**
     * 建造者模式。
     */
    public static class Builder {
        /**
         * 异常管理器
         */
        private final ExceptionManager exceptionManager;

        /**
         * 异常模块枚举
         */
        private final ExceptionModule[] modules;

        /**
         * 异常来源枚举
         */
        private final ExceptionSource[] sources;

        /**
         * 构造器。
         *
         * @param moduleClass {@link ExceptionModule} class
         * @param sourceClass {@link ExceptionSource} class
         * @throws RuntimeException 构造对象失败抛出异常
         */
        public Builder(final Class<? extends ExceptionModule> moduleClass, final Class<? extends ExceptionSource> sourceClass) throws RuntimeException {
            ExceptionModule[] modules;
            ExceptionSource[] sources;
            try {
                modules = moduleClass.getEnumConstants();
                sources = sourceClass.getEnumConstants();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            this.modules = modules;
            this.sources = sources;
            this.exceptionManager = new ExceptionManager(modules);
        }

        /**
         * 设置模块编号长度。
         *
         * @param moduleNumLen 模块编号长度
         * @return {@link Builder}
         */
        public Builder setModuleNumLen(final int moduleNumLen) {
            this.exceptionManager.moduleNumLen = moduleNumLen;
            return this;
        }

        /**
         * 设置模块编号最小值（包含）。
         *
         * @param moduleMinNum 模块编号最小值
         * @return {@link Builder}
         */
        public Builder setModuleMinNum(final int moduleMinNum) {
            this.exceptionManager.moduleMinNum = moduleMinNum;
            return this;
        }

        /**
         * 设置错误编号长度。
         *
         * @param errorNumLen 错误编号长度
         * @return {@link Builder}
         */
        public Builder setErrorNumLen(final int errorNumLen) {
            this.exceptionManager.errorNumLen = errorNumLen;
            return this;
        }

        /**
         * 设置错误编号最小值（包含）。
         *
         * @param errorMinNum 错误编号最小值
         * @return {@link Builder}
         */
        public Builder setErrorMinNum(final int errorMinNum) {
            this.exceptionManager.errorMinNum = errorMinNum;
            return this;
        }

        /**
         * 校验异常模块是否合法。
         * <ul>
         *     <li>校验模块编号是否重复。</li>
         * </ul>
         *
         * @return {@link Builder}
         * @throws RuntimeException 校验失败抛出异常
         */
        public Builder checkModule() throws RuntimeException {
            final Set<Integer> moduleNumberSet = new HashSet<>();
            final int min = exceptionManager.moduleMinNum;
            final int max = getMax(exceptionManager.moduleNumLen);
            for (ExceptionModule exceptionModule : this.modules) {
                int moduleNo = exceptionModule.getNumber();
                if (moduleNo < min || moduleNo > max) {
                    throw new RuntimeException("异常模块编号「" + moduleNo + "」越界，范围 [" + min + ", " + max + "]");
                }
                if (moduleNumberSet.contains(moduleNo)) {
                    throw new RuntimeException("异常模块编号「" + moduleNo + "」已存在");
                }
                moduleNumberSet.add(moduleNo);
            }

            return this;
        }

        /**
         * 校验异常来源是否合法。
         * <ul>
         *     <li>校验异常来源标识不为 null。</li>
         *     <li>校验异常来源标识不重复。</li>
         * </ul>
         *
         * @return {@link Builder}
         * @throws RuntimeException 校验失败抛出异常
         */
        public Builder checkSource() throws RuntimeException {
            final Set<String> sourceLabelSet = new HashSet<>();
            for (ExceptionSource exceptionSource : this.sources) {
                String label = exceptionSource.getLabel();
                if (label == null) {
                    throw new RuntimeException("异常来源标识不能为null");
                }
                if (sourceLabelSet.contains(label)) {
                    throw new RuntimeException("异常来源标识「" + label + "」已存在");
                }
                sourceLabelSet.add(label);
            }

            return this;
        }

        /**
         * 校验异常状态码是否合法。
         * <ul>
         *     <li>校验异常状态码是否重复。</li>
         *      <li>通过 {@link ExceptionModule#getExceptionCodeClazz} 和 {@link ExceptionCode#getModule} 检验模块和异常状态码是否正确双向绑定。</li>
         * </ul>
         *
         * @return {@link Builder}
         * @throws RuntimeException 校验失败抛出异常
         */
        public Builder checkCode() throws RuntimeException {
            Map<Integer, Class<? extends ExceptionCode>> moduleMap = new HashMap<>(this.modules.length);
            // 获取模块绑定的异常状态码枚举的 Class，用于判断是否成功双向绑定
            for (ExceptionModule exceptionModule : this.modules) {
                moduleMap.put(exceptionModule.getNumber(), exceptionModule.getExceptionCodeClazz());
            }

            final int min = exceptionManager.errorMinNum;
            final int max = getMax(exceptionManager.errorNumLen);
            Map<String, ExceptionCode> codeMap = new HashMap<>();
            for (ExceptionModule exceptionModule : this.modules) {
                Class<? extends ExceptionCode> exceptionCodeClazz = exceptionModule.getExceptionCodeClazz();
                if (exceptionCodeClazz == null) {
                    throw new RuntimeException("找不到「" + exceptionModule.getName() + "」绑定的异常状态码枚举类");
                }

                ExceptionCode[] exceptionCodes = moduleMap.get(exceptionModule.getNumber()).getEnumConstants();
                int moduleNo = exceptionModule.getNumber();
                for (ExceptionCode exceptionCode : exceptionCodes) {
                    int codeNo = exceptionCode.getNumber();
                    int codeModuleNo = exceptionCode.getModule().getNumber();
                    String formatCode = exceptionManager.formatCode(exceptionCode);
                    if (codeNo < min || codeNo > max) {
                        throw new RuntimeException("异常状态码「" + formatCode + "」越界，范围 [" + min + ", " + max + "]");
                    }
                    if (codeModuleNo != moduleNo) {
                        throw new RuntimeException("异常状态码「" + formatCode + "」的模块编号与异常模块中注册的模块编号「" + moduleNo + "」不一致");
                    }
                    if (codeMap.containsKey(formatCode)) {
                        throw new RuntimeException("异常错误码「" + formatCode + "」已存在");
                    }
                    codeMap.put(formatCode, exceptionCode);
                }
            }

            return this;
        }

        /**
         * 构建异常模块管理器。
         *
         * @return 异常模块管理器
         */
        public ExceptionManager build() {
            return this.exceptionManager;
        }

        /**
         * 计算指定位数的最大值是多少。
         * <p>例如：4 -> 9999。</p>
         *
         * @param digits 位数
         * @return 指定位数的最大值
         */
        private int getMax(final int digits) {
            return (int) (Math.pow(10, digits) - 1);
        }
    }
}

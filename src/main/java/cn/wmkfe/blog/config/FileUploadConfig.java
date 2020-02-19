package cn.wmkfe.blog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my-file-upload")
public class FileUploadConfig {
//    文件路径前缀
    private static String profile;
//    所属文件夹名称
    private static String path;

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        FileUploadConfig.path = path;
    }

    public static String getProfile() {
        return profile;
    }

    public static void setProfile(String profile) {
        FileUploadConfig.profile = profile;
    }
}

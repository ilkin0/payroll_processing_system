package com.ilkinmehdiyev.payroll_process_system.util;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommonUtil {

    public static List<String> PERMITTED_EXTENSIONS;

    /**
     * Common util method for validate file extension;
     *
     * @return true if extension is permitted, otherwise false.
     */
    public static boolean isFileExtensionOk(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        return PERMITTED_EXTENSIONS.contains(extension);
    }

    @Value("${file.extensions}")
    public void setPermittedExtensions(List<String> extensions) {
        CommonUtil.PERMITTED_EXTENSIONS = extensions;
    }
}

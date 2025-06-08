package com.sdjyyds.user.service;
import com.sdjyyds.user.config.FileUploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {

    @Autowired
    private FileUploadConfig fileUploadConfig;

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // 获取配置中的上传目录
        String uploadDir = fileUploadConfig.getLocation();
        File dest = new File(uploadDir + "/" + fileName);

        // 确保目录存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        // 保存文件
        file.transferTo(dest);

        // 返回相对路径（用于构建URL）
        return fileName;
    }
}

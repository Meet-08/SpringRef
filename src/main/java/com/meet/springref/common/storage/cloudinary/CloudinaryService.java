package com.meet.springref.common.storage.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file, String folderName) throws IOException {
        File convertedFile = convertMultipartFileToFile(file);
        Map uploadResult = cloudinary.uploader().upload(convertedFile,
                ObjectUtils.asMap("folder", folderName)
        );

        // Clean up the temporary file after upload
        convertedFile.delete();
        return uploadResult.get("secure_url").toString();
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }
}

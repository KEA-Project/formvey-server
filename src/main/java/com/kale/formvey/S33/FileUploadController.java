package com.kale.formvey.S33;

import com.kale.formvey.config.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @ResponseBody
    @PostMapping("/upload")
    //public String uploadImage(@RequestPart MultipartFile file) {
    public BaseResponse<String> uploadImage(@RequestPart MultipartFile file) {

        String result = fileUploadService.uploadImage(file);
        return new BaseResponse<>(result);

    }

}
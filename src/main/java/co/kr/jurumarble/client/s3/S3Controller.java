package co.kr.jurumarble.client.s3;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/images")
@Tag(name = "image", description = "S3 업로드 api")
public class S3Controller {

    private final S3Uploader s3Uploader;
    private static final Logger logger = LoggerFactory.getLogger(S3Controller.class);

    @Operation(
            summary = "이미지 파일 업로드",
            description = "MultipartFile 형태의 이미지 파일을 'images'라는 키로 form-data 형태로 전송해주세요. 이 API는 전송된 이미지를 S3에 저장하고, 저장된 이미지의 URL을 반환합니다."
    )
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadImage(@RequestParam("images") MultipartFile multipartFile) {
        try {
            String uploadedUrl = s3Uploader.uploadFiles(multipartFile, "static");
            ImageUploadDto imageUpload = new ImageUploadDto(uploadedUrl, "이미지 업로드에 성공했습니다.");
            return new ResponseEntity(imageUpload, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while uploading image: ", e);
            return new ResponseEntity("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
    }


}

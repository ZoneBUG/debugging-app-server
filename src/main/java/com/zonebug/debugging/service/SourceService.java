package com.zonebug.debugging.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.zonebug.debugging.dto.response.GetPathResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class SourceService{

    @Value("${cloud.aws.s3.bucket}")
    String bucket;

    @Autowired
    AmazonS3 amazonS3;

    public GetPathResponseDTO createURL(Long userId) {
        String fileName = userId + "/" + new Date();

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10; // 10분
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return new GetPathResponseDTO(url);
    }
}

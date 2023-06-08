package com.martins.wordtopdf.service;

import org.springframework.web.multipart.MultipartFile;

public interface ConversionService {
    public byte[] convertToPdf(MultipartFile file);
}

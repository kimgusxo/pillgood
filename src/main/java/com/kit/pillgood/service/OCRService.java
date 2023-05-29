package com.kit.pillgood.service;
import com.kit.pillgood.controller.ModelController;
import io.swagger.annotations.ApiResponse;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.features2d.MSER;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OCRService {

    private final ModelController modelController;

    @Autowired
    public OCRService(ModelController modelController) {
        this.modelController = modelController;
    }

    public void sendImage(MultipartFile image) {
        modelController.sendImage(image);
    }

}
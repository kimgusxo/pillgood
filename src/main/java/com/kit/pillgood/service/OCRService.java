package com.kit.pillgood.service;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class OCRService {
    static {
        OpenCV.loadShared();
    }
    public void extractPrescriptionTextFromImage() {

                imagePreProcess();

                Tesseract tesseract = new Tesseract();
                tesseract.setLanguage("eng+kor");
                tesseract.setDatapath("C:/Users/lenovo/Desktop/Tess4J/tessdata");
                try {
                    String result = tesseract.doOCR(new File("C:/Users/lenovo/Pictures/OCRTest/Blur.png"));
                    System.out.println(result);
                } catch (TesseractException e) {
                    e.printStackTrace();
                }
    }

    public void imagePreProcess() {
        String filePath = "C:/Users/lenovo/Pictures/OCRTest/Original.jpg";

        Mat image = Imgcodecs.imread(filePath);

        // 그레이스케일
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/GrayScale.png", grayImage);

        // 히스토그램 평활화
        Mat equalizedImage = new Mat();
        Imgproc.equalizeHist(grayImage, equalizedImage);

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Hist.png", equalizedImage);

        // 적응적 이진화
        Mat binaryImage1 = new Mat();
        Imgproc.adaptiveThreshold(
                grayImage,                        // 입력 이미지
                binaryImage1,                  // 결과 이미지
                255,                          // 이진화 값 (흰색)
                Imgproc.ADAPTIVE_THRESH_MEAN_C,// 적응적 이진화 방법 (평균)
                Imgproc.THRESH_BINARY,        // 이진화 방법 (흰색/검정색)
                467,                           // 블록 크기 (홀수)
                60                           // 평균값에서 차감할 값
        );

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/AdaptiveBinary.png", binaryImage1);

        // 팽창 모폴로지
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));
        Mat denoisedImage = new Mat();
        Imgproc.morphologyEx(binaryImage1, denoisedImage, Imgproc.MORPH_ERODE, kernel);

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Mopology.png", denoisedImage);

        // 블러
        Mat BlurImage = new Mat();
        Imgproc.GaussianBlur(denoisedImage, BlurImage, new Size(3, 3), 0);

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Blur.png", BlurImage);
    }
}
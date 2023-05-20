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

//        String filePath = "C:/Users/lenovo/Pictures/OCRTest/Original.jpg";
//
//        Mat image = Imgcodecs.imread(filePath);
//
//        Mat grayImage = new Mat();
//
//        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
//
//        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/GrayScale.png", grayImage);
//
//        Mat binaryImage1 = new Mat();
//        Imgproc.adaptiveThreshold(
//                grayImage,                        // 입력 이미지
//                binaryImage1,                  // 결과 이미지
//                255,                          // 이진화 값 (흰색)
//                Imgproc.ADAPTIVE_THRESH_MEAN_C,// 적응적 이진화 방법 (평균)
//                Imgproc.THRESH_BINARY,        // 이진화 방법 (흰색/검정색)
//                467,                           // 블록 크기 (홀수)
//                60                           // 평균값에서 차감할 값
//        );
//
//        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/AdaptiveBinary.png", binaryImage1);
//
//        Mat binaryImage2 = new Mat();
//        Imgproc.threshold(grayImage, binaryImage2, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
//
//        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/GlobalBinary.png", binaryImage2);
//
//        Mat equalizedImage = new Mat();
//        Imgproc.equalizeHist(binaryImage1, equalizedImage);
//
//        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Hist.png", equalizedImage);
//
//        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
//        Mat denoisedImage = new Mat();
//        Imgproc.morphologyEx(equalizedImage, denoisedImage, Imgproc.MORPH_OPEN, kernel);
//
//        // 결과 이미지 저장
//        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Mopology.png", denoisedImage);
//
//        Mat BlurImage = new Mat();
//        Imgproc.GaussianBlur(equalizedImage, BlurImage, new Size(3, 3), 0);
//
//        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Blur.png", BlurImage);

//        double threshold1 = 50;
//        double threshold2 = 150;
//        int apertureSize = 3;
//        boolean L2gradient = false;
//
//        // 케니 엣지 검출 수행
//        Mat edges = new Mat();
//        Imgproc.Canny(denoisedImage, edges, threshold1, threshold2, apertureSize, L2gradient);
//
//        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/result3.png", edges);


                Tesseract tesseract = new Tesseract();
                tesseract.setLanguage("kor");
                tesseract.setDatapath("C:/Users/lenovo/Desktop/Tess4J/tessdata");
                try {
                    String result = tesseract.doOCR(new File("C:/Users/lenovo/Pictures/OCRTest/Test.png"));
                    System.out.println(result);
                } catch (TesseractException e) {
                    e.printStackTrace();
                }
    }
}
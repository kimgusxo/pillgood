package com.kit.pillgood.service;
import net.sourceforge.tess4j.Tesseract;
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


//                Tesseract tesseract = new Tesseract();
//                tesseract.setLanguage("eng+kor");
//                tesseract.setDatapath("C:/Users/lenovo/Desktop/Tess4J/tessdata");
//                try {
//                    String result = tesseract.doOCR(new File("C:/Users/lenovo/Pictures/OCRTest/Blur.png"));
//                    String processedResult = result.trim();
//                    System.out.println(processedResult);
//                } catch (TesseractException e) {
//                    e.printStackTrace();
//                }
    }

    public void imagePreProcess() {
        String filePath = "C:/Users/lenovo/Pictures/OCRTest/Original.jpg";

        Mat image = Imgcodecs.imread(filePath);
        Mat clone = image.clone();

        image = cloneImagePreProcessing(clone);

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
                Imgproc.THRESH_BINARY_INV,        // 이진화 방법 (흰색/검정색)
                1023,                           // 블록 크기 (홀수)
                100                           // 평균값에서 차감할 값
        );

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/AdaptiveBinary.png", binaryImage1);

        // 팽창 모폴로지
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(30, 30));
        Mat denoisedImage = new Mat();
        Imgproc.morphologyEx(binaryImage1, denoisedImage, Imgproc.MORPH_DILATE, kernel);

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Mopology.png", denoisedImage);

        // 컨투어 검출
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(denoisedImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // 텍스트 영역 추출
        List<Rect> textRegions = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            MatOfPoint2f approxCurve = new MatOfPoint2f();
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            double epsilon = 0.02 * Imgproc.arcLength(contour2f, true);
            Imgproc.approxPolyDP(contour2f, approxCurve, epsilon, true);

            if (approxCurve.total() == 4) {
                Rect rect = Imgproc.boundingRect(new MatOfPoint(approxCurve.toArray()));
                textRegions.add(rect);
            }
        }

        // 텍스트 영역 출력
        for (Rect rect : textRegions) {
            Imgproc.rectangle(image, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
        }

        // 결과 이미지 저장
        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/box.png", image);

//        // 캐니엣지 검출
//        Mat edges = new Mat();
//        Imgproc.Canny(denoisedImage, edges, 100, 200);
//
//        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Canny.png", edges);

//        // 블러
//        Mat BlurImage = new Mat();
//        Imgproc.GaussianBlur(denoisedImage, BlurImage, new Size(3, 3), 0);
//
//        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Blur.png", BlurImage);
    }

    public Mat cloneImagePreProcessing(Mat clone) {
        // 그레이스케일
        Mat grayImage = new Mat();
        Imgproc.cvtColor(clone, grayImage, Imgproc.COLOR_BGR2GRAY);

        // 히스토그램 평활화
        Mat equalizedImage = new Mat();
        Imgproc.equalizeHist(grayImage, equalizedImage);

        // 적응적 이진화
        Mat binaryImage1 = new Mat();
        Imgproc.adaptiveThreshold(
                grayImage,                        // 입력 이미지
                binaryImage1,                  // 결과 이미지
                255,                          // 이진화 값 (흰색)
                Imgproc.ADAPTIVE_THRESH_MEAN_C,// 적응적 이진화 방법 (평균)
                Imgproc.THRESH_BINARY_INV,        // 이진화 방법 (흰색/검정색)
                801,                           // 블록 크기 (홀수)
                50                           // 평균값에서 차감할 값
        );

        Mat lines = new Mat();
        Imgproc.HoughLinesP(binaryImage1, lines, 1, Math.PI / 180, 100, 100, 3);

        // 검출된 직선 영역 제거
        for (int i = 0; i < lines.rows(); i++) {
            double[] line = lines.get(i, 0);
            Point startPoint = new Point(line[0], line[1]);
            Point endPoint = new Point(line[2], line[3]);
            Imgproc.line(clone, startPoint, endPoint, new Scalar(255, 255, 255), 2); // 검은색으로 선을 그리기 위해 Scalar 값 변경
        }

        // 결과 이미지 저장
        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Hough.png", clone);

        return clone;
    }
}
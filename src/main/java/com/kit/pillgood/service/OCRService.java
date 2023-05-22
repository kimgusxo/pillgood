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
import java.util.Collections;
import java.util.List;

@Service
public class OCRService {

    static {
        OpenCV.loadShared();
    }

    public void extractPrescriptionTextFromImage() {

        List<Mat> imageList = imagePreProcess();
        StringBuilder sb = new StringBuilder();

        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("eng+kor");
        tesseract.setDatapath("C:/Users/lenovo/Desktop/Tess4J/tessdata");

        try {
            for(int i = imageList.size()-1; i >= 0; i--) {
                // 이미지를 임시 파일로 저장
                File tempFile = File.createTempFile("temp", ".jpg");
                Imgcodecs.imwrite(tempFile.getAbsolutePath(), imageList.get(i));

                // 테서렉트 OCR로 텍스트 추출
                String result = tesseract.doOCR(tempFile);

                result.trim();
                sb.append(result);
            }
        } catch (Exception e) {
            System.err.println("오류 발생: " + e.getMessage());
        }

        System.out.println(sb);
    }

    // 이미지 전처리
    public List<Mat> imagePreProcess() {
        String filePath = "C:/Users/lenovo/Pictures/OCRTest/Original.jpg";

        Mat image = Imgcodecs.imread(filePath);

        image = adjustBrightness(image, 1.5); // 조절할 밝기 계수를 지정 (예: 1.5는 1.5배 밝게 조절)

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Bright.png", image);

        Mat clone = image.clone();

        // 선 제거 로직
        clone = cloneImagePreProcessing(clone);

        // 텍스트박스 좌표 구하는 로직
        List<Rect> textBoxes = createTextBox(clone);

        // 텍스트 영역 출력
        for (Rect rect : textBoxes) {
            Imgproc.rectangle(image, rect.tl(), rect.br(), new Scalar(255, 255, 255), 0);
        }

        // 결과 이미지 저장
        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/box.png", image);

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
                801,                           // 블록 크기 (홀수)
                50                           // 평균값에서 차감할 값
        );

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/AdaptiveBinary.png", binaryImage1);

        // 팽창 모폴로지
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat denoisedImage = new Mat();
        Imgproc.morphologyEx(binaryImage1, denoisedImage, Imgproc.MORPH_ERODE, kernel);

        // 블러
        Mat BlurImage = new Mat();
        Imgproc.GaussianBlur(denoisedImage, BlurImage, new Size(3, 3), 0);

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/Blur.png", BlurImage);

        List<Mat> finalImageList = createImageList(BlurImage, textBoxes);

        int cnt = 0;
        for (Mat finalImage : finalImageList) {
            Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/FinalImage/test" + cnt + ".png", finalImage);
            cnt++;
        }
        return finalImageList;
    }

    // 복사된 이미지로 직선들을 제거하는 함수
    private Mat cloneImagePreProcessing(Mat clone) {
        // 그레이스케일
        Mat cloneGrayImage = new Mat();
        Imgproc.cvtColor(clone, cloneGrayImage, Imgproc.COLOR_BGR2GRAY);

//        // 히스토그램 평활화
//        Mat cloneEqualizedImage = new Mat();
//        Imgproc.equalizeHist(cloneGrayImage, cloneEqualizedImage);

        // 적응적 이진화
        Mat cloneBinaryImage = new Mat();
        Imgproc.adaptiveThreshold(
                cloneGrayImage,            // 입력 이미지
                cloneBinaryImage,                  // 결과 이미지
                255,                          // 이진화 값 (흰색)
                Imgproc.ADAPTIVE_THRESH_MEAN_C,// 적응적 이진화 방법 (평균)
                Imgproc.THRESH_BINARY_INV,        // 이진화 방법 (흰색/검정색)
                1023,                           // 블록 크기 (홀수)
                100                           // 평균값에서 차감할 값
        );

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/CloneBinary.png", cloneBinaryImage);

        // 팽창 모폴로지
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat cloneDenoisedImage = new Mat();
        Imgproc.morphologyEx(cloneBinaryImage, cloneDenoisedImage, Imgproc.MORPH_DILATE, kernel);

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/CloneMopology.png", cloneDenoisedImage);

        Mat lines = new Mat();
        Imgproc.HoughLinesP(cloneDenoisedImage, lines, 1, Math.PI / 180, 100, 100, 3);

        // 검출된 직선 영역 제거
        for (int i = 0; i < lines.rows(); i++) {
            double[] line = lines.get(i, 0);
            Point startPoint = new Point(line[0], line[1]);
            Point endPoint = new Point(line[2], line[3]);
            Imgproc.line(clone, startPoint, endPoint, new Scalar(255, 255, 255), 2); // 검은색으로 선을 그리기 위해 Scalar 값 변경
        }

        // 결과 이미지 저장
        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/CloneHough.png", clone);

        return clone;
    }

    private List<Rect> createTextBox(Mat clone) {
        // 그레이스케일
        Mat testGrayImage = new Mat();
        Imgproc.cvtColor(clone, testGrayImage, Imgproc.COLOR_BGR2GRAY);

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/TestGrayScale.png", testGrayImage);

//        // 히스토그램 평활화
//        Mat testEqualizedImage = new Mat();
//        Imgproc.equalizeHist(testGrayImage, testEqualizedImage);

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/TestHist.png", testGrayImage);

        // 적응적 이진화
        Mat testBinaryImage = new Mat();
        Imgproc.adaptiveThreshold(
                testGrayImage,                // 입력 이미지
                testBinaryImage,                  // 결과 이미지
                255,                          // 이진화 값 (흰색)
                Imgproc.ADAPTIVE_THRESH_MEAN_C,// 적응적 이진화 방법 (평균)
                Imgproc.THRESH_BINARY_INV,        // 이진화 방법 (흰색/검정색)
                1023,                           // 블록 크기 (홀수)
                100                           // 평균값에서 차감할 값
        );

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/TestAdaptiveBinary.png", testBinaryImage);

        // 팽창 모폴로지
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(25, 25));
        Mat testDenoisedImage = new Mat();
        Imgproc.morphologyEx(testBinaryImage, testDenoisedImage, Imgproc.MORPH_DILATE, kernel);

        Imgcodecs.imwrite("C:/Users/lenovo/Pictures/OCRTest/TestMopology.png", testDenoisedImage);

        // 컨투어 검출
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(testDenoisedImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // 텍스트 영역 추출
        List<Rect> textBox = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            MatOfPoint2f approxCurve = new MatOfPoint2f();
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            double epsilon = 0.02 * Imgproc.arcLength(contour2f, true);
            Imgproc.approxPolyDP(contour2f, approxCurve, epsilon, true);

            if (approxCurve.total() >= 4) {
                Rect rect = Imgproc.boundingRect(new MatOfPoint(approxCurve.toArray()));
                if(rect.width > 25 && rect.height > 25) {
                    textBox.add(rect);
                }
            }
        }

        // 겹치는 영역 제거하여 최종박스 리스트 생성
        List<Rect> finalBoxes = removeOverlappingBoxes(textBox);
        return finalBoxes;
    }

    // 겹치는 박스들의 외곽선을 검출하여 최종적으로 박스만 남기는 함수
    private List<Rect> removeOverlappingBoxes(List<Rect> boxes) {
        List<Rect> finalBoxes = new ArrayList<>();
        List<Boolean> mergedFlags = new ArrayList<>(Collections.nCopies(boxes.size(), false));

        // 겹치는 박스들을 병합하여 최종 박스 생성
        for (int i = 0; i < boxes.size(); i++) {
            if (mergedFlags.get(i)) {
                // 이미 병합된 박스는 스킵
                continue;
            }

            Rect currentBox = boxes.get(i);
            Rect mergedBox = new Rect(currentBox.tl(), currentBox.br());

            for (int j = 0; j < boxes.size(); j++) {
                if (i == j) {
                    continue;
                }

                Rect nextBox = boxes.get(j);
                if (isOverlap(currentBox, nextBox)) {
                    mergedBox = mergeBoxes(mergedBox, nextBox);
                    mergedFlags.set(i, true);
                    mergedFlags.set(j, true);
                }
            }

            finalBoxes.add(mergedBox);
        }

        return finalBoxes;
    }

    // 겹치는 박스들의 꼭지점 좌표를 통해 겹치는 영역을 체크하는 함수
    private boolean isOverlap(Rect rect1, Rect rect2) {
        // 사각형1의 꼭지점 좌표
        int rect1TopLeftX = rect1.x;
        int rect1TopLeftY = rect1.y;
        int rect1BottomRightX = rect1.x + rect1.width;
        int rect1BottomRightY = rect1.y + rect1.height;

        // 사각형2의 꼭지점 좌표
        int rect2TopLeftX = rect2.x;
        int rect2TopLeftY = rect2.y;
        int rect2BottomRightX = rect2.x + rect2.width;
        int rect2BottomRightY = rect2.y + rect2.height;

        // 겹치는 영역 체크
        boolean isOverlap = (
                rect1TopLeftX < rect2BottomRightX &&
                        rect1BottomRightX > rect2TopLeftX &&
                        rect1TopLeftY < rect2BottomRightY &&
                        rect1BottomRightY > rect2TopLeftY
        );

        return isOverlap;
    }

    // 박스들을 합치는 함수
    private Rect mergeBoxes(Rect rect1, Rect rect2) {
        Point topLeft = new Point(Math.min(rect1.tl().x, rect2.tl().x), Math.min(rect1.tl().y, rect2.tl().y));
        Point bottomRight = new Point(Math.max(rect1.br().x, rect2.br().x), Math.max(rect1.br().y, rect2.br().y));
        return new Rect(topLeft, bottomRight);
    }

    // 추출된 텍스트 박스를 이미지로 만들어서 리스트에 저장하는 함수
    private List<Mat> createImageList(Mat finalBoxes, List<Rect> textRegions) {
        List<Mat> extractedImages = new ArrayList<>();

        // 각 텍스트 박스에 대해 이미지 저장
        for(Rect rect : textRegions) {

            // width와 height가 작은 박스일 경우에 사진 저장을 하지않음
            if(rect.width > 25 && rect.height > 25) {
                // 텍스트 박스 영역 이미지 추출
                Mat croppedImage = new Mat(finalBoxes, rect);

                // 이미지를 저장하여 리스트에 추가
                extractedImages.add(croppedImage);

            }
        }
        return extractedImages;
    }

    private static Mat adjustBrightness(Mat image, double brightness) {
        Mat adjustedImage = new Mat(image.size(), image.type());

        // 밝기 조절
        image.convertTo(adjustedImage, -1, brightness, 0);

        return adjustedImage;
    }

}
package com.prapps.robot;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class RobotTest {

	public static void main(String[] args) {
		System.getProperties().put("TESSDATA_PREFIX", "/home/pratik/Downloads/Tess4J/tessdata");
        File imageFile = new File("src/test/resources/Screenshot from 2017-03-17 20-14-54.png");
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }

}

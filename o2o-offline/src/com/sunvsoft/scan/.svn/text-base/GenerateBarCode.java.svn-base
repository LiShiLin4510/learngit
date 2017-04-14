package com.sunvsoft.scan;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
 
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.sunvsoft.util.StringUtil;
 
public class GenerateBarCode {
	public static String sn;
    public void GenerateBarCode() {
        try {
            //Create the barcode bean
            Code39Bean bean = new Code39Bean();
             
            final int dpi = 150;
             
            //Configure the barcode generator
            bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar 
                                                             //width exactly one pixel
            bean.setWideFactor(3);
            bean.doQuietZone(false);
            StringUtil.createFolder("D://lmtbarcode");
            //Open output file
            File outputFile = new File("D://lmtbarcode//"+sn+".png");
            OutputStream out = new FileOutputStream(outputFile);
            try {
                //Set up the canvas provider for monochrome JPEG output 
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                        out, "image/png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
             
                //Generate the barcode
                bean.generateBarcode(canvas, sn);
             
                //Signal end of generation
                canvas.finish();
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}
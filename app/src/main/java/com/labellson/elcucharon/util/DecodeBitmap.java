package com.labellson.elcucharon.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class DecodeBitmap {

    /**
     * Calcula inSampleSize, para redimensionar la imagen a un tamaño algo mayor al especificado
     * @param options options de BitmapFactory
     * @param reqWidth Ancho especificado
     * @param reqHeigth Alto especificado
     * @return inSampleSize
     */
    private static int calcularInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeigth){
        final int width = options.outWidth;
        final int heigth = options.outHeight;
        int inSampleSize = 1;

        if(width > reqWidth || heigth > reqHeigth){
//            final int halfWidth = width/2;
//            final int halfHeigth = heigth/2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
//            while((halfWidth/inSampleSize) > reqWidth && (halfHeigth/inSampleSize) > reqHeigth ){
            while((width/inSampleSize) > reqWidth && (heigth/inSampleSize) > reqHeigth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * Decodifica un Bitmap codificado en base64 con el tamaño especificado
     * @param res bitmap en base64
     * @param reqWidth Ancho especificado
     * @param reqHeigth Alto especificado
     * @return bitmap con tamaño ajustado
     */
    public static Bitmap decodeSampledBitmapFromBase64(String res, int reqWidth, int reqHeigth){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = true;
        byte[] byteData = Base64.decode(res, Base64.DEFAULT);
        //BitmapFactory.decodeByteArray(byteData, 0, byteData.length, options);
        options.inSampleSize = calcularInSampleSize(options, reqWidth, reqHeigth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(byteData, 0, byteData.length, options);
    }
}

package com.xuyang.utilcode.util

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
object ImageUtils {

    @JvmStatic
    fun bitmapFromByteArr(data: ByteArray?, newWidth: Int, newHeight: Int): Bitmap? {
        val `is` = ByteArrayInputStream(data)
        val bmOptions = BitmapFactory.Options()
        bmOptions.inSampleSize = 4
        var bitmap = BitmapFactory.decodeStream(`is`, null, bmOptions)

//        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if (bitmap.width > bitmap.height) {
            bitmap = rotate(
                bitmap,
                -90,
                bitmap.width.toFloat(),
                bitmap.height.toFloat()
            )
        }
        bitmap = mirrorConvert(bitmap, 0)
        val width = bitmap.width
        val height = bitmap.height
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    @JvmStatic
    fun resizeImage(bitmap: Bitmap, width: Int, height: Int): Bitmap? {
        val bmpWidth = bitmap.width
        val bmpHeight = bitmap.height
        val scaleWidth = width.toFloat() / bmpWidth
        val scaleHeight = height.toFloat() / bmpHeight
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true)
    }

    @JvmStatic
    fun bitmap2Bytes(bitmap: Bitmap?, format: Bitmap.CompressFormat): ByteArray? {
        if (bitmap == null) return null
        val baos = ByteArrayOutputStream()
        bitmap.compress(format, 100, baos)
        return baos.toByteArray()
    }

    @JvmStatic
    fun bytes2Bitmap(bytes: ByteArray?): Bitmap? {
        return if (bytes == null || bytes.size == 0) null else BitmapFactory.decodeByteArray(
            bytes,
            0,
            bytes.size
        )
    }

    @JvmStatic
    fun drawable2Bitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }
        val bitmap: Bitmap
        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(
                1, 1,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
        } else {
            bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    @JvmStatic
    fun bitmap2Drawable(context: Context, bitmap: Bitmap?): Drawable? {
        return if (bitmap == null) null else BitmapDrawable(context.resources, bitmap)
    }

    @JvmStatic
    fun drawable2Bytes(drawable: Drawable?, format: Bitmap.CompressFormat): ByteArray? {
        return if (drawable == null) null else bitmap2Bytes(
            drawable2Bitmap(
                drawable
            ), format
        )
    }

    @JvmStatic
    fun bytes2Drawable(context: Context, bytes: ByteArray): Drawable? {
        return bitmap2Drawable(
            context,
            bytes2Bitmap(bytes)
        )
    }

    @JvmStatic
    fun getBitmap(context: Context, @DrawableRes resId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, resId)
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable!!.getIntrinsicWidth(),
            drawable.getIntrinsicHeight(),
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight())
        drawable.draw(canvas)
        return bitmap
    }

    @JvmStatic
    fun streamBitmap(context: Context, resId: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        // 5.0(api 20)以下版本，2.3.3 （api 10）以上 版本 才有用，设置为 true 的时候，在系统内存低的时候会将 bitmap 存储在内存的像素数组回收
        // 在你需要重新访问像素数组的时候，BitmapFactory 的 decoder 会重新去 decode出来
        // 即使这个字段能防止 daivik 虚拟机内存溢出，但是严重影响了 UI绘制的性能，所以不建议使用
        options.inInputShareable = true
        options.inPurgeable = true
        // 使用这个方式获得一个 Bitmap 效率要高一点
        val `is` = context.resources.openRawResource(resId)
        return BitmapFactory.decodeStream(`is`, null, options)
    }

    @JvmStatic
    @JvmOverloads
    fun compressByQuality(
        src: Bitmap, @IntRange(from = 0, to = 100) quality: Int,
        recycle: Boolean = false
    ): Bitmap? {
        if (isEmptyBitmap(src)) return null
        val baos = ByteArrayOutputStream()
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos)
        val bytes = baos.toByteArray()
        if (recycle && !src.isRecycled) src.recycle()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    //旋转图片
    @JvmStatic
    fun rotate(src: Bitmap?, degrees: Int, px: Float, py: Float): Bitmap? {
        return src?.let { rotateRecycle(it, degrees, px, py, false) }
    }

    @JvmStatic
    @JvmOverloads
    fun rotateRecycle(src: Bitmap, degrees: Int, px: Float, py: Float, recycle: Boolean = false): Bitmap? {
        if (isEmptyBitmap(src)) return null
        if (degrees == 0) return src
        val matrix = Matrix()
        matrix.setRotate(degrees.toFloat(), px, py)
        val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }

    @JvmStatic
    fun encodeBitmapToBase64(
        bitmap: Bitmap,
        compressFormat: Bitmap.CompressFormat,
        quality: Int
    ): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(compressFormat, quality, baos)
        return Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP)
    }

    @JvmStatic
    fun decodeBase64ToBitmap(input: String): Bitmap {
        val decodedBytes = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    @JvmStatic
    fun isEmptyBitmap(src: Bitmap?): Boolean {
        return src == null || src.width == 0 || src.height == 0
    }

    @JvmStatic
    fun addStrToBitmap(
        bitmap: Bitmap,
        str: String,
        textSize: Float,
        x: Float,
        y: Float,
        color: Int
    ): Bitmap {

        val width = bitmap.width
        val hight = bitmap.height
        val icon = Bitmap.createBitmap(width, hight, Bitmap.Config.RGB_565) //建立一个空的BItMap
        val canvas = Canvas(icon)//初始化画布绘制的图像到icon上

        val photoPaint = Paint() //建立画笔
        photoPaint.isDither = true //获取跟清晰的图像采样
        photoPaint.isFilterBitmap = true//过滤一些

        val src = Rect(0, 0, bitmap.width, bitmap.height)//创建一个指定的新矩形的坐标
        val dst = Rect(0, 0, width, hight)//创建一个指定的新矩形的坐标
        canvas.drawBitmap(bitmap, src, dst, photoPaint)//将photo 缩放或则扩大到 dst使用的填充区photoPaint

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DEV_KERN_TEXT_FLAG)//设置画笔
        textPaint.textSize = textSize//字体大小
        textPaint.typeface = Typeface.DEFAULT_BOLD//采用默认的宽度
        textPaint.color = color//采用的颜色
        //textPaint.setShadowLayer(3f, 1, 1,this.getResources().getColor(android.R.color.background_dark));//影音的设置
        canvas.drawText(str, x, y, textPaint)//绘制上去字，开始未知x,y采用那只笔绘制
        canvas.save()
        canvas.restore()

        return icon
    }

    @JvmStatic
    fun mirrorConvert(srcBitmap: Bitmap, flag: Int): Bitmap {
        //flag: 0 左右翻转，1 上下翻转
        val matrix = Matrix()
        if (flag == 0) {//左右翻转
            matrix.setScale(-1f, 1f)
        }
        if (flag == 1) {//上下翻转
            matrix.setScale(1f, -1f)
        }
        return Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.width, srcBitmap.height, matrix, true)
    }
}
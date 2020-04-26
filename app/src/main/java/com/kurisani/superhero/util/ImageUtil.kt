package com.kurisani.superhero.util

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.bumptech.glide.signature.StringSignature
import java.io.File
import java.io.FileOutputStream

/**
 * ImageUtil class.
 *
 * This class is used to render images across the app.
 */
object ImageUtil {

    private val colorGenerator: ColorGenerator = ColorGenerator.MATERIAL

    /**
     * Load image into [ImageView] via url.
     *
     * @param context The app context which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     */
    fun loadImage(
        context: Context,
        view: ImageView?,
        imageUrl: String
    ) {
        if (Util.isContextInvalid(context)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    /**
     * Load image into [ImageView] via url, and set placeholder resource.
     *
     * @param context The app context which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     * @param placeHolderResId The resource Id for the placeholder image.
     */
    fun loadImage(
        context: Context,
        view: ImageView?,
        imageUrl: String,
        placeHolderResId: Int
    ) {
        if (Util.isContextInvalid(context)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(context)
            .load(imageUrl)
            .placeholder(placeHolderResId)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    /**
     * Load image into [ImageView] via url, and specify expected width and height.
     *
     * @param context The app context which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     * @param width The expected with of the image.
     * @param height The expected height of the image.
     */
    fun loadImage(
        context: Context,
        view: ImageView?,
        imageUrl: String,
        width: Int,
        height: Int
    ) {
        if (Util.isContextInvalid(context)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(width, height)
            .into(view)
    }

    /**
     * Load image into [ImageView] via url, set placeholder resource, and specify expected with and height.
     *
     * @param context The app context which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     * @param placeHolderResId The resource Id for the placeholder image.
     * @param width The expected with of the image.
     * @param height The expected height of the image.
     */
    fun loadImage(
        context: Context,
        view: ImageView?,
        imageUrl: String,
        placeHolderResId: Int,
        width: Int,
        height: Int
    ) {
        if (Util.isContextInvalid(context)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(context)
            .load(imageUrl)
            .placeholder(placeHolderResId)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(width, height)
            .into(view)
    }

    /**
     * Load image into [ImageView] via url.
     *
     * @param activity The activity which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     */
    fun loadImage(
        activity: AppCompatActivity,
        view: ImageView?,
        imageUrl: String
    ) {
        if (Util.isActivityInvalid(activity)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(activity)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    /**
     * Load image into [ImageView] via url, and set placeholder resource.
     *
     * @param activity The activity which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     * @param placeHolderResId The resource Id for the placeholder image.
     */
    fun loadImage(
        activity: AppCompatActivity,
        view: ImageView?,
        imageUrl: String,
        placeHolderResId: Int
    ) {
        if (Util.isActivityInvalid(activity)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(activity)
            .load(imageUrl)
            .placeholder(placeHolderResId)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    /**
     * Load image into [ImageView] via url, and set placeholder resource.
     *
     * @param activity The activity which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     * @param signature The image signature.
     * @param placeHolderResId The resource Id for the placeholder image.
     */
    fun loadImage(
        activity: AppCompatActivity,
        view: ImageView?,
        imageUrl: String,
        signature: String,
        placeHolderResId: Int
    ) {
        if (Util.isActivityInvalid(activity)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(activity)
                .load(imageUrl)
                .signature(StringSignature(signature))
                .into(view)
        } else {
            Glide.with(activity)
                .load(placeHolderResId)
                .signature(StringSignature(signature))
                .into(view)
        }
    }

    /**
     * Load image into [ImageView] via url, and set placeholder resource.
     *
     * @param activity The activity which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     * @param signature The image signature.
     */
    fun loadImage(
        activity: AppCompatActivity,
        view: ImageView?,
        imageUrl: String,
        signature: String
    ) {
        if (Util.isActivityInvalid(activity)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(activity)
            .load(imageUrl)
            .signature(StringSignature(signature))
            .into(view)
    }

    /**
     * Load image into [ImageView] via url, and specify expected with and height.
     *
     * @param activity The activity which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     * @param width The expected with of the image.
     * @param height The expected height of the image.
     */
    fun loadImage(
        activity: AppCompatActivity,
        view: ImageView?,
        imageUrl: String,
        width: Int,
        height: Int
    ) {
        if (Util.isActivityInvalid(activity)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(activity)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(width, height)
            .into(view)
    }

    /**
     * Load image into [ImageView] via url, set placeholder resource, and specify expected with and height.
     *
     * @param activity The activity which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     * @param placeHolderResId The resource Id for the placeholder image.
     * @param width The expected with of the image.
     * @param height The expected height of the image.
     */
    fun loadImage(
        activity: AppCompatActivity,
        view: ImageView?,
        imageUrl: String,
        placeHolderResId: Int,
        width: Int,
        height: Int
    ) {
        if (Util.isActivityInvalid(activity)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(activity)
            .load(imageUrl)
            .placeholder(placeHolderResId)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(width, height)
            .into(view)
    }

    /**
     * Load image into [ImageView] via url.
     *
     * @param fragment The fragment which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     */
    fun loadImage(
        fragment: Fragment,
        view: ImageView?,
        imageUrl: String
    ) {
        if (Util.isFragmentInvalid(fragment)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(fragment.activity)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    /**
     * Load image into [ImageView] via url, and set placeholder resource.
     *
     * @param fragment The fragment which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     * @param placeHolderResId The resource Id for the placeholder image.
     */
    fun loadImage(
        fragment: Fragment,
        view: ImageView?,
        imageUrl: String,
        placeHolderResId: Int
    ) {
        if (Util.isFragmentInvalid(fragment)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(fragment.activity)
            .load(imageUrl)
            .placeholder(placeHolderResId)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    /**
     * Load image into [ImageView] via url, and specify expected with and height.
     *
     * @param fragment The fragment which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     * @param width The expected with of the image.
     * @param height The expected height of the image.
     */
    fun loadImage(
        fragment: Fragment,
        view: ImageView?,
        imageUrl: String,
        width: Int,
        height: Int
    ) {
        if (Util.isFragmentInvalid(fragment)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(fragment.activity)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(width, height)
            .into(view)
    }

    /**
     * Load image into [ImageView] via url, set placeholder resource, and specify expected with and height.
     *
     * @param fragment The fragment which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param imageUrl The image url.
     * @param placeHolderResId The resource Id for the placeholder image.
     * @param width The expected with of the image.
     * @param height The expected height of the image.
     */
    fun loadImage(
        fragment: Fragment,
        view: ImageView?,
        imageUrl: String,
        placeHolderResId: Int,
        width: Int,
        height: Int
    ) {
        if (Util.isFragmentInvalid(fragment)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        Glide.with(fragment.activity)
            .load(imageUrl)
            .placeholder(placeHolderResId)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(width, height)
            .into(view)
    }

    /**
     * Log Gif into [ImageView] via url.
     *
     * @param context The app context which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param gifUrl The Gif url.
     */
    fun loadGif(
        context: Context,
        view: ImageView?,
        gifUrl: String
    ) {
        if (Util.isContextInvalid(context)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        val viewTarget = GlideDrawableImageViewTarget(view)
        Glide.with(context)
            .load(gifUrl)
            .into(viewTarget)
    }

    /**
     * Log Gif into [ImageView] via url.
     *
     * @param context The app context which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param gifResId The Gif resource Id.
     */
    fun loadGif(
        context: Context,
        view: ImageView?,
        gifResId: Int
    ) {
        if (Util.isContextInvalid(context)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        val viewTarget = GlideDrawableImageViewTarget(view)
        Glide.with(context)
            .load(gifResId)
            .into(viewTarget)
    }

    /**
     * Log Gif into [ImageView] via url.
     *
     * @param activity The activity which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param gifUrl The Gif url.
     */
    fun loadGif(
        activity: AppCompatActivity,
        view: ImageView?,
        gifUrl: String
    ) {
        if (Util.isActivityInvalid(activity)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        val viewTarget = GlideDrawableImageViewTarget(view)
        Glide.with(activity)
            .load(gifUrl)
            .into(viewTarget)
    }

    /**
     * Log Gif into [ImageView] via url.
     *
     * @param activity The activity which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param gifResId The Gif resource Id.
     */
    fun loadGif(
        activity: AppCompatActivity,
        view: ImageView?,
        gifResId: Int
    ) {
        if (Util.isActivityInvalid(activity)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        val viewTarget = GlideDrawableImageViewTarget(view)
        Glide.with(activity)
            .load(gifResId)
            .into(viewTarget)
    }

    /**
     * Log Gif into [ImageView] via url.
     *
     * @param fragment The fragment which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param gifUrl The Gif url.
     */
    fun loadGif(
        fragment: Fragment,
        view: ImageView?,
        gifUrl: String
    ) {
        if (Util.isFragmentInvalid(fragment)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        val viewTarget = GlideDrawableImageViewTarget(view)
        Glide.with(fragment.activity)
            .load(gifUrl)
            .into(viewTarget)
    }

    /**
     * Log Gif into [ImageView] via url.
     *
     * @param fragment The fragment which contains the [ImageView].
     * @param view The [ImageView] in which to load the image.
     * @param gifResId The Gif resource Id.
     */
    fun loadGif(
        fragment: Fragment,
        view: ImageView?,
        gifResId: Int
    ) {
        if (Util.isFragmentInvalid(fragment)) {
            return
        }
        if (view == null) {
            return
        }
        Glide.clear(view)
        val viewTarget = GlideDrawableImageViewTarget(view)
        Glide.with(fragment.activity)
            .load(gifResId)
            .into(viewTarget)
    }

    fun saveBitmap(bitmap: Bitmap, dir: String, baseName: String): String? {
        try {
            val sdcard = Environment.getExternalStorageDirectory()
            val pictureDir = File(sdcard, dir)
            pictureDir.mkdirs()
            var f: File? = null
            for (i in 1..199) {
                val name = "$baseName$i.png"
                f = File(pictureDir, name)
                if (!f.exists()) {
                    break
                }
            }
            if (!f!!.exists()) {
                val name = f.absolutePath
                val fos = FileOutputStream(name)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.flush()
                fos.close()
                return name
            }
        } catch (e: Exception) {
        }
        return null
    }

    fun toggleProfileImage(
        activity: AppCompatActivity,
        profileImage: String,
        imageView: ImageView?,
        placeholder: ImageView?,
        username: String
    ) {

        if (!TextUtils.isEmpty(profileImage)) {
            imageView?.visibility = View.VISIBLE
            placeholder?.visibility = View.GONE
            loadImage(activity, imageView, profileImage)
        } else {
            imageView?.visibility = View.GONE
            placeholder?.visibility = View.VISIBLE
            showProfileImageInitialUtil(username, placeholder)
        }
    }

    private fun showProfileImageInitialUtil(username: String, placeholder: ImageView?) {
        val textDrawableInitial = TextDrawable.builder()
            .buildRoundRect(username[0].toString().toUpperCase(), colorGenerator.randomColor, 500)

        placeholder?.setImageDrawable(textDrawableInitial)
    }

    /**
     *  Converts a drawable into a bitmap
     */
    fun getBitmapFromDrawable(context: Context, drawableId: Int): Bitmap {
        return AppCompatResources.getDrawable(context, drawableId)!!.toBitmap()
    }
}
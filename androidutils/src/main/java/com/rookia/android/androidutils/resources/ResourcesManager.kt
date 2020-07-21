package com.rookia.android.androidutils.resources

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesManager @Inject constructor(@ApplicationContext private val context: Context) {

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun getColor(resId: Int): Int {
        return ResourcesCompat.getColor(context.resources, resId, null)
    }

    fun getInteger(resId: Int): Int {
        return context.resources.getInteger(resId)
    }

    fun getResources(): Resources {
        return context.resources
    }

    fun getDpFromSp(dimensionInDp: Int): Int {
        return context.resources.getDimensionPixelSize(dimensionInDp)
    }

    fun getDimension(resId: Int): Int {
        return context.resources.getDimension(resId).toInt()
    }

    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    fun getResourceIdFromName(resourceName: String): Int {
        return context.resources.getIdentifier(resourceName, "id", context.packageName)
    }

    fun getPlural(resId: Int, value: Int, varargs: Int?): String{
        return if(varargs == null){
            context.resources.getQuantityString(resId, value)
        }else {
            context.resources.getQuantityString(resId, value, varargs)
        }
    }

    fun getPlural(resId: Int, value: Int) = getPlural(resId, value, null)
}
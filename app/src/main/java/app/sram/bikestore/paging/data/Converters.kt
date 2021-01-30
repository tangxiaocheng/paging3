package app.sram.bikestore.paging.data

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromImage(image: Image?): String? {
        return image?.photoReference
    }

    @TypeConverter
    fun toImage(url: String?): Image? {
        return url?.let { Image(it) }
    }
}

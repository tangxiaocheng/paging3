package app.sram.bikestore.paging.data

import android.net.Uri
import android.os.Parcelable
import com.adrena.commerce.paging3.BuildConfig
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(val photoReference: String) : Parcelable {
    companion object {
        private const val PATH = BuildConfig.SERVER_URL
    }

    @IgnoredOnParcel
    val medium: Uri =
        Uri.parse(
            "${PATH}maps/api/place/photo?maxheight=200" +
                "&photoreference=$photoReference" +
                "&${BuildConfig.KEY_STRING}=${BuildConfig.MOVIE_KEY}"
        )
}

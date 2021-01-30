package app.sram.bikestore.paging.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(val url: String): Parcelable {
    companion object {
        private const val PATH = "https://image.tmdb.org/t/p"
    }
    @IgnoredOnParcel
    val medium: Uri = Uri.parse("$PATH/w185/$url")
}


package id.elfastudio.rijksmuseum.data.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class CollectionResponse(

	@Json(name="artObjects")
	val artObjects: List<ArtObjectsItem>,

	@Json(name="countFacets")
	val countFacets: CountFacets,

	@Json(name="count")
	val count: Int,

	@Json(name="elapsedMilliseconds")
	val elapsedMilliseconds: Int,

	@Json(name="facets")
	val facets: List<FacetsItem>
)

@Parcelize
data class WebImage(

	@Json(name="offsetPercentageY")
	val offsetPercentageY: Int,

	@Json(name="offsetPercentageX")
	val offsetPercentageX: Int,

	@Json(name="width")
	val width: Int,

	@Json(name="guid")
	val guid: String,

	@Json(name="url")
	val url: String,

	@Json(name="height")
	val height: Int
): Parcelable

data class FacetsItem(

	@Json(name="prettyName")
	val prettyName: Int,

	@Json(name="otherTerms")
	val otherTerms: Int,

	@Json(name="name")
	val name: String,

	@Json(name="facets")
	val facets: List<FacetsItem>,

	@Json(name="value")
	val value: Int,

	@Json(name="key")
	val key: String
)

@Parcelize
data class HeaderImage(

	@Json(name="offsetPercentageY")
	val offsetPercentageY: Int,

	@Json(name="offsetPercentageX")
	val offsetPercentageX: Int,

	@Json(name="width")
	val width: Int,

	@Json(name="guid")
	val guid: String,

	@Json(name="url")
	val url: String,

	@Json(name="height")
	val height: Int
): Parcelable

@Parcelize
data class ArtObjectsItem(

	@Json(name="principalOrFirstMaker")
	val principalOrFirstMaker: String,

	@Json(name="webImage")
	val webImage: WebImage,

	@Json(name="headerImage")
	val headerImage: HeaderImage,

	@Json(name="objectNumber")
	val objectNumber: String,

	@Json(name="productionPlaces")
	val productionPlaces: List<String>,

	@Json(name="links")
	val links: Links,

	@Json(name="hasImage")
	val hasImage: Boolean,

	@Json(name="showImage")
	val showImage: Boolean,

	@Json(name="id")
	val id: String,

	@Json(name="title")
	val title: String,

	@Json(name="longTitle")
	val longTitle: String,

	@Json(name="permitDownload")
	val permitDownload: Boolean
): Parcelable

@Parcelize
data class Links(

	@Json(name="web")
	val web: String,

	@Json(name="self")
	val self: String
): Parcelable

data class CountFacets(

	@Json(name="ondisplay")
	val ondisplay: Int,

	@Json(name="hasimage")
	val hasimage: Int
)

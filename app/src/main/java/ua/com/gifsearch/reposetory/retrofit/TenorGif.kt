package ua.com.gifsearch.reposetory.retrofit



data class TenorGif (

    var next: Int,
    var results: List<GifObject>
)


data class GifObject (
    var created: Float,
    var hasaudio: Boolean,
    var id: String,
    var media: List<FormatMedia>,
    var url: String,
    var tags: List<String>,
    var title: String,
    var itemurl: String,
    var hascaption: Boolean,
    var favorite: Boolean = false
)

data class FormatMedia (var gif : Media)


data class Media (
    var preview: String,
    var url: String,
    var dims: List<Int>,
    var int: Int
)
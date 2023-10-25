//package vn.liam.codebase.base.database.converters
//
//import android.media.Image
//import androidx.room.TypeConverter
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import vn.liam.codebase.base.models.Genres
//import vn.liam.codebase.base.models.MovieModel
//import vn.liam.codebase.base.models.ProductionCompany
//import vn.liam.codebase.base.models.ProductionCountry
//import vn.liam.codebase.base.models.SpokenLanguage
//
//class EntityConverters {
//
//    @TypeConverter
//    fun listMovieInfoFromString(s: String): List<MovieModel> =
//        Gson().fromJson(s, object : TypeToken<List<MovieModel>>() {}.type)
//
//    @TypeConverter
//    fun listMovieInfoToString(movies: List<MovieModel>): String = Gson().toJson(movies)
//
//    @TypeConverter
//    fun movieInfoFromString(s: String): MovieModel = Gson().fromJson(s, object : TypeToken<MovieModel>() {}.type)
//
//    @TypeConverter
//    fun movieInfoToString(movies: MovieModel): String = Gson().toJson(movies)
//
//    @TypeConverter
//    fun productionCompanyFromString(s: String): List<ProductionCompany>? =
//        Gson().fromJson(s, object : TypeToken<List<ProductionCompany>>() {}.type)
//
//    @TypeConverter
//    fun productionCompanyToString(productionCompanies: List<ProductionCompany>?): String =
//        Gson().toJson(productionCompanies)
//
//    @TypeConverter
//    fun productionCountryFromString(s: String): List<ProductionCountry>? =
//        Gson().fromJson(s, object : TypeToken<List<ProductionCountry>>() {}.type)
//
//    @TypeConverter
//    fun productionCountryToString(productionCountries: List<ProductionCountry>?): String =
//        Gson().toJson(productionCountries)
//
//    @TypeConverter
//    fun spokenLangToString(s: String): List<SpokenLanguage>? =
//        Gson().fromJson(s, object : TypeToken<List<SpokenLanguage>>() {}.type)
//
//    @TypeConverter
//    fun spokenLangFromString(spokenLangs: List<SpokenLanguage>?): String =
//        Gson().toJson(spokenLangs)
//
//    @TypeConverter
//    fun genresLangToString(s: String): List<Genres>? =
//        Gson().fromJson(s, object : TypeToken<List<Genres>>() {}.type)
//
//    @TypeConverter
//    fun genresLangFromString(genres: List<Genres>?): String =
//        Gson().toJson(genres)
//
//    @TypeConverter
//    fun imageFromString(s: String): Image = Gson().fromJson(s, object : TypeToken<Image>() {}.type)
//
//    @TypeConverter
//    fun imageToString(image: Image): String = Gson().toJson(image)
//
//
//}
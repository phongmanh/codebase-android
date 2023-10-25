//package vn.liam.codebase.base.database.converters
//
//import androidx.room.TypeConverter
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//
//class DateConverter {
//    @TypeConverter
//    fun dateTimeFromString(s: String?): LocalDate? =
//        s?.let { LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE) }
//
//    @TypeConverter
//    fun dateTimeToString(dateTime: LocalDate?): String? =
//        dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE)
//
//}
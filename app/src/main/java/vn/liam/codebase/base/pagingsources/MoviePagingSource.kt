//package vn.liam.codebase.base.pagingsources
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import retrofit2.HttpException
//import java.io.IOException
//
//class MoviePagingSource(private val movieServices: MovieRepository, private val query: String?) :
//    PagingSource<Int, Any>() {
//
//    private val DEFAULT_PAGE_SIZE = 10
//
//    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
//        try {
//            val pageIndex = params.key ?: 1
//            val response = if (!query.isNullOrBlank()) {
//                movieServices.searchMovies(
//                    query,
//                    pageIndex,
//                    DEFAULT_PAGE_SIZE
//                )
//            } else {
//                movieServices.getTrendingMovies(
//                    pageIndex,
//                    DEFAULT_PAGE_SIZE
//                )
//            }
//
////            if (response is Resource.Error) {
////                return LoadResult.Error(Throwable(response.message))
////            }
//            val prevKey = if (pageIndex == 1) null else pageIndex - 1
//            var nextKey: Int? = null
//            var dataResult: List<Any> = emptyList()
//            response.data?.let {
//                dataResult = it
//                nextKey =
//                    if (it.size < DEFAULT_PAGE_SIZE || it.isEmpty()) null else pageIndex + 1
//            }
//            return LoadResult.Page(
//                data = dataResult,
//                prevKey = prevKey,
//                nextKey = nextKey
//            )
//
//        } catch (e: IOException) {
//            // IOException for network failures.
//            return LoadResult.Error(e)
//        } catch (e: HttpException) {
//            // HttpException for any non-2xx HTTP status codes.
//            return LoadResult.Error(e)
//        }
//    }
//
//
//}
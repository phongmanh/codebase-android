# codebase-android

✅ Add a search field to the top of the screen.
✅ If the search field is empty, please use this endpoint: https://developer.themoviedb.org/reference/trending-movies to get the trending movies for today and create an infinite list (query the next page as it scrolls).
✅ If the search field is empty, please use this endpoint: https://developer.themoviedb.org/reference/trending-movies to get the trending movies for today and create an infinite list (query the next page as it scrolls).
✅ Trending movies should work offline after loading once. Please ensure the queries are cached and use cached ones if needed again. (if the timestamp is still valid).
✅ When the search field text is not empty, please use this endpoint to search for movies: https://developer.themoviedb.org/reference/search-movie (this should only work online, no caching needed).
✅ The list header should be either: Trending movies or Search results.
x Users can get more movie details when clicking on the list item.
Movie detail screen:
✅ Show all movie information on this page obtained using this endpoint: https://developer.themoviedb.org/reference/movie-details (screen layout of preference again using Material3 design/theme.
x Allow any hyperlinks to be opened.
✅ This page should work offline if movie details are opened once before.

✅ Create Unit Tests to make sure caching validation is handled correctly.
x Create a simple UI Test.
✅ Error message handling (offline/online/failed API calls).
✅ Shared animation between list and detail screen.

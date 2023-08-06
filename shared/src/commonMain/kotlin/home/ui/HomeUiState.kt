package homeroot.ui

sealed interface HomeUiState {

    val isLoading: Boolean
    // val errorMessages: List<ErrorMessage>
    // val searchInput: String

    /**
     * There are no posts to render.
     *
     * This could either be because they are still loading or they failed to load, and we are
     * waiting to reload them.
     */
    /*data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiState*/

    /**
     * There are posts to render, as contained in [postsFeed].
     *
     * There is guaranteed to be a [selectedPost], which is one of the posts from [postsFeed].
     */
    /* data class HasPosts(
         val postsFeed: PostsFeed,
         val selectedPost: Post,
         val isArticleOpen: Boolean,
         val favorites: Set<String>,
         override val isLoading: Boolean,
         override val errorMessages: List<ErrorMessage>,
         override val searchInput: String
     ) : HomeUiState*/
}

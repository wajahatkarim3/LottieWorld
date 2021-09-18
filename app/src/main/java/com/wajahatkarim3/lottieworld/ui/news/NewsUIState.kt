package com.wajahatkarim3.lottieworld.ui.news

sealed class NewsUIState

object Loading: NewsUIState()
object ContentState: NewsUIState()
object EmptyState: NewsUIState()
class Error(val message: String): NewsUIState()
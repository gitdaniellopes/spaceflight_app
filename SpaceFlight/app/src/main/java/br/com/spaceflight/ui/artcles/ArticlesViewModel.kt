package br.com.spaceflight.ui.artcles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.domain.ListArticlesUseCase
import br.com.spaceflight.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val listArticlesUseCase: ListArticlesUseCase
) : ViewModel() {

    private val _articles = MutableStateFlow<State<List<Articles>>>(State.Loading)
    val articles = _articles.asStateFlow()


     fun getArticles() = viewModelScope.launch {
        listArticlesUseCase()
            .catch {
                _articles.value = State.Error(it)
            }
            .collect { articles ->
                if (articles.isNullOrEmpty()) {
                    _articles.value = State.Empty
                } else {
                    _articles.value = State.Success(articles)
                }
            }
    }
}
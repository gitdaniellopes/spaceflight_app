package br.com.spaceflight.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.domain.DetailsArticleUseCase
import br.com.spaceflight.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsArticleViewModel @Inject constructor(
    private val detailsArticleUseCase: DetailsArticleUseCase
) : ViewModel() {

    private val _article = MutableStateFlow<State<Articles>>(State.Loading)
    val article = _article.asStateFlow()

    fun getArticleById(id: Int) = viewModelScope.launch {
        detailsArticleUseCase(id)
            .onStart {
                _article.value = State.Loading
            }
            .catch {
                _article.value = State.Error(it)
            }
            .collect { article ->
                _article.value = State.Success(article)
            }

    }
}
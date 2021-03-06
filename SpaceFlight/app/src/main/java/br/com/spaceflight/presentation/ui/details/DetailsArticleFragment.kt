package br.com.spaceflight.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import br.com.spaceflight.core.util.extensions.formatDate
import br.com.spaceflight.core.util.state.NetworkState
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailsArticleFragment : Fragment() {

    private val args: DetailsArticleFragmentArgs by navArgs()
    private val viewModel: DetailsArticleViewModel by viewModels()

    private val binding: FragmentDetailsBinding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleId = args.article
        viewModel.getArticleById(articleId)
        initObserver()
    }

    private fun initObserver() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.article.collect { state ->
                when (state) {
                    is NetworkState.Success -> {
                        progressBarDetails.visibility = View.INVISIBLE
                        onLoadedArticle(state.result)
                    }
                    is NetworkState.Error -> {
                        progressBarDetails.visibility = View.INVISIBLE
                        Toast.makeText(context, state.error, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is NetworkState.Loading -> {
                        progressBarDetails.visibility = View.VISIBLE
                    }
                    is NetworkState.Empty -> {
                        progressBarDetails.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun onLoadedArticle(article: Articles) = with(binding) {
        Glide.with(requireContext()).load(article.imageUrl).into(imageArticleDetails)
        tvTitleArticleDetails.text = article.title
        tvNewsSite.text = article.newsSite
        tvPublishedAtDetails.text = formatDate(article.publishedAt)
        tvSummary.text = article.summary
        tvUpdateAtDetails.text = formatDate(article.updatedAt.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}
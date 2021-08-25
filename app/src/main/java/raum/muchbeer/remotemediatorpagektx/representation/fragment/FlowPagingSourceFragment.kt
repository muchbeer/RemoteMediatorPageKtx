package raum.muchbeer.remotemediatorpagektx.representation.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import raum.muchbeer.remotemediatorpagektx.adapter.PagingAdapter
import raum.muchbeer.remotemediatorpagektx.databinding.FragmentFlowPagingSourceBinding
import raum.muchbeer.remotemediatorpagektx.representation.RemoteViewModel

@AndroidEntryPoint
class FlowPagingSourceFragment : Fragment() {

    private lateinit var binding: FragmentFlowPagingSourceBinding
    private val viewModel : RemoteViewModel by viewModels()
    private lateinit var pagingAdapter : PagingAdapter
     private val linearLayoutThis : LinearLayoutManager by lazy {
         LinearLayoutManager(requireContext())
     }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFlowPagingSourceBinding.inflate(layoutInflater)
        pagingAdapter = PagingAdapter()
        binding.recyclerView.apply {
            layoutManager = linearLayoutThis
            adapter = pagingAdapter
        }
        listOfApis()

        addLoadState()

        return binding.root
    }

    private fun listOfApis() = lifecycleScope.launch {
        viewModel.retrieveApi.collectLatest {
            pagingAdapter.submitData(lifecycle, it)
        }
    }

    private fun addLoadState() {
        pagingAdapter.addLoadStateListener { loadState->

            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            //Please not if it's loadState.source means come from PagingSource
            //if without source means comes from remoteMediator
            val errorState = loadState.source.refresh as? LoadState.Error ?:
            loadState.source.append as? LoadState.Error ?:
            loadState.source.prepend as? LoadState.Error ?:
            loadState.refresh as? LoadState.Error?:
            loadState.append as? LoadState.Error?:
            loadState.prepend as? LoadState.Error

        errorState?.let {
            showSnackBar(it.error.message.toString())
        }
        }
    }
    private fun showSnackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE).apply {

            setBackgroundTint(Color.BLUE)
            setTextColor(Color.WHITE)
            setActionTextColor(Color.WHITE)
            setAction("Close") {dismiss()}
            anchorView = binding.progressBar
        }.show()
    }

}
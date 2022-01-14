package raum.muchbeer.remotemediatorpagektx.representation.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import raum.muchbeer.remotemediatorpagektx.R
import raum.muchbeer.remotemediatorpagektx.adapter.MediatorAdapter
import raum.muchbeer.remotemediatorpagektx.adapter.PagingAdapter
import raum.muchbeer.remotemediatorpagektx.databinding.FragmentFlowPagingSourceBinding
import raum.muchbeer.remotemediatorpagektx.databinding.FragmentFlowRemoteMediatorBinding
import raum.muchbeer.remotemediatorpagektx.representation.MediatorViewModel

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class FlowRemoteMediatorFragment : Fragment() {

    private lateinit var binding : FragmentFlowRemoteMediatorBinding
    private val viewModel : MediatorViewModel by viewModels()
    private lateinit var mediatorAdapter: MediatorAdapter
    private val linearLayoutThis : LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFlowRemoteMediatorBinding.inflate(layoutInflater)

        mediatorAdapter = MediatorAdapter()
        binding.remoteRecyclerView.apply {
            layoutManager = linearLayoutThis
            adapter = mediatorAdapter
        }
        listOfApis()

        addLoadState()
    setHasOptionsMenu(true)
    return binding.root
    }

    private fun listOfApis() = viewLifecycleOwner.lifecycleScope.launch {

     /*   viewModel.retrieveMediatorList().collectLatest {
            mediatorAdapter.submitData(lifecycle, it)
        }*/

        viewModel.searchListLiveData.collectLatest {
            mediatorAdapter.submitData(lifecycle, it)
        }
    }

    private fun addLoadState() {
        mediatorAdapter.addLoadStateListener { loadState->

            binding.remoteProgress.isVisible = loadState.source.refresh is LoadState.Loading
            //Please not if it's loadState.source means come from PagingSource
            //if without source means comes from remoteMediator
            val errorState = loadState.refresh as? LoadState.Error?:
            loadState.append as? LoadState.Error?:
            loadState.prepend as? LoadState.Error

            errorState?.let {
                showSnackBar(it.error.message.toString())
            }
        }
    }
    private fun showSnackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE).apply {

            setBackgroundTint(Color.GREEN)
            setTextColor(Color.WHITE)
            setActionTextColor(Color.WHITE)
            setAction("Close") {dismiss()}
         //   anchorView = binding.remoteProgress
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_item, menu)

        val search = menu.findItem(R.id.searchItems)
     //   val searchView = searchItem.actionView as SearchView
       val searchView = search.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.
        SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
             /*   if (query != null) {
                  //  getItemsFromDb(query)
                }*/
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.setSearchQuery(it)
                }

                return true
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
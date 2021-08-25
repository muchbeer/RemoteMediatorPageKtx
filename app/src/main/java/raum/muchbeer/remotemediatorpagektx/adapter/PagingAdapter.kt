package raum.muchbeer.remotemediatorpagektx.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.remotemediatorpagektx.data.remote.model.PagingModel
import raum.muchbeer.remotemediatorpagektx.databinding.TaskListViewBinding

class PagingAdapter : PagingDataAdapter<PagingModel.DtOPagingModel,
                            PagingAdapter.PagingVH>(diffUtil) {

    override fun onBindViewHolder(holder: PagingVH, position: Int) {
       getItem(position)?.let {
           holder.bindData(it)
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingVH {
        val layoutInflater = LayoutInflater.from(parent.context)
       val binding = TaskListViewBinding.inflate(layoutInflater, parent, false)
        return PagingVH(binding)
    }


    class PagingVH(val binding : TaskListViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(data : PagingModel.DtOPagingModel) {
            Log.d("PageAdapter", "The datas id are: ${data.id}")
            binding.data = data
        }
    }

    companion object diffUtil : DiffUtil.ItemCallback<PagingModel.DtOPagingModel>() {
        override fun areItemsTheSame(
            oldItem: PagingModel.DtOPagingModel,
            newItem: PagingModel.DtOPagingModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PagingModel.DtOPagingModel,
            newItem: PagingModel.DtOPagingModel
        ): Boolean {
return oldItem == newItem       }

    }

}
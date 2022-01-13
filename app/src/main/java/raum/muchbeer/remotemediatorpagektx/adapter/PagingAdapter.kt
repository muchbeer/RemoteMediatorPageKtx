package raum.muchbeer.remotemediatorpagektx.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.remotemediatorpagektx.data.remote.model.DbPagingModel
import raum.muchbeer.remotemediatorpagektx.data.remote.model.PagingModel
import raum.muchbeer.remotemediatorpagektx.databinding.TaskListViewBinding

class PagingAdapter : PagingDataAdapter<DbPagingModel,
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
        fun bindData(data : DbPagingModel) {
            Log.d("PageAdapter", "The datas id are: ${data.id}")
            binding.data = data
        }
    }

    companion object diffUtil : DiffUtil.ItemCallback<DbPagingModel>() {
        override fun areItemsTheSame(
            oldItem: DbPagingModel,
            newItem: DbPagingModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DbPagingModel,
            newItem: DbPagingModel
        ): Boolean {
return oldItem == newItem       }

    }

}
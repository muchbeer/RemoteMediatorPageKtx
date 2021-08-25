package raum.muchbeer.remotemediatorpagektx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.remotemediatorpagektx.data.local.CacheModel
import raum.muchbeer.remotemediatorpagektx.databinding.CacheListViewBinding
import raum.muchbeer.remotemediatorpagektx.databinding.TaskListViewBinding

class MediatorAdapter : PagingDataAdapter<CacheModel, MediatorAdapter.MediatorVH>(pDiffUtil) {


    override fun onBindViewHolder(holder: MediatorVH, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediatorVH {
       val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CacheListViewBinding.inflate(layoutInflater, parent, false)
        return MediatorVH(binding)
    }


    class MediatorVH(val binding : CacheListViewBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bindData(data :  CacheModel) {
                binding.data = data
            }
    }

    companion object pDiffUtil : DiffUtil.ItemCallback<CacheModel>() {
        override fun areItemsTheSame(oldItem: CacheModel, newItem: CacheModel): Boolean {
         return   oldItem.cacheId == newItem.cacheId        }

        override fun areContentsTheSame(oldItem: CacheModel, newItem: CacheModel): Boolean {
        return oldItem == newItem       }
    }


}
package com.projetointegrador.pi_application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetointegrador.pi_application.databinding.ViewPagerItemBinding

//class ViewPagerAdapter(private val context: Context, private val onBoardingItemList: List<OnBoardingItem>) :
//    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
//
//    inner class ViewHolder(private val binding: ViewPagerItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(onBoardingItem: OnBoardingItem, position: Int) {
//            with(binding) {
//                pageTittle.text = onBoardingItem.pageTittle
//                if (position == 0)
//                    pageDescription.setTextAppearance(R.style.fontForFirstViewPageritem)
//                pageDescription.text = HtmlCompat.fromHtml(onBoardingItem.pageDescription, HtmlCompat.FROM_HTML_MODE_LEGACY)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            ViewPagerItemBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(
//            onBoardingItemList[position],
//            position
//        )
//    }
//
//    override fun getItemCount() = onBoardingItemList.size
//
//}
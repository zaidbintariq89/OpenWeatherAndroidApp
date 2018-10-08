package com.framework.openweatherandroidapp.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framework.openweatherandroidapp.R
import com.framework.openweatherandroidapp.databinding.CityRowViewBinding
import com.framework.openweatherandroidapp.repository.db.CityEntity

class CityAdapter(private var cityList: ArrayList<CityEntity>): RecyclerView.Adapter<CityAdapter.BindingViewHolder>() {

    fun setClickListener(callback: View.OnClickListener) {
        mClickListener = callback
    }

    private var mClickListener: View.OnClickListener? = null
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: CityRowViewBinding  = DataBindingUtil.inflate(layoutInflater!!, R.layout.city_row_view, parent, false)

        return BindingViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.setData(cityList[position])
        holder.binding.root.setOnClickListener{
            if (mClickListener != null) {
                it.tag = position
                mClickListener!!.onClick(it)
            }
        }
    }


    class BindingViewHolder(val binding: CityRowViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(cityEntry: CityEntity) {

            binding.cityTxt.text = cityEntry.cityName
            binding.summary.text = cityEntry.summary
            binding.icon.setImageResource(cityEntry.icon)
        }
    }
}
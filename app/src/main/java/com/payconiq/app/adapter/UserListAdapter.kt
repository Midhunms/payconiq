package com.payconiq.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payconiq.app.databinding.UserListAdapterBinding
import com.payconiq.app.network.model.response.UserListResponseModel
import kotlin.collections.ArrayList

class UserListAdapter(
    private val context: Context,
    private var mItems: ArrayList<UserListResponseModel>,
    private val mListener: Listener?
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {


    init {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserListAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(holder){
           viewBinding.profileImage.setImageURI(mItems[position].avatar_url)
           viewBinding.userLoginText.text = mItems[position].login?:"NA"
           viewBinding.userTypeText.text = mItems[position].type?:"NA"
       }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }


    inner class ViewHolder(val viewBinding: UserListAdapterBinding) : RecyclerView.ViewHolder(viewBinding.root),
        View.OnClickListener {


        init {
            viewBinding.root.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            mListener?.onSelectUser(mItems[adapterPosition],viewBinding.profileImage)
        }
    }

    interface Listener {
        fun onSelectUser(item: UserListResponseModel,view:View)
    }




}
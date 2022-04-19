package com.payconiq.app.ui.activity.user.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.payconiq.app.R
import com.payconiq.app.adapter.UserListAdapter
import com.payconiq.app.databinding.ActivityUserListBinding
import com.payconiq.app.network.model.response.SearchUserResponse
import com.payconiq.app.network.model.response.UserListResponseModel
import com.payconiq.app.ui.activity.user.details.UserDetailsActivity
import com.payconiq.app.ui.base.ScopedActivity
import com.payconiq.app.ui.base.UIState
import com.payconiq.app.utils.ActivityUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListActivity : ScopedActivity(), UserListAdapter.Listener {
    private val viewModel:UserListViewModel by viewModels()
    private lateinit var viewBinding:ActivityUserListBinding
    private var mAdapter: UserListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel.processUserListRequest()

        viewBinding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                if (query.isNullOrEmpty()) {
                    viewModel.processUserListRequest()
                } else {
                    viewModel.processSearchUserRequest(viewBinding.editSearch.text.toString())
                }
            }
        })
    }

    override fun bindUI() = launch(Dispatchers.Main) {


        viewModel.getUserResponse.state.observe(this@UserListActivity) { state ->
            when (state) {
                is UIState.Loading -> { // Show Progress

                viewBinding.loader.visibility = View.VISIBLE


                }
                is UIState.Retrying -> { // Show Retry Progress
                    viewBinding.loader.visibility = View.VISIBLE
                }
                is UIState.SwipeRefreshing -> {

                    viewBinding.loader.visibility = View.GONE
                }
                is UIState.Failure -> { // On Failure
                    viewBinding.loader.visibility = View.GONE
                }
                is UIState.Success -> { // On Success
                    val mData = (state.data as ArrayList<UserListResponseModel>)
                    viewBinding.loader.visibility = View.GONE
                    if(mData.isNotEmpty()){
                        viewBinding.emptyText.visibility = View.GONE
                        viewBinding.recyclerView.visibility = View.VISIBLE
                        val mLayoutManager = LinearLayoutManager(this@UserListActivity, LinearLayoutManager.VERTICAL, false)
                        viewBinding.recyclerView.layoutManager = mLayoutManager
                        mAdapter = UserListAdapter(this@UserListActivity,mData,this@UserListActivity)
                        viewBinding.recyclerView.adapter = mAdapter
                    }else{
                        viewBinding.recyclerView.visibility = View.GONE
                        viewBinding.emptyText.visibility = View.VISIBLE
                        viewBinding.emptyText.text = "User not found"
                    }

                    Log.e("Success", "Success")
                }
                else -> {
                    Log.e("Critical", "Unexpected UIState received.")
                }
            }

        }

        viewModel.getSearchUserResponse.state.observe(this@UserListActivity) { state ->
            when (state) {
                is UIState.Loading -> { // Show Progress

                    viewBinding.refreshLayout.isRefreshing  = true


                }
                is UIState.Retrying -> { // Show Retry Progress
                    viewBinding.refreshLayout.isRefreshing  = true
                }

                is UIState.Failure -> { // On Failure
                    viewBinding.refreshLayout.isRefreshing  = false
                }
                is UIState.Success -> { // On Success
                    val mData = (state.data as SearchUserResponse)
                    viewBinding.loader.visibility = View.GONE
                    if(mData.items.isNotEmpty()){
                        viewBinding.emptyText.visibility = View.GONE
                        viewBinding.recyclerView.visibility = View.VISIBLE
                        val mLayoutManager = LinearLayoutManager(this@UserListActivity, LinearLayoutManager.VERTICAL, false)
                        viewBinding.recyclerView.layoutManager = mLayoutManager
                        mAdapter = UserListAdapter(this@UserListActivity,mData.items,this@UserListActivity)
                        viewBinding.recyclerView.adapter = mAdapter
                    }else{
                        viewBinding.emptyText.visibility = View.VISIBLE
                        viewBinding.recyclerView.visibility = View.GONE
                        viewBinding.emptyText.text = "User not found"
                    }
                    viewBinding.refreshLayout.isRefreshing  = false

                    Log.e("Success", "Success")
                }
                else -> {
                    Log.e("Critical", "Unexpected UIState received.")
                }
            }

        }

    }

    override fun onSelectUser(item: UserListResponseModel,view:View) {

        val bundle = Bundle()
        bundle.putString("LOGIN_USER",item.login)
        ActivityUtils.startActivityWithTransition(this,UserDetailsActivity::class.java,view,"sharedElement",bundle,false)
    }
}
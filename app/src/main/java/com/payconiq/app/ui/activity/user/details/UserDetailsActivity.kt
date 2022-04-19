package com.payconiq.app.ui.activity.user.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.payconiq.app.R
import com.payconiq.app.databinding.ActivityUserDetailsBinding
import com.payconiq.app.network.model.response.UserListResponseModel
import com.payconiq.app.ui.base.ScopedActivity
import com.payconiq.app.ui.base.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsActivity : ScopedActivity() {

    private val viewModel: UserDetailsViewModel by viewModels()
    private lateinit var viewBinding: ActivityUserDetailsBinding
    private var loginUserName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        loginUserName = intent?.getStringExtra("LOGIN_USER") ?: "NA"
        viewModel.processUserDetailsRequest(loginUserName)
    }

    override fun bindUI() = launch(Dispatchers.Main) {


        viewModel.getUserResponse.state.observe(this@UserDetailsActivity) { state ->
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
                    val mData = (state.data as UserListResponseModel)
                    viewBinding.loader.visibility = View.GONE
                    viewBinding.profileImage.setImageURI(mData.avatar_url)
                    viewBinding.userNameText.text= getString(R.string.user_name) +mData.name
                    viewBinding.userCompanyText.text= getString(R.string.company_name)+mData.company
                    viewBinding.userBlogText.text= getString(R.string.blog)+mData.blog
                    viewBinding.userLocationText.text= getString(R.string.location)+mData.location
                    viewBinding.userTwitterText.text= getString(R.string.twitter)+mData.twitter_username
                    Log.e("Success", "Success")
                }
                else -> {
                    Log.e("Critical", "Unexpected UIState received.")
                }
            }

        }

    }
}
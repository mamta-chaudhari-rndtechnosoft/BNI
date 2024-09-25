package com.rndtechnosoft.bconn.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rndtechnosoft.bconn.ApiConfig.RetrofitInstance
import com.rndtechnosoft.bconn.Model.MemberVerifiedResponseData
import com.rndtechnosoft.bconn.R
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivityThankYouBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThankYouActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThankYouBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThankYouBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutProgressBar.visibility = View.VISIBLE
        checkMemberVerified()

        binding.swipeRefresh.setOnRefreshListener {

            binding.layoutProgressBar.visibility = View.VISIBLE
            checkMemberVerified()
            binding.swipeRefresh.isRefreshing = false
        }

    }

    private fun checkMemberVerified() {

        val userId = SaveSharedPreference.getInstance(this@ThankYouActivity).getUserId()

        RetrofitInstance.apiInterface.memberVerified(userId!!)
            .enqueue(object : Callback<MemberVerifiedResponseData?> {
                override fun onResponse(
                    call: Call<MemberVerifiedResponseData?>,
                    response: Response<MemberVerifiedResponseData?>
                ) {
                    if (response.isSuccessful) {
                        binding.layoutProgressBar.visibility = View.GONE

                        val memberVerifiedResponseData = response.body()!!
                        val memberData = memberVerifiedResponseData.data

                        val approvedByAdmin = memberData.approvedByAdmin
                        val approveByMember = memberData.approvedByMember

                        SaveSharedPreference.getInstance(this@ThankYouActivity)
                            .saveAdminApproveStatus(approvedByAdmin)
                        SaveSharedPreference.getInstance(this@ThankYouActivity)
                            .saveMemberApproveStatus(approveByMember)

                        if (approvedByAdmin == "approved" && approveByMember == "approved") {
                            //first clear default pending status
                            /*SaveSharedPreference.getInstance(this@ThankYouActivity)
                                .clearMemberApproveStatus()
                            SaveSharedPreference.getInstance(this@ThankYouActivity)
                                .clearAdminApproveStatus()*/
                            // save the approve status


                            val intent: Intent =
                                Intent(this@ThankYouActivity, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()

                        } else if (approvedByAdmin == "pending" && approveByMember == "cancel") {
                            binding.layoutProgressBar.visibility = View.GONE

                            binding.layoutPending.visibility = View.GONE
                            binding.layoutCancel.visibility = View.VISIBLE

                        } else if (approvedByAdmin == "pending" && approveByMember == "pending") {
                            binding.layoutProgressBar.visibility = View.GONE

                            binding.layoutPending.visibility = View.VISIBLE
                            binding.layoutCancel.visibility = View.GONE

                        }


                    } else {
                        binding.layoutProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this@ThankYouActivity,
                            "Response Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MemberVerifiedResponseData?>, t: Throwable) {
                    binding.layoutProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this@ThankYouActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("Api Response", "Error: ${t.localizedMessage}")
                }
            })
    }

}
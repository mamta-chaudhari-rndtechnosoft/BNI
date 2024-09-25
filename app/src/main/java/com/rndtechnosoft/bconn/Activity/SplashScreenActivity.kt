package com.rndtechnosoft.bconn.Activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rndtechnosoft.bconn.Util.SaveSharedPreference
import com.rndtechnosoft.bconn.databinding.ActivitySplashScreenBinding


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val handler: Handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler.postDelayed({

            // startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            // finish()
            val approveByAdmin: String? =
                SaveSharedPreference.getInstance(this@SplashScreenActivity).getAdminApproveStatus()
            val approveByMember: String? =
                SaveSharedPreference.getInstance(this@SplashScreenActivity).getMemberApproveStatus()
            val userId: String? =
                SaveSharedPreference.getInstance(this@SplashScreenActivity).getUserId()

          /*  var intent: Intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()*/

            if (userId == "") {
                val intent: Intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                if (approveByAdmin == "pending" && approveByMember == "pending") {
                    val intent: Intent =
                        Intent(this@SplashScreenActivity, ThankYouActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else if (approveByAdmin == "pending" && approveByMember == "approved") {
                    val intent: Intent =
                        Intent(this@SplashScreenActivity, ThankYouActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else if(approveByAdmin == "pending" && approveByMember == "cancel"){
                    val intent: Intent =
                        Intent(this@SplashScreenActivity, ThankYouActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                else {
                    var intent: Intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }

        }, 2000)


    }
}
package com.ebinumer.biometriclogin.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.ebinumer.biometriclogin.R
import java.util.concurrent.Executor


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val biometricManager = BiometricManager.from(this)



        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {

                toastMsg("You can use the fingerprint sensor to login")

            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                toastMsg("This device doesnot have a fingerprint sensor")

            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                toastMsg("The biometric sensor is currently unavailable")

            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                toastMsg(
                    "Your device doesn't have fingerprint saved,please check your security settings")

            }

            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                toastMsg("BIOMETRIC ERROR SECURITY UPDATE REQUIRED")
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                toastMsg("BIOMETRIC ERROR UNSUPPORTED")
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                toastMsg("BIOMETRIC STATUS UNKNOWN")
            }
        }


        biometricResponse(1500)


    }



    private fun biometricResponse(time:Long) {
        val promptInfo = PromptInfo.Builder().setTitle("Unlock najuzApp")
            .setDescription("Confirm your Screen lock PIN,Pattern or Password ")

            .build()

        val executor: Executor = ContextCompat.getMainExecutor(this)
      val biometricPrompt =   BiometricPrompt(
            this@LoginActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    biometricResponse(1)
                }

                // THIS METHOD IS CALLED WHEN AUTHENTICATION IS SUCCESS
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    mLogin()


                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })

        Handler(Looper.getMainLooper()).postDelayed({
            biometricPrompt.authenticate(promptInfo)
        }, time)
    }

    private fun mLogin() {

        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finish()

    }

//    override fun onBackPressed() {
//
//        biometricResponse(10)
//        super.onBackPressed()
//    }

    fun toastMsg(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}
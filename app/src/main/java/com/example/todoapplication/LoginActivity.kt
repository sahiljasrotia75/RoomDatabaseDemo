package com.example.todoapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setClick()
    }

    private fun setClick() {
        btn_login.setOnClickListener {
            val editor = Constant.getPrefs(application).edit()
            editor.putString(Constant.isLogin, "true")
            editor.apply()
            login()
        }
    }

    private fun login() {
        if (et_userId.text.isEmpty() && et_password.text.isEmpty()) {
            showErrorDialog(getString(R.string.enter_password_email))
        } else if (et_userId.text.isEmpty()) {
            showErrorDialog(getString(R.string.enter_email))
        } else if (!Constant.isValidEmailId(et_userId.text.toString().trim())) {
            showErrorDialog(getString(R.string.enter_valid_email))
        } else if (et_password.text.isEmpty()) {
            showErrorDialog(getString(R.string.enter_password))
        } else {

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showErrorDialog(msg: String?) {
        //error dialog case of failure

        val mDialog = AlertDialog.Builder(this)
        mDialog.setTitle(getString(R.string.alert))
        mDialog.setMessage(msg)
        mDialog.setPositiveButton(
            getString(R.string.ok)
        ) { dialog, which ->
            dialog.cancel()

        }
        mDialog.show()
    }

}
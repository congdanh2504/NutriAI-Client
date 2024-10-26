package com.project.nutriai.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chibatching.kotpref.gsonpref.GsonPref
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivitySplashBinding
import com.project.nutriai.extensions.flow.collectIn
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.ui.login.LoginActivity
import com.project.nutriai.ui.main.MainActivity
import com.project.nutriai.ui.questions.QuestionsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivitySplashBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.getCurrentUser()

        viewModel.isLogin.filterNotNull().collectIn(this) { isLogin ->
            if (!isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }

        viewModel.hasAnsweredSurvey.filterNotNull().collectIn(this) { hasAnsweredSurvey ->
            if (hasAnsweredSurvey) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, QuestionsActivity::class.java))
            }
            finish()
        }
    }
}
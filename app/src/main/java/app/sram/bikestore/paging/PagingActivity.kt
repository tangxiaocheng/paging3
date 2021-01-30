package app.sram.bikestore.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import app.sram.bikestore.paging.di.AppConfig
import com.adrena.commerce.paging3.R
import com.adrena.commerce.paging3.databinding.ActivityMainBinding
import javax.inject.Inject

class PagingActivity : AppCompatActivity() {

    @Inject
    lateinit var appConfig: AppConfig
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        mBinding.bottomNavView.setupWithNavController(findNavController(R.id.fragNavHost))
    }
}

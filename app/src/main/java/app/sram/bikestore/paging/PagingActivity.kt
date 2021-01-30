package app.sram.bikestore.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.adrena.commerce.paging3.R
import com.adrena.commerce.paging3.databinding.ActivityMainBinding

class PagingActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        mBinding.bottomNavView.setupWithNavController(findNavController(R.id.fragNavHost))
    }
}
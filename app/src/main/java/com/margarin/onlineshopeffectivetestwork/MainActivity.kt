package com.margarin.onlineshopeffectivetestwork

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.margarin.onlineshopeffectivetestwork.databinding.ActivityMainBinding
import com.margarin.onlineshopeffectivetestwork.domain.model.AuthState
import com.margarin.onlineshopeffectivetestwork.presentation.CartFragment
import com.margarin.onlineshopeffectivetestwork.presentation.CatalogFragment
import com.margarin.onlineshopeffectivetestwork.presentation.DiscountsFragment
import com.margarin.onlineshopeffectivetestwork.presentation.HomeFragment
import com.margarin.onlineshopeffectivetestwork.presentation.LoginFragment
import com.margarin.onlineshopeffectivetestwork.presentation.ProfileFragment
import com.margarin.onlineshopeffectivetestwork.presentation.ViewModelFactory
import com.margarin.onlineshopeffectivetestwork.utils.replaceFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as ShopApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        onNavigationItemSelectedListener()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.authState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        AuthState.Initial -> {
                        }

                        AuthState.Authorized -> {
                            replaceFragment(HomeFragment())
                            binding.bottomNavigationView.visibility = View.VISIBLE
                        }

                        AuthState.NotAuthorized -> {
                            replaceFragment(LoginFragment())
                            binding.bottomNavigationView.visibility = View.GONE
                        }
                    }
                }
        }
    }

    private fun onNavigationItemSelectedListener() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_menu_home -> {
                    replaceFragment(HomeFragment())
                }

                R.id.bottom_menu_catalog -> {
                    replaceFragment(CatalogFragment())
                }

                R.id.bottom_menu_cart -> {
                    replaceFragment(CartFragment())
                }

                R.id.bottom_menu_discounts -> {
                    replaceFragment(DiscountsFragment())
                }

                R.id.bottom_menu_profile -> {
                    replaceFragment(ProfileFragment())
                }
            }
            true
        }
    }
}
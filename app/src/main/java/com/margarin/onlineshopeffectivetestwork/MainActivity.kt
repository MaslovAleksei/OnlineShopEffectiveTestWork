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
import com.margarin.onlineshopeffectivetestwork.presentation.ViewModelFactory
import com.margarin.onlineshopeffectivetestwork.presentation.catalog.CatalogFragment
import com.margarin.onlineshopeffectivetestwork.presentation.login.LoginFragment
import com.margarin.onlineshopeffectivetestwork.presentation.profile.ProfileFragment
import com.margarin.onlineshopeffectivetestwork.presentation.unusedfragments.CartFragment
import com.margarin.onlineshopeffectivetestwork.presentation.unusedfragments.DiscountsFragment
import com.margarin.onlineshopeffectivetestwork.presentation.unusedfragments.HomeFragment
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
                .collect { authState ->
                    when (authState) {
                        AuthState.Initial -> {
                        }

                        AuthState.Authorized -> {
                            viewModel.justNowLogged.observe(this@MainActivity){ boolean ->
                                if (boolean) {
                                    replaceFragment(HomeFragment())
                                } else {
                                    binding.bottomNavigationView.selectedItemId =
                                        R.id.bottom_menu_catalog
                                    replaceFragment(CatalogFragment())
                                }
                            }
                            binding.bottomNavigationView.visibility = View.VISIBLE
                        }

                        AuthState.NotAuthorized -> {
                            viewModel.changeJustNowLoggedState(true)
                            replaceFragment(LoginFragment())
                            binding.bottomNavigationView.visibility = View.GONE
                        }
                    }
                }
        }
    }

    private fun onNavigationItemSelectedListener() {
        var currentPageId = -1
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            if (currentPageId == item.itemId) {
                return@setOnItemSelectedListener false
            } else {
                when (item.itemId) {
                    R.id.bottom_menu_home -> {
                        currentPageId = item.itemId
                        replaceFragment(HomeFragment())
                    }

                    R.id.bottom_menu_catalog -> {
                        currentPageId = item.itemId
                        replaceFragment(CatalogFragment())
                    }

                    R.id.bottom_menu_cart -> {
                        currentPageId = item.itemId
                        replaceFragment(CartFragment())
                    }

                    R.id.bottom_menu_discounts -> {
                        currentPageId = item.itemId
                        replaceFragment(DiscountsFragment())
                    }

                    R.id.bottom_menu_profile -> {
                        currentPageId = item.itemId
                        replaceFragment(ProfileFragment())
                    }
                }
                true
            }
        }
        binding.bottomNavigationView.menu.findItem(binding.bottomNavigationView.selectedItemId)
    }
}
//package com.example.e2e4.screens.home
//
//import androidx.lifecycle.ViewModel
//import com.example.e2e4.domain.repository.PlayerRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//@HiltViewModel
//class HomeViewModel @Inject constructor(
//    private val repository: PlayerRepository
//) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {
//
//    override val container = container<HomeState, HomeSideEffect>(HomeState())
//
//    fun onIntent(intent: HomeIntent) = when (intent) {
//        is HomeIntent.EnterName -> updateName(intent.name)
//        is HomeIntent.FetchStats -> fetchStats()
//    }
//
//    private fun updateName(name: String) = intent {
//        reduce { state.copy(name = name, showStats = false) }
//    }
//
//    private fun fetchStats() = intent {
//        val player = repository.getOrCreatePlayer(state.name)
//        reduce {
//            state.copy(
//                wins = player.wins,
//                losses = player.losses,
//                showStats = true
//            )
//        }
//    }
//}

package ir.baky.mvp_noteapp.utils.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ir.baky.mvp_noteapp.ui.main.MainContracts

@Module
@InstallIn(ActivityComponent::class)
class ContractsViewModuleAC {

    @Provides
    fun mainView(activity: Activity): MainContracts.View {
        return activity as MainContracts.View
    }
}
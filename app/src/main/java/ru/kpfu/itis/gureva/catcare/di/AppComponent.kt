package ru.kpfu.itis.gureva.catcare.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.kpfu.itis.gureva.catcare.data.di.DataBinderModule
import ru.kpfu.itis.gureva.catcare.data.di.NetworkModule
import ru.kpfu.itis.gureva.catcare.data.di.DataModule
import ru.kpfu.itis.gureva.catcare.presentation.MainActivity
import ru.kpfu.itis.gureva.catcare.presentation.di.ViewModelBinderModule
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.diary.DiaryFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.diary.adding.DiaryAddingFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.helpful.CatFactFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.helpful.HelpfulFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.medicine.MedicineFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.pets.MyPetsFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileEditingFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileEditingViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.unusual.UnusualBehaviourFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.unusual.UnusualBehaviourViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.unusual.adding.BehaviourAddingViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.vaccination.VaccinationViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.vaccination.adding.VaccinationAddingViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.medicine.MedicineViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.medicine.adding.MedicineAddingViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.registration.WelcomeViewPagerFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.treatment.TreatmentFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.treatment.TreatmentViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.treatment.adding.TreatmentAddingViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.vaccination.VaccinationFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.vet.VetFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.vet.VetViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.vet.adding.VetAddingViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.weight.WeightControlFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.weight.adding.WeightAddingViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.weight.WeightControlViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    DataBinderModule::class,
    ViewModelBinderModule::class,
    DataModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun getPetProfileViewModel(): PetProfileViewModel.Factory
    fun getPetProfileEditingViewModel(): PetProfileEditingViewModel.Factory
    fun getWeightControlViewModel(): WeightControlViewModel.Factory
    fun getWeightAddingViewModel(): WeightAddingViewModel.Factory
    fun getUnusualBehaviourViewModel(): UnusualBehaviourViewModel.Factory
    fun getBehaviourAddingViewModel(): BehaviourAddingViewModel.Factory
    fun getVaccinationViewModel(): VaccinationViewModel.Factory
    fun getVaccinationAddingViewModel(): VaccinationAddingViewModel.Factory
    fun getMedicineViewModel(): MedicineViewModel.Factory
    fun getMedicineAddingViewModel(): MedicineAddingViewModel.Factory
    fun getTreatmentViewModel(): TreatmentViewModel.Factory
    fun getTreatmentAddingViewModel(): TreatmentAddingViewModel.Factory
    fun getVetViewModel(): VetViewModel.Factory
    fun getVetAddingViewModel(): VetAddingViewModel.Factory

    fun inject(activity: MainActivity)
    fun inject(fragment: HelpfulFragment)
    fun inject(fragment: CatFactFragment)
    fun inject(fragment: PetProfileEditingFragment)
    fun inject(fragment: MyPetsFragment)
    fun inject(fragment: BaseFragment)
    fun inject(fragment: DiaryFragment)
    fun inject(fragment: DiaryAddingFragment)
    fun inject(fragment: WelcomeViewPagerFragment)
}


//package com.alexllanas.jefitproject.di;
//
//import android.app.Application;
//
//import com.alexllanas.jefitproject.ui.MainApplication;
//
//import javax.inject.Singleton;
//
//import dagger.BindsInstance;
//import dagger.Component;
//import dagger.android.AndroidInjector;
//import dagger.android.support.AndroidSupportInjectionModule;
//
//@Singleton
//@Component(
//        modules = {
//                AppModule.class,
//                ViewModelFactoryModule.class,
//                MainViewModelModule.class,
//                ActivityBuildersModule.class
//        }
//)
//public interface AppComponent extends AndroidInjector<MainApplication> {
//
//    @Component.Builder
//    interface Builder {
//
//        @BindsInstance
//        Builder application(Application application);
//
//        AppComponent build();
//    }
//}

package com.example.mychatappclone.di

import android.content.Context
import androidx.compose.ui.tooling.preview.Preview
import com.example.mychatappclone.data.remote.BackForAppRepository
import com.example.mychatappclone.data.remote.FireStoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

  @Singleton
  @Provides
  fun getFireStoreInstance(): FirebaseFirestore{
      return FirebaseFirestore.getInstance()
  }

    @Singleton
    @Provides
    fun fireAuthInstance(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseRep(fireStore : FirebaseFirestore, firebaseAuth : FirebaseAuth,
                           fireStorage : FirebaseStorage, @ApplicationContext context : Context): FireStoreRepository{
        return FireStoreRepository(fireStore, firebaseAuth,fireStorage,context)
    }

    @Singleton
    @Provides
    fun fireBaseStorageInstance(): FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun backForAppRepoInstance(): BackForAppRepository{
        return BackForAppRepository()
    }


}
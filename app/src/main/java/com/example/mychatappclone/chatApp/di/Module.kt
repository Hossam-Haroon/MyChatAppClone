package com.example.mychatappclone.chatApp.di

import android.content.Context
import com.example.mychatappclone.chatApp.data.repository.FireStoreRepositoryImpl
import com.example.mychatappclone.chatApp.domain.firestoreRepository.FireStoreRepository
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
                            @ApplicationContext context : Context): FireStoreRepository {
        return FireStoreRepositoryImpl(fireStore, firebaseAuth,context)
    }

    @Singleton
    @Provides
    fun fireBaseStorageInstance(): FirebaseStorage{
        return FirebaseStorage.getInstance()
    }




}
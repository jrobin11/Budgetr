package com.example.budgetr

import com.example.budgetr.models.Category
import com.example.budgetr.models.Expense
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

/**
 * Configuration for the Firebase database that defines the data schema.
 */
val config = RealmConfiguration.create(schema = setOf(Expense::class, Category::class))

/**
 * The Firebase database instance for managing data persistence.
 */
val db: Realm = Realm.open(config)
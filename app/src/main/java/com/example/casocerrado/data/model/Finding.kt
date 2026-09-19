package com.example.casocerrado.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


  /*  @Entity(
        tableName= "findings",
        foreignKeys =[
            ForeignKey(
                entity= Case::class,
                parentColumns = ["id"]
                childColumns = ["caseId"],
                onDelete = ForeignKey.CASCADE
            )
        ],
        indices =[Index(caseId)]
    )
    */
data class Finding (
    @PrimaryKey(autoGenerate=true)
    val id: Long=0,
    val caseId: Long,
    val title: String,
    val description: String,
    val date: Long = System.currentTimeMillis()
)

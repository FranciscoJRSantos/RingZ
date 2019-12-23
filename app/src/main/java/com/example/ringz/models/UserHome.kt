package com.example.ringz.models

class UserHome(userId: Int, homeId: Int) {
   private var userId: Int? = null
   private var homeId: Int? = null

   init {
      this.userId = userId
      this.homeId = homeId
   }
}
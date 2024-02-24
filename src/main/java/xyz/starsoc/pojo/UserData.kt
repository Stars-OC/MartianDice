package xyz.starsoc.pojo

import kotlinx.serialization.Serializable

@Serializable
class UserData {
    var userId: Long? = null
    var playedTimes : Long = 0
    var wonTimes : Long = 0
}
package com.proyecto404.socialnetwork.core.domain

import com.proyecto404.socialnetwork.core.domain.user.UserId

object UserExamples {
    val usernames = TestExamples("@alice", "@bob", "@charlie", "@david")
    object alice {
        val id: UserId = UserId(1)
        val userName = "@alice"
        val password = "1234"
    }
    object bob {
        val id: UserId = UserId(2)
        val userName = "@bob"
        val password = "1234"
    }
}

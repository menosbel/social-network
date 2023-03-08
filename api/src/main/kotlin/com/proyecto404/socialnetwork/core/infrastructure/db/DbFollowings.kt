package com.proyecto404.socialnetwork.core.infrastructure.db

import com.proyecto404.socialnetwork.core.domain.following.Following
import com.proyecto404.socialnetwork.core.domain.following.Followings
import com.proyecto404.socialnetwork.core.domain.user.UserId
import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.tables.Followings.FOLLOWINGS
import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.tables.records.FollowingsRecord
import org.jooq.impl.DSL


class DbFollowings(private val credentials: Credentials): Followings {
    private fun context() = DSL.using(credentials.url, credentials.user, credentials.password)

    override fun findByUser(userId: UserId): List<Following> {
        val record = context().selectFrom(FOLLOWINGS)
            .where(FOLLOWINGS.USER_ID.eq(userId.toInt()))
            .fetch()
        return record.map {
            fromRecord(it)
        }.toList()
    }

    private fun fromRecord(record: FollowingsRecord) =
        Following.fromSnapshot(
            Following.Snapshot(
                record.userId,
                record.followedId
            )
        )

    override fun add(following: Following) {
        val snapshot = following.snapshot()
        context().insertInto(FOLLOWINGS, FOLLOWINGS.USER_ID, FOLLOWINGS.FOLLOWED_ID)
            .values(snapshot.userId, snapshot.followedId)
            .execute()
    }
}
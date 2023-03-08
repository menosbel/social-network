package com.proyecto404.socialnetwork.core.infrastructure.db

import com.proyecto404.socialnetwork.core.domain.user.User
import com.proyecto404.socialnetwork.core.domain.user.User.Snapshot
import com.proyecto404.socialnetwork.core.domain.user.UserId
import com.proyecto404.socialnetwork.core.domain.user.Users
import com.proyecto404.socialnetwork.core.infrastructure.Environment
import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.Sequences.USERS_ID_SEQ
import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.tables.Users.USERS
import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.tables.records.UsersRecord
import org.jooq.impl.DSL

class DbUsers(private val credentials: Credentials): Users {
    override fun nextId() = UserId(context().nextval(USERS_ID_SEQ))

    private fun context() = DSL.using(credentials.url, credentials.user, credentials.password)

    override fun get(id: UserId): User {
        val record = context().selectFrom(USERS)
            .where(USERS.ID.eq(id.toInt()))
            .fetchOne()!!
        return fromRecord(record)
    }

    override fun get(userName: String): User {
        val record = context().selectFrom(USERS)
            .where(USERS.USERNAME.eq(userName))
            .fetchOne()!!
        return fromRecord(record)
    }

    private fun fromRecord(record: UsersRecord) =
        User.fromSnapshot(Snapshot(
            record.id,
            record.username,
            record.password,
            record.sessionToken,
        ))

    override fun add(user: User) {
        val snapshot = user.snapshot()
        context().insertInto(USERS, USERS.ID, USERS.USERNAME, USERS.PASSWORD, USERS.SESSION_TOKEN)
            .values(snapshot.id, snapshot.userName, snapshot.password, snapshot.sessionToken)
            .execute()
    }

    override fun update(user: User) {
        val snapshot = user.snapshot()
        context().update(USERS)
            .set(USERS.USERNAME, snapshot.userName)
            .set(USERS.SESSION_TOKEN, snapshot.sessionToken)
            .where(USERS.ID.eq(snapshot.id))
            .execute()
    }

    override fun findBySessionToken(sessionToken: String): User? {
        val record = context().selectFrom(USERS)
            .where(USERS.SESSION_TOKEN.eq(sessionToken))
            .limit(1)
            .fetchOne() ?: return null
        return fromRecord(record)
    }
}

package com.proyecto404.socialnetwork.core.infrastructure.db

import com.proyecto404.socialnetwork.core.domain.post.Post
import com.proyecto404.socialnetwork.core.domain.post.PostId
import com.proyecto404.socialnetwork.core.domain.post.Posts
import com.proyecto404.socialnetwork.core.domain.user.UserId
import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.Sequences
import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.tables.Posts.POSTS
import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.tables.records.PostsRecord
import org.jooq.impl.DSL

class DbPosts(private val credentials: Credentials): Posts {
    private fun context() = DSL.using(credentials.url, credentials.user, credentials.password)

    override fun nextId() = PostId(context().nextval(Sequences.POSTS_ID_SEQ))

    override fun get(id: PostId): Post {
        val record = context().selectFrom(POSTS)
            .where(POSTS.ID.eq(id.toInt()))
            .fetchOne()!!
        return fromRecord(record)
    }

    private fun fromRecord(record: PostsRecord) =
        Post.fromSnapshot(
            Post.Snapshot(
                record.id,
                record.message,
                record.userid,
                record.createdat
            )
        )

    override fun findByUserId(userId: UserId): List<Post> {
        val record = context().selectFrom(POSTS)
            .where(POSTS.USERID.eq(userId.toInt()))
            .fetch()
        return record.map { fromRecord(it) }.toList()
    }

    override fun add(post: Post) {
        val snapshot = post.snapshot()
        context().insertInto(POSTS, POSTS.ID, POSTS.MESSAGE, POSTS.USERID, POSTS.CREATEDAT)
            .values(snapshot.id, snapshot.message, snapshot.userId, snapshot.createdAt)
            .execute()
    }
}
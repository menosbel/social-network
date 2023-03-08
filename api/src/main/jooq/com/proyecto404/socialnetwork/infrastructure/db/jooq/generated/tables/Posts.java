/*
 * This file is generated by jOOQ.
 */
package com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.tables;


import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.Keys;
import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.Public;
import com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.tables.records.PostsRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Posts extends TableImpl<PostsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.posts</code>
     */
    public static final Posts POSTS = new Posts();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PostsRecord> getRecordType() {
        return PostsRecord.class;
    }

    /**
     * The column <code>public.posts.id</code>.
     */
    public final TableField<PostsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.posts.message</code>.
     */
    public final TableField<PostsRecord, String> MESSAGE = createField(DSL.name("message"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.posts.userid</code>.
     */
    public final TableField<PostsRecord, Integer> USERID = createField(DSL.name("userid"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.posts.createdat</code>.
     */
    public final TableField<PostsRecord, String> CREATEDAT = createField(DSL.name("createdat"), SQLDataType.VARCHAR(300), this, "");

    private Posts(Name alias, Table<PostsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Posts(Name alias, Table<PostsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.posts</code> table reference
     */
    public Posts(String alias) {
        this(DSL.name(alias), POSTS);
    }

    /**
     * Create an aliased <code>public.posts</code> table reference
     */
    public Posts(Name alias) {
        this(alias, POSTS);
    }

    /**
     * Create a <code>public.posts</code> table reference
     */
    public Posts() {
        this(DSL.name("posts"), null);
    }

    public <O extends Record> Posts(Table<O> child, ForeignKey<O, PostsRecord> key) {
        super(child, key, POSTS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<PostsRecord, Integer> getIdentity() {
        return (Identity<PostsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<PostsRecord> getPrimaryKey() {
        return Keys.POSTS_PKEY;
    }

    @Override
    public List<UniqueKey<PostsRecord>> getKeys() {
        return Arrays.<UniqueKey<PostsRecord>>asList(Keys.POSTS_PKEY);
    }

    @Override
    public Posts as(String alias) {
        return new Posts(DSL.name(alias), this);
    }

    @Override
    public Posts as(Name alias) {
        return new Posts(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Posts rename(String name) {
        return new Posts(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Posts rename(Name name) {
        return new Posts(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, String, Integer, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}

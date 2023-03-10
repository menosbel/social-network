/*
 * This file is generated by jOOQ.
 */
package com.proyecto404.socialnetwork.infrastructure.db.jooq.generated.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Posts implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer id;
    private final String  message;
    private final Integer userid;
    private final String  createdat;

    public Posts(Posts value) {
        this.id = value.id;
        this.message = value.message;
        this.userid = value.userid;
        this.createdat = value.createdat;
    }

    public Posts(
        Integer id,
        String  message,
        Integer userid,
        String  createdat
    ) {
        this.id = id;
        this.message = message;
        this.userid = userid;
        this.createdat = createdat;
    }

    /**
     * Getter for <code>public.posts.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Getter for <code>public.posts.message</code>.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Getter for <code>public.posts.userid</code>.
     */
    public Integer getUserid() {
        return this.userid;
    }

    /**
     * Getter for <code>public.posts.createdat</code>.
     */
    public String getCreatedat() {
        return this.createdat;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Posts (");

        sb.append(id);
        sb.append(", ").append(message);
        sb.append(", ").append(userid);
        sb.append(", ").append(createdat);

        sb.append(")");
        return sb.toString();
    }
}
